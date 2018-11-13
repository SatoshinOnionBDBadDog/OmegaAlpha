package ch.supsi.omega.tracking.stats;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;

public class SPTStatsFileReader {
	private String folder = "";
	private String fileName = "";

	private double minSNR = 0.0;
	private double maxSNR = 0.0;
	private double minVar = 0.0;
	private double avgMinSNR = 0.0;

	public double getMinSNR() {
		return minSNR;
	}

	public double getMaxSNR() {
		return maxSNR;
	}

	public double getMinVar() {
		return minVar;
	}

	public double getAvgMinSNR() {
		return avgMinSNR;
	}

	public SPTStatsFileReader(String directory) {
		this.folder = directory;
		this.fileName = directory + System.getProperty("file.separator")
		        + "stats.txt";
	}

	/**
	 * Reads the MIN SNR, MAX SNR and VAR SNR values.
	 */
	private void ReadStats() {
		Pattern pattern = Pattern.compile(":(.*?)\\(");
		Matcher matcher = null;

		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			while ((strLine = br.readLine()) != null) {
				strLine = strLine.trim().replace(" ", "");
				matcher = pattern.matcher(strLine);

				if (matcher.find()) {
					String temp = matcher.group(1);

					if (strLine.startsWith("MinimumSNRvariance"))
						minVar = Double.valueOf(temp);
					else if (strLine.startsWith("MinimumSNR"))
						minSNR = Double.valueOf(temp);
					else if (strLine.startsWith("MaximumSNR"))
						maxSNR = Double.valueOf(temp);
					else if (strLine.startsWith("AverageminimumSNR"))
						avgMinSNR = Double.valueOf(temp);
				}
			}

			in.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Return the MIN SNR value from a stats file.
	 * 
	 * @param map
	 * @return
	 */
	public static double getMinimunSNR(HashMap<String, List<String>> map) {
		double minSNR = Double.MAX_VALUE;

		Iterator<Entry<String, List<String>>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) it
			        .next();

			List<String> dir = pairs.getValue();

			for (String string : dir) {
				SPTStatsFileReader reader = new SPTStatsFileReader(string);
				reader.ReadStats();

				if (reader.minSNR < minSNR)
					minSNR = reader.minSNR;
			}
		}

		return minSNR;
	}

	/**
	 * Return the MIN AVG SNR value from a stats file.
	 * 
	 * @param map
	 * @return
	 */
	public static double getMinimunAverageSNR(HashMap<String, List<String>> map) {
		double avgMinSNR = Double.MAX_VALUE;

		Iterator<Entry<String, List<String>>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) it
			        .next();

			List<String> dir = pairs.getValue();

			for (String string : dir) {
				SPTStatsFileReader reader = new SPTStatsFileReader(string);
				reader.ReadStats();

				if (reader.avgMinSNR < avgMinSNR)
					avgMinSNR = reader.avgMinSNR;
			}
		}

		return avgMinSNR;
	}

	/**
	 * Returns the minimum frame MEAN SNR for a specified trajectory.
	 * 
	 * @param trajectoryFileName
	 * @return
	 */
	public double getMinimunFrameMeanSNR(String trajectoryFileName,
	        int offset1, int offset2) {
		String file = folder + System.getProperty("file.separator")
		        + trajectoryFileName;

		int minB = 1;
		int maxB = 1;

		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			minB = Integer.valueOf(StringHelper.splitString(br.readLine(),
			        OmegaConstants.TRACKS_FILES_LINE_SEPARATOR)[0]);

			while ((strLine = br.readLine()) != null) {
				if (strLine.length() > 5) {
					String[] splitted = StringHelper.splitString(br.readLine(),
					        OmegaConstants.TRACKS_FILES_LINE_SEPARATOR);

					if (splitted.length == OmegaConstants.TRAJECTORY_FILE_COLUMN_NUMBER) {
						maxB = Integer.valueOf(splitted[0]);
					}
				}
			}

			in.close();
		} catch (Exception e) {
		}

		minB = +offset1;
		maxB = +offset2;

		return getMinimunFrameMeanSNR(minB, maxB);
	}

	/**
	 * Returns the minimum frame MEAN SNR for a specified time window.
	 * 
	 * @param minFrame
	 * @param maxFrame
	 * @return
	 */
	public double getMinimunFrameMeanSNR(int minFrame, int maxFrame) {
		double minMeanSNR = Double.MAX_VALUE;

		DataInputStream in = null;
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			br.readLine();

			while ((strLine = br.readLine()).length() > 0) {
				String[] splitted = strLine.trim().split(" ");

				if (Integer.valueOf(splitted[0]) >= minFrame
				        && Integer.valueOf(splitted[0]) <= maxFrame) {
					double meanSNR = Double.valueOf(splitted[3]);

					if (meanSNR < minMeanSNR)
						minMeanSNR = meanSNR;
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}

		return minMeanSNR;
	}

	// public static void main(String[] args)
	// {
	// SPTStatsFileReader reader = new
	// SPTStatsFileReader("C:\\Users\\galliva\\Desktop\\2012-06-20_stats_test\\Artificial_xyt_8bit");
	// System.out.println(String.format("%f",reader.getMinimunFrameMeanSNR(2,
	// 5)));
	// }
}
