package com.example.spacegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.spacegame.sprites.Sprite;

public class Wave 
{
	Map<Integer, List<Sprite>> shipsToBuild = new HashMap<Integer, List<Sprite>>();

	public Wave(Object [][] shipsToBuild) 
	{
		for(Object[] shipInfo : shipsToBuild)
		{
			Integer timerCount = (Integer)shipInfo[0];
			if(this.shipsToBuild.get(timerCount) == null)
			{
				List<Sprite> sprites = new ArrayList<Sprite>();
				sprites.add((Sprite)shipInfo[1]);
				this.shipsToBuild.put(timerCount, sprites);
			}
			else
			{
				List<Sprite> sprites = this.shipsToBuild.get(timerCount);
				sprites.add((Sprite)shipInfo[1]);
				this.shipsToBuild.put(timerCount, sprites);
			}
		}
	}
	
	public List<Sprite> buildSpaceShip(int count)
	{
		return shipsToBuild.get(count);
	}
	
	public int getWaveEndCount()
	{
		int highNum = 0;
		for(int num : shipsToBuild.keySet()){
			if(num > highNum)
				highNum = num;
		}
		return highNum;
	}
}
