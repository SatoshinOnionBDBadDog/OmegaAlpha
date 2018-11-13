package ch.supsi.omega.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;

public class TrajectoryVerifier
{
	/**
	 * Verifies if the trajectories in the trajectoriesDirectory have enough points to be processed by the TS algorithm.
	 * @param trajectoriesDirectory the directory with the trajectories
	 * @return true if the trajectories can be processed, false otherwise
	 */
	public static boolean verifyTrajectories(String trajectoriesDirectory)
	{
		try
		{
			File dir = new File(trajectoriesDirectory);

			FilenameFilter filter = new FilenameFilter()
			{
				public boolean accept(File dir, String name)
				{
					return name.endsWith(OmegaConstants.TRACKS_FILES_EXTENSION);
				}
			};

			String[] children = dir.list(filter);

			if (children == null)
				return false;
			else
			{
				for (int i = 0; i < children.length; i++)
				{
					if (!verifyTrajectory(String.format("%s%s%s", trajectoriesDirectory, System.getProperty("file.separator"), children[i])))
						return false;
				}
			}
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	private static boolean verifyTrajectory(String fileName)
	{
		File file = new File(fileName);
		Scanner scanner = null;

		int count = 0;
		try
		{
			scanner = new Scanner(file);

			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();

				// minimun lenght for a point: 5 (e.g.: '3 1 1')
				if (line.length() > 5)
					count++;
			}
		}
		catch (FileNotFoundException e)
		{
			return false;
		}

		if (count < OmegaConstants.TS_MINIMUN_NUMBER_OF_POINTS)
			return false;
		else
			return true;
	}

	/**
	 * Test verifyTrajectories.
	 * @param args none
	 */
	public static void main(String[] args)
	{
		System.out.println(verifyTrajectories("C:\\Users\\galliva\\Desktop\\trash\\Artificial_xyt"));
	}
}
