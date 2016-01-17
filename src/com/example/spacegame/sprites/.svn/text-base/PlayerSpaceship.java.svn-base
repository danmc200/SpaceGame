package com.example.spacegame.sprites;


import com.example.spacegame.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class PlayerSpaceship extends Sprite
implements Damageable
{
	protected int 
		xPos, 
		yPos,
		movePos = 0;
	protected static int 
		DYING_COUNT = 100;
	public static int
		TOTAL_HIT_POINTS = 500;
	public static Bitmap 
		playerImg,
		explosion;
	protected int hitPoints = TOTAL_HIT_POINTS;
	protected static float yppi;

	public PlayerSpaceship(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		image = playerImg;
	}
	
	public PlayerSpaceship()
	{
		this(0, 0);
	}
	
	@Override
	public int getSpeed() 
	{
		return 0;
	}

	@Override
	public Point getPosition() 
	{
		return new Point(xPos, yPos);
	}

	public static void setResource(Resources res) 
	{
		playerImg = BitmapFactory.decodeResource(res, R.drawable.sprite_player_ship5);
		explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
		DisplayMetrics dm = res.getDisplayMetrics();
		yppi = dm.ydpi * dm.densityDpi / 160;
	}

	@Override
	public Bitmap getImage() 
	{
		return image;
	}
	
	@Override
	public void nextPos() 
	{
		if(isDying() && movePos < DYING_COUNT)
		{
			movePos++;
		}
		else if(isDying() && movePos >= DYING_COUNT)
		{
			movePos = 0;
			setDying(false);
			hitPoints = TOTAL_HIT_POINTS;
			image = playerImg;
		}
	}
	
	@Override
	public int setDying(boolean dying)
	{
		super.setDying(dying);
		if(dying)
		{
			image = explosion;
		}
		return dying?-1000:0;
	}

	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos - (getImage().getWidth() / 2);
//		this.xPos = xPos - (imageSize.x/2);
		//subtract a nickel inch for fingertip
		this.yPos = (int) (yPos - (getImage().getHeight()) - (yppi/5));
//		this.yPos = (int) (yPos - imageSize.y - (yppi/5));
	}

	@Override
	public int getHitPoints() 
	{
		return hitPoints;
	}

	@Override
	public int setDamage(int damage) 
	{
		hitPoints -= damage;
		return hitPoints > 0 ? 0 : setDying(true);
	}

}
