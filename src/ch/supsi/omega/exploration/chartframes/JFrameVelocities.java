package ch.supsi.omega.exploration.chartframes;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.jfree.data.Range;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.charts.VelocitiesChart;
import ch.supsi.omega.exploration.charts.velocities.JFrameVelocitiesStats;
import ch.supsi.omega.exploration.common.JFrameTrajectoriesChooser;
import ch.supsi.omega.exploration.common.imagedata.OmeroDataHelper;
import ch.supsi.omega.exploration.processing.FileIn;
import ch.supsi.omega.exploration.processing.Stats;

import com.galliva.gallibrary.GLogManager;

public class JFrameVelocities extends AbstractJFrameForChart {
	private static final long serialVersionUID = -1858307929847425427L;

	/**
	 * The Chart to be displayed in this JFrame.
	 */
	protected VelocitiesChart velocitiesChart = null;

	/**
	 * The folder to be processed.
	 */
	protected String currentFolder = null;

	/**
	 * Arraylist containing the data information to be visualized / exported in
	 * CSV.
	 */
	protected List<String[]> currentChartData = null;

	/**
	 * Index used when creating the currentChartData List.
	 */
	protected int currentDataIndex = 0;

	/**
	 * An OmeroDataHelper instance, used to get the OMERO image data (pixels
	 * sizes, delta t).
	 */
	protected OmeroDataHelper omeroDataHelper = new OmeroDataHelper(this);

	/**
	 * JFrame used by the User to choose the trajectories to be visualized.
	 */
	protected JFrameTrajectoriesChooser jFrameTrajectoriesChooser = null;

	/**
	 * JFrame displaying the velocites stats for the trajectories.
	 */
	protected JFrameVelocitiesStats jFrameVelocitiesStats = null;

	/**
	 * Arraylist with all the velocities stats.
	 */
	protected ArrayList<double[]> velocitiesStats = new ArrayList<double[]>();

	/**
	 * The Range of the domain axys (keeped in order to have always the same
	 * scaling).
	 */
	protected Range originalDomainRange = null;

	/**
	 * The Range of the range axys.
	 */
	protected Range originalRangeRange = null;

	/**
	 * The "original sizes" of the image, i.e.: sizes X, Y and T
	 */
	protected double[] originalSizes = null;

	/**
	 * Used for intensities JFrame, who inherits from this class (walk around)
	 */
	protected boolean total = true;

	public void setOriginalDomainRange(Range originalDomainRange) {
		this.originalDomainRange = originalDomainRange;
	}

	public void setOriginalRangeRange(Range originalRangeRange) {
		this.originalRangeRange = originalRangeRange;
	}

	public JFrameVelocities(String map, int x, int y) {
		super(x, y);
		this.currentFolder = map;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE,
		        "RVE Module", "Mean Fluorescence Intensities"));

		addWindowListener();
		processDataAndDisplayChart();

		displayStatsFrame();
	}

	public JFrameVelocities(String map, int x, int y, boolean total) {
		super(x, y);
		this.currentFolder = map;
		this.total = total;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE,
		        "RVE Module", "Mean Fluorescence Intensities"));

		addWindowListener();
		processDataAndDisplayChart();

		displayStatsFrame();
	}

	private void addWindowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				jFrameTrajectoriesChooser.dispose();
				jFrameVelocitiesStats.dispose();
			}
		});
	}

	@Override
	public void processDataAndDisplayChart() {
		createChoosingFrame();
		processDataset();
		jFrameTrajectoriesChooser.setVisible(true);
	}

	private void createChoosingFrame() {
		// close the choosing JFrame, if present
		try {
			jFrameTrajectoriesChooser.dispose();
		} catch (Exception e) {
			// nothing to do...
		}

		// re-create the chhosing frame
		jFrameTrajectoriesChooser = new JFrameTrajectoriesChooser(this);

		File dir = new File(currentFolder);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(OmegaConstants.TRACKS_FILES_EXTENSION);
			}
		};

		String[] children = dir.list(filter);

		jFrameTrajectoriesChooser.addCheckBoxs(children.length);
	}

	public void processDataset() {
		getNewChart();

		// reset the current chart data
		currentChartData = new ArrayList<String[]>();
		currentDataIndex = 0;

		if (!processFolder(currentFolder))
			return;

		chartPanel = velocitiesChart.returnChart();

		velocitiesChart.scaleAxes(originalDomainRange, originalRangeRange);

		displayChart();
	}

	protected void getNewChart() {
		// create the velocities chart
		velocitiesChart = new VelocitiesChart(this, true);
	}

	protected boolean processFolder(String folder) {
		File dir = new File(folder);

		// ====
		// find the trajectories files
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(OmegaConstants.TRACKS_FILES_EXTENSION);
			}
		};

		String[] children = dir.list(filter);

		// get the "image name"
		String imageName;
		try {
			String[] temp = folder.split("\\\\");
			imageName = temp[temp.length - 1];
		} catch (Exception e1) {
			imageName = "unknown";
		}

		originalSizes = omeroDataHelper.getOriginalSizes(folder);

		// ====
		// read the trajectories and process them
		for (int i = 0; i < children.length; i++) {
			double[][] trajectories = null;
			try {
				trajectories = FileIn.readTrajectory(folder
				        + System.getProperty("file.separator") + children[i]);
			} catch (IOException e) {
				GLogManager.log(e.toString(), Level.SEVERE);
			}

			// use micron values to display the chart
			double x[] = trajectories[0];
			double xMicron[] = new double[x.length];
			double y[] = trajectories[1];
			double yMicron[] = new double[y.length];

			for (int iX = 0; iX < x.length; iX++)
				xMicron[iX] = x[iX] * originalSizes[0];

			for (int iY = 0; iY < x.length; iY++)
				yMicron[iY] = y[iY] * originalSizes[1];

			Stats stats;
			try {
				stats = new Stats(xMicron, yMicron, originalSizes[2], null);
			} catch (Exception e) {
				return false;
			}

			double v[] = stats.instantVelocities();

			calculateVelocityStats(v);

			// set the correct size in the chart (domain axis)
			velocitiesChart.setTimeDimension(originalSizes[2]);

			// display the trajectories based on the choice in the Jframe
			if (jFrameTrajectoriesChooser.getjCheckBoxs().get(i).isSelected()) {
				velocitiesChart.addDataToChart(v, i);

				for (int j = 0; j < v.length; j++) {
					String currentData[] = {
					        String.valueOf(++currentDataIndex),
					        String.format("%s.%d.%d", imageName, i + 1, j),
					        String.valueOf(v[j]) };
					currentChartData.add(currentData);
				}
			}
		}

		velocitiesChart.colorateSeries();

		return true;
	}

	/**
	 * Calculates the velocities stats. Please keep in mind that (in the RVE
	 * module) we are using the FileIn static class from L. Giulietti in order
	 * to load the trajectories: for every trajectory's point we are not aware
	 * anymore of the original frame number (relative to the image's T size).
	 * The Tmax and Tmin indexes are relative to the v[] array and they are not
	 * related to original frames numbers!
	 * 
	 * @param v
	 *            an array of double.
	 */
	protected void calculateVelocityStats(double v[]) {
		double avg = 0.0;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		int minInd = 0;
		int maxInd = 0;

		for (int i = 0; i < v.length; i++) {
			avg = avg + v[i];

			if (v[i] < min) {
				min = v[i];
				minInd = i;
			}

			if (v[i] > max) {
				max = v[i];
				maxInd = i;
			}
		}

		avg = avg / v.length;

		double std = Stats.standardDeviationN(v);

		double[] temp = { v.length, avg, std, min, max,
		        minInd * originalSizes[2], maxInd * originalSizes[2] };

		velocitiesStats.add(temp);
	}

	/**
	 * Displays the velocites stats frame.
	 */
	protected void displayStatsFrame() {
		jFrameVelocitiesStats = new JFrameVelocitiesStats(this);
		jFrameVelocitiesStats.setModel(velocitiesStats);
		jFrameVelocitiesStats.setVisible(true);
	}

	public void setClickedIndexInTheStatsFrame(int index) {
		jFrameTrajectoriesChooser.setIndexFromExternalFrame(index);
	}

	@Override
	protected List<String[]> generateData() {
		ArrayList<String[]> data = new ArrayList<String[]>(
		        currentChartData.size() + 1);

		String[] row0 = { "index", "image.trajectory.frame", "v [microm/s]" };
		data.add(row0);

		for (String[] dataElement : currentChartData)
			data.add(dataElement);

		return data;
	}
}
