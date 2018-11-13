package ch.supsi.omega.segmentation.label;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.omega.segmentation.trajectory.Trajectory;

/**
 * Manages the exporting of the trajectoreis labels.
 * @author galliva
 */
public abstract class LabelsExporter
{
	protected List<Trajectory>	trajectories	= new ArrayList<Trajectory>();

	public List<Trajectory> getTrajectories()
	{
		return trajectories;
	}

	public void setTrajectories(List<Trajectory> trajectories)
	{
		this.trajectories = trajectories;
	}

	abstract public void saveLabels();
}

// /**
// * Creates the labels array.
// * @param trajectories a List<Trajectory> of trajectories
// */
// private static void processLabelsArray(List<Trajectory> trajectories)
// {
// int[][] labels = createLabelsArray(trajectories);
// }
//
// /**
// * Creates the labels array.
// * @param trajectories a List<Trajectory> of trajectories
// * @return a bidimensioal array of int
// */
// private static int[][] createLabelsArray(List<Trajectory> trajectories)
// {
// int trajectoriesNumber = trajectories.size();
//
// int maxLenght = 0;
//
// // find the max lenght of the labels arrays
// for (int i = 0; i < trajectoriesNumber; i++)
// {
// if (trajectories.get(0).getLabels().length > maxLenght)
// maxLenght = trajectories.get(0).getLabels().length;
// }
//
// // allocate the label array
// int labels[][] = new int[trajectoriesNumber][maxLenght];
//
// // add each label[] to the array[][]
// int i = 0;
//
// for (Trajectory trajectory : trajectories)
// labels[i++] = trajectory.getLabels();
//
// return labels;
// }
