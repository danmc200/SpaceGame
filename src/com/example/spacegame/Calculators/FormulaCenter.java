package com.example.spacegame.Calculators;

import android.graphics.Point;

public class FormulaCenter implements CalculatePosition
{

	@Override
	public Point calculatePosition(Point spriteXY, Point spriteSize) 
	{
		return new Point(spriteXY.x + (spriteSize.x/2), spriteXY.y);
	}

}
