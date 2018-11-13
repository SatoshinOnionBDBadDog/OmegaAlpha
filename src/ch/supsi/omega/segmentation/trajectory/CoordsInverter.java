package ch.supsi.omega.segmentation.trajectory;

import java.util.List;

import ch.supsi.omega.segmentation.TPoint;

public class CoordsInverter
{
	public static Trajectory InvertCoordinates(Trajectory trajectory)
	{
		List<TPoint> points = trajectory.getPoints();
		
		double temp = 0.0;
		
		for (TPoint point : points)
		{
			temp = point.getX();
			
			point.setX(point.getY());
			point.setY(temp);
		}
		
		return trajectory;
	}
}
