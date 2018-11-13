package ch.supsi.omega.exploration.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.galliva.gallibrary.GLogManager;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;

public final class CSVExporter
{
	public static void exportCSV(List<String[]> data)
	{
		FileHelper fileHelper = new FileHelper();
		
		String file = fileHelper.selectFile("csvFile", "Please select the name of the file to be saved", JFileChooser.FILES_ONLY);
		
		saveData(file, data);
	}
	
	private static void saveData(String filePath, List<String[]> data)
	{	
		File file = new File(filePath);
		
		if (file != null)
		{
			try
			{
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);
				
				for (String[] rows : data)
				{
					int i = 0;
					
					for (; i < rows.length -1; i++)
					{
						out.write(rows[i] + ";");
					}
					
					out.write(rows[i] + System.getProperty("line.separator"));
				}
				
				out.close();
			}
			catch(Exception e)
			{
				GLogManager.log(OmegaConstants.ERROR_SAVE_CSV + e.toString(), Level.SEVERE);
				JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_SAVE_CSV, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
