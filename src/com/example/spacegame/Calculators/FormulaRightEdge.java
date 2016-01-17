package com.example.spacegame.Calculators;

import android.graphics.Point;

public class FormulaRightEdge
implements CalculatePosition
{

	@Override
	public Point calculatePosition(Point spriteXY, Point spriteSize) 
	{
		int sprX = spriteXY.x;
		int sprY = spriteXY.y;
		int width = spriteSize.x;
		return new Point(sprX + width, sprY);
	}
	
}
