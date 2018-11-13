package ch.supsi.omega.exploration.chartframes;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.exploration.charts.MSDChart;
import ch.supsi.omega.exploration.charts.MSSChart;
import ch.supsi.omega.exploration.common.JFrameStatsMSD;
import ch.supsi.omega.exploration.common.JFrameStatsMSS;
import ch.supsi.omega.exploration.processing.Stats;
import ch.supsi.omega.gui.JPanelExploration;

public class JFrameMSSMSD extends AbstractJFrameForChart {
	private static final long serialVersionUID = -1858307929847425427L;

	/**
	 * The JPanel who has created this JFrame.
	 */
	private JPanelExploration jPanelExploration = null;

	/**
	 * The current image name (needed for the data window).
	 */
	private String currentImageName = "";

	/**
	 * The chart type to be displayed.
	 */
	private String chartType = "";

	/**
	 * Array of Stats for the current trajectories.
	 */
	private Stats[] stats = null;

	private MSSChart mssChart = null;
	private MSDChart msdChart = null;

	/**
	 * The JFrameStats objects (there will be one for MSD and one for MSS)
	 */
	private JFrameStatsMSD jFrameStatsMSD = null;

	private JFrameStatsMSS jFrameStatsMSS = null;

	public void setCurrentImageName(final String currentImageName) {
		this.currentImageName = currentImageName;
	}

	public void setChartType(final String chartType) {
		this.chartType = chartType;
	}

	public JFrameStatsMSD getjFrameStatsMSD() {
		return this.jFrameStatsMSD;
	}

	public JFrameStatsMSS getjFrameStatsMSS() {
		return this.jFrameStatsMSS;
	}

	public JFrameMSSMSD(final JPanelExploration jPanelExploration,
	        final String chartType, final Stats[] stats) {
		super();
		this.jPanelExploration = jPanelExploration;
		this.chartType = chartType;
		this.stats = stats;
		this.setSliderBounds();
		this.addWindowListener();
	}

	public JFrameMSSMSD(final JPanelExploration jPanelExploration,
	        final String chartType, final Stats[] stats, final int x,
	        final int y) {
		super(x, y);
		this.jPanelExploration = jPanelExploration;
		this.chartType = chartType;
		this.stats = stats;
		this.setSliderBounds();
		this.addWindowListener();
	}

	private void setSliderBounds() {
		this.jSliderBottom.setMaximum(this.stats.length - 1);

		this.jSliderBottom.setValue(0);
		// processDataAndDisplayChart();
	}

	private void addWindowListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				// close also the other JFrame
				int index = 0;

				if (JFrameMSSMSD.this.chartType.equals("MSD")) {
					index = 1;
				}

				JFrameMSSMSD.this.jPanelExploration.getFrames()[index]
				        .dispose();
				JFrameMSSMSD.this.dispose();

				// close also the stats frames
				if (JFrameMSSMSD.this.chartType.equals("MSD")) {
					JFrameMSSMSD.this.jFrameStatsMSD.dispose();
					JFrameMSSMSD.this.jPanelExploration.getFrames()[1]
					        .getjFrameStatsMSS().dispose();
				} else {
					JFrameMSSMSD.this.jFrameStatsMSS.dispose();
					JFrameMSSMSD.this.jPanelExploration.getFrames()[0]
					        .getjFrameStatsMSD().dispose();
				}
			}
		});
	}

	@Override
	protected void createBottomSlider() {
		this.getContentPane().add(this.jSliderBottom, BorderLayout.SOUTH);

		this.jSliderBottom.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				JFrameMSSMSD.this.processDataAndDisplayChart();

				// move also the other JFrame's JSlider
				int index = 0;

				if (JFrameMSSMSD.this.chartType.equals("MSD")) {
					index = 1;
				}

				try {
					JFrameMSSMSD.this.jPanelExploration.getFrames()[index]
					        .getjSliderBottom().setValue(
					                JFrameMSSMSD.this.jSliderBottom.getValue());
				} catch (final Exception ee) {
				}
			}
		});
		this.jSliderBottom.requestFocusInWindow();
	}

	@Override
	protected void processDataAndDisplayChart() {
		// the current trajectory
		final int currentTrack = this.jSliderBottom.getValue();

		// set the title accordingly
		this.setTitle(String.format("%s - %s - %s %s %d",
		        OmegaConstants.OMEGA_TITLE, "RVE Module", this.chartType,
		        "of trajectory", currentTrack + 1));

		if (this.chartType.equals("MSS")) {
			final double[][] nu__gamma__S_MSS = this.stats[currentTrack]
			        .nu__gamma__S_MSS();

			this.mssChart = new MSSChart();
			this.mssChart.createDataset(nu__gamma__S_MSS);

			this.chartPanel = this.mssChart.returnChart();
		} else {
			final double[][] log_delta_t__log_mu__gamma__D = this.stats[currentTrack]
			        .log_delta_t__log_mu__gamma__D(2);

			this.msdChart = new MSDChart();
			this.msdChart.createDataset(log_delta_t__log_mu__gamma__D);

			this.chartPanel = this.msdChart.returnChart();
		}

		this.displayStatsFrames(currentTrack);

		this.displayChart();
	}

	/**
	 * Displays the stats JFrames for the two charts.
	 * 
	 * @param currentTrack
	 */
	private void displayStatsFrames(final int currentTrack) {
		if (this.chartType.equals("MSD")) {
			if (this.jFrameStatsMSD == null) {
				this.jFrameStatsMSD = new JFrameStatsMSD(this.getX() + 4,
				        this.getY() + this.getHeight(), "MSD [" + Greeks.MU
				                + "m\u00B2]");
				this.jFrameStatsMSD.setVisible(true);
			}

			// we need to remove the first value in the array as is always 0.0
			// we need then to raise the log values in order to calculate the
			// mean, median and std dev
			double mean = 0.0;
			double median = 0.0;
			double dev = 0.0;

			try {
				final double[] logMSD = this.stats[currentTrack]
				        .log_delta_t__log_mu__gamma__D(2)[1];
				final double[] MSD = new double[logMSD.length - 1];

				for (int i = 0; i < MSD.length; i++) {
					MSD[i] = Math.exp(logMSD[i + 1]);
				}

				mean = Stats.mean(MSD);
				median = Stats.median(MSD);
				dev = Stats.standardDeviationN(MSD);
			} catch (final Exception e) {
				JOptionPane
				        .showMessageDialog(
				                null,
				                "Error during the stats calculation. Mean,\nmedian, standard deviation not available",
				                OmegaConstants.OMEGA_TITLE,
				                JOptionPane.ERROR_MESSAGE);
			}

			this.jFrameStatsMSD.setModel(mean, median, dev);
		} else {
			if (this.jFrameStatsMSS == null) {
				this.jFrameStatsMSS = new JFrameStatsMSS(this.getX() + 4,
				        this.getY() + this.getHeight(), "MSS [-]");
				this.jFrameStatsMSS.setVisible(true);
			}

			final double[][] nu__gamma__S_MSS = this.stats[currentTrack]
			        .nu__gamma__S_MSS();
			final double slope = nu__gamma__S_MSS[2][0];

			this.jFrameStatsMSS.setModel(slope);
		}
	}

	@Override
	protected List<String[]> generateData() {
		final List<String[]> data = new ArrayList<String[]>();

		XYSeriesCollection dataset = null;

		// get the serie
		XYSeries xYserie = null;

		if (this.chartType.equals("MSS")) {
			dataset = this.mssChart.getDataset();
			xYserie = dataset.getSeries(0);
			// (columns description)
			final String[] row1 = { "index", "image.trajectory.segment",
			        "mu [-]", "gammamu [-]" };
			data.add(row1);
		} else {
			dataset = this.msdChart.getDataset();
			xYserie = dataset.getSeries(1);
			// (columns description)
			final String[] row1 = { "index", "image.trajectory", "deltat [s]",
			        "MSD [microm^2]" };
			data.add(row1);
		}

		int index;
		if (this.chartType.equals("MSS")) {
			index = 0;
		} else {
			index = 1;
		}

		for (int item = 0; item < xYserie.getItemCount(); item++) {
			Double xValue = null;
			Double yValue = null;

			try {
				xValue = (Double) xYserie.getX(item);
				yValue = (Double) xYserie.getY(item);
			} catch (final Exception e) {

			}

			if (Double.isNaN(xValue) || Double.isNaN(yValue)) {
				continue;
			}

			if (this.chartType.equals("MSD")) {
				xValue = Math.exp(xValue);
				yValue = Math.exp(yValue);
			}

			final String[] row = {
			        String.valueOf(index++),
			        String.format("%s.%d", this.currentImageName,
			                this.jSliderBottom.getValue() + 1),
			        String.valueOf(xValue), String.valueOf(yValue) };
			data.add(row);
		}

		return data;
	}
}
