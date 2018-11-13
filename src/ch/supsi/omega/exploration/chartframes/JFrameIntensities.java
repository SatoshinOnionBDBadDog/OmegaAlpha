package ch.supsi.omega.exploration.chartframes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.charts.VelocitiesChart;
import ch.supsi.omega.exploration.charts.velocities.JFrameVelocitiesStats;
import ch.supsi.omega.segmentation.TPoint;
import ch.supsi.omega.segmentation.trajectory.TrajectoriesLoader;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class JFrameIntensities extends JFrameVelocities
{
	private static final long	serialVersionUID	= 5211412577751323457L;
	
	public JFrameIntensities(String map, int x, int y, boolean total)
	{
		super(map, x, y, total);		
	}

	@Override
	protected void getNewChart()
	{
		// create the velocities chart
		velocitiesChart = new VelocitiesChart(this, false);
	}
	
	protected boolean processFolder(String folder)
	{
		// get the "image name"
		String imageName;
		try
		{
			String[] temp = folder.split("\\\\");
			imageName = temp[temp.length - 1];
		}
		catch (Exception e1)
		{
			imageName = "unknown";
		}

		originalSizes = omeroDataHelper.getOriginalSizes(folder);

		// ====
		// read the trajectories and process them
		TrajectoriesLoader trajectoriesLoader = new TrajectoriesLoader(folder + System.getProperty("file.separator"), OmegaConstants.TRACKS_FILES_EXTENSION);

		List<Trajectory> trajectories = trajectoriesLoader.loadTrajectories();

		for (int i = 0; i < trajectories.size(); i++)
		{
			ArrayList<Double>  valuesTotal = new ArrayList<Double>();
			ArrayList<Double>  valuesMean  = new ArrayList<Double>();
			ArrayList<Integer> nrOfPixels  = new ArrayList<Integer>();

			for (TPoint point : trajectories.get(i).getPoints())
			{
					valuesTotal.add(point.getTotalSignal());
					valuesMean.add(point.getTotalSignal() / point.getCntSignal());
					nrOfPixels.add(point.getCntSignal());
			}

			double[] vTotal = new double[valuesTotal.size()];
			double[] vMean  = new double[valuesTotal.size()];

			for (int j = 0; j < valuesTotal.size(); j++)
			{
				vTotal[j] = valuesTotal.get(j);
				vMean[j]  = valuesMean.get(j);
			}

			if(total)
				calculateVelocityStats(vTotal);
			else
				calculateVelocityStats(vMean);

			// set the correct size in the chart (domain axis)
			velocitiesChart.setTimeDimension(originalSizes[2]);

			// display the trajectories based on the choice in the Jframe
			if (jFrameTrajectoriesChooser.getjCheckBoxs().get(i).isSelected())
			{
				if(total)
					velocitiesChart.addDataToChart(vTotal, i);
				else
					velocitiesChart.addDataToChart(vMean, i);
				
				for (int j = 0; j < vTotal.length; j++)
				{	
					String currentData[] = { String.valueOf(++currentDataIndex), String.format("%s.%d.%d", imageName, i + 1, j), String.valueOf((int)vTotal[j]), String.valueOf(new DecimalFormat("#.###").format(vMean[j])), String.valueOf(nrOfPixels.get(j)) };
					currentChartData.add(currentData);
				}
			}

		}

		velocitiesChart.colorateSeries();

		return true;
	}
		
	/**
	 * Displays the velocites stats frame.
	 */
	@Override
	protected void displayStatsFrame()
	{
		jFrameVelocitiesStats = new JFrameVelocitiesStats(this);
		jFrameVelocitiesStats.setModel(velocitiesStats);
		jFrameVelocitiesStats.setVisible(true);
	}
	
	@Override
	protected List<String[]> generateData()
	{
		ArrayList<String[]> data = new ArrayList<String[]>(currentChartData.size() + 1);

		String[] row0 = { "index", "image.trajectory.frame", "IFI []", "MFI []", "Nr. of pixels" };
		data.add(row0);

		for (String[] dataElement : currentChartData)
			data.add(dataElement);

		return data;
	}
}
