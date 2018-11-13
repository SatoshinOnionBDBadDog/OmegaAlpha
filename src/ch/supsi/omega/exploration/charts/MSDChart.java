package ch.supsi.omega.exploration.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.science.Greeks;

import com.galliva.gallibrary.GLogManager;

public class MSDChart {
	private XYSeriesCollection dataset = null;

	public XYSeriesCollection getDataset() {
		return this.dataset;
	}

	public ChartPanel returnChart() {
		final JFreeChart chart = this.createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);

		return chartPanel;
	}

	public void createDataset(final double[][] log_delta_t__log_mu__gamma__D) {
		final int n = log_delta_t__log_mu__gamma__D[0].length - 1;

		// slope
		final XYSeries series1 = new XYSeries("First");

		try {
			series1.add(
			        log_delta_t__log_mu__gamma__D[0][1],
			        (log_delta_t__log_mu__gamma__D[0][1] * log_delta_t__log_mu__gamma__D[2][0])
			                + log_delta_t__log_mu__gamma__D[2][1]);
			series1.add(
			        log_delta_t__log_mu__gamma__D[0][n],
			        (log_delta_t__log_mu__gamma__D[0][n] * log_delta_t__log_mu__gamma__D[2][0])
			                + log_delta_t__log_mu__gamma__D[2][1]);
		} catch (final Exception e) {
			GLogManager.log(
			        "Unable to add the slope the serie: " + e.toString(),
			        Level.FINE);
		}

		// main line
		final XYSeries series2 = new XYSeries("Second");

		try {
			for (int i = 1; i <= n; i++) {
				series2.add(log_delta_t__log_mu__gamma__D[0][i],
				        log_delta_t__log_mu__gamma__D[1][i]);
			}

			GLogManager.log(String.format("inseriti nel grafico %d valori", n));
		} catch (final Exception e) {
			GLogManager.log(
			        "Unable to display correctly the chart: " + e.toString(),
			        Level.FINE);
			JOptionPane.showMessageDialog(null,
			        "Unable to display correctly the chart",
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
		}

		this.dataset = new XYSeriesCollection();
		this.dataset.addSeries(series1);
		this.dataset.addSeries(series2);
	}

	private JFreeChart createChart() {
		final JFreeChart chart = ChartFactory.createXYLineChart(null,
		        String.format("log(%st) [s]", Greeks.DELTA),
		        String.format("log(MSD) [%sm\u00B2]", Greeks.MU), this.dataset,
		        PlotOrientation.VERTICAL, false, true, false);

		final XYPlot plot = chart.getXYPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		// background
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(Color.WHITE);

		// series1
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesStroke(0, new BasicStroke(1.0f,
		        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
		        new float[] { 6.0f, 6.0f }, 0.0f));

		// series2
		renderer.setSeriesLinesVisible(1, true);
		renderer.setSeriesShapesVisible(1, false);

		plot.setRenderer(renderer);

		return chart;
	}
}