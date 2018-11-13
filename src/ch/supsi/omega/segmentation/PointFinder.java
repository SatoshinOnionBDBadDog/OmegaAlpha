package ch.supsi.omega.segmentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the closest TPoint to the mouse click.
 * @author galliva
 */
public class PointFinder
{
	/**
	 * Finds the closest TPoint to the mouse click.
	 * @param points a List<TPoint> of points
	 * @param x the mouse-click x coordinate
	 * @param y the mouse-click y coordinate
	 * @return the closest TPoint to the click
	 */
	public static int findPoint(List<TPoint> points, double x, double y)
	{
		List<Double> distances = new ArrayList<Double>();

		for (TPoint p : points)
		{
			double temp = Math.abs(x - p.getX()) * Math.abs(x - p.getX()) + Math.abs(y - p.getY()) * Math.abs(y - p.getY());

			distances.add(temp);
		}

		double minDistance = Double.MAX_VALUE;

		int i = 0;
		int index = 0;

		for (double d : distances)
		{
			if (d < minDistance)
			{
				minDistance = d;
				index = i;
			}
			i++;
		}

		return index;
	}

}
