package ch.supsi.omega.exploration.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.util.ShapeUtilities;

import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.gui.OMEGA;
import ch.supsi.omega.segmentation.pattern.PatternsLoader;

public class SMSSvsDChart extends JFrame {
	private static final long serialVersionUID = 3363820280247085938L;

	/**
	 * The current's plot.
	 */
	private XYPlot plot = null;

	/**
	 * The 4 serie (morions types) to be added to the Chart.
	 */
	private final XYIntervalSeries[] series = {
	        new XYIntervalSeries(OMEGA.MOTIONTYPES[0]),
	        new XYIntervalSeries(OMEGA.MOTIONTYPES[1]),
	        new XYIntervalSeries(OMEGA.MOTIONTYPES[2]),
	        new XYIntervalSeries(OMEGA.MOTIONTYPES[3]),
	        new XYIntervalSeries(OMEGA.MOTIONTYPES[4]) };

	/**
	 * The chart's dataset.
	 */
	private final XYIntervalSeriesCollection xyintervalseriescollection = new XYIntervalSeriesCollection();

	public ChartPanel returnChart() {
		final JFreeChart chart = this.createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);

		return chartPanel;
	}

	private JFreeChart createChart() {
		for (final XYIntervalSeries serie : this.series) {
			this.xyintervalseriescollection.addSeries(serie);
		}

		// axis customization
		final NumberAxis numberaxisX = new LogarithmicAxis(String.format(
		        "D [%sm\u00B2 / s]", Greeks.MU));
		final NumberAxis numberaxisY = new NumberAxis("SMSS");
		numberaxisY.setRange(0.0, 1.0);
		numberaxisY.setTickUnit(new NumberTickUnit(0.1));

		// error bars customization
		final XYErrorRenderer renderer = new XYErrorRenderer();
		renderer.setErrorStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
		        BasicStroke.JOIN_ROUND, 0.5f, new float[] { 6.0f, 6.0f }, 0.0f));

		this.plot = new XYPlot(this.xyintervalseriescollection, numberaxisX,
		        numberaxisY, renderer);
		this.plot.setBackgroundPaint(Color.WHITE);

		// series
		final Shape cross = ShapeUtilities.createDiagonalCross(1, 1);

		// series shape
		for (int i = 0; i < this.series.length; i++) {
			renderer.setSeriesLinesVisible(i, false);
			renderer.setSeriesShapesVisible(i, true);
			renderer.setSeriesShape(i, cross);
		}

		// series color
		for (int i = 0; i < this.series.length; i++) {
			renderer.setSeriesPaint(i, PatternsLoader.getPatternsColor()[i]);
		}

		this.plot.setRenderer(renderer);

		// add a labelled marker for 0.5
		final Marker marker = new ValueMarker(0.5);
		marker.setPaint(Color.BLACK);
		marker.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
		        BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 6.0f }, 0.0f));
		this.plot.addRangeMarker(marker);

		final JFreeChart jfreechart = new JFreeChart(null, this.plot);
		jfreechart.setBackgroundPaint(Color.white);
		return jfreechart;
	}

	/**
	 * Draws a JFreeChart Marker for the minimum detectable D.
	 * 
	 * @param value
	 *            The x coordinate where to put the line.
	 */
	public void drawMinimumDetectableDLine(final double value) {
		final Marker currentEnd = new ValueMarker(value);
		currentEnd.setPaint(Color.red);
		this.plot.addDomainMarker(currentEnd);
	}

	/**
	 * Adds data to the JFreeChart dataset.
	 * 
	 * @param datasetName
	 * @param values
	 */
	public void addDataToChart(final double D, double S_MSS, int motionType,
	        final double uD, final double uSMSS) {
		if (S_MSS < 0.0) {
			S_MSS = 0.0;
		}

		if (motionType == 0) {
			motionType = 4;
		} else {
			motionType--;
		}

		this.series[motionType].add(D, D - uD, D + uD, S_MSS, S_MSS - uSMSS,
		        S_MSS + uSMSS);
	}
}