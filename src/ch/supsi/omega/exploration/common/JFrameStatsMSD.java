package ch.supsi.omega.exploration.common;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JFrameStatsMSD extends JFrame
{
	private static final long	serialVersionUID	= -8960604531762240333L;

	private JScrollPane			jScrollPaneStats	= new JScrollPane();
	private JTable					jTableStats			= new JTable();

	private String					title					= "";

	public JFrameStatsMSD(String frameTitleOrNull)
	{
		if (frameTitleOrNull != null)
			this.title = frameTitleOrNull;

		initComponents();
	}

	public JFrameStatsMSD(int x, int y, String frameTitleOrNull)
	{
		if (frameTitleOrNull != null)
			this.title = frameTitleOrNull;

		setLocation(x, y);
		initComponents();
	}

	private void initComponents()
	{
		setTitle(title);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setPreferredSize(new Dimension(270, 105));
		setMinimumSize(new Dimension(270, 105));
		setMaximumSize(new Dimension(600, 155));

		getContentPane().add(jScrollPaneStats, BorderLayout.CENTER);

		pack();
	}

	public void setModel(double mean, double median, double deviation)
	{		
	   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	   renderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		jTableStats.setModel(new DefaultTableModel(new Object[][]{
				{ "Mean", mean }, 
				{ "Median", median }, 
				{ "Standard deviation", deviation }
				}, new String[] { "Measure", "Value" }));
		
		jTableStats.getTableHeader().setReorderingAllowed(false);
		jScrollPaneStats.setViewportView(jTableStats);
		
		jTableStats.getColumnModel().getColumn(0).setResizable(false);
		jTableStats.getColumnModel().getColumn(0).setMinWidth(110);
		jTableStats.getColumnModel().getColumn(0).setMaxWidth(110);
		
		jTableStats.getColumnModel().getColumn(1).setResizable(false);
		jTableStats.getColumnModel().getColumn(1).setCellRenderer(renderer);
	}
	
	public void setModel(DefaultTableModel model)
	{
	   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	   renderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		jTableStats.setModel(model);
		
		jTableStats.getTableHeader().setReorderingAllowed(false);
		jScrollPaneStats.setViewportView(jTableStats);
		
		jTableStats.getColumnModel().getColumn(0).setResizable(false);
		jTableStats.getColumnModel().getColumn(0).setMinWidth(110);
		jTableStats.getColumnModel().getColumn(0).setMaxWidth(110);
		
		jTableStats.getColumnModel().getColumn(1).setResizable(false);
		jTableStats.getColumnModel().getColumn(1).setCellRenderer(renderer);
		
	}
	
}
