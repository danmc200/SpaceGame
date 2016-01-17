package com.example.spacegame.sprites;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class Laser 
extends Sprite
implements Firepower
{
	protected int 
		xPos, 
		yPos,
		yLimit,
		firepower = 100;
	
	public Laser()
	{
	}

	@Override
	public Bitmap getImage() 
	{
		return image;
	}
	
	@Override
	public int setDying(boolean dying)
	{
		setDead(true);
		return 0;
	}
	
	@Override
	public int getFirepower()
	{
		return firepower;
	}

	@Override
	public Point getPosition() 
	{
		return new Point(xPos, yPos);
	}

}
