package com.example.spacegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.spacegame.Calculators.CalculatePosition;
import com.example.spacegame.Calculators.FormulaCenter;
import com.example.spacegame.Calculators.FormulaLeftEdge;
import com.example.spacegame.Calculators.FormulaRightEdge;
import com.example.spacegame.sprites.Laser;
import com.example.spacegame.sprites.PlayerLaser;
import com.example.spacegame.sprites.Sprite;

public class PlayerLasers 
{
	public enum Lasers
	{
		None,
		laser1
	}
	private static int currentWave = 0;
	private static final int YLIMIT = -10;
	private static Map<Integer, Lasers[]> waveAndDefaultLasers = 
			new HashMap<Integer, Lasers[]>();
	static
	{
//		waveAndDefaultLasers.put(0, new Lasers[]{});
		waveAndDefaultLasers.put(0, new Lasers[]{
				Lasers.laser1, Lasers.laser1, Lasers.laser1});
	}
	private static Map<Integer, CalculatePosition[]> waveAndPosition = 
			new HashMap<Integer, CalculatePosition[]>();
	static
	{
//		waveAndPosition.put(0, null);
		waveAndPosition.put(0, new CalculatePosition[]{new FormulaLeftEdge(), 
				new FormulaCenter(), new FormulaRightEdge()});
	}
	public static List<Laser> loadPlayerLaser(Sprite playerShip, int wave)
	{
		setCurrentWaveCount(wave);
		List<Laser> lasers = new ArrayList<Laser>();
		Lasers[] tmp = waveAndDefaultLasers.get(currentWave);
		CalculatePosition[] tmp2 = waveAndPosition.get(currentWave);

		for(int i = 0; i < tmp.length; i++)
		{
			Lasers l = tmp[i];
			CalculatePosition pos = tmp2[i];
			switch (l)
			{
			case None:
				break;
			case laser1:
				Bitmap img = playerShip.getImage();
				Point sze = new Point(img.getWidth(), img.getHeight());
				Point p = pos.calculatePosition(playerShip.getPosition(), sze);
				lasers.add(new PlayerLaser(p.x, p.y, YLIMIT));
				break;
			}
		}
		return lasers;
	}
	
	public static void setCurrentWaveCount(int wave)
	{
		if(waveAndDefaultLasers.containsKey(wave))
		{
			currentWave = wave;
		}
	}
	
}
