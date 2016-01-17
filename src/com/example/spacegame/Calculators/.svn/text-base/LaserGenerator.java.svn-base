package com.example.spacegame.Calculators;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.example.spacegame.sprites.EnemyLaser;
import com.example.spacegame.sprites.Shootable;
import com.example.spacegame.sprites.Sprite;

public class LaserGenerator 
{
	public static List<EnemyLaser> generateEnemyLasers(List<Sprite> ships,
			Sprite player,
			int SCREEN_WIDTH, int SCREEN_HEIGHT)
	{
		List<EnemyLaser> lasers = new ArrayList<EnemyLaser>();
		for(Sprite s : ships)
		{
			if(s instanceof Shootable && !s.isDying())
			{
				Shootable es = (Shootable) s;
				if(es.toShoot())//do mx+b
				{
					Point pos = s.getPosition();
					Point playerPos = player.getPosition();
					playerPos.x += player.getSize().x/2;
					playerPos.y += player.getSize().y/2;
					playerPos = offsetPosition(playerPos, 50, 60);
					double xchange = pos.x - playerPos.x;
					double ychange = pos.y - playerPos.y;
					double xnew = xchange > 0? -1: 1;
					double ynew = ychange > 0? -1: 1;
					double m = MxbCalculator.getM(xchange, ychange);
					double b = MxbCalculator.getB(playerPos.x, playerPos.y, xchange, ychange);
					EnemyLaser l = new EnemyLaser(
							new MxbCalculator(
								m, pos.x, pos.y, b, xnew, ynew
							),
						SCREEN_WIDTH, SCREEN_HEIGHT
					);
					lasers.add(l);
				}
			}
		}
		return lasers;
	}
	
	private static Point offsetPosition(Point pos, int xmax, int ymax)
	{
		int yNeg = (int) (2 * Math.random()) >= 1? -1 : 1;
		int xNeg = (int) (2 * Math.random()) >= 1? -1 : 1;
		int xoffset = (int) (xmax * Math.random()) * xNeg;
		int yoffset = (int) (ymax * Math.random()) * yNeg;
		pos.x += xoffset;
		pos.y += yoffset;
		return pos;
	}

}
