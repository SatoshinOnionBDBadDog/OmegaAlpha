package ch.supsi.omega.segmentation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class TrajectoryTest
{
	@SuppressWarnings("deprecation")
	@Test
	public void testEqualsObject()
	{
		TPoint pOne   = new TPoint(1, 1, 0.121, 0.12);
		TPoint pTwo   = new TPoint(1, 1, 0.124, 0.12);
		TPoint pThree = new TPoint(1, 1, 0.128, 0.12);
		
		List<TPoint> lOne = new ArrayList<TPoint>();
		lOne.add(pOne);
		lOne.add(pTwo);
		Trajectory tOne = new Trajectory(lOne);

		List<TPoint> lTwo = new ArrayList<TPoint>();
		lTwo.add(pOne);
		lTwo.add(pTwo);
		Trajectory tTwo = new Trajectory(lTwo);
		
		List<TPoint> lThree = new ArrayList<TPoint>();
		lThree.add(pOne);
		lThree.add(pThree);
		Trajectory tThree = new Trajectory(lThree);
		
		assertEquals(tOne, tTwo);
		assertFalse(tOne.equals(tThree));		
	}

}
