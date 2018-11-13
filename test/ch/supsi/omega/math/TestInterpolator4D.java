package ch.supsi.omega.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.supsi.omega.math.interpolation4d.FileLoader;
import ch.supsi.omega.math.interpolation4d.InterpolationType;
import ch.supsi.omega.math.interpolation4d.Interpolator4d;

public class TestInterpolator4D
{
	private int				pointsNo		= 12 * 11 * 6 * 11;

	@Test
	public void testFileLoader()
	{
		FileLoader fileLoader = new FileLoader();
		fileLoader.loadHyperPoints(InterpolationType.SMSS);

		assertEquals(pointsNo, fileLoader.getHyperPoints().size());
	}

	@Test
	public void testClosestPoints()
	{
		FileLoader fileLoader = new FileLoader();
		fileLoader.loadHyperPoints(InterpolationType.SMSS);
		
		Interpolator4d interpolator4d = new Interpolator4d(InterpolationType.SMSS);

		Double[] closestSNRPoints = interpolator4d.getClosestPoint(fileLoader.getSNRValues(), 1.50);

		assertEquals(1.291059, closestSNRPoints[0].doubleValue(), 0.0);
		assertEquals(1.99051,  closestSNRPoints[1].doubleValue(), 0.0);
		
		Double[] closestLPoints = interpolator4d.getClosestPoint(fileLoader.getLValues(), 95);

		assertEquals(90.0, closestLPoints[0].doubleValue(), 0.0);
		assertEquals(100.0,  closestLPoints[1].doubleValue(), 0.0);
	}
	
	@Test
	public void testNormalizer()
	{
		FileLoader fileLoader = new FileLoader();
		fileLoader.loadHyperPoints(InterpolationType.SMSS);
		
		Interpolator4d interpolator4d = new Interpolator4d(InterpolationType.SMSS);
		
		Double[] closestLPoints = interpolator4d.getClosestPoint(fileLoader.getLValues(), 95);
		
		assertEquals(0.5, interpolator4d.normalizeValue(95.0, closestLPoints), 0.0);
	}
	
	@Test
	public void testInterpolation()
	{
		Interpolator4d interpolator4d = new Interpolator4d(InterpolationType.SMSS);
				
		// 1.291059	 80 0.2 0.0075 0.0481021983259848
		assertEquals(0.0481021983259848, interpolator4d.interpolate(1.291059, 80, 0.2, 0.0075), 10E-10);
		// 19.326731 70 0.8 0.02   0.0833493616099158
		assertEquals(0.0833493616099158, interpolator4d.interpolate(19.326731, 70, 0.8, 0.02), 10E-10);		
		// 19.326731 100 0.4 0.005	0.0667260824385207
		assertEquals(0.0667260824385207, interpolator4d.interpolate(19.326731, 100, 0.4, 0.005), 10E-10);
	}
	
	@Test(expected = IllegalArgumentException.class)  
	public void testException()
	{
		Interpolator4d interpolator4d = new Interpolator4d(InterpolationType.SMSS);
		interpolator4d.interpolate(1.20, 80, 0.2, 0.0075);
	}
}
