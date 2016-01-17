package com.example.spacegame.sprites;

import java.util.ArrayList;
import java.util.List;

import com.example.spacegame.Path;
import com.example.spacegame.R;
import com.example.spacegame.Calculators.Repeats;
import com.example.spacegame.shooting.ShootingHabits;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class SpaceshipZx1 
extends EnemySpaceship
{
	private static int SPEED = 8;
	protected ShootingHabits habits;
	public static Bitmap 
		explosion,
		shipImg;

	public SpaceshipZx1(ShootingHabits habits, Path path, int repeat)
	{
		super.setShootingHabits(habits);
		super.loadMovement(path.getPath(), repeat);
		image = shipImg;
	}
	public SpaceshipZx1(ShootingHabits habits, Path [] paths, Repeats repeatTimes)
	{
		super.setShootingHabits(habits);
		List<List<Point>> allPoints = new ArrayList<List<Point>>();
		for(Path path : paths)
		{
			allPoints.add(path.getPath());
		}
		super.loadMovement(allPoints, repeatTimes);
		image = shipImg;
	}
	public SpaceshipZx1(ShootingHabits habits, Path path)
	{
		this(habits,path, 0);
	}
	
	@Override
	public int getSpeed()
	{
		return SPEED;
	}
	
	public static void setResource(Resources res)
	{
		shipImg = BitmapFactory.decodeResource(res, R.drawable.spaceshipzx1);
		explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
	}

	@Override
	public int setDying(boolean dying)
	{
		super.setDying(dying);
		image = explosion;
		movePos = 0;;
		return 100;
	}
}
