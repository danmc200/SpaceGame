package com.example.spacegame.Calculators;

import java.util.ArrayList;
import java.util.List;

import com.example.spacegame.PathIterator;

import android.graphics.Point;

public class PathGenerator 
{
	public enum PathType 
	{
		LINE(0),
		ZIG_ZAG(1),
		QUAD_CURVE(2);

		int index = 0;
		PathType(int index)
		{
			this.index = index;
		}

		public static PathType getPathType(int index)
		{
			for(PathType pt : PathType.values())
			{
				if(pt.index == index)
				{
					return pt;
				}
			}
			return null;
		}
		public static int getPathTypeSize()
		{
			return PathType.values().length;
		}
	}

	public PathGenerator()
	{
		
	}
	
	public static List<Point> makePath(PathType pt, Point start, Point end)
	{
		List<Point> points = new ArrayList<Point>();
		Object [][] movement = null;
		switch (pt)
		{
		case LINE:
			movement = new Object[][]{
					{PathIterator.SEG_MOVETO, new Double[]{(double) start.x,
					(double) start.y}},
					{PathIterator.SEG_LINETO, new Double[]{(double) end.x,
					(double) end.y}},
			};
			points = CalculatePoints.calculateMovement(movement);
			break;
		case ZIG_ZAG:
			movement = new Object[][]{
					{PathIterator.SEG_MOVETO, new Double[]{(double) start.x,
					(double) start.y}},
					{PathIterator.SEG_LINETO, new Double[]{(double) end.x,
					(double) end.y}},
			};
			points = CalculatePoints.calculateMovement(movement);
			break;
		case QUAD_CURVE:
			int cy = end.y - start.y;
			int cx = end.x + start.x;
			movement = new Object[][]{
					{PathIterator.SEG_MOVETO, new Double[]{(double) start.x,
					(double) start.y}},
					{PathIterator.SEG_QUADTO, new Double[]{
							(double) cx, (double) cy, 
							(double) end.x, (double) end.y}},
			};
			points = CalculatePoints.calculateMovement(movement);
			break;
		}

		return points;
	}
	
	public static PathType generateRandomPathType()
	{
		int rand = (int) (Math.random() * PathType.getPathTypeSize());
		PathType pt = PathType.getPathType(rand);
		return pt;
	}

}
