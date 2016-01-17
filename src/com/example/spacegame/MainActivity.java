package com.example.spacegame;

import com.example.spacegame.util.SpaceGameConstants;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display dis = getWindowManager().getDefaultDisplay();
        Point size = new Point();
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2)
        {
        	size.x = dis.getWidth();
        	size.y = dis.getHeight();
        }
//        else
        {
//	        dis.getSize(size);
        }
		View view = new SpaceGameView(this, null);
		SpaceGameConstants.SCREEN_HEIGHT = size.y;
		SpaceGameConstants.SCREEN_WIDTH = size.x;
        setContentView(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    }

	public void drawCycle(final View view, final double drawInterval) {
		Runnable task = new Runnable() 
		{
			@Override
			public void run() 
			{
				while(true)
				{
					try {
						Thread.sleep((long) (drawInterval * 1000));
						view.invalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread t = new Thread(task);
		t.start();
	}
    
}
