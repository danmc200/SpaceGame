package com.example.spacegame.Calculators;

import android.graphics.Point;

public class MxbCalculator implements ShotCalculator
{
	double 
		yPos = 0.0,
		xPos = 0.0,
		m,
		b,
		xnew,
		ynew;

	public MxbCalculator(double m, double x, double y, double b, double xnew, double ynew)
	{
		this.xPos = x;
		this.yPos = y;
		this.m = m;
		this.b = b;
		this.xnew = xnew;
		this.ynew = ynew;
	}

	@Override
	public Point getNextPosition(int speed)
	{
		double xPostmp = xPos + (speed * xnew);
		Point p = new Point();
		p.y = (int) ((m * xPostmp) + b);
		p.x = (int) xPostmp;
		
		double yPostmp = yPos + (speed * ynew);
		Point p2 = new Point();
		p2.x = (int) ((yPostmp-b)/m);
		p2.y = (int) yPostmp;
		
		if(Math.abs(p2.x - xPos) < Math.abs(p.y - yPos))
		{
			yPos = p2.y;
			xPos = p2.x;
			return p2;
		}
		else
		{
			yPos = p.y;
			xPos = p.x;
			return p;
		}
	}
	
	public static double getM(double xchange, double ychange)
	{
		return ychange/xchange;
	}
	
	public static double getB(double x, double y, double xchange, double ychange)
	{
		return y - ((ychange/xchange) * x);
	}
}
