package ch.supsi.omega.exploration.charts.velocities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.exploration.chartframes.JFrameVelocities;
import ch.supsi.omega.exploration.common.CSVExporter;

public class JFrameVelocitiesStats extends JFrame {
	private static final long serialVersionUID = -8960604531762240333L;

	/**
	 * The JMenu of this JFrame.
	 */
	protected VelocitiesStatsMenu menu = new VelocitiesStatsMenu(this);

	/**
	 * The stats to be displayed / saved.
	 */
	protected ArrayList<double[]> stats = null;

	protected JFrameVelocities jFrameVelocities = null;
	protected JScrollPane jScrollPaneStats = new JScrollPane();
	protected JTable jTableStats = null;
	private String title = String.format("%s - %s - %s",
	        OmegaConstants.OMEGA_TITLE, "RVE Module",
	        "Mean Fluorescence Intensity Stats");

	public JFrameVelocitiesStats(JFrameVelocities jFrameVelocities) {
		this.jFrameVelocities = jFrameVelocities;
		initComponents();
	}

	protected void initComponents() {
		setLocation(jFrameVelocities.getX(), jFrameVelocities.getY()
		        + jFrameVelocities.getHeight());
		setPreferredSize(new Dimension(700, 350));

		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setJMenuBar(menu.getMenuBar());

		getContentPane().add(jScrollPaneStats, BorderLayout.CENTER);
		pack();
	}

	public void setModel(ArrayList<double[]> stats) {
		this.stats = stats;

		DecimalFormat df = new DecimalFormat("#.#######");
		DecimalFormat df2 = new DecimalFormat("#");
		DecimalFormat df3 = new DecimalFormat("#.###");

		DefaultTableModel model = new DefaultTableModel();

		jTableStats = new JTable(model);

		model.addColumn("track");
		model.addColumn("length");
		model.addColumn(String.format("avg [%sm/s]", Greeks.MU));
		model.addColumn(String.format("std dev [%sm/s]", Greeks.MU));
		model.addColumn(String.format("min [%sm/s]", Greeks.MU));
		model.addColumn(String.format("max [%sm/s]", Greeks.MU));
		model.addColumn("T min [s]");
		model.addColumn("T max [s]");

		if (stats != null && stats.size() > 0) {
			int trackIndex = 1;
			for (double[] ds : stats) {
				Object[] temp = { trackIndex++, df2.format(ds[0]),
				        df.format(ds[1]), df.format(ds[2]), df.format(ds[3]),
				        df.format(ds[4]), df3.format(ds[5]), df3.format(ds[6]) };

				model.addRow(temp);
			}
		}

		jTableStats.getTableHeader().setReorderingAllowed(false);
		jScrollPaneStats.setViewportView(jTableStats);

		jTableStats.getColumnModel().getColumn(0).setMaxWidth(50);
		jTableStats.getColumnModel().getColumn(1).setMaxWidth(70);
		jTableStats.getColumnModel().getColumn(6).setMaxWidth(60);
		jTableStats.getColumnModel().getColumn(7).setMaxWidth(60);
		jTableStats.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTableStats.getColumnModel().getColumn(1).setPreferredWidth(70);
		jTableStats.getColumnModel().getColumn(6).setPreferredWidth(60);
		jTableStats.getColumnModel().getColumn(7).setPreferredWidth(60);

		// listener on the clicked row
		jTableStats.getSelectionModel().addListSelectionListener(
		        new ListSelectionListener() {
			        public void valueChanged(ListSelectionEvent e) {
				        int sel = jTableStats.getSelectedRow();
				        jFrameVelocities.setClickedIndexInTheStatsFrame(sel);
			        }
		        });
	}

	public void saveData() {
		ArrayList<String[]> exportStrings = new ArrayList<String[]>();

		String[] title = { "track", "length", "avg [microm/s]",
		        "std dev [microm/s]", "min [microm/s]", "max [microm/s]",
		        "T min", "T max" };

		exportStrings.add(title);

		int trackIndex = 1;

		for (double[] stat : stats) {
			String[] tableColumnsToStrings = new String[stat.length + 1];

			tableColumnsToStrings[0] = String.valueOf(trackIndex++);

			for (int i = 0; i < stat.length; i++)
				tableColumnsToStrings[i + 1] = String.valueOf(stat[i]);

			exportStrings.add(tableColumnsToStrings);
		}

		CSVExporter.exportCSV(exportStrings);
	}
}
