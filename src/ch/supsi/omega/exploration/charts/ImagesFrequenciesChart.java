package ch.supsi.omega.exploration.charts;

import java.awt.Color;
import java.awt.GradientPaint;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import ch.supsi.omega.segmentation.pattern.PatternsLoader;

public class ImagesFrequenciesChart extends JFrame
{
	private static final long			serialVersionUID	= 6984950691786770240L;

	private final String					series0				= "Unkown";
	private final String					series1				= "Direct";
	private final String					series2				= "Fast Drift";
	private final String					series3				= "Slow Drift";
	private final String					series4				= "Confined";

	private DefaultCategoryDataset	dataset				= new DefaultCategoryDataset();

	public DefaultCategoryDataset getDataset()
	{
		return dataset;
	}

	/**
	 * Creates the JFreeChart chart.
	 */
	public ChartPanel createChart()
	{
		final JFreeChart chart 		 = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		
		return chartPanel;
	}
	
	/**
	 * Creates the JFreeChart chart for the "type 1" analysis.
	 * @param A JFreeChart CategoryDataset
	 * @return The chart
	 */
	private JFreeChart createChart(final CategoryDataset dataset)
	{
		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart("Motion type frequency", // chart title
				"Motion type", // domain axis label
				"Frequency [%]", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.LIGHT_GRAY,  0.0f, 0.0f, Color.lightGray);
		renderer.setSeriesPaint(0, gp);
		
		int i = 1;
		for (Color  color : PatternsLoader.getPatternsColor())
		{
			gp = new GradientPaint(0.0f, 0.0f, color,  0.0f, 0.0f, Color.lightGray);
			renderer.setSeriesPaint(i++, gp);
		}

		return chart;
	}

	/**
	 * Adds data to the JFreeChart dataset.
	 * @param datasetName
	 * @param values
	 */
	public void addDataToChart(String imageName, int[] values)
	{
		dataset.clear();
		
		int sum = values[0] + values[1] + values[2] + values[3] + values[4];
		
		if(sum > 0)
		{
			dataset.addValue(100*values[0]/sum, series0, imageName);
			dataset.addValue(100*values[1]/sum, series1, imageName);
			dataset.addValue(100*values[2]/sum, series2, imageName);
			dataset.addValue(100*values[3]/sum, series3, imageName);
			dataset.addValue(100*values[4]/sum, series4, imageName);
		}
	}
}
