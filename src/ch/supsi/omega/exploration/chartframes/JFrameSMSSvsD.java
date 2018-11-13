package ch.supsi.omega.exploration.chartframes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.exploration.charts.SMSSvsDChart;
import ch.supsi.omega.exploration.common.JFrameStatsMSD;
import ch.supsi.omega.exploration.common.imagedata.OmeroDataHelper;
import ch.supsi.omega.exploration.processing.FileIn;
import ch.supsi.omega.exploration.processing.Stats;
import ch.supsi.omega.gui.OMEGA;
import ch.supsi.omega.math.SplineInterpolation;
import ch.supsi.omega.math.SplineInterpolation.Size;
import ch.supsi.omega.math.interpolation4d.InterpolationType;
import ch.supsi.omega.math.interpolation4d.Interpolator4d;
import ch.supsi.omega.tracking.stats.SPTStatsFileReader;

import com.galliva.gallibrary.GLogManager;

public class JFrameSMSSvsD extends AbstractJFrameForChart {
	private static final long serialVersionUID = -1858307929847425427L;

	/**
	 * The Chart to be displayed in this JFrame.
	 */
	private SMSSvsDChart smsSvsDChart = null;

	/**
	 * The DS, folders map.
	 */
	private HashMap<String, List<String>> map = null;

	/**
	 * Arraylist containg the current D and SMSS values.
	 */
	private final ArrayList<double[]> D_SMSS = new ArrayList<double[]>();

	/**
	 * Arraylist containing the data information to be visualized / exported in
	 * CSV
	 */
	private List<String[]> currentChartData = null;

	/**
	 * Index used when creating the currentChartData List.
	 */
	private int currentDataIndex = 0;

	/**
	 * The JFrameStats object for the D value.
	 */
	private JFrameStatsMSD jFrameStatsD = null;

	/**
	 * The JFrameStats object for the SMSS value.
	 */
	private JFrameStatsMSD jFrameStatsSMSS = null;

	/**
	 * An OmeroDataHelper instance, used to get the OMERO image data (pixels
	 * sizes, delta t).
	 */
	private final OmeroDataHelper omeroDataHelper = new OmeroDataHelper(this);

	public JFrameSMSSvsD(final HashMap<String, List<String>> map) {
		super();
		this.map = map;
		this.setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE,
		        "RVE Module", "Slope MSS vs D"));
		this.addDatasets();
		this.addWindowListener();
	}

	public JFrameSMSSvsD(final HashMap<String, List<String>> map, final int x,
	        final int y) {
		super(x, y);
		this.map = map;
		this.setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE,
		        "RVE Module", "Slope MSS vs D"));
		this.addDatasets();
		this.addWindowListener();
	}

	private void addWindowListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				try {
					JFrameSMSSvsD.this.jFrameStatsD.dispose();
					JFrameSMSSvsD.this.jFrameStatsSMSS.dispose();
				} catch (final Exception e1) {
				}
			}
		});
	}

	@Override
	protected void createTopPanel() {
		this.jPanelTop.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
		this.jLabelDataset.setText("Dataset: ");
		this.jPanelTop.add(this.jLabelDataset);

		this.jPanelTop.add(this.jComboBoxDataset);
		this.getContentPane().add(this.jPanelTop, BorderLayout.NORTH);

		this.jComboBoxDataset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				JFrameSMSSvsD.this.processDataAndDisplayChart();
			}
		});
	}

	/**
	 * Adds the datasets list to the JComboBox (when the JFrame is istantiaded
	 * for the first time).
	 */
	private void addDatasets() {
		final List<String> dataSets = new ArrayList<String>();

		final Iterator<Entry<String, List<String>>> it = this.map.entrySet()
		        .iterator();

		while (it.hasNext()) {
			final Map.Entry<String, List<String>> pairs = it.next();
			dataSets.add(pairs.getKey());
		}

		this.jComboBoxDataset.setModel(new DefaultComboBoxModel(dataSets
		        .toArray()));

		this.processDataAndDisplayChart();
	}

	@Override
	public void processDataAndDisplayChart() {
		// reset the D_SMSS field
		this.D_SMSS.clear();

		// reset the current chart data
		this.currentChartData = new ArrayList<String[]>();
		this.currentDataIndex = 0;

		// get the current dataset
		final String currentDataset = this.jComboBoxDataset.getSelectedItem()
		        .toString();

		// get the list of the folders to be processed
		final List<String> datasetFolders = this.map.get(currentDataset);

		// create the SMSS vs D chart
		this.smsSvsDChart = new SMSSvsDChart();

		// process each folder in the dataset, add data to the chart
		for (final String folder : datasetFolders)
			if (!this.processFolder(folder))
				return;

		// get the chart panel
		this.chartPanel = this.smsSvsDChart.returnChart();

		this.drawMinimumDetectableDLine(currentDataset, datasetFolders);

		this.displayChart();

		this.displayStatsFrames();
	}

	/**
	 * Calcluates the minimum detectable D and display it on the chart.
	 * 
	 * @param currentDataset
	 * @param datasetFolders
	 */
	private void drawMinimumDetectableDLine(final String currentDataset,
	        final List<String> datasetFolders) {
		// find the minimum SNR for the current dataset
		final HashMap<String, List<String>> currentDirectories = new HashMap<String, List<String>>();
		currentDirectories.put(currentDataset, datasetFolders);
		final double minSNR = SPTStatsFileReader
		        .getMinimunAverageSNR(currentDirectories);

		try {
			// extrapolate from Ivo's values
			final double bias = SplineInterpolation.interpolate(minSNR,
			        Size.BIAS);
			final double sigma = SplineInterpolation.interpolate(minSNR,
			        Size.SIGMA);

			// Martin's model
			final double minD = ((sigma * sigma) + (bias * bias)) / (2 * 2);

			GLogManager.log("min detectable D is: " + minD);

			this.smsSvsDChart.drawMinimumDetectableDLine(minD);
		} catch (final IllegalArgumentException e) {
			GLogManager.log(e.toString(), Level.WARNING);
		}
	}

	/**
	 * Displays the stats JFrames.
	 */
	private void displayStatsFrames() {
		if (this.jFrameStatsD == null) {
			this.jFrameStatsD = new JFrameStatsMSD(this.getX() + 4, this.getY()
			        + this.getHeight(), "D [" + Greeks.MU + "m\u00B2/s]");
			this.jFrameStatsD.setVisible(true);

			this.jFrameStatsSMSS = new JFrameStatsMSD(this.getX() + 4 + 270,
			        this.getY() + this.getHeight(), "SMSS [-]");
			this.jFrameStatsSMSS.setVisible(true);
		}

		final double[] D = new double[this.D_SMSS.size()];
		final double[] SMSS = new double[this.D_SMSS.size()];

		for (int i = 0; i < this.D_SMSS.size(); i++) {
			D[i] = this.D_SMSS.get(i)[0];
			SMSS[i] = this.D_SMSS.get(i)[1];
		}

		try {
			final double meanD = Stats.mean(D);
			final double medianD = Stats.median(D);
			final double devD = Stats.standardDeviationN(D);

			final double meanSMSS = Stats.mean(SMSS);
			final double medianSMSS = Stats.median(SMSS);
			final double devSMSS = Stats.standardDeviationN(SMSS);

			this.jFrameStatsD.setModel(meanD, medianD, devD);
			this.jFrameStatsSMSS.setModel(meanSMSS, medianSMSS, devSMSS);
		} catch (final Exception e) {
			this.jFrameStatsD.setVisible(false);
			this.jFrameStatsSMSS.setVisible(false);
			JOptionPane.showMessageDialog(this,
			        OmegaConstants.ERROR_STATISTICAL_CALCULATION,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean processFolder(final String folder) {
		final File dir = new File(folder);

		// ====
		// find the labels file(s)
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.endsWith(OmegaConstants.LABELS_FILES_EXTENSION);
			}
		};

		String[] children = dir.list(filter);

		// check if we have more than 1 labels file
		if (children.length > 1) {
			JOptionPane.showMessageDialog(this, String.format(
			        "%d labels files found.\nUsing %s.", children.length,
			        children[0]), OmegaConstants.OMEGA_TITLE,
			        JOptionPane.INFORMATION_MESSAGE);
		}

		// set the labels file, if we have one
		String labels = null;
		if (children.length > 0) {
			labels = children[0];
		}

		// ====
		// find the trajectories files
		filter = new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.endsWith(OmegaConstants.TRACKS_FILES_EXTENSION);
			}
		};

		children = dir.list(filter);

		// ====
		// read the segmentation (if we have the labels file) or set it to 0
		int[][] segmentation = null;

		if (labels == null) {
			segmentation = new int[children.length][];

			for (int i = 0; i < children.length; i++) {
				try {
					double[][] trajectories = null;

					trajectories = FileIn.readTrajectory(folder
					        + System.getProperty("file.separator")
					        + children[i]);

					final int[] zeros = new int[trajectories[0].length - 1];
					Arrays.fill(zeros, 0);

					segmentation[i] = zeros;
				} catch (final IOException e) {
				}
			}
		} else {
			try {
				segmentation = FileIn.readSegmentation(folder
				        + System.getProperty("file.separator") + labels);
			} catch (final IOException e) {
				GLogManager.log(e.toString(), Level.SEVERE);
			}
		}

		// get the "image name"
		final String[] temp = folder.split("\\\\");
		final String imageName = temp[temp.length - 1];

		final double[] originalSizes = this.omeroDataHelper
		        .getOriginalSizes(folder);

		// ====
		// read the trajectories and process them
		for (int i = 0; i < children.length; i++) {
			double[][] trajectories = null;
			try {
				trajectories = FileIn.readTrajectory(folder
				        + System.getProperty("file.separator") + children[i]);
			} catch (final IOException e) {
				GLogManager.log(e.toString(), Level.SEVERE);
			}

			// use micron values to display the chart
			final double x[] = trajectories[0];
			final double xMicron[] = new double[x.length];
			final double y[] = trajectories[1];
			final double yMicron[] = new double[y.length];

			for (int iX = 0; iX < x.length; iX++) {
				xMicron[iX] = x[iX] * originalSizes[0];
			}

			for (int iY = 0; iY < x.length; iY++) {
				yMicron[iY] = y[iY] * originalSizes[1];
			}

			Stats stats;
			try {
				stats = new Stats(xMicron, yMicron, originalSizes[2],
				        segmentation[i]);
			} catch (final Exception e) {
				return false;
			}

			// for each segment
			for (int segment = 0; segment < stats.labelsLength(); ++segment) {
				final double[][] log_delta_t__log_mu__gamma__D = stats
				        .log_delta_t__log_mu__gamma__D(2, segment);
				final double[][] nu__gamma__S_MSS = stats
				        .nu__gamma__S_MSS(segment);

				final double D = log_delta_t__log_mu__gamma__D[2][3];
				final double S_MSS = nu__gamma__S_MSS[2][0];

				final int motionType = stats.labels(segment);

				if (Double.isNaN(D) || Double.isNaN(S_MSS)) {
					continue;
				}

				if ((motionType > -1) && (motionType < 5)) {
					// get segment's bounds and length
					final int minBound = stats.from(segment) + 1;
					final int maxBound = stats.to(segment) + 1;

					// System.out.println(minBound);
					// System.out.println(maxBound);

					// get the length of the segment
					final int l = stats.to(segment) - stats.from(segment);

					// get the min frame mean SNR within the time window of the
					// of the segment
					final SPTStatsFileReader reader = new SPTStatsFileReader(
					        folder);
					final double minMeanSNR = reader.getMinimunFrameMeanSNR(
					        children[i], minBound - 1, maxBound);

					// System.out.println(minMeanSNR);

					double uncertD = 0.0;
					double uncertSMSS = 0.0;

					try {
						final Interpolator4d dInterpolator = new Interpolator4d(
						        InterpolationType.D);
						uncertD = dInterpolator.interpolate(minMeanSNR, l,
						        S_MSS, D);
					} catch (final Exception e) {
						GLogManager.log(e.toString(), Level.WARNING);
					}

					try {
						final Interpolator4d sInterpolator = new Interpolator4d(
						        InterpolationType.SMSS);
						uncertSMSS = sInterpolator.interpolate(minMeanSNR, l,
						        S_MSS, D);
					} catch (final Exception e) {
						GLogManager.log(e.toString(), Level.WARNING);
					}

					this.smsSvsDChart.addDataToChart(D, S_MSS, motionType,
					        uncertD, uncertSMSS);

					// add the values also to the field
					final double[] D_SMSS_temp = { D, S_MSS };
					this.D_SMSS.add(D_SMSS_temp);

					// fix motions types
					int motionIndex = motionType;

					if (motionIndex == 0) {
						motionIndex = 4;
					} else {
						motionIndex--;
					}

					final String motionName = OMEGA.MOTIONTYPES[motionIndex];

					// add data to the ArrayList
					final String currentData[] = {
					        String.valueOf(++this.currentDataIndex),
					        String.format("%s.%d.%d", imageName, i + 1,
					                segment + 1), String.valueOf(D),
					        String.valueOf(S_MSS), motionName };
					this.currentChartData.add(currentData);
				}
			}
		}

		return true;
	}

	@Override
	protected List<String[]> generateData() {
		final ArrayList<String[]> data = new ArrayList<String[]>(
		        this.currentChartData.size() + 1);

		final String[] row0 = { "index", "image.trajectory.segment",
		        "D [microm^2/s]", "SMSS [-]", "motion type" };
		data.add(row0);

		for (final String[] dataElement : this.currentChartData) {
			data.add(dataElement);
		}

		return data;
	}
}
