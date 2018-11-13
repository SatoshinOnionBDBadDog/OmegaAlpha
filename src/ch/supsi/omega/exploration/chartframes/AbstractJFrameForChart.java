package ch.supsi.omega.exploration.chartframes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;

import ch.supsi.omega.exploration.common.CSVExporter;
import ch.supsi.omega.exploration.common.JFrameChartData;

public abstract class AbstractJFrameForChart extends JFrame {
	private static final long serialVersionUID = -1858307929847425427L;

	/**
	 * The (standard) Menu to be added to this JFrame.
	 */
	protected ChartMenu chartMenu = new ChartMenu(this);

	protected JPanel jPanelTop = new JPanel();
	protected JComboBox jComboBoxDataset = new JComboBox();
	protected JLabel jLabelDataset = new JLabel();
	protected JPanel jPanelCenter = new JPanel();
	protected JSlider jSliderBottom = new JSlider();

	/**
	 * The JFreeChart ChartPanel to be added in the jPanelCenter.
	 */
	protected ChartPanel chartPanel = null;

	// @formatter:off
	public JPanel getjPanelTop() {
		return jPanelTop;
	}

	public JComboBox getjComboBoxDataset() {
		return jComboBoxDataset;
	}

	public JLabel getjLabelDataset() {
		return jLabelDataset;
	}

	public JPanel getjPanelCenter() {
		return jPanelCenter;
	}

	public JSlider getjSliderBottom() {
		return jSliderBottom;
	}

	// @formatter:on

	public AbstractJFrameForChart() {
		initComponents();
	}

	public AbstractJFrameForChart(int x, int y) {
		setLocation(x, y);
		initComponents();
	}

	private final void initComponents() {
		createJFrame();

		createMenu();

		createTopPanel();

		createCenterPanel();

		createBottomSlider();

		pack();
	}

	/**
	 * Sets the layout of this JFrame.
	 */
	private final void createJFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(600, 500));
		getContentPane().setLayout(new BorderLayout(3, 0));
	}

	/**
	 * Sets the standard JMenuBar for this JFrame.
	 */
	protected void createMenu() {
		setJMenuBar(chartMenu.getMenuBar());
	}

	/**
	 * Override this method if a top JPanel is needed.
	 */
	protected void createTopPanel() {
		// override if needed
	}

	/**
	 * Sets the layout of the center JPanel and add it to the JFrame.
	 */
	private final void createCenterPanel() {
		jPanelCenter.setLayout(new BorderLayout());
		getContentPane().add(jPanelCenter, BorderLayout.CENTER);
	}

	/**
	 * Override this method if a bottom JSlider is needed.
	 */
	protected void createBottomSlider() {
		// override if needed
	}

	/**
	 * Override this method and call it to display the JFreeChart chart in the
	 * center JPanel.
	 */
	protected abstract void processDataAndDisplayChart();

	/**
	 * Adds the JFreeChart ChartPanel to the center JPanel.
	 */
	protected final void displayChart() {
		if (chartPanel != null) {
			chartPanel.setMinimumSize(new Dimension(900, 900));

			jPanelCenter.removeAll();
			jPanelCenter.add(chartPanel);
			jPanelCenter.revalidate();
		}
	}

	/**
	 * Override this method in order to generate the data to be saved on the CSV
	 * file / displayed on a new JFrame.
	 * 
	 * @return
	 */
	protected abstract List<String[]> generateData();

	/**
	 * Displays a new JFrame showing the JFreeChart dataset's values.
	 */
	public final void displayData() {
		List<String[]> data = generateData();
		JFrameChartData jFrameChartData = new JFrameChartData(data);

		// additional check on SMSS vs D chart
		if (this.getClass() == JFrameSMSSvsD.class)
			jFrameChartData.setSMSSvsDadditionalCheck(true);

		jFrameChartData.addRows();

		jFrameChartData.setLocationRelativeTo(this);
		jFrameChartData.setVisible(true);
	}

	/**
	 * Saves the JFreeChart dataset's values in CSV file.
	 */
	public final void saveData() {
		List<String[]> data = generateData();
		CSVExporter.exportCSV(data);
	}
}
