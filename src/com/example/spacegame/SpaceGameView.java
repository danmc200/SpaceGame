package com.example.spacegame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.spacegame.Calculators.FrameCalculator;
import com.example.spacegame.Calculators.FrameListener;
import com.example.spacegame.Calculators.LaserGenerator;
import com.example.spacegame.Calculators.SpriteManager;
import com.example.spacegame.sprites.Laser;
import com.example.spacegame.sprites.PlayerSpaceship;
import com.example.spacegame.sprites.Sprite;
import com.example.spacegame.util.PaintUtil;
import com.example.spacegame.util.SpaceGameConstants;
import com.example.spacegame.util.SpriteEnum;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SpaceGameView extends View 
implements FrameListener
{
	private boolean fingerPressed = false;
	private int 
		playerScore = 0,
		waveCount = 0,
		waveEnd = 0,
		framesCalc = 0,
		framesDraw = 0,
		calcCount = 0;
	private FrameCalculator drawCalc = new FrameCalculator(
			this, SpaceGameConstants.FPS_CALCULATOR);
	private Paint playerPaint;
	private MediaPlayer laserPlayer = new MediaPlayer();
	private final List<Sprite> 
		playerLasers = new ArrayList<Sprite>(),
	 	enemyLasers = new ArrayList<Sprite>(),
	 	enemyships = new ArrayList<Sprite>();
    private final List<List<Sprite>> allSprites = new ArrayList<List<Sprite>>();
	private PlayerSpaceship player; 
	private Waves waves;

	public SpaceGameView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		SpriteEnum.initAllResources(getResources());
		initWave();
		player = new PlayerSpaceship(
				SpaceGameConstants.SCREEN_WIDTH / 2, SpaceGameConstants.SCREEN_HEIGHT - 250);
		playerPaint = PaintUtil.getPlayerPaint();
		setBackgroundColor(SpaceGameConstants.BACKGOUND_COLOR);
		allSprites.add(playerLasers);
		allSprites.add(enemyLasers);
		allSprites.add(enemyships);
		try {
			AssetFileDescriptor afd = getContext().getAssets().openFd("laser.mp3");
			laserPlayer.setDataSource(
					afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			laserPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		startDrawCycle();
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		//draw info
		canvas.drawText("Score: " + String.valueOf(playerScore), 5, 15, playerPaint);
		String healthBar = getHealthBar(player);
		canvas.drawText("Health: " + healthBar, 5, 40, playerPaint);
		canvas.drawText(String.valueOf(framesCalc) + ", " + String.valueOf(framesDraw), 5, 65, playerPaint);

		//draw sprites
		Point playerPos = player.getPosition();
		canvas.drawBitmap(player.getImage(), playerPos.x, playerPos.y, playerPaint);
		for(List<Sprite> ss : allSprites)
		{
			synchronized (ss)
			{
				for(Sprite s : ss)
				{
					Point pos = s.getPosition();
					canvas.drawBitmap(s.getImage(), pos.x, pos.y, playerPaint);
				}
			}
		}
		drawCalc.incFrame();
	}
	
	private void initWave()
	{
		waveCount = 0;
		calcCount = 0;
		waves = new Waves();
		waveEnd = waves.getWave(waveCount).getWaveEndCount();
	}
	
	private String getHealthBar(PlayerSpaceship player)
	{
		int totalHitpoints = PlayerSpaceship.TOTAL_HIT_POINTS / 100;
		int numOfHealth = player.getHitPoints() / 100;
		int spaces = totalHitpoints - numOfHealth;
		String healthBar = "[";
		for (int i = 0; i < numOfHealth; i++)
		{
			healthBar += "=";
		}
		for(int i = 0; i < spaces; i++)
		{
			healthBar += "_";
		}
		healthBar += "]";

		return healthBar;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		float eventX = event.getX();
		float eventY = event.getY();
		
		if(eventX >= 0 && eventX <= 100 &&
				eventY >= 0 && eventY <= 100)
		{
			initWave();
		}
		else 
		{
			player.setPosition((int)eventX, (int)eventY);
		}

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			fingerPressed = true;
			break;
		case MotionEvent.ACTION_UP:
			fingerPressed = false;
			break;
		}
		return true;
	}
	
	@Override
	protected void drawableStateChanged()
	{
		(new Handler (Looper.getMainLooper ())).post (new Runnable () 
        {
            @Override
            public void run () 
            {
				invalidate();
            }
        });
	}
	
	public void startDrawCycle() 
	{
		Runnable task = new Runnable() 
		{
			@Override
			public void run() 
			{
				calcCount = 0;
				long timePrev = new Date().getTime();
				FrameCalculator posCalc = new FrameCalculator(
						SpaceGameView.this, SpaceGameConstants.POSITION_CALCULATOR);
				posCalc.startTimer();
				drawCalc.startTimer();
				while(true)
				{
					long timeNow = new Date().getTime();
					long timeToSleep = SpaceGameConstants.DRAW_INTERVAL - (timeNow - timePrev);
					timePrev = new Date().getTime();
					try 
					{
						if(timeToSleep > 0)
						{
							Thread.sleep(timeToSleep);
						}
						calculatePositions(calcCount);
						refreshDrawableState();
						if(calcCount >= waveEnd && enemyships.isEmpty())
						{
							if(waveCount + 1 < waves.getWaveCount())
							{
								waveEnd = waves.getWave(waveCount).getWaveEndCount();
								waveCount++;
							}
							calcCount = 0;
						}
						calcCount++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					posCalc.incFrame();
				}
			}
		};
		Thread t = new Thread(task);
		t.start();
	}

	public void calculatePositions(int count) 
	{
		//deal with player lasers
		if(
			fingerPressed && 
			!player.isDying() && 
			(playerLasers.size() < SpaceGameConstants.PLAYER_SHOT_COUNT) && 
			count % SpaceGameConstants.LASER_TIMEOUT == 0
		)
		{
			List<Laser> tmp = PlayerLasers.loadPlayerLaser(player, waveCount);
			if(!tmp.isEmpty())
			{
				synchronized (playerLasers)
				{
					playerLasers.addAll(tmp);
				}
				laserPlayer.seekTo(0);
				laserPlayer.start();
			}
		}
		SpriteManager.removeDeadSprites(playerLasers);
		SpriteManager.doNextPos(playerLasers);

		//deal with enemies
		List<Sprite> eship = waves.getWave(waveCount).buildSpaceShip(count);
		if(eship != null)
		{
			synchronized (enemyships) 
			{
				for(Sprite s : eship)
				{
					if(!enemyships.contains(s))
					{
						enemyships.add(s);
					}
				}
			}
		}
		SpriteManager.removeDeadSprites(enemyships);

		//deal with enemy lasers
		SpriteManager.removeDeadSprites(enemyLasers);
		SpriteManager.doNextPos(enemyships);
		synchronized (enemyLasers) 
		{
			enemyLasers.addAll(LaserGenerator.generateEnemyLasers(
					enemyships, player, 
					SpaceGameConstants.SCREEN_WIDTH, SpaceGameConstants.SCREEN_HEIGHT));
		}
		SpriteManager.doNextPos(enemyLasers);

		//deal with player
		player.nextPos();
		playerScore += SpriteManager.findCollisions(playerLasers, enemyships);
		List<Sprite> allThreats = new ArrayList<Sprite>();
		allThreats.addAll(enemyships);
		allThreats.addAll(enemyLasers);
		playerScore += SpriteManager.findCollisions(allThreats, 
				Collections.singletonList((Sprite)player));
	}

	@Override
	public synchronized void receiveFrameCount(int frameCount, int id) 
	{
		switch(id)
		{
		case SpaceGameConstants.POSITION_CALCULATOR:
			this.framesCalc = frameCount;
			break;
		case SpaceGameConstants.FPS_CALCULATOR:
			this.framesDraw = frameCount;
			break;
		}
	}
}
