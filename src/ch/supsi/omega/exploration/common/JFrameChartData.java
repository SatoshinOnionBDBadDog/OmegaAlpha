package ch.supsi.omega.exploration.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ch.supsi.omega.common.science.StringConverter;

import com.galliva.gallibrary.GLogManager;

public class JFrameChartData extends JFrame
{
	private static final long	serialVersionUID	= 3353239898957901644L;

	private JScrollPane			jScrollPaneData	= new JScrollPane();
	private JTable					jTableData			= new JTable();

	/**
	 * The data to be displayed.
	 */
	private List<String[]>		data					= null;

	/**
	 * Set to true to perform additional check on the rows.
	 */
	private boolean				sMSSvsDadditionalCheck	= false;
	
	public void setSMSSvsDadditionalCheck(boolean sMSSvsDadditionalCheck)
	{
		this.sMSSvsDadditionalCheck = sMSSvsDadditionalCheck;
	}
	
	public JFrameChartData(List<String[]> data)
	{
		this.data = data;

		initComponents();
	}

	private void initComponents()
	{
		setTitle("Chart data");
		//setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// set the correct height
		int height = data.size() * 16 + 3;
		height = (height > 400) ? 400 : height;
		
		// motion type - %
		if (data.get(0).length == 2)
			jScrollPaneData.setPreferredSize(new Dimension(200, height));
		// index - string- float - float
		if (data.get(0).length == 4)
			jScrollPaneData.setPreferredSize(new Dimension(390, height));
		// index - string- float - float - string
		if (data.get(0).length == 5)
			jScrollPaneData.setPreferredSize(new Dimension(470, height));

		// set the correct table model, depending on data
		jTableData.setModel(new DefaultTableModel(new Object[][] {}, StringConverter.getScientificString(data.get(0))));

		setTableRenderer();

		getContentPane().add(jScrollPaneData, BorderLayout.CENTER);

		pack();
	}

	public void setTableRenderer()
	{
		jTableData.setRowSelectionAllowed(false);
		jTableData.setEnabled(false);

		// intensities
		if(data.get(0).length == 5 && data.get(0)[4].equals("Nr. of pixels"))
		{
			jTableData.getColumnModel().getColumn(0).setPreferredWidth(50);
			jTableData.getColumnModel().getColumn(0).setCellRenderer(new CenterValueRenderer());
			jTableData.getColumnModel().getColumn(1).setPreferredWidth(200);
			jTableData.getColumnModel().getColumn(2).setPreferredWidth(70);
			jTableData.getColumnModel().getColumn(3).setPreferredWidth(70);
			jTableData.getColumnModel().getColumn(4).setPreferredWidth(80);
			jScrollPaneData.setViewportView(jTableData);
			return;
		}
		
		// motion type - %
		if (data.get(0).length == 2)
		{
			jTableData.getColumnModel().getColumn(0).setResizable(false);
			jTableData.getColumnModel().getColumn(0).setPreferredWidth(50);
			jTableData.getColumnModel().getColumn(0).setCellRenderer(new CenterValueRenderer());

			jTableData.getColumnModel().getColumn(1).setResizable(false);
			jTableData.getColumnModel().getColumn(1).setPreferredWidth(5);
			jTableData.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
		}

		// index - string- float - float
		if (data.get(0).length > 3)
		{
			jTableData.getColumnModel().getColumn(0).setPreferredWidth(50);
			jTableData.getColumnModel().getColumn(0).setCellRenderer(new CenterValueRenderer());

			jTableData.getColumnModel().getColumn(1).setPreferredWidth(200);

			jTableData.getColumnModel().getColumn(2).setPreferredWidth(60);
			jTableData.getColumnModel().getColumn(2).setCellRenderer(new DecimalFormatRenderer());
			
			jTableData.getColumnModel().getColumn(3).setPreferredWidth(60);
			jTableData.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
		}
		
		// index - string- float - float - string
		if(data.get(0).length > 4)
		{
			jTableData.getColumnModel().getColumn(4).setPreferredWidth(100);
			jTableData.getColumnModel().getColumn(4).setCellRenderer(new CenterValueRenderer());
		}

		jScrollPaneData.setViewportView(jTableData);
	}

	public void addRows()
	{
		try
		{
			DefaultTableModel tableModel = (DefaultTableModel) jTableData.getModel();
			
			// add the rows (skip the header)
			for (int i = 1; i < data.size(); i++)
			{
				String[] row = data.get(i);
				
				// additional check on SMSS vs D, don't add motions names
				if(sMSSvsDadditionalCheck && row.length == 1)
					continue;
				
				tableModel.addRow(row);
			}
		}
		catch (Exception e)
		{
			GLogManager.log(e.toString(), Level.SEVERE);
		}
	}

	/**
	 * @author galliva
	 */
	private static class CenterValueRenderer extends DefaultTableCellRenderer
	{
		private static final long	serialVersionUID	= 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			setHorizontalAlignment(JLabel.CENTER);
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	/**
	 * @author galliva
	 */
	private static class DecimalFormatRenderer extends DefaultTableCellRenderer
	{
		private static final long				serialVersionUID	= 4975066388391033087L;

		private static final DecimalFormat	formatter			= new DecimalFormat("0.0000");

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			try
			{
				String s = (String) value;
				Float f = Float.valueOf(s);
				value = formatter.format(f);
			}
			catch (Exception e)
			{
				// nothing we can do...
			}

			setHorizontalAlignment(JLabel.RIGHT);
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
}
