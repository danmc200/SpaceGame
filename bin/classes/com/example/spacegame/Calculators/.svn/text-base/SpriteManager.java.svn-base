package com.example.spacegame.Calculators;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.example.spacegame.sprites.*;

public class SpriteManager 
{
	public static int findCollisions(List<Sprite> lasers, List<Sprite> ships)
	{
		int score = 0;
		for(Sprite l : lasers)
		{
			int lx = l.getPosition().x;
			int ly = l.getPosition().y;
			int lWidth = l.getSize().x;
			int lHeight = l.getSize().y;
			Rect lr = new Rect(lx, ly, lx + lWidth, ly + lHeight);

			for(Sprite e : ships)
			{
				int ex = e.getPosition().x;
				int ey = e.getPosition().y;
				int eWidth = e.getSize().x;
				int eHeight = e.getSize().y;
				Rect er = new Rect(ex, ey, ex + eWidth, ey + eHeight);

				//TODO 
				if(er.intersect(lr) && !e.isDying())
				{
					if(e instanceof Damageable)
					{
						Damageable es = (Damageable) e;
						if(l instanceof Firepower)
						{
							score += es.setDamage(((Laser) l).getFirepower());
						}
					}
					else
					{
						score += e.setDying(true);
					}
					score += l.setDying(true);
				}
			}
		}
		return score;
	}
	
	public static List<Sprite> removeDeadSprites(List<Sprite> sprites)
	{
		List<Sprite> rem = new ArrayList<Sprite>();
		for(Sprite s : sprites)
		{
			if(s.isDead())
			{
				rem.add(s);
			}
		}
		synchronized (sprites) 
		{
			for(Sprite r : rem)
			{
				sprites.remove(r);
			}
		}
		
		return sprites;
	}

	public static void doNextPos(List<Sprite> sprites)
	{
		for(Sprite s : sprites)
		{
			s.nextPos();
		}
	}
}
