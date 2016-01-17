package com.example.spacegame.util;

import android.graphics.Color;

public class SpaceGameConstants 
{
	public static final int 
		POSITION_CALCULATOR = 1,
		FPS_CALCULATOR = 2,
		PLAYER_SHOT_COUNT = 100,
		LASER_TIMEOUT = 15,
		BACKGOUND_COLOR = Color.BLACK;
	public static final long DRAW_INTERVAL = (long) ((1.0/30.0) * 1000.0);
	public static int
		SCREEN_HEIGHT,
		SCREEN_WIDTH;
}
