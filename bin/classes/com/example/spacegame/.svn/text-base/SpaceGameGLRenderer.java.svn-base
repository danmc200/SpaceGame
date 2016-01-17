package com.example.spacegame;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.spacegame.opengl.sprites.EnemyLaserOG;
import com.example.spacegame.opengl.sprites.MineOG;
import com.example.spacegame.opengl.sprites.PlayerLaserOG;
import com.example.spacegame.opengl.sprites.PlayerSpaceshipOG;
import com.example.spacegame.opengl.sprites.SpaceshipZx1OG;
import com.example.spacegame.opengl.sprites.SpaceshipZx2OG;
import com.example.spacegame.opengl.sprites.SpriteOG;
import com.example.spacegame.sprites.EnemyLaser;
import com.example.spacegame.sprites.Mine;
import com.example.spacegame.sprites.PlayerLaser;
import com.example.spacegame.sprites.PlayerSpaceship;
import com.example.spacegame.sprites.SpaceshipZx1;
import com.example.spacegame.sprites.SpaceshipZx2;
import com.example.spacegame.sprites.Sprite;

import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class SpaceGameGLRenderer implements GLSurfaceView.Renderer
{
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private float mAngle;
    private PlayerSpaceshipOG player; 
    private List<Sprite> playerLasers;
    private List<List<Sprite>> allSprites = new ArrayList<List<Sprite>>();
    private int width,height;

	@Override
	public void onDrawFrame(GL10 gl) 
	{
		float[] scratch = new float[16];

		// Draw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		// Set the camera position (View matrix)
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1f, 0.0f);
		
		// Calculate the projection and view transformation
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		
		scratch = mMVPMatrix;

		Matrix.setIdentityM(scratch, 0);

		doTranslation(player.getPosition(), scratch, width, height);
		// Draw square
//		PlayerLaser pl = new PlayerLaser(0,0,height);
//		pl.init();
//		pl.draw(scratch);
		player.draw(scratch);
		
		for(List<Sprite> sprites : allSprites)
		{
			synchronized(sprites)
			{
				for(Sprite s : sprites)
				{
					if(!s.hasInit())
					{
						((SpriteOG)s).init();
					}
					scratch = mMVPMatrix;
					Matrix.setIdentityM(scratch, 0);
					Point pos = s.getPosition();
					doTranslation(pos, scratch, width, height);
					((SpriteOG)s).draw(scratch);
				}
			}
		}
		// Create a rotation for the triangle
		
		// Use the following code to generate constant rotation.
		// Leave this code out when using TouchEvents.
		// long time = SystemClock.uptimeMillis() % 4000L;
		// float angle = 0.090f * ((int) time);
		
//		Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
		
		// Combine the rotation matrix with the projection and camera view
		// Note that the mMVPMatrix factor *must be first* in order
		// for the matrix multiplication product to be correct.
//		Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
		
		// Draw triangle
	}
	
	private void doTranslation(Point pos, float[] scratch, int width, int height)
	{
		float y = ((float)pos.y - (height/2));
		y = y/(-height/2);
		float x = ((float)pos.x - (width/2));
		x = x/(width/2);
		Matrix.translateM(scratch, 0, x, y, 0f);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
		 // Adjust the viewport based on geometry changes,
        // such as screen rotation
		this.width = width;
		this.height = height;
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
		setSizes(width, height);
		player.init();
		
	}
	
	public void setSizes(int width, int height)
	{
		PlayerSpaceshipOG.setSize(new Point(
				PlayerSpaceship.playerImg.getWidth(),PlayerSpaceship.playerImg.getHeight()),
				new Point(width, height));
		SpaceshipZx1OG.setSize(new Point(
				SpaceshipZx1.shipImg.getWidth(),SpaceshipZx1.shipImg.getHeight()),
				new Point(width, height));
		SpaceshipZx2OG.setSize(new Point(
				SpaceshipZx2.shipImg.getWidth(),SpaceshipZx2.shipImg.getHeight()),
				new Point(width, height));
		MineOG.setSize(new Point(
				Mine.mineImg.getWidth(),Mine.mineImg.getHeight()),
				new Point(width, height));
		EnemyLaserOG.setSize(new Point(
				EnemyLaser.laserImg.getWidth(),EnemyLaser.laserImg.getHeight()),
				new Point(width, height));
		PlayerLaserOG.setSize(new Point(
				PlayerLaser.laserImg.getWidth(),PlayerLaser.laserImg.getHeight()),
				new Point(width, height));
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		//black
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public PlayerSpaceship getPlayerSprite()
	{
		return (PlayerSpaceship) player;
	}
	
	public void setPlayerSprite(PlayerSpaceshipOG player)
	{
		this.player = player;
	}
	
	public void setPlayerLasers(List<Sprite> playerLasers)
	{
		this.playerLasers = playerLasers;
	}
	
	public void setAllSprites(List<List<Sprite>> allSprites)
	{
		this.allSprites = allSprites;
	}

	public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
