package com.example.spacegame.Calculators;

import android.graphics.Point;

public interface CalculatePosition 
{
	public abstract Point calculatePosition(Point spriteXY, Point spriteSize);
}
