package ch.supsi.omega.common.math;

import java.util.ArrayList;

import org.junit.Test;

import ch.supsi.omega.math.BilinearInterpolation;
import ch.supsi.omega.math.SurfacePoint;
import ch.supsi.omega.math.BilinearInterpolation.SurfaceType;

import junit.framework.TestCase;

public class BilinerInterpolationTest extends TestCase
{
	private static int KNOWN_POINTS = 12*11;

	private static ArrayList<SurfacePoint> knownPoints = null;
	
	public void setUp()
	{
		knownPoints = BilinearInterpolation.getKnownPoints(SurfaceType.SMSS);
	}
	
	@Test
	public void testKnowsPoints()
	{
		assertEquals(knownPoints.size(), KNOWN_POINTS);
	}
	
	@Test
	public void testClosestPoints()
	{
		ArrayList<SurfacePoint> closestPoints = BilinearInterpolation.getClosest4Points(knownPoints, 1.30, 69);
		
		assertEquals(closestPoints.get(0).SNR, 1.291059);
		assertEquals(closestPoints.get(0).L, 60);
		
		assertEquals(closestPoints.get(1).SNR, 1.291059);
		assertEquals(closestPoints.get(1).L, 70);
		
		assertEquals(closestPoints.get(2).SNR, 1.99051);
		assertEquals(closestPoints.get(2).L, 60);
		
		assertEquals(closestPoints.get(3).SNR, 1.99051);
		assertEquals(closestPoints.get(3).L, 70);
		
		double result = BilinearInterpolation.doInterpolation(1.30, 69, closestPoints);
		
		System.out.println(result);
	}
}
