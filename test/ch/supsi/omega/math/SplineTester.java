package ch.supsi.omega.math;

import ch.supsi.omega.math.SplineInterpolation;
import ch.supsi.omega.math.SplineInterpolation.Size;

public class SplineTester
{
	public static void main(String arg[])
	{
		System.out.println(SplineInterpolation.interpolate(1.7, Size.BIAS));
		System.out.println(SplineInterpolation.interpolate(5.6, Size.BIAS));
		System.out.println(SplineInterpolation.interpolate(6.3, Size.BIAS));
		System.out.println(SplineInterpolation.interpolate(8.3, Size.BIAS));
		System.out.println(SplineInterpolation.interpolate(9.9, Size.BIAS));
		
//		System.out.println(CubicSplineInterpolation.Interpolate(0.00001, Size.BIAS));
		
		/*
		System.out.println();
		
		System.out.println(CubicSplineInterpolation.Interpolate(1.7, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(2.0, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(2.5, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(5.0, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(6.0, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(7.8, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(9.0, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(10.3, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(12.0, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(13.4, Size.SIGMA));
		System.out.println(CubicSplineInterpolation.Interpolate(24.0, Size.SIGMA));
		*/
	}
}