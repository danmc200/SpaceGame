package com.example.spacegame.sprites;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.spacegame.Calculators.Repeats;
import com.example.spacegame.Calculators.Repeats.RelativeToRepeat;
import com.example.spacegame.shooting.ShootingHabits;

public abstract class EnemySpaceship 
extends Sprite
implements Shootable, Damageable
{
	protected int movePos = 0;
	protected int 
		xPos,
		yPos,
		hitPoints = 100,
		allPointsItr = 0,
		repeatItr = 0;
	protected Repeats repeat;
	private static int dyingCount = 100;
	protected ShootingHabits habits;
	protected List<List<Point>> allPoints = new ArrayList<List<Point>>();
	protected List<Point> movementPoints = new ArrayList<Point>();

	protected void loadMovement(List<List<Point>> allPoints, Repeats repeat) 
	{
		this.allPoints = allPoints;
		this.movementPoints = allPoints.get(allPointsItr);
		this.repeat = repeat;
	}

	protected void loadMovement(List<Point> points, int repeat) 
	{
		this.repeat = new Repeats(0,0,repeat);
		loadMovement(points);
	}

	protected void loadMovement(List<Point> points) 
	{
		movementPoints = points;
		allPoints.add(movementPoints);
		this.repeat = null;
	}

	public List<Point> getMovement(){
		return movementPoints;
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
	
	protected void setShootingHabits(ShootingHabits sh)
	{
		this.habits = sh;
	}

	@Override
	public boolean toShoot()
	{
		return habits.doShoot();
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
		return hitPoints>0?0:setDying(true);
	}

	@Override
	public void nextPos()
	{
		if(isDying()) 
		{
			if(movePos >= dyingCount)
			{
				setDead(true);
			}
			movePos++;
		}
		else if(movePos >= movementPoints.size())
		{
			if(repeat != null && repeatItr < repeat.getRepeatCount())
			{
				RelativeToRepeat rr = repeat.getRelativeToRepeat(allPointsItr);
				boolean dead = false;
				switch(rr)
				{
				case Before:
					dead = !incAllPoints();
					break;
				case In:
					movePos = 0;
					repeatItr++;	
					break;
				case After:
					dead = !incAllPoints();
					break;
				}
				setDead(dead);
			}
			else if(!incAllPoints())
			{
				setDead(true);
			}
		}
		if(!isDying() && !isDead())
		{
			Point point = movementPoints.get(movePos);
			yPos = point.y;
			xPos = point.x;
			movePos++;
		}
	}
	
	private boolean incAllPoints()
	{
		if(allPointsItr < allPoints.size() - 1)
		{
			allPointsItr++;
			movementPoints = allPoints.get(allPointsItr);
			movePos = 0;
			return true;
		}
		return false;
	}
}
