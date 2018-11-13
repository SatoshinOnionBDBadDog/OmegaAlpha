package ch.supsi.omega.segmentation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.segmentation.trajectory.TrajectoryNormalizer;

public class TrajectoryNormalizerTest
{

	@SuppressWarnings("deprecation")
	@Test
	public void testNormalizeTrajectory()
	{
		TPoint pOne   = new TPoint(1, 1, 0.03144156, 0.01291213);
		TPoint pTwo   = new TPoint(1, 1, -0.00475184, -0.0139383);
		TPoint pThree = new TPoint(1, 1, -0.0142567, -0.00638907);
		
		List<TPoint> lOne = new ArrayList<TPoint>();
		lOne.add(pOne);
		lOne.add(pTwo);
		lOne.add(pThree);
		Trajectory tOne = new Trajectory(lOne);
		
		tOne = TrajectoryNormalizer.normalizeTrajectory(tOne);
		
		
		TPoint pOneN   = new TPoint(1, 1, 0.001436825, 0.000844219);
		TPoint pTwoN   = new TPoint(1, 1, 0.000298848, 0);
		TPoint pThreeN = new TPoint(1, 1, 0, 0.00023736);
		
		List<TPoint> lOneN = new ArrayList<TPoint>();
		lOneN.add(pOneN);
		lOneN.add(pTwoN);
		lOneN.add(pThreeN);
		Trajectory tOneN = new Trajectory(lOneN);
		
		assertEquals(tOne, tOneN);	
	}

}
