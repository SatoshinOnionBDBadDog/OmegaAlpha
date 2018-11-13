package ch.supsi.omega.segmentation.label;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class LabelsFileImporter extends LabelsImporter
{
	private String	currentFileName	= "";

	public String getCurrentFileName()
	{
		return currentFileName;
	}

	@Override
	public void loadLabels()
	{
		File file = null;

		if (fileName == null)
			file = getSelectedFile();
		else
			file = new File(fileName);

		if (file != null)
		{
			int i = 0;

			try
			{
				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				String strLine;

				// number of trajectories
				int tracksNumber = Integer.valueOf(br.readLine());

				if (tracksNumber != trajectories.size())
					JOptionPane.showMessageDialog(null, String.format(OmegaConstants.WARNING_LABELS_NUMBER_NOT_CORRECT, trajectories.size(), i), OmegaConstants.OMEGA_TITLE, JOptionPane.WARNING_MESSAGE);

				while ((strLine = br.readLine()) != null)
				{
					int[] labelsTemp = splitString(strLine);

					int[] labels = Arrays.copyOfRange(labelsTemp, 1, labelsTemp.length);

					if (labels != null)
					{
						Trajectory trajectory = trajectories.get(i);
						trajectory.setLabels(labels);
						i++;
					}
				}

				if (i != trajectories.size())
					JOptionPane.showMessageDialog(null, String.format(OmegaConstants.WARNING_LABELS_NUMBER_NOT_CORRECT, trajectories.size(), i), OmegaConstants.OMEGA_TITLE, JOptionPane.WARNING_MESSAGE);
			}
			catch (IndexOutOfBoundsException e)
			{
				JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_LOADING_SEGMENTATION, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_LOADING_SEGMENTATION, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
				GLogManager.log("trajectory skipped: " + e.toString(), Level.SEVERE);
			}
		}
	}

	private int[] splitString(String strLine)
	{
		try
		{
			String[] temp = strLine.split(OmegaConstants.LABELS_FILES_LINE_SEPARATOR);

			int splitted[] = new int[temp.length];

			for (int i = 0; i < splitted.length; i++)
			{
				splitted[i] = Integer.parseInt(temp[i]);
			}

			return splitted;
		}
		catch (Exception e)
		{
			GLogManager.log("labels skipped: " + e.toString(), Level.WARNING);
			return null;
		}
	}

	private File getSelectedFile()
	{
		GConfigurationManager configurationManager = new GConfigurationManager();

		String dir = null;

		try
		{
			configurationManager.setIniFile(OmegaConstants.INIFILE);
			dir = configurationManager.readConfig("labelsFile");
		}
		catch (Exception e)
		{
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		if (dir == null)
			dir = System.getProperty("user.dir");

		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle(OmegaConstants.INFO_SELECTDIR_TRACKS);
		chooser.setSelectedFile(new File(dir));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			currentFileName = file.getName();
			return file;
		}
		else
			return null;
	}
}
