package ch.supsi.omegaanalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.python.google.common.primitives.Doubles;

import com.galliva.gallibrary.GLogManager;

public class StdDevCalculator
{
	/* configuration */
	private static String				WORKING_FOLDER		= "C:\\Users\\galliva\\Desktop\\out\\";
	private static int					SNRs					= 12;
	private static int					Ls						= 16;
	private static int					SMSSs					= 11;
	private static int					Ds						= 13;
	private static int					HEADERS_NUMBER		= 6;
	private static int					GENERATED_TRACKS	= 1000;
	private static String				CSV_DELIMITER		= ";";
	/* end configuration */

	private static ArrayList<String>	RESULTS				= new ArrayList<String>();
	private static boolean				SMSS					= true;

	public static void main(String[] args)
	{
		processFolder(WORKING_FOLDER + "SMSS/");
		printResult();

		SMSS = false;

		processFolder(WORKING_FOLDER + "D/");
		printResult();
	}

	private static void processFolder(String workingDirectory)
	{
		try
		{
			File dir = new File(workingDirectory);

			FilenameFilter filter = new FilenameFilter()
			{
				public boolean accept(File dir, String name)
				{
					return name.endsWith("csv");
				}
			};

			String[] children = dir.list(filter);

			Arrays.sort(children);

			if (children.length != SNRs * Ls)
				throw new Exception("wrong number of files");

			if (children != null)
			{
				for (int i = 0; i < children.length; i++)
				{
					processFile(workingDirectory + children[i]);
				}
			}
		}
		catch (Exception e)
		{
			GLogManager.log("can't process the folder:" + e, Level.SEVERE);
		}
	}

	private static void processFile(String file)
	{
		DataInputStream in = null;
		BufferedReader br  = null;
		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			String[] splitted = null;

			while ((strLine = br.readLine()) != null)
			{
				splitted = splitString(strLine);
				
				if (splitted != null)
					processRow(splitted);
			}
		}
		catch (Exception e)
		{
			GLogManager.log("can't process the file:" + e, Level.SEVERE);
		}
		finally
		{
			try
			{
				br.close();
				in.close();
			}
			catch (IOException e)
			{
			}
		}
	}

	private static String[] splitString(String strLine)
	{
		String[] splitted = new String[HEADERS_NUMBER + GENERATED_TRACKS];

		try
		{
			strLine = strLine.trim();

			splitted = strLine.split(CSV_DELIMITER);

			if (splitted.length != HEADERS_NUMBER + GENERATED_TRACKS)
			{
				GLogManager.log(String.format("wrong number of colums (%d): line skipped!", splitted.length), Level.WARNING);
				return null;
			}

			return splitted;
		}
		catch (Exception e)
		{
			GLogManager.log("Exception: line skipped!", Level.WARNING);
			return null;
		}
	}

	private static void processRow(String[] splitted) throws Exception
	{
		ArrayList<Double> values = new ArrayList<Double>();

		try
		{
			String[] valuesString = Arrays.copyOfRange(splitted, HEADERS_NUMBER, HEADERS_NUMBER + GENERATED_TRACKS);

			for (int i = 0; i < valuesString.length; i++)
				values.add(Double.valueOf(valuesString[i]));

		}
		catch (Exception e)
		{
			GLogManager.log("can't convert the values: line skipped!", Level.WARNING);
		}
		
		if(values.size() != GENERATED_TRACKS)
			throw new Exception("wrong number of values");
		
		DescriptiveStatistics stats = new DescriptiveStatistics(Doubles.toArray(values));

		double stddev = stats.getStandardDeviation();

		String result = splitted[1] + ";" + splitted[2] + ";" + splitted[3] + ";" + splitted[4] + ";" + stddev;

		RESULTS.add(result);
	}

	private static void printResult()
	{
		try
		{
			if (RESULTS.size() != SNRs * Ls * SMSSs * Ds)
				throw new Exception("wrong number of results");

			String filename = String.format("%s/%s_interpolation_data.csv", WORKING_FOLDER, SMSS ? "SMSS" : "D");
			
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);

			for (String string : RESULTS)
				out.write(string + "\n");

			out.close();
			
			RESULTS.clear();
		}
		catch (Exception e)
		{
		}
	}
}
