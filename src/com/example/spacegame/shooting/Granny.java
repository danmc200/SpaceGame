package com.example.spacegame.shooting;

public class Granny extends ShootingHabits
{
	@Override
	public boolean doShoot() 
	{
		super.doShoot();
		if(timesRequested % 300 == 0)
		{
			return true;
		}
		return false;
	}
}
