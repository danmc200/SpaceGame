package com.example.spacegame.sprites;

import java.util.ArrayList;
import java.util.List;

import com.example.spacegame.Path;
import com.example.spacegame.Calculators.ShotCalculator;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class Sprite 
{
    public Point imageSize;
    public List<Point[]> startAndEndPoints = new ArrayList<Point[]>();

	protected Path[] imagePaths;
    protected List<ShotCalculator> explosionCalc = new ArrayList<ShotCalculator>();
	public android.graphics.Path path;
	public android.graphics.Path tmpPath;
	public android.graphics.Path[] paths;
	public android.graphics.Path[] tmpPaths;
   
	private boolean isDead = false;
	private boolean isDying = false;
	protected Bitmap image = null;
	protected boolean hasInit = false;

	public abstract int getSpeed();
	public abstract Point getPosition();
	public abstract Bitmap getImage();
	public abstract void nextPos();
	public Point getSize()
	{
		int width = getImage().getWidth();
		int height = getImage().getHeight();
		return new Point(width, height);
	}
	public boolean hasInit()
	{
		return hasInit;
	}

	public int setDying(boolean dying){
		this.isDying = dying;
		return 0;
	}
	
	public boolean isDying(){
		return isDying;
	}
	
	public void setDead(boolean dead){
		this.isDead = dead;
	}
	
	public boolean isDead(){
		return isDead;
	}
}
