package ch.supsi.omega.exploration.chartframes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.data.category.DefaultCategoryDataset;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.charts.ImagesFrequenciesChart;
import ch.supsi.omega.exploration.common.JDialogImageName;
import ch.supsi.omega.exploration.processing.Type2Processor;
import ch.supsi.omega.gui.OMEGA;

public class JFrameImageFrequencies extends AbstractJFrameForChart
{
	private static final long						serialVersionUID			= -1858307929847425427L;

	protected HashMap<String, List<String>>	map							= null;

	private List<String>								currentFolders				= null;

	/**
	 * Hashmap containing the default image name and the user-choosen name of this chart.
	 */
	private Map<String, String>					imageNames					= new HashMap<String, String>();

	/**
	 * The default image name of this chart.
	 */
	private String										defaultImageName			= null;

	/**
	 * The current image name.
	 */
	private String										currentImageName			= null;

	/**
	 * The chart to be displayed.
	 */
	private ImagesFrequenciesChart				imagesFrequenciesChart	= null;

	public String getCurrentImageName()
	{
		return currentImageName;
	}
	
	public JFrameImageFrequencies(HashMap<String, List<String>> map)
	{
		super();
		this.map = map;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE, "RVE Module", "Motion type frequency by image"));
		addDatasets();
	}

	public JFrameImageFrequencies(HashMap<String, List<String>> map, int x, int y)
	{
		super(x, y);
		this.map = map;
		setTitle(String.format("%s - %s - %s", OmegaConstants.OMEGA_TITLE, "RVE Module", "Motion type frequency by image"));
		addDatasets();
	}

	@Override
	protected void createMenu()
	{
		JMenuBar jMenuBar = chartMenu.getMenuBar();

		JMenu chartMenu = new JMenu("Chart");

		JMenuItem changeNameAction = new JMenuItem("Change image name...");
		changeNameAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		chartMenu.add(changeNameAction);

		changeNameAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				changeImageName();
			}
		});

		jMenuBar.add(chartMenu);

		super.createMenu();
	}

	private void changeImageName()
	{
		JDialogImageName jDialogImageName = new JDialogImageName(this);
		jDialogImageName.setLocationRelativeTo(this);
		jDialogImageName.setVisible(true);
		
		String newImageName = jDialogImageName.getNewImageName();
		
		if(newImageName != null)
		{
			imageNames.remove(defaultImageName);
			imageNames.put(defaultImageName, newImageName);
		}
		
		processDataAndDisplayChart();
	}

	@Override
	protected void createTopPanel()
	{
		jPanelTop.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
		jLabelDataset.setText("Dataset: ");
		jPanelTop.add(jLabelDataset);

		jPanelTop.add(jComboBoxDataset);
		getContentPane().add(jPanelTop, BorderLayout.NORTH);
	}

	@Override
	protected void createBottomSlider()
	{
		jSliderBottom.setMinimum(0);
		jSliderBottom.setSnapToTicks(true);

		getContentPane().add(jSliderBottom, BorderLayout.SOUTH);

		// listeners
		jComboBoxDataset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jSliderBottom.requestFocusInWindow();
				updateJSlider();
			}
		});
		jSliderBottom.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				processDataAndDisplayChart();
			}
		});
	}

	/**
	 * Adds the datasets list to the JComboBox (when the JFrame is istantiaded for the first time).
	 */
	private void addDatasets()
	{
		List<String> dataSets = new ArrayList<String>();

		Iterator<Entry<String, List<String>>> it = map.entrySet().iterator();

		while (it.hasNext())
		{
			Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) it.next();
			dataSets.add(pairs.getKey());
		}

		jComboBoxDataset.setModel(new DefaultComboBoxModel(dataSets.toArray()));

		updateJSlider();
	}

	private void updateJSlider()
	{
		currentFolders = map.get(jComboBoxDataset.getSelectedItem().toString());

		int size = currentFolders.size();

		jSliderBottom.setMaximum(size - 1);

		jSliderBottom.requestFocusInWindow();

		processDataAndDisplayChart();
	}

	@Override
	public void processDataAndDisplayChart()
	{
		// get the folder to be processed
		String currentFolder = null;

		if (currentFolders.size() < 2)
			currentFolder = currentFolders.get(0);
		else
			currentFolder = currentFolders.get(jSliderBottom.getValue());

		// get the default "image name"
		String[] temp    = currentFolder.split("\\\\");
		defaultImageName = temp[temp.length - 1];

		// check if the user has changed the image name
		currentImageName = imageNames.get(defaultImageName);

		if (currentImageName == null)
			currentImageName = defaultImageName;

		// process folder
		Type2Processor dataProcessor = new Type2Processor();
		dataProcessor.resetCounters();
		dataProcessor.processFolder(currentFolder);

		// results
		int[] motionsFrequencies = dataProcessor.getMotionsCounter();

		// display chart
		imagesFrequenciesChart = new ImagesFrequenciesChart();
		imagesFrequenciesChart.addDataToChart(currentImageName, motionsFrequencies);

		chartPanel = imagesFrequenciesChart.createChart();

		displayChart();
	}

	@Override
	protected List<String[]> generateData()
	{
		List<String[]> data = new ArrayList<String[]>();

		// get data
		DefaultCategoryDataset dataset = imagesFrequenciesChart.getDataset();

		String[] m1 = { OMEGA.MOTIONTYPES[4], dataset.getValue(0, 0).toString() };
		String[] m2 = { OMEGA.MOTIONTYPES[0], dataset.getValue(1, 0).toString() };
		String[] m3 = { OMEGA.MOTIONTYPES[1], dataset.getValue(2, 0).toString() };
		String[] m4 = { OMEGA.MOTIONTYPES[2], dataset.getValue(3, 0).toString() };
		String[] m5 = { OMEGA.MOTIONTYPES[3], dataset.getValue(4, 0).toString() };

		// first row (columns description)
		String[] row0 = { "Motion type", "Frequency [%]" };
		data.add(row0);

		// rows
		data.add(m1);
		data.add(m2);
		data.add(m3);
		data.add(m4);
		data.add(m5);

		return data;
	}
}
