package ch.supsi.omega.openbis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import ch.supsi.omega.common.OmegaConstants;

/**
 * JDialog where the User can specify the datasets to be saved in openBIS.
 * @author galliva
 */
public class JDialogOpenBisSaveData extends JDialog
{
	private static final long					serialVersionUID	= -4718353668893658380L;

	private static JDialogOpenBisSaveData	INSTANCE				= null;

	/**
	 * The openBIS dataset type to be loaded.
	 */
	private String									dataSetType			= "";
	
	/**
	 * The directory to upload.
	 */
	private String									directory			= "";

	public void setDataSetType(String dataSetType)
	{
		this.dataSetType = dataSetType;
	}
	public void setDirectory(String directory)
	{
		this.directory = directory;
	}
	public void setOmeroImageName(String omeroImageName)
	{
		jTextFieldOmeroImageName.setText(omeroImageName);
	}
	public void setOmeroDatasetName(String omeroDatasetName)
	{
		jTextFieldOmeroDatasetName.setText(omeroDatasetName);
	}

	public JDialogOpenBisSaveData()
	{
		INSTANCE = this;

		setPreferredSize(new Dimension(400, 200));
		setModal(true);
		setTitle(OmegaConstants.OMEGA_OPENBIS_SAVEDATA_TITLE);

		initComponents();
	}

	private void initComponents()
	{
		setResizable(false);

		jLabelOmeroImageName 		= new JLabel();
		jTextFieldOmeroImageName 	= new JTextField();
		jLabelOmeroDatasetName 		= new JLabel();
		jTextFieldOmeroDatasetName = new JTextField();
		jLabelUserDatasetName 		= new JLabel();
		jTextFieldUserDatasetName 	= new JTextField();
		jLabelUserDescription 		= new JLabel();
		jTextFieldUserDescription 	= new JTextField();
		jButtonOK 						= new JButton();
		jButtonCancel 					= new JButton();

		getContentPane().setLayout(new java.awt.GridLayout(5, 2, 5, 5));

		// omero DS name
		jLabelOmeroDatasetName.setText("  OMERO dataset name:");
		getContentPane().add(jLabelOmeroDatasetName);
		jTextFieldOmeroDatasetName.setText("not found, please set it manually");
		getContentPane().add(jTextFieldOmeroDatasetName);

		// omero image name
		jLabelOmeroImageName.setText("  OMERO image name:");
		getContentPane().add(jLabelOmeroImageName);
		jTextFieldOmeroImageName.setText("not found, please set it manually");
		getContentPane().add(jTextFieldOmeroImageName);

		// save as
		jLabelUserDatasetName.setText("  save as:");
		getContentPane().add(jLabelUserDatasetName);
		getContentPane().add(jTextFieldUserDatasetName);

		// description
		jLabelUserDescription.setText("  description:");
		getContentPane().add(jLabelUserDescription);
		getContentPane().add(jTextFieldUserDescription);

		// set default description
	   try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String description = format.format(date);
			jTextFieldUserDescription.setText(String.format("%s: ", description));
		}
		catch (Exception e)
		{
			// nothing to do...
		}
		
		// button OK
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButtonOKActionPerformed(evt);
			}
		});
		getContentPane().add(jButtonOK);

		// button Cancel
		jButtonCancel.setText("Cancel");
		jButtonCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				INSTANCE.dispose();
			}
		});
		getContentPane().add(jButtonCancel);

		getRootPane().setDefaultButton(jButtonOK);

		pack();
	}

	private void jButtonOKActionPerformed(ActionEvent evt)
	{
		// upload dataset
		String dataSetCode = OpenBisHelper.generateDataSetCode();

		OpenBisPropertiesHandler openBisPropertiesHandler = new OpenBisPropertiesHandler(jTextFieldOmeroDatasetName.getText(), jTextFieldOmeroImageName.getText(), jTextFieldUserDatasetName.getText(), jTextFieldUserDescription.getText());

		// exceptions already handled in the OpenBisHelper class
		if (dataSetType.length() > 0 && directory.length() > 0)
			OpenBisHelper.uploadDataSet(dataSetCode, dataSetType, openBisPropertiesHandler, directory, this);

		INSTANCE.dispose();
	}

	private JButton		jButtonCancel;
	private JButton		jButtonOK;
	private JLabel			jLabelOmeroImageName;
	private JLabel			jLabelOmeroDatasetName;
	private JLabel			jLabelUserDatasetName;
	private JLabel			jLabelUserDescription;
	private JTextField	jTextFieldOmeroImageName;
	private JTextField	jTextFieldOmeroDatasetName;
	private JTextField	jTextFieldUserDatasetName;
	private JTextField	jTextFieldUserDescription;
}
