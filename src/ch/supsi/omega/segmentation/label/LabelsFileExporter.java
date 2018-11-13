package ch.supsi.omega.segmentation.label;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class LabelsFileExporter extends LabelsExporter
{
	private String	currentFileName	= "";

	public String getCurrentFileName()
	{
		return currentFileName;
	}

	public LabelsFileExporter()
	{
	}

	@Override
	public void saveLabels()
	{
		File file = getSelectedFile();

		if(file != null)
			currentFileName = file.getName();

		save(file);
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
			dir = System.getProperty("user.dir") + OmegaConstants.LABELS_FILE_NAME + "." + OmegaConstants.LABELS_FILES_EXTENSION;

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(OmegaConstants.INFO_SELECTDIR_LABELS);
		chooser.setSelectedFile(new File(dir));

		int returnVal = chooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			configurationManager.writeConfig("labelsFile", file.toString());
			return file;
		}
		else
			return null;
	}

	public void saveLabels(String fileLocation)
	{
		File file = new File(fileLocation);

		save(file);
	}

	private void save(File file)
	{
		if (file != null)
		{
			try
			{
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);

				out.write(trajectories.size() + System.getProperty("line.separator"));

				for (Trajectory trajectory : trajectories)
				{
					int[] labels = trajectory.getLabels();

					out.write(labels.length + OmegaConstants.LABELS_FILES_LINE_SEPARATOR);

					int i;
					for (i = 0; i < labels.length - 1; i++)
					{
						out.write(labels[i] + OmegaConstants.LABELS_FILES_LINE_SEPARATOR);
					}
					out.write(labels[i] + System.getProperty("line.separator"));
				}
				out.close();
			}
			catch (Exception e)
			{
				GLogManager.log(OmegaConstants.ERROR_SAVE_LABELS + e.toString(), Level.SEVERE);
				JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_SAVE_LABELS, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
