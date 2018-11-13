package ch.supsi.omega.segmentation;

import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.segmentation.trajectory.TrajectoriesLoader;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

import com.galliva.gallibrary.GConfigurationManager;

public final class Segmentator extends JPanel
{
	private static final long					serialVersionUID		= 1L;

	private static GConfigurationManager	configurationManager	= new GConfigurationManager();
	private static String						workingDirectory		= "";

	public Segmentator()
	{
		// show a JFileChooser in order to select the working directory
		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle(OmegaConstants.INFO_SELECTDIR_TRACKS);
		chooser.setCurrentDirectory(new File(configurationManager.readConfig("trajectoriesDir")));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			String selectedDir = chooser.getSelectedFile().toString();
			configurationManager.writeConfig("trajectoriesDir", selectedDir);
			workingDirectory = selectedDir;
		}
		else
			System.exit(0);
	}

	public static void main(String[] args)
	{
		// check if the configuration file already exists, otherwise create it
		configurationManager.setIniFile(OmegaConstants.INIFILE);
		configurationManager.checkConfigFile(OmegaConstants.ORIGINALINIFILE);

		new Segmentator();

		// initialize the Labels
		// PatternsLoader.loadPatterns();

		// load the trajectories
		TrajectoriesLoader trajectoriesLoader = new TrajectoriesLoader(workingDirectory + System.getProperty("file.separator"),
				OmegaConstants.TRACKS_FILES_EXTENSION);
		List<Trajectory> trajectories = trajectoriesLoader.loadTrajectories();

		// start the main frame
		if (!(trajectories.size() == 0))
		{
			// new SegmentationFrame(trajectories);
		}
		else
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_NOTRAJECTORIES, OmegaConstants.OMEGA_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
