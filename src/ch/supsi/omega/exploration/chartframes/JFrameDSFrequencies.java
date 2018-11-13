package ch.supsi.omega.exploration.chartframes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.charts.DSFrequenciesChart;
import ch.supsi.omega.exploration.processing.Type2Processor;
import ch.supsi.omega.gui.OMEGA;

public class JFrameDSFrequencies extends AbstractJFrameForChart
{
	private static final long						serialVersionUID		= -1858307929847425427L;

	protected HashMap<String, List<String>>	map						= null;

	private DSFrequenciesChart						dsFrequenciesChart	= null;

	public JFrameDSFrequencies(HashMap<String, List<String>> map)
	{
		super();
		this.map = map;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE, "RVE Module", "Motion type frequency by dataset"));
		processDataAndDisplayChart();
	}

	public JFrameDSFrequencies(HashMap<String, List<String>> map, int x, int y)
	{
		super(x, y);
		this.map = map;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE, "RVE Module", "Motion type frequency by dataset"));
		processDataAndDisplayChart();
	}

	@Override
	public void processDataAndDisplayChart()
	{
		Type2Processor dataProcessor = new Type2Processor();
		dataProcessor.processDataset(map);
		dsFrequenciesChart = dataProcessor.getdSFrequenciesChart();
		chartPanel = dsFrequenciesChart.displayChart();

		displayChart();
	}

	@Override
	protected List<String[]> generateData()
	{
		List<String[]> data = new ArrayList<String[]>();

		// get data
		DefaultCategoryDataset dataset = dsFrequenciesChart.getDataset();
		
		@SuppressWarnings("unchecked")
		List<String> datasets = dataset.getColumnKeys();
		
		// first row (columns description)
		String[] row0 = {"Motion type", "Frequency [%]" };
		data.add(row0);
		
		int datasetIndex = 0;
		
		for (String datasetName : datasets)
		{
			String[] row1 = { datasetName };
			data.add(row1);

			// values
			String[] m1 = { OMEGA.MOTIONTYPES[0], dataset.getValue(0, datasetIndex).toString() };
			String[] m2 = { OMEGA.MOTIONTYPES[1], dataset.getValue(1, datasetIndex).toString() };
			String[] m3 = { OMEGA.MOTIONTYPES[2], dataset.getValue(2, datasetIndex).toString() };
			String[] m4 = { OMEGA.MOTIONTYPES[3], dataset.getValue(3, datasetIndex).toString() };

			// rows
			data.add(m1);
			data.add(m2);
			data.add(m3);
			data.add(m4);
			
			datasetIndex++;
		}

		return data;
	}
}
