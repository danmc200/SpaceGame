package com.example.spacegame.shooting;

public abstract class ShootingHabits 
{
	protected long timesRequested = 0;

	public boolean doShoot()
	{
		this.timesRequested++;
		return false;
	}
}
