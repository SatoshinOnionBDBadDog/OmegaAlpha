package ch.supsi.omega.common;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.segmentation.trajectory.TrajectoriesLoader;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class FileHelper extends JPanel {
	private static final long serialVersionUID = -7245648290687692002L;

	private static GConfigurationManager configurationManager = new GConfigurationManager();

	private String selectedDirectory = null;

	public String getSelectedDirectory() {
		return selectedDirectory;
	}

	public FileHelper() {
		try {
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		} catch (Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}
	}

	/**
	 * Display a JDialog in order to let the user select a directory / file.
	 * 
	 * @param fileKey
	 *            the key of the file / directory value in the configuration
	 *            file
	 * @param title
	 *            the tile of the JDialog
	 * @param fileSelectionMode
	 *            the SelectionMode of the JDialog
	 * @return the selected file / directory
	 */
	public String selectFile(String fileKey, String title, int fileSelectionMode) {
		String dir = configurationManager.readConfig(fileKey);

		if (dir == null)
			dir = System.getProperty("user.dir");

		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle(title);
		chooser.setFileSelectionMode(fileSelectionMode);
		chooser.setAcceptAllFileFilterUsed(false);

		if (fileSelectionMode == JFileChooser.DIRECTORIES_ONLY)
			chooser.setCurrentDirectory(new File(dir));
		else
			chooser.setSelectedFile(new File(dir));

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String selectedDir = chooser.getSelectedFile().toString();
			configurationManager.writeConfig(fileKey, selectedDir);
			return selectedDir;
		} else
			return null;
	}

	/**
	 * Display a JDialog in order to let the user select from where load the
	 * tracks, then load the tracks.
	 * 
	 * @param pathKey
	 *            the key of the directory value in the configuration file
	 * @return a List<Trajectory> of tracks
	 */
	public List<Trajectory> loadTracks(String pathKey) {
		String trajectoriesDirectory;

		// load the tracks
		String dir = configurationManager.readConfig(pathKey);

		if (dir == null)
			dir = System.getProperty("user.dir");

		JFileChooser chooser = new JFileChooser();

		chooser.setDialogTitle(OmegaConstants.INFO_SELECTDIR_TRACKS);
		chooser.setCurrentDirectory(new File(dir));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			selectedDirectory = chooser.getSelectedFile().toString();
			configurationManager.writeConfig("trajectoriesTrainDir",
			        selectedDirectory);
			trajectoriesDirectory = selectedDirectory;

			// load the trajectories
			try {
				TrajectoriesLoader trajectoriesLoader = new TrajectoriesLoader(
				        trajectoriesDirectory
				                + System.getProperty("file.separator"),
				        OmegaConstants.TRACKS_FILES_EXTENSION);
				return trajectoriesLoader.loadTrajectories();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.ERROR_NOTRAJECTORIES,
				        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} else
			return null;
	}

	/**
	 * Creates a directory.
	 * 
	 * @param dirName
	 *            the directory name
	 */
	public static void createDirectory(String dirName) {
		try {
			File dir = new File(dirName);

			if (!dir.exists()) {
				dir.mkdir();
			}
		} catch (Exception e) {
			GLogManager.log(
			        String.format("%s: %s", "Cannot create the directory",
			                e.toString()), Level.WARNING);
		}
	}

	/**
	 * Empty completely a directory.
	 * 
	 * @param dirName
	 *            the directory name
	 */
	public static void emptyDirectory(String dirName) {
		try {
			File dir = new File(dirName);

			if (!dir.exists())
				return;

			String[] info = dir.list();

			for (int i = 0; i < info.length; i++) {
				File n = new File(dirName + File.separator + info[i]);
				if (n.isFile())
					n.delete();
				else if (n.isDirectory())
					deleteDir(n);
			}
		} catch (Exception e) {
			GLogManager.log(
			        String.format("%s: %s", "Cannot empty the directory",
			                e.toString()), Level.WARNING);
		}
	}

	private static boolean deleteDir(File dir) {
		String[] children = dir.list();
		for (int i = 0; i < children.length; i++) {
			boolean success = deleteDir(new File(dir, children[i]));
			if (!success)
				return false;
		}
		return dir.delete();
	}
}
