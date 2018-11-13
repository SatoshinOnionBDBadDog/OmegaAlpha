package ch.supsi.omega.exploration.charts;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.exploration.processing.Stats;

public class MSSChart
{
	/**
	 * The XYSeriesCollection with the "main" line.
	 */
	private XYSeriesCollection	_DSmainDataLine	= new XYSeriesCollection();
	/**
	 * The XYSeriesCollection with the slope.
	 */
	private XYSeriesCollection	_DSslope				= new XYSeriesCollection();
	/**
	 * The XYSeriesCollection with Y = X and Y = 0.5 * X.
	 */
	private XYSeriesCollection	_DSarea				= new XYSeriesCollection();

	public XYSeriesCollection getDataset()
	{
		return _DSmainDataLine;
	}

	public ChartPanel returnChart()
	{
		final JFreeChart chart = createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);

		return chartPanel;
	}

	public void createDataset(double[][] nu__gamma__S_MSS)
	{
		int n = nu__gamma__S_MSS[0].length - 1;

		// main data
		final XYSeries mainDataLine = new XYSeries("xxx");

		for (int i = 0; i <= Stats.MAX_NU; ++i)
			mainDataLine.add(i, nu__gamma__S_MSS[1][i]);
		
		_DSmainDataLine.addSeries(mainDataLine);

		// slope
		final XYSeries slope = new XYSeries("Slope");
		slope.add(nu__gamma__S_MSS[0][0], nu__gamma__S_MSS[0][0] * nu__gamma__S_MSS[2][0] + nu__gamma__S_MSS[2][1]);
		slope.add(nu__gamma__S_MSS[0][n], nu__gamma__S_MSS[0][n] * nu__gamma__S_MSS[2][0] + nu__gamma__S_MSS[2][1]);

		_DSslope.addSeries(slope);
		
		// Y = X and Y = 0.5 * X
		final XYSeries series1 = new XYSeries("Y = X");
		series1.add(0.0, 0.0);
		series1.add(6.0, 6.0);
		final XYSeries series05 = new XYSeries("Y = 1/2 * X");
		series05.add(0.0, 0.0);
		series05.add(6.0, 3.0);

		_DSarea.addSeries(series1);
		_DSarea.addSeries(series05);
	}

	private JFreeChart createChart()
	{
		final JFreeChart chart = ChartFactory.createXYLineChart(null, // chart title
				Greeks.MU + " [-]", // x axis label
				Greeks.GAMMA + Greeks.MU + " [-]", 
				null, // data
				PlotOrientation.VERTICAL, 
				false, // include legend
				true, // tooltips
				false // urls
				);

		final XYPlot plot = chart.getXYPlot();

		// axis
		final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		xAxis.setRange(new Range(0.0, 6.1));
		final NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		yAxis.setRange(new Range(0.0, 6.1));

		// background
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(Color.WHITE);

		// 3 renderer, 3 datasets
		final XYLineAndShapeRenderer rendererDSmainDataLine 	= new XYLineAndShapeRenderer();
		final XYDifferenceRenderer rendererDSarea 				= new XYDifferenceRenderer(new Color(192, 192, 192, 80), new Color(192, 192, 192, 80), false);
		final XYLineAndShapeRenderer rendererDSslope 			= new XYLineAndShapeRenderer();

		rendererDSmainDataLine.setSeriesLinesVisible(0, true);
		rendererDSmainDataLine.setSeriesShapesVisible(0, true);
		rendererDSmainDataLine.setSeriesPaint(0, Color.RED);

		rendererDSarea.setSeriesPaint(0, Color.GRAY);
		rendererDSarea.setSeriesPaint(1, Color.GRAY);
		rendererDSarea.setSeriesStroke(0, new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 6.0f }, 0.0f));
		rendererDSarea.setSeriesStroke(1, new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 6.0f }, 0.0f));

		rendererDSslope.setSeriesLinesVisible(0, true);
		rendererDSslope.setSeriesShapesVisible(0, false);
		rendererDSslope.setSeriesStroke(0, new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 6.0f }, 0.0f));
		rendererDSslope.setSeriesPaint(0, Color.BLUE);

		// set the correct association datasets / renderers
		plot.setDataset(0, _DSmainDataLine);
		plot.setRenderer(0, rendererDSmainDataLine);
		plot.setDataset(1, _DSslope);
		plot.setRenderer(1, rendererDSslope);
		plot.setDataset(2, _DSarea);
		plot.setRenderer(2, rendererDSarea);

		return chart;
	}
}