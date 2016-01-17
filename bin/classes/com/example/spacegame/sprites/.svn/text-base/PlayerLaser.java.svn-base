package com.example.spacegame.sprites;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.spacegame.R;

public class PlayerLaser extends Laser
{

	private static int SPEED = 10;
	public static Bitmap laserImg;
	
	public PlayerLaser()
	{
		image = laserImg;
		firepower = 100;
	}
	public PlayerLaser(int xPos, int yPos, int yLimit)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.yLimit = yLimit;
		image = laserImg;
		firepower = 100;
	}

	@Override
	public int getSpeed() 
	{
		return SPEED;
	}

	@Override
	public void nextPos() 
	{
		yPos = yPos - getSpeed();
		if(yPos < yLimit)
		{
			setDead(true);
		}
	}
	
	@Override
	public int setDying(boolean dying)
	{
		setDead(true);
		return 0;
	}
	
	@Override
	public Point getPosition() 
	{
		return new Point(xPos, yPos);
	}

	public static void setResource(Resources res) 
	{
		laserImg = BitmapFactory.decodeResource(res, R.drawable.shot2);
	}

}
