package com.example.spacegame;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Point;

import com.example.spacegame.Calculators.Repeats;
import com.example.spacegame.shooting.AverageJoe;
import com.example.spacegame.shooting.Granny;
import com.example.spacegame.sprites.SpaceshipZx1;
import com.example.spacegame.sprites.SpaceshipZx2;
import com.example.spacegame.util.SpaceGameConstants;

public class Waves 
{
	// A wave is screen cycle count and ship to deploy
	//template: new Path(new Object[][]{}),
	private static final int
		SPEED_DEFAULT = 7,
		SPEED_SLOW = 1;
	private static final Path[] MOVEMENTS = 
	{	
			//0
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{169.52252,2.88526}},{PathIterator.SEG_CUBICTO, new Double[]{-0.30804739,854.25057,408.54331,1282.82902,408.54331,1282.82882}}}, SPEED_DEFAULT),
			//1
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{423.46041,2.88314}},{PathIterator.SEG_CUBICTO, new Double[]{361.75974,934.13127,685.68829,1291.40262,685.68829,1291.40182}}}, SPEED_DEFAULT ),
			//2
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{640.0,5.7142857}},{PathIterator.SEG_CUBICTO, new Double[]{700.0,811.42857,62.857143,1262.8571,62.857143,1262.857}}}, SPEED_DEFAULT),
			//3
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{145.71429,11.428571}},{PathIterator.SEG_CUBICTO, new Double[]{520.0,711.428569,-154.2857186,634.285719,77.14285,794.28571}},{PathIterator.SEG_CUBICTO, new Double[]{308.57142,954.28571,602.85714,877.14285,471.42857,1048.57141}},{PathIterator.SEG_CUBICTO, new Double[]{340.0,1220.00001,28.57142,1251.42851,28.57142,1251.42841}}}, SPEED_DEFAULT),
			//4 
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{148.57143,11.428571}},{PathIterator.SEG_CUBICTO, new Double[]{45.714286,677.14286,108.57143,717.14286,108.57143,717.14286}},{PathIterator.SEG_CUBICTO, new Double[]{108.57143,717.14286,360.0,865.71429,302.85714,1022.8571}},{PathIterator.SEG_CUBICTO, new Double[]{245.71429,1180.0,94.285714,1282.8571,94.285714,1282.8571}}}, SPEED_DEFAULT),
			//5
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{551.86014,7.14288}},{PathIterator.SEG_CUBICTO, new Double[]{654.71729,672.85718,591.86014,712.85718,591.86014,712.85718}},{PathIterator.SEG_CUBICTO, new Double[]{591.86014,712.85718,340.43157,861.42861,397.57443,1018.57141}},{PathIterator.SEG_CUBICTO, new Double[]{454.71728,1175.71431,606.14586,1278.57142,606.14586,1278.57142}}}, SPEED_DEFAULT),
			//6
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{-5.7142857,145.71429}},{PathIterator.SEG_CUBICTO, new Double[]{517.14286,494.28571,911.42857,248.57143,911.42857,248.57143}},{PathIterator.SEG_CUBICTO, new Double[]{911.42857,248.57143,917.14286,465.71429,742.85714,488.57143}},{PathIterator.SEG_CUBICTO, new Double[]{568.57143,511.42857,511.42857,1000.0,222.85714,780.0}},{PathIterator.SEG_CUBICTO, new Double[]{-65.714286,560.0,117.14286,425.71429,268.57143,451.42857}},{PathIterator.SEG_CUBICTO, new Double[]{420.0,477.14286,497.14286,482.85714,642.85714,688.57143}},{PathIterator.SEG_CUBICTO, new Double[]{788.57143,894.28571,594.28571,1294.2857,594.28571,1294.2857}}}, SPEED_DEFAULT),
			//7
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{325.71429,2.8571429}}, {PathIterator.SEG_LINETO, new Double[]{320.0,1271.428}}}, SPEED_DEFAULT),
			//8
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{457.14286,2.8571429}}, {PathIterator.SEG_LINETO, new Double []{457.0,1274.285757}}}, SPEED_DEFAULT),
			//9 wave1_8 (left)
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{-4.2857167e-08,340.0}},{PathIterator.SEG_CUBICTO, new Double[]{385.71428,517.14285,420.0,297.142853,420.0,297.142853}},{PathIterator.SEG_CUBICTO, new Double[]{420.0,297.142853,451.42857,225.7142829,397.14285,157.142857}},{PathIterator.SEG_CUBICTO, new Double[]{342.85714,88.57143,228.57143,160.0,228.57143,160.0}},{PathIterator.SEG_CUBICTO, new Double[]{228.57143,160.0,142.85714,200.0,162.85714,268.571433}},{PathIterator.SEG_CUBICTO, new Double[]{182.85714,337.14285,257.14285,334.28571,257.14285,334.28571}}}, SPEED_DEFAULT),
			//10 wave1_8 (right)
			new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{805.95308,340.39014}},{PathIterator.SEG_CUBICTO, new Double[]{420.2388,517.53298,385.95308,297.532986,385.95308,297.532986}},{PathIterator.SEG_CUBICTO, new Double[]{385.95308,297.532986,354.52451,226.1044129,408.81023,157.532993}},{PathIterator.SEG_CUBICTO, new Double[]{463.09594,88.96156,577.38165,160.390133,577.38165,160.390133}},{PathIterator.SEG_CUBICTO, new Double[]{577.38165,160.390133,663.09594,200.390133,643.09594,268.961566}},{PathIterator.SEG_CUBICTO, new Double[]{623.09594,337.53299,548.81023,334.67585,548.81023,334.67585}}}, SPEED_DEFAULT),
	};
	
	private static final Path[] TOP_MOVEMENTS = 
	{
		//0 wave1_7
		new Path(new Object[][]{{PathIterator.SEG_MOVETO, new Double[]{668.57143,20.0}},{PathIterator.SEG_CUBICTO, new Double[]{605.71429,319.999997,491.42857,291.428567,491.42857,291.428567}},{PathIterator.SEG_CUBICTO, new Double[]{491.42857,291.428567,308.57143,254.285717,428.57143,102.85714}},{PathIterator.SEG_CUBICTO, new Double[]{548.57143,-48.57143,668.57143,20.0,668.57143,20.0}}}, SPEED_DEFAULT),
		//1
//		new Path(new Object[][]{}),
	};
	
	Point [][] paths = new Point[][]
	{
		{
			new Point(600, 0), new Point(300, 400), new Point(250, 200)
		},
		{
			new Point(100, 0), new Point(200, 300), new Point(400, 350)
		}
	};

	private static Repeats repeatsW2_3 = new Repeats(2,3, 30);
	private final int
		W2_1 = 0,
		W2_2 = 100,
		W2_3 = 350,
		W2_4 = 800;
	private int 
		w2_1 = W2_1,
		w2_1Inc = 15,
		w2_2 = W2_2,
		w2_2Inc = 15,
		w2_3 = W2_3,
		w2_3Inc = 15,
		w2_4 = W2_4,
		w2_4Inc = 15;
	private static Map<Integer, Path[]> generatedPathsW2_3 = new HashMap<Integer, Path[]>();
	static
	{
		for(int i = 1; i <= 9; i++)
		{
			Path[] paths = new Path[5];
			paths[0] = MOVEMENTS[9];
			paths[1] = makeLinePath(40 * i, 400, MOVEMENTS[9], SPEED_DEFAULT);
			paths[2] = makeLinePath((40 * i) + 15, 400, paths[1], SPEED_SLOW);
			paths[3] = makeLinePath((40 * i), 400, paths[2], SPEED_SLOW);
			paths[4] = makeLinePath((40*i), SpaceGameConstants.SCREEN_HEIGHT, paths[3], SPEED_DEFAULT);
			generatedPathsW2_3.put(i, paths);
		}
	}
	
	private Wave [] waves = 
	{
//		new Wave(//wave 1
//			new Object [][]
//			{
//				{200, new Mine(500, 0)},
//				{310, new Mine(100, 0)},
//				{350, new Mine(200, 0)},
//				{430, new Mine(300, 0)},
//				{240, new Mine(700, 0)},
//	
//				{100, new Mine(600, 0)},
//				{210, new Mine(190, 0)},
//				{250, new Mine(250, 0)},
//				{330, new Mine(400, 0)},
//				{140, new Mine(700, 0)},
//	
//				{220, new Mine(500, 0)},
//				{330, new Mine(100, 0)},
//				{380, new Mine(200, 0)},
//				{450, new Mine(300, 0)},
//				{260, new Mine(700, 0)},
//	
//				{120, new Mine(500, 0)},
//				{230, new Mine(100, 0)},
//				{280, new Mine(200, 0)},
//				{350, new Mine(300, 0)},
//				{160, new Mine(700, 0)},
//
//				{500, new Mine(500, 0)},
//				{610, new Mine(100, 0)},
//				{650, new Mine(200, 0)},
//				{730, new Mine(300, 0)},
//				{540, new Mine(700, 0)},
//	
//				{400, new Mine(600, 0)},
//				{510, new Mine(190, 0)},
//				{550, new Mine(250, 0)},
//				{630, new Mine(400, 0)},
//				{440, new Mine(700, 0)},
//	
//				{520, new Mine(500, 0)},
//				{630, new Mine(100, 0)},
//				{680, new Mine(200, 0)},
//				{750, new Mine(300, 0)},
//				{560, new Mine(700, 0)},
//	
//				{420, new Mine(500, 0)},
//				{530, new Mine(100, 0)},
//				{580, new Mine(200, 0)},
//				{650, new Mine(300, 0)},
//				{460, new Mine(700, 0)},
//			}
//		),
		//wave 2
		new Wave(
			new Object [][]
			{
				{w2_1, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[4])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[4])},
	
				{w2_1 = W2_1, new SpaceshipZx1(new Granny(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[5])},
				{w2_1 += w2_1Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[5])},
	
				{w2_4, new SpaceshipZx1(new Granny(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[7])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[7])},

				{w2_4 = W2_4, new SpaceshipZx1(new Granny(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new Granny(), MOVEMENTS[8])},
				{w2_4 += w2_4Inc, new SpaceshipZx1(new AverageJoe(), MOVEMENTS[8])},

				{w2_2 = W2_2, new SpaceshipZx2(new Granny(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new Granny(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new Granny(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx2(new AverageJoe(), MOVEMENTS[6])},
				{w2_2 += w2_2Inc, new SpaceshipZx1(new AverageJoe(), TOP_MOVEMENTS[0], 1000)},

				{w2_3 = W2_3, new SpaceshipZx2(new AverageJoe(), generatedPathsW2_3.get(1),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new Granny(), generatedPathsW2_3.get(2),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new Granny(), generatedPathsW2_3.get(3),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new AverageJoe(), generatedPathsW2_3.get(4),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new Granny(), generatedPathsW2_3.get(5),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new Granny(), generatedPathsW2_3.get(6),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new AverageJoe(), generatedPathsW2_3.get(7),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new Granny(), generatedPathsW2_3.get(8),repeatsW2_3)},
				{w2_3 += w2_3Inc, new SpaceshipZx2(new AverageJoe(), generatedPathsW2_3.get(9),repeatsW2_3)},
			}
		)
	};
	
	public Wave getWave(int num)
	{
		return waves[num];
	}
	
	public int getWaveCount()
	{
		return waves.length;
	}
	
	private static Path makeLinePath(double x, double y, Path lastPath, int speed)
	{
		return makeLinePath(x, y, lastPath.getLastPoint(), speed);
	}
	private static Path makeLinePath(double x, double y, Point lastPoint, int speed)
	{
		Path p = new Path(new Object[][]{
			{PathIterator.SEG_MOVETO, new Double[]{(double) lastPoint.x, (double) lastPoint.y}},
			{PathIterator.SEG_LINETO, new Double[]{x,y}}}, speed);
		return p;
	}
	
}
