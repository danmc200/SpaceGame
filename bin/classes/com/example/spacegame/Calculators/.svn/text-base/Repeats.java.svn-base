package com.example.spacegame.Calculators;

public class Repeats 
{
	public enum RelativeToRepeat 
	{
		Before,
		In,
		After
	}
	private int start, end, repeatCount;
	public Repeats(int start, int end, int repeatCount)
	{
		this.start = start;
		this.end = end;
		this.repeatCount = repeatCount;
	}
	public int getStart()
	{
		return start;
	}
	public int getEnd()
	{
		return end;
	}
	public int getRepeatCount()
	{
		return repeatCount;
	}
	
	public RelativeToRepeat getRelativeToRepeat(int itr)
	{
		if(start > itr)
		{
			return RelativeToRepeat.Before;
		}
		else if(end < itr)
		{
			return RelativeToRepeat.After;
		}
		else
		{
			return RelativeToRepeat.In;
		}
	}
}
