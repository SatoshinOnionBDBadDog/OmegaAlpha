package ch.supsi.omega.segmentation.trajectory;

public class TrajectoryInformation implements Cloneable
{
	private boolean	isNormalized			= false;
	private double		normalizedMaxX			= 0.0;
	private double		normalizedMaxY			= 0.0;
	private double		normalizedMinX			= 0.0;
	private double		normalizedMinY			= 0.0;
	private double		averageTrackLength	= 0.0;

	public boolean isNormalized()
	{
		return isNormalized;
	}

	public void setNormalized(boolean isNormalized)
	{
		this.isNormalized = isNormalized;
	}

	public double getNormalizedMaxX()
	{
		return normalizedMaxX;
	}

	public void setNormalizedMaxX(double normalizedMaxX)
	{
		this.normalizedMaxX = normalizedMaxX;
	}

	public double getNormalizedMaxY()
	{
		return normalizedMaxY;
	}

	public void setNormalizedMaxY(double normalizedMaxY)
	{
		this.normalizedMaxY = normalizedMaxY;
	}

	public double getNormalizedMinX()
	{
		return normalizedMinX;
	}

	public void setNormalizedMinX(double normalizedMinX)
	{
		this.normalizedMinX = normalizedMinX;
	}

	public double getNormalizedMinY()
	{
		return normalizedMinY;
	}

	public void setNormalizedMinY(double normalizedMinY)
	{
		this.normalizedMinY = normalizedMinY;
	}

	public double getAverageTrackLength()
	{
		return averageTrackLength;
	}

	public void setAverageTrackLength(double averageTrackLength)
	{
		this.averageTrackLength = averageTrackLength;
	}

	public TrajectoryInformation()
	{
	}

	/**
	 * Creates and returns a copy of this TrajectoryInformation.
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		TrajectoryInformation clone = (TrajectoryInformation) super.clone();
		return clone;
	}
}
