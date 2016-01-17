package com.example.spacegame;

import java.util.ArrayList;
import java.util.List;

import com.example.spacegame.Calculators.CalculatePoints;

import android.graphics.Point;

public class Path 
{
	List<Point> points = null;
	Object[][] movement;
	private int speed = 0;
	private float scale = 1f;

	public Path(Object[][] movement, int speed)
	{
		this.speed = speed;
		this.movement = movement;
		points = CalculatePoints.calculateMovement(movement);
	}

	public Path(Object[][] movement, int speed, float scale)
	{
		this.scale = scale;
		this.speed = speed;

		Object[][] tmpMovement = new Object[movement.length][];
		int i = 0;
		for (Object [] seg : movement)
		{
			Object[] tmpSetup = new Object[seg.length];
			PathIterator pi = (PathIterator) seg[0];
			Double [] coords = (Double[]) seg[1];
			Double[] tmpCoords = new Double[coords.length];
			int j = 0;
			for(Double d : coords)
			{
				d *= scale;
				tmpCoords[j] = d;
				j++;
			}
			tmpSetup[0] = pi;
			tmpSetup[1] = tmpCoords;
			tmpMovement[i] = tmpSetup;
			i++;
		}
		this.movement = tmpMovement;
		points = CalculatePoints.calculateMovement(this.movement);
	}

	public List<Point> getPath()
	{
		List<Point> tmp = new ArrayList<Point>();
		for(int i = 0; i < points.size(); i += speed)
		{
			tmp.add(points.get(i));
		}
		return tmp;
	}
	
	public Object [][] getMovement()
	{
		return this.movement;
	}
	public Point getLastPoint()
	{
		return new Point(points.get(points.size() - 1));
	}
	
	public int getMinX()
	{
		int minX = Integer.MAX_VALUE;
		for(Point p : points)
		{
			if(p.x < minX)
			{
				minX = p.x;
			}
		}
		return minX;
	}

	public int getMinY()
	{
		int minY = Integer.MAX_VALUE;
		for(Point p : points)
		{
			if(p.y < minY)
			{
				minY = p.y;
			}
		}
		return minY;
	}

	public int getMaxX()
	{
		int maxX = -Integer.MAX_VALUE;
		for(Point p : points)
		{
			if(p.x > maxX)
			{
				maxX = p.x;
			}
		}
		return maxX;
	}

	public int getMaxY()
	{
		int maxY = -Integer.MAX_VALUE;
		for(Point p : points)
		{
			if(p.y > maxY)
			{
				maxY = p.y;
			}
		}
		return maxY;
	}
}
