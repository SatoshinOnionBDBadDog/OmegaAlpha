package ch.supsi.omega.segmentation.pattern;

import java.util.List;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.gui.OMEGA;

import com.galliva.gallibrary.GLogManager;

public class LoadedPatternsFrame extends javax.swing.JFrame
{
	private static final long	serialVersionUID	= 3353239898957901644L;

	public LoadedPatternsFrame(List<Pattern> patterns)
	{
		initComponents();

		addMotionTypes();

		try
		{
			DefaultTableCellRenderer rendererCenter = new DefaultTableCellRenderer();
			rendererCenter.setHorizontalAlignment(JLabel.CENTER);

			jTablePatterns.getColumn("ID").setCellRenderer(rendererCenter);

			PatternTableCellRenderer renderer = new PatternTableCellRenderer();
			jTablePatterns.setDefaultRenderer(Object.class, renderer);
		}
		catch (Exception e)
		{
			GLogManager.log("cannot render the pattern table: " + e.toString(), Level.WARNING);
		}
	}

	public void addMotionTypes()
	{
		try
		{
			DefaultTableModel tableModel = (DefaultTableModel) jTablePatterns.getModel();
			
			for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
				tableModel.removeRow(i);
			
			tableModel.addRow(new Object[] { 0, OMEGA.MOTIONTYPES[4]});
			
			for (int i = 0; i < OMEGA.MOTIONTYPES.length-1; i++)
				tableModel.addRow(new Object[] { i+1, OMEGA.MOTIONTYPES[i]});
			
		}
		catch (Exception e)
		{
		}
	}

	private void initComponents()
	{

		jScrollPanePatterns = new javax.swing.JScrollPane();
		jTablePatterns = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle(OmegaConstants.OMEGA_AVAILABLE_MOTIONS_TITLE);

		jScrollPanePatterns.setPreferredSize(new java.awt.Dimension(240, 99));

//		jTablePatterns.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Width", "Features" })
		jTablePatterns.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name"})
		{
			private static final long	serialVersionUID	= -4708522382328760669L;

			Class<?>[]						types					= new Class[]   { java.lang.Integer.class, java.lang.String.class };
			boolean[]						canEdit				= new boolean[] { false, false };

			public Class<?> getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});

		jTablePatterns.setRowSelectionAllowed(false);
		jTablePatterns.setEnabled(false);
		jScrollPanePatterns.setViewportView(jTablePatterns);
		jTablePatterns.getColumnModel().getColumn(0).setResizable(false);
		jTablePatterns.getColumnModel().getColumn(0).setPreferredWidth(50);
		jTablePatterns.getColumnModel().getColumn(1).setResizable(false);
		jTablePatterns.getColumnModel().getColumn(1).setPreferredWidth(150);
//		jTablePatterns.getColumnModel().getColumn(2).setResizable(false);
//		jTablePatterns.getColumnModel().getColumn(2).setPreferredWidth(25);
//		jTablePatterns.getColumnModel().getColumn(3).setResizable(false);
//		jTablePatterns.getColumnModel().getColumn(3).setPreferredWidth(25);

		getContentPane().add(jScrollPanePatterns, java.awt.BorderLayout.CENTER);

		pack();
	}

	private javax.swing.JScrollPane	jScrollPanePatterns;
	private javax.swing.JTable			jTablePatterns;
}
