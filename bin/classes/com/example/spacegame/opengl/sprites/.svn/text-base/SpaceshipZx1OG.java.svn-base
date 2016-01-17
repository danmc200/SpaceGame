package com.example.spacegame.opengl.sprites;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Point;
import android.opengl.GLES20;

import com.example.spacegame.Path;
import com.example.spacegame.SpaceGameGLRenderer;
import com.example.spacegame.shooting.ShootingHabits;
import com.example.spacegame.sprites.SpaceshipZx1;

public class SpaceshipZx1OG extends SpaceshipZx1
implements SpriteOG
{
	public SpaceshipZx1OG(ShootingHabits habits, Path path) 
	{
		super(habits, path);
	}

	//openGL
	private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // The matrix must be included as a modifier of gl_Position.
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
    static float textureCoords[];
//	    	{
//	            -0.25f,  0.25f, 0.0f,   // top left
//	            -0.25f, -0.25f, 0.0f,   // bottom left
//	             0.25f, -0.25f, 0.0f,   // bottom right
//	             0.25f,  0.25f, 0.0f }; // top right

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[];
//	    	{
//	            -0.25f,  0.25f, 0.0f,   // top left
//	            -0.25f, -0.25f, 0.0f,   // bottom left
//	             0.25f, -0.25f, 0.0f,   // bottom right
//	             0.25f,  0.25f, 0.0f }; // top right

    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
//    {
//	    imagePaths = new Path[]
//	    {
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{227.26611,237.4800565}},{PathIterator.SEG_CUBICTO, new Double[]{525.40682,247.470424,416.83477,13.26022,416.83477,13.26022}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{226.96302,175.134145}},{PathIterator.SEG_CUBICTO, new Double[]{439.30559,177.574466,415.19101,13.58449,415.19101,13.58449}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{225.2063,235.2620505}},{PathIterator.SEG_CUBICTO, new Double[]{463.27542,231.3618535,436.23922,493.45523,436.23922,493.45523}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{225.2939,235.6736175}},{PathIterator.SEG_CUBICTO, new Double[]{-12.775293,231.7734205,14.260967,493.86679,14.260967,493.86679}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{224.86671,328.05697}},{PathIterator.SEG_CUBICTO, new Double[]{462.77206,324.826294,435.75442,541.92848,435.75442,541.92848}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{224.95479,329.10996}},{PathIterator.SEG_CUBICTO, new Double[]{-12.950573,325.901724,14.067087,541.49541,14.067087,541.49541}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{229.93509,236.3366855}},{PathIterator.SEG_CUBICTO, new Double[]{-68.205623,246.327054,40.366427,12.11685,40.366427,12.11685}}}, 1, 1/10f),
//		    new Path(new Object [][]{{PathIterator.SEG_MOVETO, new Double[]{230.23818,173.990762}},{PathIterator.SEG_CUBICTO, new Double[]{17.895607,176.431092,42.010187,12.44112,42.010187,12.44112}}}, 1, 1/10f),
//		    new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{181.37632,335.81072}},{PathIterator.SEG_CUBICTO, new Double[]{225.66203,468.66786,268.51917,340.09643,268.51917,340.09643}}}, 1, 1/10f),
//	    };
//    }

    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

	public void init()
	{
		hasInit = true;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = SpaceGameGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = SpaceGameGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }
	
	public void draw(float[] mvpMatrix) 
	{
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

	public static void setSize(Point size, Point screenSize)
    {
    	float x = (float)size.x / (float)screenSize.x;
    	float y = (float)size.y / (float)screenSize.y;
	    textureCoords = new float[]{
	            -x,  y, 0.0f,   // top left
	            -x, -y, 0.0f,   // bottom left
	             x, -y, 0.0f,   // bottom right
	             x,  y, 0.0f }; // top right
	
	    // number of coordinates per vertex in this array
	    squareCoords = new float[]{
	            -x,  y, 0.0f,   // top left
	            -x, -y, 0.0f,   // bottom left
	             x, -y, 0.0f,   // bottom right
	             x,  y, 0.0f }; // top right
    }
}
