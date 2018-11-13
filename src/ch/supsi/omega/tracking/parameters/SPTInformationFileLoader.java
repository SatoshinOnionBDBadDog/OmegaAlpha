package ch.supsi.omega.tracking.parameters;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import com.galliva.gallibrary.GLogManager;

import ch.supsi.omega.common.OmegaConstants;

public class SPTInformationFileLoader extends SPTInformationLoader
{
	private String				fileName	= "";
	private FileInputStream	fstream	= null;
	private DataInputStream	in			= null;
	private BufferedReader	br			= null;

	public SPTInformationFileLoader(String fileName)
	{
		this.fileName = fileName;
		executionInfoHandler = new SPTExecutionInfoHandler();
	}

	@Override
	public void initLoader()
	{
		try
		{
			fstream = new FileInputStream(fileName);
			in      = new DataInputStream(fstream);
			br      = new BufferedReader(new InputStreamReader(in));
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_NO_SPT_INFORMATION, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_NO_SPT_INFORMATION, e.toString()), Level.SEVERE);
			br = null;
		}
	}

	@Override
	public SPTExecutionInfoHandler loadInformation()
	{
		if (br != null)
		{
			String line   = null;
			boolean error = false;
			
			try
			{
				while ((line = br.readLine()) != null)
				{
					if(!processLine(line))
					{
						error = true;
						break;
					}
				}
			}

			catch (IOException e)
			{
				GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_NO_SPT_INFORMATION, e.toString()), Level.SEVERE);
				error = true;
			}
			
			if(error)
			{
				JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_NO_SPT_INFORMATION, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
				return null;
			}
			else
				return executionInfoHandler;
		}
		else
			return null;
	}
	
	private boolean processLine(String line)
	{
		line = line.trim().replaceAll(" ", "");
		
		String[] splitted = new String[2];
		
		try
		{
			splitted = line.split(OmegaConstants.SPT_INFORMATION_SEPARATOR);

			// image data
			if(splitted[0].equals("image_name"))
				executionInfoHandler.getImageData().setImageName(splitted[1]);
			if(splitted[0].equals("image_dataset"))
				executionInfoHandler.getImageData().setImageDatasetName(splitted[1]);
			if(splitted[0].equals("frames_number"))
				executionInfoHandler.getImageData().setT(Integer.valueOf(splitted[1]));
			if(splitted[0].equals("image_width"))
				executionInfoHandler.getImageData().setX(Integer.valueOf(splitted[1]));
			if(splitted[0].equals("image_height"))
				executionInfoHandler.getImageData().setY(Integer.valueOf(splitted[1]));
			if(splitted[0].equals("image_width_size"))
				executionInfoHandler.getImageData().setSizeX(Double.valueOf(splitted[1]));
			if(splitted[0].equals("image_height_size"))
				executionInfoHandler.getImageData().setSizeY(Double.valueOf(splitted[1]));
			if(splitted[0].equals("image_delta_t"))
				executionInfoHandler.getImageData().setSizeT(Double.valueOf(splitted[1]));
			
			// omero data
			if(splitted[0].equals("channel_processed"))
				executionInfoHandler.getOmeroParameters().setC(Integer.valueOf(splitted[1]));
			if(splitted[0].equals("plane_processed"))
				executionInfoHandler.getOmeroParameters().setZ(Integer.valueOf(splitted[1]));
			
			// SPT running data
			if(splitted[0].equals("radius"))
				executionInfoHandler.setRadius(splitted[1]);
			if(splitted[0].equals("cut_off"))
				executionInfoHandler.setCutOff(splitted[1]);
			if(splitted[0].equals("percentile"))
				executionInfoHandler.setPercentile(splitted[1]);
			if(splitted[0].equals("displacement"))
				executionInfoHandler.setDisplacement(splitted[1]);
			if(splitted[0].equals("link_range"))
				executionInfoHandler.setLinkRange(splitted[1]);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public void closeLoader()
	{
		try
		{
			in.close();
		}
		catch (IOException e)
		{
			GLogManager.log(String.format("%s: %s", "unable to close the reader", e.toString()), Level.WARNING);
		}
	}
}
