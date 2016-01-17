package com.example.spacegame.Calculators;

import java.util.Date;

public class FrameCalculator 
{
	private int 
		id,
		frameCount;
	private Date 
		startDate,
		endDate;
	private FrameListener frameListener;
	
	public FrameCalculator(FrameListener frameListener, int id)
	{
		this.frameListener = frameListener;
		this.id = id;
	}

	public void startTimer()
	{
		frameCount = 0;
		startDate = new Date();
	}
	
	public void incFrame()
	{
		frameCount++;
		endDate = new Date();
		if(endDate.getTime() - startDate.getTime() >= 1000)
		{
			notifyListeners();
			startTimer();
		}
	}
	
	private void notifyListeners()
	{
		this.frameListener.receiveFrameCount(frameCount, id);
	}
}
