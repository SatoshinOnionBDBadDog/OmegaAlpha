package ch.supsi.omegaanalysis.errorestimation;

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

public class Aggregator
{
	String	Folder;
	double	L;
	double	SMSS;
	double	D;

	public Aggregator(String folder, double l, double sMSS, double d)
	{
		super();
		Folder = folder;
		L = l;
		SMSS = sMSS;
		D = d;
	}

	/**
	 * @throws Exception
	 */
	public void aggregate() throws Exception
	{
		ArrayList<double[]> results = processFolder();

		if (results.size() != 24000)
			throw new Exception("Results number must be 24'000!");

		saveResults(results);
	}

	/**
	 * Returns an ArrayList<double[]> containing a list of double[] arrays. Each array contains the SNR, L, SMSS, D indexes and the average result of the simulation for these indexes.
	 * @return
	 */
	private ArrayList<double[]> processFolder() throws Exception
	{
		ArrayList<double[]> folderResults = new ArrayList<double[]>();
		
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".csv");
		    }
		};

		File file    = new File(Folder);
		File[] files = file.listFiles(filter);

		if (files.length != 240)
			throw new Exception("Files number must be 240!");

		for (int fileInList = 0; fileInList < files.length; fileInList++)
		{
			folderResults.addAll(processFile(files[fileInList].toString()));
		}

		return folderResults;
	}

	/**
	 * Returns an ArrayList<double[]> containing a list of double[] arrays. Each array contains the SNR, L, SMSS, D indexes and the average result of the simulation for these indexes.
	 * @param file The file name.
	 * @return
	 */
	private ArrayList<double[]> processFile(String file)
	{
		ArrayList<double[]> fileResults = new ArrayList<double[]>();

		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			while ((strLine = br.readLine()) != null)
			{
				String[] splitted = strLine.split(" ");

				if (splitted.length != 1006)
					throw new Exception("Lines must contain 1006 values!");

				double[] lineResults = new double[5];

				double average = 0.0;
				for (int i = 6; i < splitted.length; i++)
				{
					average = average + Double.valueOf(splitted[i]);
				}

				lineResults[0] = Double.valueOf(splitted[1]);
				lineResults[1] = Double.valueOf(splitted[2]);
				lineResults[2] = Double.valueOf(splitted[3]);
				lineResults[3] = Double.valueOf(splitted[4]);
				lineResults[4] = average / 1000.0;

				fileResults.add(lineResults);
			}

			br.close();
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
			return null;
		}

		return fileResults;
	}
	
	/**
	 * Saves the results to a file.
	 * @param results
	 * @throws IOException 
	 */
	private void saveResults(ArrayList<double[]> results) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Folder + File.separator + "aggregate_results.csv"), true));

		
		if(L < 0.0 && SMSS < 0.0 && D < 0.0)
		{
			// 12-aggregates array
			double aggregate [] = new double[Main.SNR.length];
			for(int i=0; i < Main.SNR.length; i++)
				aggregate[i] = 0.0;
			
			// add each line to the correct aggregate
			for (double[] ds : results)
			{
				for (int i = 0; i < Main.SNR.length; i++)
				{
					if(ds[0] == Main.SNR[i])
						aggregate[i] = aggregate[i] + ds[4];				
				}
			}
			
			// divide the aggregates by the number of lines added
			for(int i=0; i < Main.SNR.length; i++)
			{
				aggregate[i] = aggregate[i] / Main.L.length / Main.SMSS.length / Main.D.length;
				
				String s = String.format("%f %f", Main.SNR[i], aggregate[i]);
				bw.write(s);
				bw.newLine();
			}
		}
		else if(SMSS < 0.0 && D < 0.0)
		{
			// 240-aggregates array
			double aggregate [] = new double[Main.SNR.length * Main.L.length];
			for(int i=0; i < Main.SNR.length * Main.L.length; i++)
				aggregate[i] = 0.0;
			
			for (double[] ds : results)
			{
				for (int i = 0; i < Main.SNR.length; i++)
				{
					if(ds[0] == Main.SNR[i])
					{
						for (int j = 0; j < Main.L.length; j++)
						{
							if(ds[1] == Main.L[j])
							{
								aggregate[i*j] = aggregate[i*j] + ds[4];
							}
						}
					}
				}
			}
			
			// divide the aggregates by the number of lines added
			for(int i=0; i < Main.SNR.length; i++)
			{
				for(int j=0; j < Main.L.length; j++)
				{
					aggregate[i*j] = aggregate[i*j] / Main.SMSS.length / Main.D.length;
				
					String s = String.format("%f %f %f", Main.SNR[i], Main.L[j], aggregate[i*j]);
					bw.write(s);
					bw.newLine();
				}
			}
			
		}
		else if(D < 0.0)
		{
			
		}
		else
		{
			// 24'000 results
			for (double[] ds : results)
			{
				String s = String.format("%f %f %f %f %f", ds[0], ds[1], ds[2], ds[3], ds[4]);
				bw.write(s);
				bw.newLine();
			}
		}
		
		bw.close();
	}
}
