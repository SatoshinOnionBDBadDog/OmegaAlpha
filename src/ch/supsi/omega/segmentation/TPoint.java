package ch.supsi.omega.segmentation;

import java.text.DecimalFormat;

/**
 * Describes a point of a Trajectory.
 * @author galliva
 */
public final class TPoint implements Cloneable
{
	/**
	 * Particle’s frame ID
	 */
	private int		ID;

	/**
	 * Particle’s frame ID from the DLL
	 */
	private int		frame;

	/**
	 * Particle’s X position
	 */
	private double	x;
	/**
	 * Particle’s Y position
	 */
	private double	y;

	/**
	 * Particle’s SNR values
	 */
	private double	SNR;

	/**
	 * Particle’s total signal
	 */
	private double	totalSignal;

	/**
	 * Particle’s signal values count
	 */
	private int		cntSignal;

	public int getID()
	{
		return ID;
	}

	public void setID(int iD)
	{
		ID = iD;
	}

	public int getFrame()
	{
		return frame;
	}

	public void setFrame(int frame)
	{
		this.frame = frame;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getSNR()
	{
		return SNR;
	}

	public void setSNR(double SNR)
	{
		this.SNR = SNR;
	}

	public double getTotalSignal()
	{
		return totalSignal;
	}

	public void setTotalSignal(double totalSignal)
	{
		this.totalSignal = totalSignal;
	}

	public int getCntSignal()
	{
		return cntSignal;
	}

	public void setCntSignal(int cntSignal)
	{
		this.cntSignal = cntSignal;
	}

	/**
	 * Initializes a TPoint.
	 */
	public TPoint()
	{
	}

	/**
	 * Initializes a TPoint.
	 * @param ID the ID of the point is the trajectory
	 * @param frame the frame number in the image
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @deprecated As of 2012-06-18, replaced by {@link #public TPoint(int ID, int frame, double x, double y, double SNR)}
	 */
	@Deprecated
	public TPoint(int ID, int frame, double x, double y)
	{
		this.ID = ID;
		this.frame = frame;
		this.x = x;
		this.y = y;
	}

	/**
	 * Initializes a TPoint.
	 * @param ID the ID of the point is the trajectory
	 * @param frame the frame number in the image
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param SNR the SNR value
	 */
	@Deprecated
	public TPoint(int ID, int frame, double x, double y, double SNR)
	{
		this.ID = ID;
		this.frame = frame;
		this.x = x;
		this.y = y;
		this.SNR = SNR;
	}

	/**
	 * Initializes a TPoint.
	 * @param ID the ID of the point is the trajectory
	 * @param frame the frame number in the image
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param SNR the SNR value
	 * @param totalSignal the total signal
	 * @param cntSignal the signal values count
	 */
	public TPoint(int ID, int frame, double x, double y, double SNR, double totalSignal, int cntSignal)
	{
		this.ID = ID;
		this.frame = frame;
		this.x = x;
		this.y = y;
		this.SNR = SNR;
		this.totalSignal = totalSignal;
		this.cntSignal = cntSignal;
	}

	/**
	 * Indicates whether some other TPoint is "equal to" this one.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof TPoint)
		{
			TPoint p = (TPoint) obj;

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			if (twoDForm.format(this.getX()).equals(twoDForm.format(p.getX())))
				return true;
			return false;
		}
		return false;
	}

	/**
	 * Creates and returns a copy of this TPoint.
	 */
	public Object clone() throws CloneNotSupportedException
	{
		TPoint clone = (TPoint) super.clone();
		return clone;
	}
}
