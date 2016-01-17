package com.example.spacegame.util;

import java.lang.reflect.Method;

import android.content.res.Resources;

import com.example.spacegame.sprites.*;

public enum SpriteEnum 
{
	playerSpaceship(
			PlayerSpaceship.class),
	playerLaser(
			PlayerLaser.class),
	spaceshipZx1(
			SpaceshipZx1.class),
	spaceshipZx2(
			SpaceshipZx2.class),
	mine(
			Mine.class),
	enemyLaser(
			EnemyLaser.class);
	
	private Method setResource;
	private SpriteEnum(Class<?> clazz)
	{
		try {
			Method m = clazz.getMethod("setResource", Resources.class);
			this.setResource = m;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public static void initAllResources(Resources resource)
	{
		for(SpriteEnum sprite : SpriteEnum.values())
		{
			try {
				sprite.setResource.invoke(null, resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
