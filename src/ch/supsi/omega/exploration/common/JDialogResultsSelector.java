package ch.supsi.omega.exploration.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;

public class JDialogResultsSelector extends JDialog implements ComponentListener
{
	private static final long					serialVersionUID	= 1129751290138993997L;

	private JDialogResultsSelector			INSTANCE				= null;

	/**
	 * The datasets names to be shown in the JComboBoxs.
	 */
	private static String[]						defaultDatasets	= new String[] { "DS 1", "DS 2", "DS 3", "DS 4", "DS 5" };

	/**
	 * The FileHelper used in this class.
	 */
	private FileHelper							fileHelper			= new FileHelper();

	/**
	 * The List of the datasets JPanels.
	 */
	protected List<JPanelDataset>				jPanelList			= new ArrayList<JPanelDataset>();

	/**
	 * The Hashmap with the selected directories and datasets.
	 */
	private HashMap<String, List<String>>	map					= new HashMap<String, List<String>>();

	public HashMap<String, List<String>> getMap()
	{
		return map;
	}

	public static String[] getDefaultDatasets()
	{
		return defaultDatasets;
	}

	public static void setDefaultDatasets(String[] defaultDatasets)
	{
		JDialogResultsSelector.defaultDatasets = defaultDatasets;
	}

	public JDialogResultsSelector()
	{
		INSTANCE = this;
		initComponents();
		addComponentListener(this);
	}

	private void initComponents()
	{
		// @formatter:off
		jPanel4 				= new JPanel();
		jPanelCenter 		= new JPanel();
		jPanelBottom 		= new JPanel();
		jPanelBottomLeft 	= new JPanel();
		jPanelBottomRight = new JPanel();
		jButtonOk 			= new JButton();
		jButtonClear 		= new JButton();
		jButtonNames 		= new JButton();
		jPanel5 				= new JPanel();
		jPanel6 				= new JPanel();
		jPanel7 				= new JPanel();
		// @formatter:on

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setModal(true);

		setTitle(OmegaConstants.OMEGA_TITLE + " - Add charts datasets");

		setMinimumSize(new java.awt.Dimension(500, 0));

		jPanel4.setPreferredSize(new java.awt.Dimension(0, 5));

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 900, Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));

		getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

		jPanelList.add(new JPanelDataset());

		// ======================
		// CENTER / BOTTOM PANELS
		jPanelBottom.setLayout(new BorderLayout(5, 0));

		// DS names button
		jButtonNames.setText("DS names");
		jButtonNames.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JDialogDSNames jDialogDSNames = new JDialogDSNames(INSTANCE);
				jDialogDSNames.setLocation(getLocation().x, getLocation().y);
				jDialogDSNames.setAlwaysOnTop(true);
				jDialogDSNames.setVisible(true);
			}
		});

		jPanelBottomLeft.setLayout(new java.awt.GridLayout(1, 1, 5, 0));
		jPanelBottomLeft.add(jButtonNames);

		jPanelBottomRight.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

		jButtonOk.setText("OK");
		jButtonOk.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addDirectoriesAndDatasets();
				dispose();
			}
		});

		jButtonClear.setText("Clear");
		jButtonClear.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jPanelList.clear();
				jPanelList.add(new JPanelDataset());
				addCenterPanels();
			}
		});
		jPanelBottomRight.add(jButtonOk);
		jPanelBottomRight.add(jButtonClear);

		jPanelBottom.add(jPanelBottomRight, BorderLayout.EAST);
		jPanelBottom.add(jPanelBottomLeft, BorderLayout.WEST);
		// end of bottom

		addCenterPanels();

		getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);
		// ======================

		jPanel5.setPreferredSize(new java.awt.Dimension(0, 5));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 900, Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));

		getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

		jPanel6.setPreferredSize(new java.awt.Dimension(5, 0));

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 290, Short.MAX_VALUE));

		getContentPane().add(jPanel6, java.awt.BorderLayout.EAST);

		jPanel7.setPreferredSize(new java.awt.Dimension(5, 0));

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 290, Short.MAX_VALUE));

		getContentPane().add(jPanel7, java.awt.BorderLayout.WEST);

		pack();
	}
	
	public void changeDatasetsNames()
	{
		for (JPanelDataset jPanelDataset : jPanelList)
		{
			int currentSelection = jPanelDataset.jPanel_DS_Combobox.getSelectedIndex();
			jPanelDataset.jPanel_DS_Combobox.setModel(new DefaultComboBoxModel(defaultDatasets));
			jPanelDataset.jPanel_DS_Combobox.setSelectedIndex(currentSelection);
		}
	}

	public void addCenterPanels()
	{
		jPanelCenter.removeAll();
		jPanelCenter.revalidate();

		int panelsCount = jPanelList.size();

		setMinimumSize(new Dimension(500, 110 + (panelsCount - 1) * 33));
		setSize(new Dimension(getWidth(), 110 + (panelsCount - 1) * 33));

		jPanelCenter.setLayout(new java.awt.GridLayout(panelsCount + 1, 0, 0, 5));

		for (JPanelDataset jPanelDataset : jPanelList)
			jPanelCenter.add(jPanelDataset);

		jPanelCenter.add(jPanelBottom);

		getRootPane().setDefaultButton(jPanelList.get(jPanelList.size() - 1).jPanel_DS_Button);
	}

	private void addDirectoriesAndDatasets()
	{
		map.clear();

		for (int i = 0; i < defaultDatasets.length; i++)
		{
			String datasetName = defaultDatasets[i];

			List<String> directoryList = new ArrayList<String>();

			for (JPanelDataset jPanelDataset : jPanelList)
			{
				if (jPanelDataset.jPanel_DS_Combobox.getSelectedItem().toString().equals(datasetName) && jPanelDataset.jPanel_DS_TextField.getText().length() > 0)
					directoryList.add(jPanelDataset.jPanel_DS_TextField.getText());
			}

			if (directoryList.size() > 0)
				map.put(datasetName, directoryList);
		}
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		setSize(new Dimension(getWidth(), 0));
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
	}

	private class JPanelDataset extends JPanel
	{
		private static final long	serialVersionUID	= 8246964735661856621L;

		private JPanel					jPanel_DS_left;
		private JPanel					jPanel_DS_Right;
		private JTextField			jPanel_DS_TextField;
		private JComboBox				jPanel_DS_Combobox;
		private JButton				jPanel_DS_Button;

		private boolean				panelAdded			= false;

		public JPanelDataset()
		{
			initComponents();
		}

		private void initComponents()
		{
			// @formatter:off
			jPanel_DS_left 		= new JPanel();
			jPanel_DS_TextField 	= new JTextField();
			jPanel_DS_Right 		= new JPanel();
			jPanel_DS_Combobox 	= new JComboBox();
			jPanel_DS_Button 		= new JButton();
			// @formatter:on

			setLayout(new BorderLayout(5, 0));

			jPanel_DS_left.setLayout(new BorderLayout());

			jPanel_DS_TextField.setEnabled(false);
			jPanel_DS_left.add(jPanel_DS_TextField, BorderLayout.CENTER);

			add(jPanel_DS_left, BorderLayout.CENTER);

			jPanel_DS_Right.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

			jPanel_DS_Combobox.setModel(new javax.swing.DefaultComboBoxModel(defaultDatasets));
			jPanel_DS_Right.add(jPanel_DS_Combobox);

			jPanel_DS_Button.setText(" Add  ");
			jPanel_DS_Right.add(jPanel_DS_Button);

			add(jPanel_DS_Right, java.awt.BorderLayout.EAST);

			jPanel_DS_Button.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					String selectedDirectory = fileHelper.selectFile("trajectoriesOutDir", OmegaConstants.INFO_SELECTDIR_DATASET, JFileChooser.DIRECTORIES_ONLY);

					if (selectedDirectory == null || selectedDirectory.length() < 1)
						return;

					jPanel_DS_TextField.setText(selectedDirectory);

					if (!panelAdded)
					{
						jPanelList.add(new JPanelDataset());
						addCenterPanels();
						panelAdded = true;
					}
				}
			});
		}
	}

	private JPanel		jPanel4;
	private JPanel		jPanel5;
	private JPanel		jPanel6;
	private JPanel		jPanel7;
	private JPanel		jPanelBottom;
	private JPanel		jPanelBottomLeft;
	private JPanel		jPanelBottomRight;
	private JPanel		jPanelCenter;
	private JButton	jButtonOk;
	private JButton	jButtonClear;
	private JButton	jButtonNames;
}
