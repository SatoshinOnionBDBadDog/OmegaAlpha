package ch.supsi.omega.segmentation.label;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.omega.segmentation.trajectory.Trajectory;

public abstract class LabelsImporter
{
	protected List<Trajectory>	trajectories	= new ArrayList<Trajectory>();

	protected String				fileName			= null;

	public List<Trajectory> getTrajectories()
	{
		return trajectories;
	}

	public void setTrajectories(List<Trajectory> trajectories)
	{
		this.trajectories = trajectories;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	abstract public void loadLabels();
}
