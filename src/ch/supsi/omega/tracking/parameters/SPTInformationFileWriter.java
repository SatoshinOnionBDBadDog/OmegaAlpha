package ch.supsi.omega.tracking.parameters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SPTInformationFileWriter extends SPTInformationWriter
{
	private String				fileName		= "";

	private FileWriter		fstream		= null;
	private BufferedWriter	out			= null;

	public SPTInformationFileWriter(String fileName)
	{
		this.fileName = fileName;
	}

	@Override
	public void initWriter()
	{
		try
		{
			fstream = new FileWriter(fileName);
			out = new BufferedWriter(fstream);
		}
		catch (IOException e)
		{

		}
	}

	@Override
	public String writeInformation(SPTExecutionInfoHandler executionInfoHandler)
	{
		String statsString	= "";
		
		try
		{
			if (executionInfoHandler != null)
			{
				ImageDataHandler imageDataHandler = executionInfoHandler.getImageData();
				if (imageDataHandler != null)
				{
					out.write(String.format("%s = %s", "image_name       ", imageDataHandler.getImageName()));
					out.newLine();
					out.write(String.format("%s = %s", "image_dataset    ", imageDataHandler.getImageDatasetName()));
					out.newLine();
					out.write(String.format("%s = %d", "image_width      ", imageDataHandler.getX()));
					out.newLine();
					out.write(String.format("%s = %f", "image_width_size ", imageDataHandler.getSizeX()));
					out.newLine();
					out.write(String.format("%s = %d", "image_height     ", imageDataHandler.getY()));
					out.newLine();
					out.write(String.format("%s = %f", "image_height_size", imageDataHandler.getSizeY()));
					out.newLine();
					out.write(String.format("%s = %d", "frames_number    ", imageDataHandler.getT()));
					out.newLine();
					out.write(String.format("%s = %f", "total_time       ", imageDataHandler.getSizeT()));
					out.newLine();
					out.write(String.format("%s = %f", "image_delta_t    ", imageDataHandler.getSizeT() / imageDataHandler.getT()));
					out.newLine();
					
					statsString = statsString + String.format("%s : %s", "image name       ", imageDataHandler.getImageName()) + "\n";
					statsString = statsString + String.format("%s : %s", "image dataset    ", imageDataHandler.getImageDatasetName()) + "\n";
					statsString = statsString + String.format("%s : %d", "image width      ", imageDataHandler.getX()) + "\n";
					statsString = statsString + String.format("%s : %f", "image width size ", imageDataHandler.getSizeX()) + "\n";
					statsString = statsString + String.format("%s : %d", "image height     ", imageDataHandler.getY()) + "\n";
					statsString = statsString + String.format("%s : %f", "image height size", imageDataHandler.getSizeY()) + "\n";
					statsString = statsString + String.format("%s : %d", "frames number    ", imageDataHandler.getT()) + "\n";
					statsString = statsString + String.format("%s : %f", "total time       ", imageDataHandler.getSizeT()) + "\n";
					statsString = statsString + String.format("%s : %f", "image delta_t    ", imageDataHandler.getSizeT() / imageDataHandler.getT()) + "\n";
				}

				OmeroParametersHandler omeroParametersHandler = executionInfoHandler.getOmeroParameters();
				if (omeroParametersHandler != null)
				{
					out.write(String.format("%s = %d", "channel_processed", omeroParametersHandler.getC()));
					out.newLine();
					out.write(String.format("%s = %d", "plane_processed  ", omeroParametersHandler.getZ()));
					out.newLine();
					
					statsString = statsString + "\n";
					statsString = statsString + String.format("%s : %d", "channel processed", omeroParametersHandler.getC()) + "\n";
					statsString = statsString + String.format("%s : %d", "plane processed  ", omeroParametersHandler.getZ())+ "\n";
				}

				out.write(String.format("%s = %s", "radius           ", executionInfoHandler.getRadius()));
				out.newLine();
				out.write(String.format("%s = %s", "cut_off          ", executionInfoHandler.getCutOff()));
				out.newLine();
				out.write(String.format("%s = %s", "percentile       ", executionInfoHandler.getPercentile()));
				out.newLine();
				out.write(String.format("%s = %s", "displacement     ", executionInfoHandler.getDisplacement()));
				out.newLine();
				out.write(String.format("%s = %s", "link_range       ", executionInfoHandler.getLinkRange()));
				
				statsString = statsString + String.format("%s : %s", "radius           ", executionInfoHandler.getRadius()) + "\n";
				statsString = statsString + String.format("%s : %s", "cut_off          ", executionInfoHandler.getCutOff()) + "\n";
				statsString = statsString + String.format("%s : %s", "percentile       ", executionInfoHandler.getPercentile()) + "\n";
				statsString = statsString + String.format("%s : %s", "displacement     ", executionInfoHandler.getDisplacement()) + "\n";
				statsString = statsString + String.format("%s : %s", "link_range       ", executionInfoHandler.getLinkRange()) + "\n";
			}
		}
		catch (IOException e)
		{
		}
		
		return statsString;
	}

	@Override
	public void closeWriter()
	{
		try
		{
			out.close();
		}
		catch (IOException e)
		{
		}
	}
}
