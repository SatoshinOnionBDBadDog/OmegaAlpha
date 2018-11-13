package ch.supsi.omega.segmentation;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class LabelTest extends TestCase
{
	Trajectory			tOne;
	Trajectory			tTwo;

	List<Trajectory>	trajectories	= new ArrayList<Trajectory>();

	@SuppressWarnings("deprecation")
	public void setUp()
	{
		TPoint one   = new TPoint(1, 1, 0.121, 0.12);
		TPoint two   = new TPoint(1, 1, 0.124, 0.12);
		TPoint three = new TPoint(1, 1, 0.128, 0.12);
		TPoint four  = new TPoint(1, 1, 0.128, 0.12);

		// Trajectory ONE
		List<TPoint> lOne = new ArrayList<TPoint>();
		lOne.add(one);
		lOne.add(two);
		lOne.add(three);
		lOne.add(four);

		tOne = new Trajectory(lOne);
		tOne.sizeLabelsAccordinglyToPoints();

		int[] labels = new int[3];
		labels[0] = 1;
		labels[1] = 0;
		labels[2] = 1;

		tOne.setLabels(labels);
	
		// Trajectory TWO
		List<TPoint> lTwo = new ArrayList<TPoint>();
		lTwo.add(one);
		lTwo.add(two);
		lTwo.add(three);
		lTwo.add(four);

		tTwo = new Trajectory(lOne);
		tTwo.sizeLabelsAccordinglyToPoints();

		labels = new int[3];
		labels[0] = 1;
		labels[1] = 1;

		tTwo.setLabels(labels);

		trajectories.add(tOne);
		trajectories.add(tTwo);
	}

	@Test
	public void testSize()
	{
		assertEquals(tOne.getLabels().length, 3);
	}
	
	@Test
	public void testExporter()
	{
		int[][] labels = new int[2][3];
		labels[0][0] = 1;
		labels[0][1] = 0;
		labels[0][2] = 1;
		labels[1][0] = 1;
		labels[1][1] = 1;
		
//		int[][] exported = LabelsExporter.createLabelsArray(trajectories);
//		
//		for(int i = 0; i < 2; i ++)
//			for(int j = 1; j <3; j ++)
//				assertEquals(labels[i][j], exported[i][j]);
	}

}
