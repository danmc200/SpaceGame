package com.example.spacegame.Calculators;
/******************************************************************************
 * Filename: CalculatePoints.java
 * 
 * @author Daniel Boydelatour
 * 
 * Description:
 * 	Converts a vector shape into A list of points.
 *
 *****************************************************************************/
import java.util.ArrayList;
import java.util.List;

import com.example.spacegame.PathIterator;

import android.graphics.Point;

public class CalculatePoints 
{
	private static ArrayList<Point> shapePoints;
	//TODO calculate steps to take?
	private static float steps = 2500.0f; //i.e. number of points to sample
	private static float bezierRSteps = 8500.0f;
	
	public static List<Point> calculateMovement(Object[][] movement)
	{
		shapePoints = new ArrayList<Point>();
		steps = 2500.0f; //i.e. number of points to sample
		//point where path iterator currently is
		double lastPointX = 0;
		double lastPointY = 0;

		for (Object [] seg : movement)
		{
			PathIterator pi = (PathIterator) seg[0];
			Double [] coords = (Double[]) seg[1];
			//add all points to end of list
			List<Point> ps = describeCurrentSegment(pi, coords, lastPointX, lastPointY);
			lastPointX = ps.get(ps.size() - 1).x;
			lastPointY = ps.get(ps.size() - 1).y;
			shapePoints.addAll(ps);
		}
		
		return shapePoints;
	}
	
	public static List<Point> describeCurrentSegment(PathIterator pi, Double[] doubles, double lastPointX, double lastPointY)
	{
		List<Integer> pointsI = new ArrayList<Integer>();
		switch(pi)
		{
		case SEG_MOVETO:
			pointsI.add(doubles[0].intValue());
			pointsI.add(doubles[1].intValue());
			lastPointX = doubles[0];
			lastPointY = doubles[1];
			break;
			
		case SEG_LINETO:
			pointsI.addAll(segLine(lastPointX, lastPointY, doubles[0], 
					doubles[1]));
			lastPointX = doubles[0];
			lastPointY = doubles[1];
			break;
			
		case SEG_QUADTO:
			pointsI.addAll(segQuad(lastPointX, lastPointY, doubles[0],
					doubles[1], doubles[2],doubles[3]));
			lastPointX = pointsI.get(pointsI.size() - 2);
			lastPointY = pointsI.get(pointsI.size() - 1);
			break;

		case SEG_CUBICTO:
			pointsI.addAll(segCubic(lastPointX, lastPointY, doubles[0],
					doubles[1], doubles[2], doubles[3], 
					doubles[4], doubles[5]));
			lastPointX = pointsI.get(pointsI.size() - 2);
			lastPointY = pointsI.get(pointsI.size() - 1);
			break;

//		case SEG_QUADTO:
//		case SEG_CUBICTO:
		case SEG_CURVETO:
			Point[] pointsArray = new Point[(doubles.length/2) + 1];
			pointsArray[0] = new Point((int)lastPointX, (int)lastPointY);
			for(int i = 0; i < doubles.length - 1; i+=2)
			{
				Point p = new Point();
				p.x = doubles[i].intValue();
				p.y = doubles[i + 1].intValue();
				pointsArray[(i/2) + 1] = p;
			}
			for(float i = 1.0f; i < bezierRSteps; i++)
			{
				float t = i/bezierRSteps;
				Point p = bezier(pointsArray, t)[0];
				pointsI.add(p.x);
				pointsI.add(p.y);
				pointsI = deleteRepeats(pointsI);
			}
			lastPointX = pointsI.get(pointsI.size() - 2);
			lastPointY = pointsI.get(pointsI.size() - 1);
			break;
			
		case SEG_CLOSE:
			break;
		default:
			break;
		}
		return getPoints(pointsI);
	}
	
	public static List<Point> getPoints(List<Integer> nums)
	{
		List<Point> points = new ArrayList<Point>();
		for(int i = 0; i+1 < nums.size(); i+=2)
		{
			points.add(new Point(nums.get(i), nums.get(i+1)));
		}
		return points;
	}
	
	/**
	 * 
	 * @param x1
	 * @param x2
	 * @return A list of integer values collectively representing a line segment
	 */
	public static List<Integer> segLine(double x1, double y1, double x2, double y2)
	{
		List<Integer> line = new ArrayList<Integer>();
		double length = lineLength(x1, y1, x2, y2);
		int repeat = 0;
		int x = 0;
		int y = 0;
		
		//find the coordinates for the line segment
		for (double i = 0; i < 1; i += 1.0 / length)
		{
			x = ((int)((1 - i) * x1 + (i * x2)));
			y = ((int)((1 - i) * y1 + (i * y2)));
			
			line.add(x);
			line.add(y);
			line = deleteRepeats(line);
		}
		return line;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return A list of integer values collectively representing a 
	 * quadratic curve
	 */
	public static List<Integer> segQuad(double x1, double y1, 
			double x2, double y2, double x3,
			double y3)
	{
		double len = 0;
		int repeat = 0; //used to hold the number of repeated samples
		len = quadraticLength(x1, y1, x2, y2, x3, y3);
		steps = (int) len;
		int x = 0;
		int y = 0;
		
		List<Integer> quad = new ArrayList<Integer>();
		for(double t = 0; t < 1; t += 1 / steps)
		{
			x = ((int)(Math.pow((1-t), 2) * x1 + 2 * (1 - t)
					* t * x2 + Math.pow(t, 2) * x3));
			
			y = ((int)(Math.pow((1-t), 2) * y1 + 2 * (1 - t)
					* t * y2 + Math.pow(t, 2) * y3));
			
			quad.add(x);
			quad.add(y);
			quad = deleteRepeats(quad);
			
		}
		
		return quad;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return A list of integer values collectively representing a cubic curve
	 */
	public static List<Integer> segCubic(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4)
	{
		double len = 0;
		int repeat = 0; //used to hold the number of repeated samples
		len = cubicLength(x1, y1, x2, y2, x3, y3, x4, y4);
		steps = (int) len;
		int x = 0;
		int y = 0;
		
		List<Integer> cubic = new ArrayList<Integer>();
		for(double t = 0; t < 1; t += 1 / steps)
		{
			x = ((int)(x1 * Math.pow((1-t), 3) + 3 * x2 * t
					* Math.pow((1-t), 2) + 3 * x3 * Math.pow(t, 2) *
					(1 - t) + x4 * Math.pow(t, 3)));
			
			y =((int)(y1 * Math.pow((1-t), 3) + 3 * y2 * t
					* Math.pow((1-t), 2) + 3 * y3 * Math.pow(t, 2) *
					(1 - t) + y4 * Math.pow(t, 3)));
			
			cubic.add(x);
			cubic.add(y);
			cubic = deleteRepeats(cubic);
		}
		return cubic;
	}
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return an approx. length of a quadratic curve (larger than)
	 */
	public static double quadraticLength(double x1, double y1, double x2, double y2,
			double x3, double y3)
	{
		double minDx = 0;
		double minDy = 0;
		double maxDx = 0;
		double maxDy = 0;
		double length;
		
		//find the bounding box for the quadratic curve
		double[] dx = {x1, x2, x3};
		maxDx = maxLength(dx);
		minDx = minLength(dx);
		double [] dy = {y1, y2, y3};
		maxDy = maxLength(dy);
		minDy = minLength(dy);
		
		double width = maxDx - minDx;
		double height = maxDy - minDy;
		length = Math.sqrt(height * width)*4;
		
		return length;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return an approx. length of a cubic curve (larger than)
	 */
	public static double cubicLength(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4)
	{
		double minDx = 0;
		double minDy = 0;
		double maxDx = 0;
		double maxDy = 0;
		double length;
		
		//find the bounding box for the quadratic curve
		double[] dx = {x1, x2, x3, x4};
		maxDx = maxLength(dx);
		minDx = minLength(dx);
		double [] dy = {y1, y2, y3, y4};
		maxDy = maxLength(dy);
		minDy = minLength(dy);
		
		double width = maxDx - minDx;
		double height = maxDy - minDy;
		length = Math.sqrt(height * width) * 4;
		
		return length;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the length of a line segment
	 */
	public static int lineLength(double x1, double y1, double x2, double y2)
	{
		int length = 0;
		double a = 0;
		double b = 0;
		
		a = Math.abs(x2 - x1);
		b = Math.abs(y2 - y1);
		length = (int) Math.sqrt(Math.pow(a,2) + Math.pow(b, 2)); 
		
		return length + 1;
	}
	
	public static double maxLength(double [] points)
	{
		double max = 0;
		for(double d: points)
		{
			if (d > max)
			{
				max = d;
			}
		}
		return max;
	}
	
	public static double minLength(double [] points)
	{
		double min = 0;
		for(double d: points)
		{
			if (d < min)
			{
				min = d;
			}
		}
		return min;
	}
	
	public static List<Integer> deleteRepeats(List<Integer> points)
	{
		int repeat = 0;
		if(points.size() < 4)
		{
			return points;
		}
		if((int)points.get(points.size() - 1) == (int)points.get(points.size() - 3)
				&& (int)points.get(points.size() - 2) == (int)points.get(points.size() - 4))
			{
				points.remove(points.size() - 1);
				points.remove(points.size() - 1);
				repeat++;
			}
			if(points.size() >= 8)
			{
				if((int)points.get(points.size() - 1) == (int)points.get(
						points.size() - 5)
						&& (int)points.get(points.size() - 2) == 
							(int)points.get(points.size() - 6)
						&& (int)points.get(points.size() - 3) == 
							(int)points.get(points.size() - 7)
						&& (int)points.get(points.size() - 4) == 
							(int)points.get(points.size() - 8))
					{
						points.remove(points.size() - 1);
						points.remove(points.size() - 1);
						points.remove(points.size() - 1);
						points.remove(points.size() - 1);
						repeat++;
					}
			}
			return points;
		}

	// simple linear interpolation between two points
	private static Point lerp(Point a, Point b, float t)
	{
		Point dest = new Point();
	    dest.x = (int) (a.x + (b.x-a.x)*t);
	    dest.y = (int) (a.y + (b.y-a.y)*t);
	    return dest;
	}

	// evaluate a point on a bezier-curve. t goes from 0 to 1.0
	private static Point [] bezier(Point [] coords, float t)
	{
		List<Point> dest = new ArrayList<Point>();
		Point a = null;
		for(int i = 0; i < coords.length; i+=1)
		{
			Point b = coords[i];
			if(a != null)
			{
				dest.add(lerp(a, b, t));
			}
			a = b;
		}
		Point [] points = dest.toArray(new Point[dest.size()]);
		if(points.length > 1)
			return bezier(points, t);
	    return points;
	}

}
