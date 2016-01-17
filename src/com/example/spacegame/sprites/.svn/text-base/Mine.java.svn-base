package com.example.spacegame.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.spacegame.R;
import com.example.spacegame.util.SpaceGameConstants;

public class Mine extends Sprite
{
	protected static int 
		SPEED = 1,
		DYING_COUNT = 20;
	public static Bitmap
		explosion,
		mineImg;
	public int
		xPos,
		yPos,
		yLimit = SpaceGameConstants.SCREEN_HEIGHT,
		movePos = 0;


	public Mine(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		image = mineImg;
	}

	@Override
	public int getSpeed() 
	{
		return SPEED;
	}
	
	public static void setResource(Resources res)
	{
		mineImg = BitmapFactory.decodeResource(res, R.drawable.mine);
		explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
	}

	@Override
	public Point getPosition() 
	{
		return new Point(xPos, yPos);
	}

	@Override
	public Bitmap getImage() 
	{
		return image;
	}
	
	@Override
	public int setDying(boolean dying)
	{
		super.setDying(dying);
		movePos = 0;
		image = explosion;
		return 50;
	}	

	@Override
	public void nextPos() 
	{
		if(isDying()) 
		{
			if(movePos >= DYING_COUNT)
			{
				setDead(true);
			}
			movePos++;
		}
		yPos = yPos + getSpeed();
		if(yPos > yLimit)
		{
			setDead(true);
		}
	}
}
