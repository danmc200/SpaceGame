package com.example.spacegame.shooting;

public class Gunny extends ShootingHabits
{
	@Override
	public boolean doShoot() 
	{
		super.doShoot();
		if(timesRequested % 50 == 0)
		{
			return true;
		}
		return false;
	}

}
