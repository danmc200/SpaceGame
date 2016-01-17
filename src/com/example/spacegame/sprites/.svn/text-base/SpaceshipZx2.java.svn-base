package com.example.spacegame.sprites;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.spacegame.Path;
import com.example.spacegame.R;
import com.example.spacegame.Calculators.Repeats;
import com.example.spacegame.shooting.ShootingHabits;

public class SpaceshipZx2
extends EnemySpaceship
{
	private static int SPEED = 7;
	protected ShootingHabits habits;
	public static Bitmap 
		explosion,
		shipImg;

	public SpaceshipZx2(ShootingHabits habits, Path [] path, Repeats repeats)
	{
		List<List<Point>> allPoints = new ArrayList<List<Point>>();
		for(Path p : path)
		{
			allPoints.add(p.getPath());
		}
		super.loadMovement(allPoints, repeats);
		setShootingHabits(habits);
		hitPoints = 400;
		image = shipImg;
	}
	public SpaceshipZx2(ShootingHabits habits, Path path)
	{
		super.setShootingHabits(habits);
		super.loadMovement(path.getPath());
		hitPoints = 400;
		image = shipImg;
	}
	
	@Override
	public int getSpeed() 
	{
		return SPEED;
	}
	
	public static void setResource(Resources res)
	{
		shipImg = BitmapFactory.decodeResource(res, R.drawable.spaceshipzx2);
		explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);
	}

	@Override
	public int setDying(boolean dying)
	{
		super.setDying(dying);
		image = explosion;
		movePos = 0;
		return 200;
	}
}