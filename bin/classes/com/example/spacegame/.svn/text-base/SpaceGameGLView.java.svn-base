package com.example.spacegame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.spacegame.Calculators.MxbCalculator;
import com.example.spacegame.opengl.sprites.PlayerSpaceshipOG;
import com.example.spacegame.sprites.Damageable;
import com.example.spacegame.sprites.EnemyLaser;
import com.example.spacegame.sprites.EnemySpaceship;
import com.example.spacegame.sprites.Firepower;
import com.example.spacegame.sprites.Laser;
import com.example.spacegame.sprites.Mine;
import com.example.spacegame.sprites.PlayerLaser;
import com.example.spacegame.sprites.PlayerSpaceship;
import com.example.spacegame.sprites.Shootable;
import com.example.spacegame.sprites.SpaceshipZx1;
import com.example.spacegame.sprites.SpaceshipZx2;
import com.example.spacegame.sprites.Sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SpaceGameGLView extends GLSurfaceView
{
	private static final int 
		PLAYER_SHOT_COUNT = 500,
		LASER_TIMEOUT = 20,
		BACKGOUND_COLOR = Color.BLACK;
	public static int
		SCREEN_WIDTH,
		SCREEN_HEIGHT;
	private static final long DRAW_INTERVAL = (long) ((1.0/30.0) * 1000.0);
	private Paint playerPaint;
	private List<Sprite> playerLasers = new ArrayList<Sprite>();
	private List<Sprite> enemyLasers = new ArrayList<Sprite>();
	private List<Sprite> enemyships = new ArrayList<Sprite>();
    private List<List<Sprite>> allSprites = new ArrayList<List<Sprite>>();
	private PlayerSpaceshipOG player; 
	private boolean fingerPressed = false;
	private int 
		playerScore = 0,
		waveCount = 0,
		framesCalc = 0,
		framesDraw = 0,
		framesDrawCount = 0,
		shipsNum = 0;
	private long timeStart = new Date().getTime();
	private Waves waves;
	private Wave wave;
	private int waveEnd;
	private SpaceGameGLRenderer mRenderer;

	public SpaceGameGLView(Context context, Point size, AttributeSet attrs) {
		super(context, attrs);
		SCREEN_WIDTH = size.x;
		SCREEN_HEIGHT = size.y;
		initResources(getResources());
		initWave();
		player = new PlayerSpaceshipOG(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 250);
		playerPaint = getPlayerPaint();
//		setBackgroundColor(BACKGOUND_COLOR);
		allSprites.add(playerLasers);
		allSprites.add(enemyLasers);
		allSprites.add(enemyships);

		 // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new SpaceGameGLRenderer();
        // Render the view only when there is a change in the drawing data
        mRenderer.setPlayerSprite(player);
        Sprite s = new PlayerLaser(player.getPosition().x, player.getPosition().y, SCREEN_HEIGHT);
        playerLasers.add(s);
        mRenderer.setAllSprites(allSprites);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		startDrawCycle();
	}

	
	private void initResources(Resources resource)
	{
		PlayerSpaceship.setResource(resource);
		PlayerLaser.setResource(resource);
		EnemyLaser.setResource(resource);
		SpaceshipZx1.setResource(resource);
		SpaceshipZx2.setResource(resource);
		Mine.setResource(resource);
	}
	
	private void initWave()
	{
		waves = new Waves();
		wave = waves.getWave(waveCount);
		waveEnd = wave.getWaveEndCount();
	}
	
	private Paint getPlayerPaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1f);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		return paint;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		float eventX = event.getX();
		float eventY = event.getY();

		//TODO WARNING!
		player = (PlayerSpaceshipOG) mRenderer.getPlayerSprite();
		player.setPosition((int)eventX, (int)eventY);
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
				requestRender();
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
				int count = 0;
				long timePrev = new Date().getTime();
				long fps = 0;
				long timeStart = timePrev;
				while(true)
				{
					long timeNow = new Date().getTime();
					long timeToSleep = DRAW_INTERVAL - (timeNow - timePrev);
					timePrev = new Date().getTime();
					try 
					{
						if(timePrev - timeStart >= 1000)
						{
							framesCalc = (int) fps;
							fps = 0;
							timeStart = timePrev;
							shipsNum = enemyships.size();
							System.out.println(framesCalc);
						}
						if(timeToSleep > 0)
						{
							Thread.sleep(timeToSleep);
						}
						SpaceGameGLView.this.calculatePositions(count);
						SpaceGameGLView.this.refreshDrawableState();
						if(count >= waveEnd)
						{
							waveCount++;
							if(waveCount < waves.getWaveCount()){
								wave = waves.getWave(waveCount);
								waveEnd = wave.getWaveEndCount();
							}
							count = 0;
						}
						count++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fps++;
				}
			}
		};
		Thread t = new Thread(task);
		t.start();
	}

	public void calculatePositions(int count) 
	{
		//deal with lasers
		if(fingerPressed && !player.isDying() && 
				(playerLasers.size() < PLAYER_SHOT_COUNT) && 
				count % LASER_TIMEOUT == 0) 
		{
			Point pos = player.getPosition();
			Laser l = new PlayerLaser(pos.x, pos.y, -10);
			synchronized (playerLasers) 
			{
				playerLasers.add(l);
				Laser l2 = new PlayerLaser(pos.x + 10, pos.y, -10);
				playerLasers.add(l2);
				Laser l3 = new PlayerLaser(pos.x - 10, pos.y, -10);
				playerLasers.add(l3);
			}
		}
		playerLasers = removeDeadSprites(playerLasers);
		doNextPos(playerLasers);
		//deal with enemies
		List<Sprite> eship = wave.buildSpaceShip(count);
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
		enemyships = removeDeadSprites(enemyships);
		enemyLasers = removeDeadSprites(enemyLasers);
		doNextPos(enemyships);
		synchronized (enemyLasers) 
		{
			enemyLasers.addAll(generateEnemyLasers(enemyships));
		}
		doNextPos(enemyLasers);
		player.nextPos();
		playerScore += findCollisions(playerLasers, enemyships);
		List<Sprite> allThreats = new ArrayList<Sprite>();
		allThreats.addAll(enemyships);
		allThreats.addAll(enemyLasers);
		playerScore += findCollisions(allThreats, 
				Collections.singletonList((Sprite)player));
	}

	public int findCollisions(List<Sprite> lasers, List<Sprite> ships)
	{
		int score = 0;
		for(Sprite l : lasers)
		{
			int lx = l.getPosition().x;
			int ly = l.getPosition().y;
			int lWidth = l.getImage().getHeight();
			int lHeight = l.getImage().getWidth();
			Rect lr = new Rect(lx, ly, lx + lWidth, ly + lHeight);

			for(Sprite e : ships)
			{
				int ex = e.getPosition().x;
				int ey = e.getPosition().y;
				int eWidth = e.getImage().getHeight();
				int eHeight = e.getImage().getWidth();
				Rect er = new Rect(ex, ey, ex + eWidth, ey + eHeight);

				//TODO 
				if(er.intersect(lr) && !e.isDying())
				{
					if(e instanceof Damageable)
					{
						Damageable es = (Damageable) e;
						if(l instanceof Firepower)
						{
							score += es.setDamage(((Laser) l).getFirepower());
						}
					}
					else
					{
						score += e.setDying(true);
					}
					score += l.setDying(true);
				}
			}
		}
		return score;
	}
	
	public List<Sprite> removeDeadSprites(List<Sprite> sprites)
	{
		List<Sprite> rem = new ArrayList<Sprite>();
		for(Sprite s : sprites)
		{
			if(s.isDead())
			{
				rem.add(s);
			}
		}
		synchronized (sprites) 
		{
			for(Sprite r : rem)
			{
				sprites.remove(r);
			}
		}
		
		return sprites;
	}

	public void doNextPos(List<Sprite> sprites)
	{
		for(Sprite s : sprites)
		{
			s.nextPos();
		}
	}
	
	public List<EnemyLaser> generateEnemyLasers(List<Sprite> ships)
	{
		List<EnemyLaser> lasers = new ArrayList<EnemyLaser>();
		for(Sprite s : ships)
		{
			if(s instanceof Shootable)
			{
				Shootable es = (Shootable) s;
				if(es.toShoot())//do mx+b
				{
					Point pos = s.getPosition();
					Point playerPos = player.getPosition();
					double xchange = pos.x - playerPos.x;
					double ychange = pos.y - playerPos.y;
					double xnew = xchange > 0? -1: 1;
					double ynew = ychange > 0? -1: 1;
					double m = (ychange/xchange);
					double b = playerPos.y - ((ychange/xchange) * playerPos.x);
					EnemyLaser l = new EnemyLaser(
							new MxbCalculator(
								m, pos.x, pos.y, b, xnew, ynew
							),
						SCREEN_WIDTH, SCREEN_HEIGHT
					);
					lasers.add(l);
				}
			}
		}
		return lasers;
	}
}
