package ch.supsi.omega.exploration.processing;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import javax.swing.table.DefaultTableModel;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.common.JFrameStatsMSD;
import ch.supsi.omega.gui.MainFrame;

import com.galliva.gallibrary.GLogManager;

public class StudentTTest extends Thread {
	private MainFrame mainFrame = null;
	private String datasetOnePath = null;
	private String datasetTwoPath = null;

	private final double[][] DS_MSD = new double[2][];
	private final double[][] DS_D = new double[2][];
	private final double[][] DS_SMSS = new double[2][];

	double MSD_t_test = 0.0;
	double D_t_test = 0.0;
	double SMSS_t_test = 0.0;

	public StudentTTest(final MainFrame mainFrame, final String datasetOnePath,
	        final String datasetTwoPath) {
		super();
		this.mainFrame = mainFrame;
		this.datasetOnePath = datasetOnePath;
		this.datasetTwoPath = datasetTwoPath;
	}

	@Override
	public void run() {
		this.processDatasetsFolders();
		this.calculateTTest();
		this.displayResults();
	}

	private void processDatasetsFolders() {
		this.processDatasetFolder(this.datasetOnePath, 0);
		this.processDatasetFolder(this.datasetTwoPath, 1);
	}

	private void processDatasetFolder(final String datasetFolder,
	        final int datasetIndex) {
		final File dir = new File(datasetFolder);

		// find the trajectories files
		final FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.endsWith(OmegaConstants.TRACKS_FILES_EXTENSION);
			}
		};

		final String[] children = dir.list(filter);

		// don't consider any segmentation
		final int[][] segmentation = new int[children.length][];

		for (int i = 0; i < children.length; i++) {
			try {
				double[][] trajectories = null;

				trajectories = FileIn.readTrajectory(datasetFolder
				        + System.getProperty("file.separator") + children[i]);

				final int[] zeros = new int[trajectories[0].length - 1];
				Arrays.fill(zeros, 0);

				segmentation[i] = zeros;
			} catch (final IOException e) {
			}
		}

		this.DS_MSD[datasetIndex] = new double[children.length];
		this.DS_D[datasetIndex] = new double[children.length];
		this.DS_SMSS[datasetIndex] = new double[children.length];

		// read the trajectories and process them
		for (int i = 0; i < children.length; i++) {
			double[][] trajectories = null;

			try {
				trajectories = FileIn.readTrajectory(datasetFolder
				        + System.getProperty("file.separator") + children[i]);
			} catch (final IOException e) {
				GLogManager.log(e.toString(), Level.SEVERE);
			}

			// call the Stats library
			final Stats stats = new Stats(trajectories[0], trajectories[1],
			        0.02, segmentation[i]);
			final double[][] log_delta_t__log_mu__gamma__D = stats
			        .log_delta_t__log_mu__gamma__D(2);
			final double[][] nu__gamma__S_MSS = stats.nu__gamma__S_MSS(0);

			// for each trajectory, we have an array of MSD values from where we
			// calculate the mean
			final double MSD = Stats.mean(log_delta_t__log_mu__gamma__D[1]);
			// D and S_MSS are already single values
			final double D = log_delta_t__log_mu__gamma__D[2][3];
			final double S_MSS = nu__gamma__S_MSS[2][0];

			this.DS_MSD[datasetIndex][i] = MSD;
			this.DS_D[datasetIndex][i] = Math.exp(D);
			this.DS_SMSS[datasetIndex][i] = S_MSS;
		}
	}

	private void calculateTTest() {
		this.MSD_t_test = Stats.t_test(this.DS_MSD[0], this.DS_MSD[1]);
		this.D_t_test = Stats.t_test(this.DS_D[0], this.DS_D[1]);
		this.SMSS_t_test = Stats.t_test(this.DS_SMSS[0], this.DS_SMSS[1]);
	}

	private void displayResults() {
		final DefaultTableModel model = new DefaultTableModel(new Object[][] {
		        { "MSD", this.MSD_t_test }, { "D", this.D_t_test },
		        { "SMSS", this.SMSS_t_test } }, new String[] { "Measure", "T" });

		final JFrameStatsMSD jFrameStats = new JFrameStatsMSD(
		        this.mainFrame.getLocation().x + this.mainFrame.getWidth(),
		        this.mainFrame.getLocation().y, "Student's T test");
		jFrameStats.setModel(model);
		jFrameStats.setVisible(true);
	}
}
