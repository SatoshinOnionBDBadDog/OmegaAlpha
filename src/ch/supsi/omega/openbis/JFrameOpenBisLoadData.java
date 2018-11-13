package ch.supsi.omega.openbis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;

//import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.DataSet;
import ch.systemsx.cisd.openbis.dss.client.api.v1.DataSet;

/**
 * JDialog where the User can specify the datasets to be loaded from openBIS.
 * @author galliva
 */
public class JFrameOpenBisLoadData extends JDialog
{
	private static final long		serialVersionUID							= -5935448207403662526L;

	/**
	 * The JComponent who has called this JDialog.
	 */
	private JComponent				jComponent									= null;

	/**
	 * The List of the Datasets in the OMERO database.
	 */
	private ArrayList<DSHandler>	dataSets										= new ArrayList<DSHandler>();

	/**
	 * Set to true when data is loaded from openBIS.
	 */
	private boolean					dataLoaded									= false;
	
	/**
	 * The directory where the files will be saved
	 */
	private String						saveDirectory								= "";

	/**
	 * Just to not fire the action listener on the jComboBoxImage.
	 */
	private boolean					removingOMEROImagesFromJComboBox		= false;

	/**
	 * Just to not fire the action listener on the jComboBoxOpenbisName.
	 */
	private boolean					removingOPENBISImagesFromJComboBox	= false;

	
	public boolean isDataLoaded()
	{
		return dataLoaded;
	}
	public String getSaveDirectory()
	{
		return saveDirectory;
	}
	
	public JFrameOpenBisLoadData()
	{
		initComponents();
	}
	
	public JFrameOpenBisLoadData(JComponent jComponent)
	{
		this.jComponent = jComponent;
		initComponents();
	}

	private void initComponents()
	{
		jPanelTopDS 			= new JPanel();
		jLabelDS 				= new JLabel();
		jComboBoxDS 			= new JComboBox();
		jPanelTopImage 		= new JPanel();
		jLabelImage 			= new JLabel();
		jComboBoxImage 		= new JComboBox();
		jPanelTopOpenbisName = new JPanel();
		jLabelOpenbisName 	= new JLabel();
		jComboBoxOpenbisName = new JComboBox();
		jPanelMainCode 		= new JPanel();
		jLabelCode 				= new JLabel();
		jLabelOpenBisCode 	= new JLabel();
		jPanelMainDesc 		= new JPanel();
		jLabelDesc 				= new JLabel();
		jLabelOpenBisDesc 	= new JLabel();
		jPanelCenterBottom 	= new JPanel();
		jButtonOK 				= new JButton();
		jButtonCancel 			= new JButton();

		// windows properties
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setMinimumSize(new java.awt.Dimension(350, 250));
		setResizable(false);

		setTitle(OmegaConstants.INFO_OPENBIS_LOAD_TITLE);
		
		// layout
		getContentPane().setLayout(new java.awt.GridLayout(6, 0, 10, 5));
		
		jPanelTopDS.setLayout(new java.awt.BorderLayout());
		jLabelDS.setText("OMERO dataset:");
		jLabelDS.setPreferredSize(new java.awt.Dimension(130, 14));
		jPanelTopDS.add(jLabelDS, java.awt.BorderLayout.WEST);
		jPanelTopDS.add(jComboBoxDS, java.awt.BorderLayout.CENTER);
		getContentPane().add(jPanelTopDS);

		jPanelTopImage.setLayout(new java.awt.BorderLayout());
		jLabelImage.setText("OMERO image:");
		jLabelImage.setPreferredSize(new java.awt.Dimension(130, 14));
		jPanelTopImage.add(jLabelImage, java.awt.BorderLayout.WEST);
		jPanelTopImage.add(jComboBoxImage, java.awt.BorderLayout.CENTER);
		getContentPane().add(jPanelTopImage);

		jPanelTopOpenbisName.setLayout(new java.awt.BorderLayout());
		jLabelOpenbisName.setText("openBIS name:");
		jLabelOpenbisName.setPreferredSize(new java.awt.Dimension(130, 14));
		jPanelTopOpenbisName.add(jLabelOpenbisName, java.awt.BorderLayout.WEST);
		jPanelTopOpenbisName.add(jComboBoxOpenbisName, java.awt.BorderLayout.CENTER);
		getContentPane().add(jPanelTopOpenbisName);

		jPanelMainDesc.setLayout(new java.awt.BorderLayout());
		jLabelDesc.setText("openBIS description:");
		jLabelDesc.setPreferredSize(new java.awt.Dimension(130, 14));
		jPanelMainDesc.add(jLabelDesc, java.awt.BorderLayout.WEST);
		jPanelMainDesc.add(jLabelOpenBisDesc, java.awt.BorderLayout.CENTER);
		getContentPane().add(jPanelMainDesc);

		jPanelMainCode.setLayout(new java.awt.BorderLayout());
		jLabelCode.setText("openBIS code:");
		jLabelCode.setPreferredSize(new java.awt.Dimension(130, 14));
		jPanelMainCode.add(jLabelCode, java.awt.BorderLayout.WEST);
		jPanelMainCode.add(jLabelOpenBisCode, java.awt.BorderLayout.CENTER);
		getContentPane().add(jPanelMainCode);

		jPanelCenterBottom.setLayout(new java.awt.GridLayout(0, 2, 10, 0));
		jButtonOK.setText("Load");
		jPanelCenterBottom.add(jButtonOK);
		jButtonCancel.setText("Cancel");
		jPanelCenterBottom.add(jButtonCancel);
		getContentPane().add(jPanelCenterBottom);

		// listeners on the buttons
		jButtonOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButtonOKActionPerformed(evt);
			}
		});

		jButtonCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButtonCancelActionPerformed(evt);
			}
		});

		// listeners of the combobox
		jComboBoxDS.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setOmeroImagesNames(jComboBoxDS.getSelectedItem().toString());
			}
		});

		jComboBoxImage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!removingOMEROImagesFromJComboBox)
					setOpenBisNames(jComboBoxDS.getSelectedItem().toString(), jComboBoxImage.getSelectedItem().toString());
			}
		});

		jComboBoxOpenbisName.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!removingOPENBISImagesFromJComboBox)
					setDescriptionAndCode(jComboBoxDS.getSelectedItem().toString(), jComboBoxImage.getSelectedItem().toString(), jComboBoxOpenbisName.getSelectedItem().toString());
			}
		});

		pack();
	}
	
	/**
	 * Select an item in the jComboBoxDS JComboBox and and item in the jComboBoxImage JComboBox.
	 * @param itemOmeroDataset The item to be selected in the jComboBoxDS
	 * @param itemOmeroImage The item to be selected in the jComboBoxImage
	 */
	public void setSelectedItems(String itemOmeroDataset, String itemOmeroImage)
	{
		try
		{
			jComboBoxDS.setSelectedItem(itemOmeroDataset);
			jComboBoxImage.setSelectedItem(itemOmeroImage);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Loads the Datasets from the openBIS server.
	 * @param dataSetType An existing openBIS dataset type
	 */
	public void loadDatasets(String dataSetType)
	{
		// get all the dataset in the OMERO server
		List<DataSet> ds = OpenBisHelper.listDatasets(dataSetType, jComponent);

		if (ds == null)
			return;
		
		// add them to the internal ArrayList
		for (DataSet dataSet : ds)
		{
			try
			{
				HashMap<String, String> properties = dataSet.getProperties();

				dataSets.add(new DSHandler(dataSet.getCode(), properties.get("OMERO-DATASET-NAME"), properties.get("OMERO-IMAGE-NAME"), properties.get("USER-DATASET-NAME"), properties.get("USER-DESCRIPTION")));
			}
			catch (Exception e)
			{
				// nothing we can do
				continue;
			}
		}

		setOmeroDataSetsNames();
	}

	private void setOmeroDataSetsNames()
	{
		try
		{
			// keep unique names
			HashSet<String> sets = new HashSet<String>();

			for (DSHandler dsHandler : dataSets)
				sets.add(dsHandler.omeroDSName);

			// add to the DS combo box
			for (String s : sets)
				jComboBoxDS.addItem(s);
		}
		catch (Exception e)
		{
		}
	}

	private void setOmeroImagesNames(String omeroDataSetName)
	{
		try
		{
			// remove all from the Omero Images JComboBox
			removingOMEROImagesFromJComboBox = true;
			jComboBoxImage.removeAllItems();
			removingOMEROImagesFromJComboBox = false;

			// keep unique names
			HashSet<String> sets = new HashSet<String>();

			for (DSHandler dsHandler : dataSets)
			{
				if (dsHandler.omeroDSName.equals(omeroDataSetName))
					sets.add(dsHandler.omeroImageName);
			}

			// add to the DS combo box
			for (String s : sets)
				jComboBoxImage.addItem(s);
		}
		catch (Exception e)
		{
		}
	}

	private void setOpenBisNames(String omeroDataSetName, String omeroImageName)
	{
		try
		{
			// remove all from the OpenBIS Images JComboBox
			removingOPENBISImagesFromJComboBox = true;
			jComboBoxOpenbisName.removeAllItems();
			removingOPENBISImagesFromJComboBox = false;

			// keep unique names
			HashSet<String> sets = new HashSet<String>();

			for (DSHandler dsHandler : dataSets)
			{
				if (dsHandler.omeroDSName.equals(omeroDataSetName) && dsHandler.omeroImageName.equals(omeroImageName))
					sets.add(dsHandler.userName);
			}

			// add to the DS combo box
			for (String s : sets)
				jComboBoxOpenbisName.addItem(s);
		}
		catch (Exception e)
		{
		}
	}

	private void setDescriptionAndCode(String omeroDataSetName, String omeroImageName, String openBISName)
	{
		try
		{
			for (DSHandler dsHandler : dataSets)
			{
				if (dsHandler.omeroDSName.equals(omeroDataSetName) && dsHandler.omeroImageName.equals(omeroImageName) && dsHandler.userName.equals(openBISName))
				{
					jLabelOpenBisCode.setText(dsHandler.openBISCode);
					jLabelOpenBisDesc.setText(dsHandler.userDescription);
				}
			}
		}
		catch (Exception e)
		{
		}
	}

	private void jButtonOKActionPerformed(ActionEvent evt)
	{
		// the current dataset need to be saved in the  temp directory
		String sysTempDirectory = System.getProperty("java.io.tmpdir");
		String tempDirectory    = sysTempDirectory + "DopenBIS";
		
		try
		{
			// delete the temporary directory (if exists)
			deleteDirectory(new File(tempDirectory));
			
			// create a directory
			FileHelper.createDirectory(tempDirectory);			
		}
		catch (Exception e)
		{
			// already handled in the FileHelper class...
		}
		
		// the ID of the dataset to be downloaded
		String openBISCode = jLabelOpenBisCode.getText().trim();
		
		try
		{
			// download the dataset
			OpenBisHelper.downloadDataSet(openBISCode, tempDirectory, jComponent);
			
			// generate the name of the directory where the files where saved
			saveDirectory = tempDirectory + System.getProperty("file.separator") + openBISCode + System.getProperty("file.separator") + "original";
			File file     = new File(saveDirectory);
			saveDirectory = saveDirectory + System.getProperty("file.separator") + file.list()[0].toString();

			// set dataLoaded 
			dataLoaded = true;
		}
		catch (Exception e)
		{
			dataLoaded = false;
		}
		finally
		{
			dispose();
		}
	}
	
	private static boolean deleteDirectory(File path)
	{
		if (path.exists())
		{
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					deleteDirectory(files[i]);
				}
				else
				{
					files[i].delete();
				}
			}
		}
		return path.delete();
	}

	private void jButtonCancelActionPerformed(ActionEvent evt)
	{
		dataLoaded = false;
		dispose();
	}

	/**
	 * Internal class used to handle the loaded openBIS datasets.
	 * @author Vanni Galli
	 */
	private class DSHandler
	{
		private String	openBISCode			= "";
		private String	omeroDSName			= "";
		private String	omeroImageName		= "";
		private String	userName				= "";
		private String	userDescription	= "";

		public DSHandler(String openBISCode, String omeroDSName, String omeroImageName, String userName, String userDescription)
		{
			this.openBISCode = openBISCode;
			this.omeroDSName = omeroDSName;
			this.omeroImageName = omeroImageName;
			this.userName = userName;
			this.userDescription = userDescription;
		}
	}

	private JPanel		jPanelCenterBottom;
	private JPanel		jPanelMainCode;
	private JPanel		jPanelMainDesc;
	private JPanel		jPanelTopDS;
	private JPanel		jPanelTopImage;
	private JPanel		jPanelTopOpenbisName;
	private JLabel		jLabelCode;
	private JLabel		jLabelDS;
	private JLabel		jLabelDesc;
	private JLabel		jLabelImage;
	private JLabel		jLabelOpenBisCode;
	private JLabel		jLabelOpenBisDesc;
	private JLabel		jLabelOpenbisName;
	private JComboBox	jComboBoxDS;
	private JComboBox	jComboBoxImage;
	private JComboBox	jComboBoxOpenbisName;
	private JButton	jButtonCancel;
	private JButton	jButtonOK;

	public static void main(String args[])
	{
		JFrameOpenBisLoadData jFrameOpenBisLoadData = new JFrameOpenBisLoadData();

		jFrameOpenBisLoadData.loadDatasets(OmegaConstants.OPENBIS_TRAJECTORY_TYPE);

		jFrameOpenBisLoadData.setVisible(true);
	}
}
