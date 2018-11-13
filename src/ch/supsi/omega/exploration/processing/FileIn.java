package ch.supsi.omega.exploration.processing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class FileIn
{
	/**
	 * Returns 2 arrays. The first contains the values of the x coordinates. The second contains the values of the y coordinates.
	 * @param trajectoryPath
	 * @return
	 * @throws IOException
	 */
	public static double[][] readTrajectory(String trajectoryPath) throws IOException
	{
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(trajectoryPath), "ASCII"));
		String line;
		ArrayList<Double> xl = new ArrayList<Double>();
		ArrayList<Double> yl = new ArrayList<Double>();
		while ((line = reader.readLine()) != null)
		{
			String[] strings = line.split(" ");
			if (!strings[0].isEmpty())
			{
				xl.add(Double.parseDouble(strings[1]));
				yl.add(Double.parseDouble(strings[2]));
			}
		}
		double[] x = new double[xl.size()];
		double[] y = new double[yl.size()];
		for (int i = 0; i < x.length; ++i)
		{
			x[i] = xl.get(i);
			y[i] = yl.get(i);
		}
		
		reader.close();
		
		return new double[][] { x, y };
	}

	/**
	 * Returns as many arrays as there are trajectories Each (sub)array contains the labels of the segments in one trajectory.
	 * @param segmentationPath
	 * @return
	 * @throws IOException
	 */
	public static int[][] readSegmentation(String segmentationPath) throws IOException
	{
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(segmentationPath), "ASCII"));
		reader.readLine(); // skip line containing the length
		ArrayList<int[]> labels = new ArrayList<int[]>();
		String line;
		while ((line = reader.readLine()) != null)
		{
			String[] strings = line.split(" ");
			int[] labels_l = new int[strings.length - 1];
			for (int i = 0; i < labels_l.length; ++i)
				labels_l[i] = Integer.parseInt(strings[i + 1]);
			labels.add(labels_l);
		}
		
		reader.close();
		
		return labels.toArray(new int[labels.size()][]);
	}

}
