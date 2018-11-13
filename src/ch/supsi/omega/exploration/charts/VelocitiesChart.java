package ch.supsi.omega.exploration.charts;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeEventType;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.supsi.omega.common.RandomColors;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.exploration.chartframes.JFrameVelocities;

public class VelocitiesChart extends JFrame implements ChartChangeListener
{
	private static final long			serialVersionUID	= 3363820280247085938L;

	private JFrameVelocities			jFrameVelocities	= null;

	private XYSeriesCollection			dataset				= new XYSeriesCollection();

	private XYPlot							plot					= null;

	private XYLineAndShapeRenderer	renderer				= new XYLineAndShapeRenderer();

	private double							timeDimension		= 1.0;

	private int								trackIndex			= 0;

	private ArrayList<Integer>			originalIndexes	= new ArrayList<Integer>();

	/**
	 * Used to manage the axis label for the velocity or intensity charts.
	 */
	private boolean						velocity				= true;

	public XYSeriesCollection getDataset()
	{
		return dataset;
	}

	public void setTimeDimension(double timeDimension)
	{
		this.timeDimension = timeDimension;
	}

	public VelocitiesChart(JFrameVelocities jFrameVelocities, boolean velocity)
	{
		this.jFrameVelocities = jFrameVelocities;
		this.velocity = velocity;
	}

	/**
	 * Creates the JFreeChart chart.
	 */
	public ChartPanel returnChart()
	{
		final JFreeChart chart = createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);

		chart.addChangeListener(this);

		return chartPanel;
	}

	private JFreeChart createChart()
	{
		String yLabel = (velocity) ? String.format("v [%sm/s]", Greeks.MU) : "I []";
		
		final JFreeChart chart = ChartFactory.createXYLineChart(null, // chart title
				"t [s]", // x axis label
				yLabel, dataset, // data
				PlotOrientation.VERTICAL, false, // include legend
				true, // tooltips
				false // urls
				);

		plot = chart.getXYPlot();

		// background
		chart.setBackgroundPaint(Color.WHITE);
		plot.setBackgroundPaint(Color.WHITE);

		plot.setRenderer(renderer);

		return chart;
	}

	public void scaleAxes(Range originalDomainRange, Range originalRangeRange)
	{
		if (originalDomainRange == null)
		{
			plot.getDomainAxis().setAutoRange(true);
			plot.getRangeAxis().setAutoRange(true);

			jFrameVelocities.setOriginalDomainRange(plot.getDomainAxis().getRange());
			jFrameVelocities.setOriginalRangeRange(plot.getRangeAxis().getRange());
		}
		else
		{
			plot.getDomainAxis().setAutoRange(false);
			plot.getRangeAxis().setAutoRange(false);

			plot.getDomainAxis().setRange(originalDomainRange);
			plot.getRangeAxis().setRange(originalRangeRange);
		}
	}

	/**
	 * Adds data to the JFreeChart dataset.
	 */
	public void addDataToChart(double v[], int originalIndex)
	{
		final XYSeries series1 = new XYSeries(trackIndex++);

		for (int i = 0; i < v.length; i++)
			series1.add(i * timeDimension, v[i]);

		dataset.addSeries(series1);

		// keep the original track indexes (i.e.: the trajectory number), in order to set later the correct color for each track
		originalIndexes.add(Integer.valueOf(originalIndex));
	}

	/**
	 * Colorate each series using the original serie index.
	 */
	public void colorateSeries()
	{
		int i = 0;

		for (Integer originalIndex : originalIndexes)
		{
			renderer.setSeriesLinesVisible(i, true);
			renderer.setSeriesShapesVisible(i, false);
			renderer.setSeriesPaint(i, RandomColors.getColors().get(originalIndex));
			i++;
		}
	}

	@Override
	public void chartChanged(ChartChangeEvent arg0)
	{
		if (arg0 instanceof PlotChangeEvent && arg0.getType() == ChartChangeEventType.GENERAL)
		{
			jFrameVelocities.setOriginalDomainRange(plot.getDomainAxis().getRange());
			jFrameVelocities.setOriginalRangeRange(plot.getRangeAxis().getRange());
		}
	}
}