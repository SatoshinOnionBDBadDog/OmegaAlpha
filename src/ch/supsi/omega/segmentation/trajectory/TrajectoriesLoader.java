package ch.supsi.omega.segmentation.trajectory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.segmentation.TPoint;

import com.galliva.gallibrary.GLogManager;

/**
 * Loads the trajectories from file.
 * @author galliva
 */
public final class TrajectoriesLoader
{
	private String			workingDirectory	= null;
	private String			filesExtension		= null;

	private List<String>	files					= new ArrayList<String>();

	public String getWorkingDirectory()
	{
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}

	public String getFilesExtension()
	{
		return filesExtension;
	}

	public void setFilesExtension(String filesExtension)
	{
		this.filesExtension = filesExtension;
	}

	public TrajectoriesLoader()
	{
	}

	public TrajectoriesLoader(String path, String extension)
	{
		this.workingDirectory = path;
		this.filesExtension = extension;
	}

	/**
	 * Loads all the trajectories in the workingDirectory directory.
	 * @return a List<Trajectory> of trajectories
	 */
	public List<Trajectory> loadTrajectories()
	{
		List<Trajectory> trajectories = new ArrayList<Trajectory>();

		getFiles();

		int id = 0;
		for (String file : files)
		{
			id++;

			List<TPoint> points = processFile(workingDirectory + file);
			if (points != null && points.size() > 0)
			{
				Trajectory trajectory = new Trajectory();
				trajectory.setTrajectoryId(id);
				trajectory.setPoints(points);
				trajectory.sizeLabelsAccordinglyToPoints();
				trajectory.getTrajectoryInformation().setNormalized(false);

				trajectories.add(trajectory);
			}
		}
				
		// if we need to invert X and Y (SPT DLL "bug"!)
		if(OmegaConstants.INVERT_TRAJECTORY_POINTS)
		{
			for(Trajectory trajectory : trajectories)
				CoordsInverter.InvertCoordinates(trajectory);
		}

		return trajectories;
	}

	/**
	 * Gets all the files in the working directory.
	 */
	private void getFiles()
	{
		try
		{
			File dir = new File(workingDirectory);

			FilenameFilter filter = new FilenameFilter()
			{
				public boolean accept(File dir, String name)
				{
					return name.endsWith(filesExtension);
				}
			};

			String[] children = dir.list(filter);

			if (children == null)
			{
				// nothing we can do
			}
			else
			{
				for (int i = 0; i < children.length; i++)
				{
					files.add(children[i]);
				}
			}

		}
		catch (Exception e)
		{
			GLogManager.log("error loading trajectories: " + e.toString(), Level.SEVERE);
			// nothing we can do, files.size() will be 0 and no trajectories will be loaded
		}
	}

	/**
	 * Process a trajectory file.
	 * @param file the file to be processed
	 * @return a List<Point> of Points
	 */
	private List<TPoint> processFile(String file)
	{
		List<TPoint> points = new ArrayList<TPoint>();

		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			int pointID = 1;
			while ((strLine = br.readLine()) != null)
			{
				String[] splitted = splitString(strLine);

				if (splitted != null)
				{
					try
					{
						int frame = Integer.valueOf(splitted[0].trim());
						double x   = Double.valueOf(splitted[1].trim());
						double y   = Double.valueOf(splitted[2].trim());
						
						// the new DLL has also stats included
						double SNR = 0.0;
						double totalSig = 0.0;
						int cntSig = 0;
						
						if(splitted.length > 3)
						{
							SNR 		= Double.valueOf(splitted[7].trim());
							totalSig = Double.valueOf(splitted[10].trim());
							cntSig 	= Integer.valueOf(splitted[11].trim());
						}

						TPoint p = new TPoint(pointID, frame, x, y, SNR, totalSig, cntSig);
						points.add(p);
						pointID++;
					}
					catch (NumberFormatException ee)
					{
						GLogManager.log("point skipped: " + ee.toString(), Level.WARNING);
						continue;
					}
				}
			}

			in.close();

			return points;
		}
		catch (Exception e)
		{
			// nothing we can do...
			return null;
		}
	}

	private String[] splitString(String strLine)
	{
		String[] splitted = new String[20];

		try
		{
			strLine = strLine.trim();
			
			if(strLine.length() < 5)
				return null;
			
			splitted = strLine.split(OmegaConstants.TRACKS_FILES_LINE_SEPARATOR);

			if (splitted.length < 3)
			{
				GLogManager.log("point skipped: not enough information", Level.WARNING);
				return null;
			}

			return splitted;
		}
		catch (Exception e)
		{
			GLogManager.log("point skipped: " + e.toString(), Level.WARNING);
			return null;
		}
	}
}
