package ch.supsi.omega.segmentation.trajectory;

import java.util.logging.Level;
import com.galliva.gallibrary.GLogManager;
import ch.supsi.omega.segmentation.TPoint;

/**
 * Normalizes a Trajectory.
 * @author galliva
 */
public final class TrajectoryNormalizer
{
	/**
	 * Normalizes a Trajectory.
	 * @param trajectory the Trajectory to be normalized
	 * @return the Trajectory normalized
	 */
	public static Trajectory normalizeTrajectory(Trajectory trajectory)
	{
		try
		{
			double maxX = Double.MIN_VALUE;
			double minX = Double.MAX_VALUE;
			double maxY = Double.MIN_VALUE;
			double minY = Double.MAX_VALUE;

			// find the minimum value in order to move the axys of the points
			for (TPoint p : trajectory.getPoints())
			{
				// minimun
				if (p.getX() < minX)
					minX = p.getX();
				if (p.getY() < minY)
					minY = p.getY();
			}

			// move the axys of the points
			for (TPoint p : trajectory.getPoints())
			{
				p.setX(p.getX() - minX);
				p.setY(p.getY() - minY);
			}

			// find the new maximum in order to normalize the points
			for (TPoint p : trajectory.getPoints())
			{
				// maximum
				if (Math.abs(p.getX()) > maxX)
					maxX = Math.abs(p.getX());

				if (Math.abs(p.getY()) > maxY)
					maxY = Math.abs(p.getY());
			}

			double max = (maxX >= maxY) ? maxX : maxY;

			// normalize the points
			for (TPoint p : trajectory.getPoints())
			{
				p.setX(p.getX() / max);
				p.setY(p.getY() / max);
			}

			// re-set the correct information
			maxX = Double.MIN_VALUE;
			minX = Double.MAX_VALUE;
			maxY = Double.MIN_VALUE;
			minY = Double.MAX_VALUE;

			for (TPoint p : trajectory.getPoints())
			{
				// maximum
				if (p.getX() > maxX)
					maxX = p.getX();
				if (p.getY() > maxY)
					maxY = p.getY();
				// minimun
				if (p.getX() < minX)
					minX = p.getX();
				if (p.getY() < minY)
					minY = p.getY();
			}

			trajectory.getTrajectoryInformation().setNormalizedMaxX(maxX);
			trajectory.getTrajectoryInformation().setNormalizedMaxY(maxY);
			trajectory.getTrajectoryInformation().setNormalizedMinX(minX);
			trajectory.getTrajectoryInformation().setNormalizedMinY(minY);
		}
		catch (Exception e)
		{
			GLogManager.log(String.format("%s: %s", "Error normalizing the trajectory", e.toString()), Level.SEVERE);
		}

		trajectory.getTrajectoryInformation().setNormalized(true);

		return trajectory;
	}
}
