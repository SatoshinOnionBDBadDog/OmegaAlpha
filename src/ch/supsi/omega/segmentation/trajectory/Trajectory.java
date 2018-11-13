package ch.supsi.omega.segmentation.trajectory;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.omega.segmentation.TPoint;

/**
 * Describes a trajectory.
 * @author galliva
 */
public final class Trajectory implements Cloneable
{
	private TrajectoryInformation	trajectoryInformation;
	private int							trajectoryId;
	private List<TPoint>				points;
	private int[]						labels;

	public TrajectoryInformation getTrajectoryInformation()
	{
		return trajectoryInformation;
	}

	public void setTrajectoryInformation(TrajectoryInformation trajectoryInformation)
	{
		this.trajectoryInformation = trajectoryInformation;
	}

	public int getTrajectoryId()
	{
		return trajectoryId;
	}

	public void setTrajectoryId(int trajectoryId)
	{
		this.trajectoryId = trajectoryId;
	}

	public List<TPoint> getPoints()
	{
		return points;
	}

	public void setPoints(List<TPoint> points)
	{
		this.points = points;
	}

	public int[] getLabels()
	{
		return labels;
	}

	public void setLabels(int[] labels)
	{
		this.labels = labels;
	}

	/**
	 * Initializes a Trajectory with no TPoints.
	 */
	public Trajectory()
	{
		this.points = new ArrayList<TPoint>();
		this.trajectoryInformation = new TrajectoryInformation();
	}

	/**
	 * Initializes a Trajectory with a List of TPoints.
	 * @param points a List<TPoint> of points
	 */
	public Trajectory(List<TPoint> points)
	{
		this.points = points;
		this.trajectoryInformation = new TrajectoryInformation();
	}

	/**
	 * Initializes a Trajectory with a List of TPoints and a
	 * TrajectoryInformation.
	 * @param points a List<TPoint> of points
	 * @param trajectoryInformation a TrajectoryInformation object
	 */
	public Trajectory(List<TPoint> points, TrajectoryInformation trajectoryInformation)
	{
		this.points = points;
		this.trajectoryInformation = trajectoryInformation;
	}

	/**
	 * Sizes the label[] array.
	 * @param labelSize the size of the label[] array
	 */
	public void sizeLabels(int labelSize)
	{
		this.labels = new int[labelSize - 1];
		resetLabels();
	}

	/**
	 * Sizes the label[] array, accordingly to the points dimension.
	 */
	public void sizeLabelsAccordinglyToPoints()
	{
		this.labels = new int[points.size() - 1];
		resetLabels();
	}

	/**
	 * Sets all the labels to 0.
	 */
	private void resetLabels()
	{
		for (int i = 0; i < labels.length; i++)
			labels[i] = 0;
	}

	/**
	 * Returns a string representation of the Trajectory.
	 */
	public String toString()
	{
		String temp = "";

		for (TPoint p : points)
		{
			temp = temp + String.format("[%5d %5d %+,2.3f %+,2.3f]", p.getID(), p.getFrame(), p.getX(), p.getY()) + System.getProperty("line.separator");
		}
		return temp;
	}

	/**
	 * Indicates whether some other Trajectory is "equal to" this one.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Trajectory)
		{
			Trajectory t = (Trajectory) obj;

			if (this.getPoints().size() != t.getPoints().size())
				return false;

			int i = 0;
			for (TPoint p : points)
			{
				if (!p.equals(t.getPoints().get(i)))
					return false;
				i++;
			}
			return true;
		}
		return false;
	}

	/**
	 * Creates and returns a copy of this Trajectory.
	 */
	public Object clone() throws CloneNotSupportedException
	{
		Trajectory clone = (Trajectory) super.clone();

		clone.trajectoryInformation = (TrajectoryInformation) trajectoryInformation.clone();

		List<TPoint> clonedPoints = new ArrayList<TPoint>(points.size());

		for (TPoint point : points)
			clonedPoints.add((TPoint) point.clone());

		clone.points = clonedPoints;
		return clone;
	}
}
