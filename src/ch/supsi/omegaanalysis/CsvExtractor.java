package ch.supsi.omegaanalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;

import com.galliva.gallibrary.GLogManager;

public class CsvExtractor
{
	/* configuration */
	private static String WORKING_FOLDER   = "C:\\Users\\galliva\\Desktop\\out";
	private static int HEADERS_NUMBER      = 6;
	private static int GENERATED_TRACKS    = 1000;
	private static boolean TRANSPOSE_DATA  = true;
	private static boolean AGGREGATE_DATA  = true;
	private static boolean INSERT_DATA     = false;
	private static String CSV_DELIMITER    = ";"; 
	/* end configuration */
	
	private static Connection	con	 		= null;
	private static String currentFile 		= null;
	private static String[] aggregateData  = new String[HEADERS_NUMBER+GENERATED_TRACKS-2];	
	
	public static void main(String[] args)
	{			
		if(!TRANSPOSE_DATA)
			AGGREGATE_DATA = false;
		
		try
		{
			if(INSERT_DATA)
			{
				connectToMySQL();
				GLogManager.log("Successfully connected to " + "MySQL server using TCP/IP...", Level.INFO);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		processFolder(WORKING_FOLDER + "\\D\\");
		processFolder(WORKING_FOLDER + "\\SMSS\\");

		try
		{
			if(INSERT_DATA)
				closeConnection();
		}
		catch (SQLException e)
		{
		}
	}

	private static void connectToMySQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		con = DriverManager.getConnection("jdbc:mysql://146.189.73.119:3306/omega", "omegauser", "0m30m3*");
	}

	private static void closeConnection() throws SQLException
	{
		con.close();
	}

	private static void processFolder(String workingDirectory)
	{
		if(AGGREGATE_DATA)
		{
			for (int i = 0; i < HEADERS_NUMBER+GENERATED_TRACKS-2; i++)
				aggregateData[i] = "";
		}	
		
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
			
			if (children != null)
			{
				for (int i = 0; i < children.length; i++)
				{
					currentFile = children[i];
					GLogManager.log(String.format("processing file %d / %d", i+1, children.length), Level.INFO);
					processFile(workingDirectory + children[i]);
				}
			}
		}
		catch (Exception e)
		{
			GLogManager.log("can't process the folder!", Level.SEVERE);
		}
		
		if(AGGREGATE_DATA)
			aggregateData(workingDirectory);
	}

	private static void processFile(String file)
	{		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			while ((strLine = br.readLine()) != null)
			{
				String[] splitted = splitString(strLine);

				if (splitted != null)
					processRow(splitted);
			}

			in.close();
		}
		catch (Exception e)
		{
			GLogManager.log("can't process the file!", Level.SEVERE);
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

	private static void processRow(String[] splitted)
	{
		try
		{			
			Double SNR = Double.valueOf(splitted[1]);
			Double L = Double.valueOf(splitted[2]);
			Double SMSS = Double.valueOf(splitted[3]);
			Double D = Double.valueOf(splitted[4]);

			String[] valuesString = Arrays.copyOfRange(splitted, HEADERS_NUMBER, HEADERS_NUMBER+GENERATED_TRACKS);

			Double[] values = new Double[valuesString.length];

			for (int i = 0; i < valuesString.length; i++)
				values[i] = Double.valueOf(valuesString[i]);
			
			if(TRANSPOSE_DATA)
				transposeData(SNR, L, SMSS, D, values);
			
			if(INSERT_DATA)
				insertDataInMySQL(SNR, L, SMSS, D, values);
		}
		catch (Exception e)
		{
			GLogManager.log("can't convert the values: line skipped!", Level.WARNING);
		}
	}

	private static void transposeData(Double SNR, Double L, Double SMSS, Double D, Double[] values)
	{
		try
		{
			FileWriter fstream = new FileWriter(WORKING_FOLDER + "\\transposed_" + currentFile);
			
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(String.format("SNR: %f\nL: %f\nSMSS: %f\nD: %f\n", SNR, L, SMSS, D));
			
			if(AGGREGATE_DATA)
			{
				aggregateData[0] = aggregateData[0] + String.format("SNR:  %f", SNR)  + CSV_DELIMITER;
				aggregateData[1] = aggregateData[1] + String.format("L:    %f", L)    + CSV_DELIMITER;
				aggregateData[2] = aggregateData[2] + String.format("SMSS: %f", SMSS) + CSV_DELIMITER;
				aggregateData[3] = aggregateData[3] + String.format("D:    %f", D)    + CSV_DELIMITER;
			}
			
			int i = 4;
			
			for (Double value : values)
			{
				out.write(value + "\n");
				
				if(AGGREGATE_DATA)
				{
					aggregateData[i] = aggregateData[i] + value + CSV_DELIMITER;
					i++;
				}
			}
			
			out.close();
		}
		catch (Exception e)
		{
			GLogManager.log("can't save the file!", Level.SEVERE);
		}
	}
	
	private static void aggregateData(String workingDirectory)
	{
		try
		{
			FileWriter fstream = new FileWriter(workingDirectory + "aggregated_data.csv");
			
			BufferedWriter out = new BufferedWriter(fstream);
			
			for (String string : aggregateData)
				out.write(string + "\n");
			
			out.close();
		}
		catch (Exception e)
		{
			GLogManager.log("can't save the file!", Level.SEVERE);
		}	
	}
	
	private static void insertDataInMySQL(Double SNR, Double L, Double SMSS, Double D, Double[] values)
	{
		if (values.length != 1000)
		{
			GLogManager.log("wrong number of values (!= 1000): line skipped!", Level.WARNING);
			return;
		}

		try
		{	
			double avg = 0.0;
			
			for (Double value : values)
				avg = avg + value;
			
			avg = avg / 1000.0;
			
			insertInputs(SNR, L, SMSS, D, avg);
			
			//if (newID != -1)
				//addValues(newID, values);
		}
		catch (Exception e)
		{
			System.err.println("Exception: " + e.getMessage());
		}
	}

	private static int insertInputs(double SNR, double L, double SMSS, double D, double avg) throws SQLException
	{
		PreparedStatement pst = con.prepareStatement("INSERT INTO SMSS_results(SNR, L, SMSS, D, result) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		pst.setDouble(1, SNR);
		pst.setDouble(2, L);
		pst.setDouble(3, SMSS);
		pst.setDouble(4, D);
		pst.setDouble(5, avg);
		pst.executeUpdate();

		// get the new ID
//		ResultSet rs = pst.getGeneratedKeys();
//		if (rs != null && rs.next())
//			return rs.getInt(1);

		return -1;
	}

	

//	private static void addValues(int inputsId, Double[] values) throws SQLException
//	{
//		PreparedStatement pst = con.prepareStatement("INSERT INTO SMSS_values(input_id, value) VALUES(?, ?)");
//		
//		for (Double value : values)
//		{
//			pst.setInt(1, inputsId);
//			pst.setDouble(2, value);
//			pst.addBatch();
//		}
//
//		GLogManager.log("  before executeBatch");
//		pst.executeBatch();
//		GLogManager.log("  after executeBatch");
//	}
}


/*
Successfully connected to MySQL server using TCP/IP...

16-ott-2012 15:25:32 com.galliva.gallibrary.GLogManager log
INFO: processing row 1 of 100...

16-ott-2012 15:25:32 com.galliva.gallibrary.GLogManager log
INFO:   processRow: starting conversion

16-ott-2012 15:25:32 com.galliva.gallibrary.GLogManager log
INFO:   processRow: ended conversion

16-ott-2012 15:25:32 com.galliva.gallibrary.GLogManager log
INFO:   insertDataInMySQL: insert inputs

16-ott-2012 15:25:32 com.galliva.gallibrary.GLogManager log
INFO:   insertDataInMySQL: insert values

16-ott-2012 15:26:06 com.galliva.gallibrary.GLogManager log
INFO: processing row 2 of 100...
*/
