package com.example.spacegame.sprites;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.spacegame.R;
import com.example.spacegame.Calculators.ShotCalculator;

public class EnemyLaser extends Laser
{
	private static int SPEED = 8;
	private ShotCalculator sc;
	private int 
		yMax, 
		xMax;
	protected int 
		movePos = 0,
		firepower = 100;
	public static Bitmap laserImg;


	public EnemyLaser(ShotCalculator sc, int xMax, int yMax) 
	{
		this.sc = sc;
		this.xMax = xMax;
		this.yMax = yMax;
		image = laserImg;
	}
	
	@Override
	public int getSpeed() 
	{
		return SPEED;
	}
	
	public static void setResource(Resources res) 
	{
		laserImg = BitmapFactory.decodeResource(res, R.drawable.enemy_shot2);
	}

	public void nextPos()
	{
		Point p = sc.getNextPosition(SPEED);
		xPos = p.x;
		yPos = p.y;
		if(p.y > yMax || p.y < 0 || p.x > xMax || p.x < 0)
		{
			setDead(true);
		}
	}
}