package ch.supsi.omega.exploration.processing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.charts.DSFrequenciesChart;

import com.galliva.gallibrary.GLogManager;

public class Type2Processor
{
	/**
	 * Counts the frequency for each motion type.
	 */
	private int						MotionsCounter[]		= new int[5];

	/**
	 * The JFreeChart chart to be displayed.
	 */
	private DSFrequenciesChart	dSFrequenciesChart	= new DSFrequenciesChart();

	public int[] getMotionsCounter()
	{
		return MotionsCounter;
	}
	
	public DSFrequenciesChart getdSFrequenciesChart()
	{
		return dSFrequenciesChart;
	}

	/**
	 * Resets the motions counters.
	 */
	public void resetCounters()
	{
		MotionsCounter[0] = 0;
		MotionsCounter[1] = 0;
		MotionsCounter[2] = 0;
		MotionsCounter[3] = 0;
		MotionsCounter[4] = 0;
	}

	/**
	 * Process every entry of the hashmap.
	 * @param map a <Dataset name, List<Folders>> hashmap
	 */
	public void processDataset(HashMap<String, List<String>> map)
	{
		// for each dataset
		Iterator<Entry<String, List<String>>> it = map.entrySet().iterator();

		while (it.hasNext())
		{
			Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) it.next();

			// get the name of the dataset and his folders
			String datasetName = pairs.getKey();
			List<String> datasetFolders = pairs.getValue();

			GLogManager.log(String.format("Processing dataset %s, containing %d folders...", datasetName, datasetFolders.size()), Level.INFO);

			// reset the motions counters
			resetCounters();

			for (String folder : datasetFolders)
			{
				processFolder(folder);
				dSFrequenciesChart.addDataToChart(datasetName, MotionsCounter);
			}
		}
	}

	/**
	 * Process every folder of a dataset.
	 * @param folder the folder path
	 */
	public void processFolder(String folder)
	{
		// filter the current folder, look for ".dat" files
		File dir = new File(folder);

		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.endsWith(OmegaConstants.LABELS_FILES_EXTENSION);
			}
		};

		String[] children = dir.list(filter);

		if (children != null)
		{
			for (int i = 0; i < children.length; i++)
			{
				processFile(String.format("%s\\%s", folder, children[i]));
			}
		}
	}

	/**
	 * Process every file of a folder.
	 * @param labelsPath the file path
	 */
	private void processFile(String labelsPath)
	{
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(labelsPath);
		}
		catch (FileNotFoundException e)
		{
		}

		if (fileInputStream != null)
			readLabelsFile(fileInputStream);
	}

	/**
	 * Reads a file and counts the motion frequency.
	 * @param fstream the file path
	 */
	private void readLabelsFile(FileInputStream fstream)
	{
		try
		{
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// skip first line
			String strLine = br.readLine();

			GLogManager.log(String.format("processing %s lines...", strLine), Level.INFO);

			// read all the lines of the file
			while ((strLine = br.readLine()) != null)
			{
				String line[] = strLine.split(" ");

				// skip first column
				for (int i = 1; i < line.length; i++)
				{
					// calculate motions frequency
					if (line[i].equals("0"))
						MotionsCounter[0]++;
					if (line[i].equals("1"))
						MotionsCounter[1]++;
					else if (line[i].equals("2"))
						MotionsCounter[2]++;
					else if (line[i].equals("3"))
						MotionsCounter[3]++;
					else if (line[i].equals("4"))
						MotionsCounter[4]++;
				}
			}

			in.close();
		}
		catch (Exception e)
		{
		}
	}
}
