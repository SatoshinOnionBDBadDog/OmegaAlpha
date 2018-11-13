package ch.supsi.omega.openbis;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;
import ch.systemsx.cisd.openbis.dss.client.api.v1.DssComponentFactory;
import ch.systemsx.cisd.openbis.dss.client.api.v1.IDataSetDss;
import ch.systemsx.cisd.openbis.dss.client.api.v1.IDssComponent;
import ch.systemsx.cisd.openbis.dss.client.api.v1.IOpenbisServiceFacade;
import ch.systemsx.cisd.openbis.dss.client.api.v1.ISimpleOpenbisServiceFacade;
import ch.systemsx.cisd.openbis.dss.client.api.v1.OpenbisServiceFacadeFactory;
import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO;
import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO.DataSetOwner;
import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO.DataSetOwnerType;
//import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.DataSet;
import ch.systemsx.cisd.openbis.dss.client.api.v1.DataSet;
import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.SearchCriteria;
import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.SearchCriteria.MatchClause;
import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.SearchCriteria.MatchClauseAttribute;
import ch.systemsx.cisd.openbis.generic.shared.api.v1.dto.SearchSubCriteria;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class OpenBisHelper
{
	private static GConfigurationManager			configurationManager	= new GConfigurationManager();

	private static String								openBISURL				= "";
	private static String								username					= "";
	private static String								password					= "";

	private static ISimpleOpenbisServiceFacade	openbis					= null;

	public static String getPassword()
	{
		return password;
	}

	public static void setPassword(String password)
	{
		OpenBisHelper.password = password;
	}

	
	/**
	 * Test a connection to the openBIS server.
	 * @param componentCallerOrNull the Java component who has called this method, can be null
	 */
	public static void testConnection(Component componentCallerOrNull)
	{
		getSettingsFromFile();
		
		// trust any certificate
		SslCertificateHelper.trustAnyCertificate(openBISURL);
		
		// ask for the password, if not already set
		if (password.length() == 0 || password == null)
		{
			JDialogOpenBisPassword jDialogOpenBisPassword = new JDialogOpenBisPassword();
			if (componentCallerOrNull != null)
				jDialogOpenBisPassword.setLocationRelativeTo(componentCallerOrNull);
			jDialogOpenBisPassword.setVisible(true);
		}

		try
		{			
			// test the connection
			IDssComponent component = DssComponentFactory.tryCreate(username, password, openBISURL, 10000);
			component.getSessionToken();

			// display the information
			JOptionPane.showMessageDialog(null, String.format("%s\n%s: %s\n%s: %s", OmegaConstants.INFO_OPENBIS_CONNECTION_OK, "hostname", openBISURL, "username", username), OmegaConstants.OMEGA_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception e)
		{
			GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_OPENBIS_CONNECTION_FAIL, e.toString()), Level.SEVERE);
			JOptionPane.showMessageDialog(null, String.format("%s\n%s: %s\n%s: %s", OmegaConstants.ERROR_OPENBIS_CONNECTION_FAIL, "hostname", openBISURL, "username", username), OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Uploads a dataset to an openBIS server.
	 * @param dataSetCode the openBIS dataset code
	 * @param dataSetType the type of the dataset (must exists in openBIS!)
	 * @param openBisPropertiesHandler an OpenBisPropertiesHandler object with the dataset properties
	 * @param datasetContentsDirectory the directory with the dataset's files
	 * @param componentCallerOrNull the Component who has called this method, can be null
	 * @return true if no errors occurred, false otherwise
	 */
	public static boolean uploadDataSet(String dataSetCode, String dataSetType, OpenBisPropertiesHandler openBisPropertiesHandler, String datasetContentsDirectory, Component componentCallerOrNull)
	{
		getSettingsFromFile();
		
		// trust any certificate
		SslCertificateHelper.trustAnyCertificate(openBISURL);

		// ask for the password, if needed
		if (password.length() == 0 || password == null)
		{
			JDialogOpenBisPassword jDialogOpenBisPassword = new JDialogOpenBisPassword();
			if (componentCallerOrNull != null)
				jDialogOpenBisPassword.setLocationRelativeTo(componentCallerOrNull);
			jDialogOpenBisPassword.setVisible(true);
		}

		openbis = createOpenbisFacade();

		try
		{
			// this is the directory we want to upload
			File dataSetContents = new File(datasetContentsDirectory);

			GLogManager.log((String.format("Uploading local folder '%s' as data set to openBIS ...", datasetContentsDirectory)));
			
			// upload data set
			NewDataSetDTO newDataSet = createDataSetDTO(dataSetCode, dataSetContents, dataSetType, openBisPropertiesHandler);
			openbis.putDataSet(newDataSet, dataSetContents);

			GLogManager.log((String.format("New data set with code '%s' successfully uploaded.", dataSetCode)));
			
			JOptionPane.showMessageDialog(null, OmegaConstants.INFO_OPENBIS_UPLOAD_OK, OmegaConstants.OMEGA_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception e)
		{
			GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_OPENBIS_UPLOAD, e.toString()), Level.SEVERE);
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_OPENBIS_UPLOAD, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		finally
		{
			openbis.logout();
		}

		return true;
	}

	/**
	 * Downloads a dataset from an openBIS server.
	 * @param dataSetCode the openBIS dataset code
	 * @param datasetDownloadDirectory the directory where to save the dataset's files
	 * @param componentCallerOrNull the Component who has called this method, can be null
	 * @return true if no errors occurred, false otherwise
	 */
	public static boolean downloadDataSet(String dataSetCode, String datasetDownloadDirectory, Component componentCallerOrNull)
	{
		getSettingsFromFile();
		
		// trust any certificate
		SslCertificateHelper.trustAnyCertificate(openBISURL);

		// ask for the password, if needed
		if (password.length() == 0 || password == null)
		{
			JDialogOpenBisPassword jDialogOpenBisPassword = new JDialogOpenBisPassword();
			if (componentCallerOrNull != null)
				jDialogOpenBisPassword.setLocationRelativeTo(componentCallerOrNull);
			jDialogOpenBisPassword.setVisible(true);
		}

		openbis = createOpenbisFacade();

		try
		{
			// download data set
			

			IDataSetDss dataSet = openbis.getDataSet(dataSetCode).getDataSetDss();
			
			File downloadDir = new File(datasetDownloadDirectory);
			dataSet.getLinkOrCopyOfContents("/", downloadDir);

			//GLogManager.log((String.format("Data set with code '%s' downloaded to local folder '%s'.", dataSetCode, downloadDir.getAbsolutePath())));
		}
		catch (Exception e)
		{
			GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_OPENBIS_DOWNLOAD, e.toString()), Level.SEVERE);
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_OPENBIS_DOWNLOAD, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		finally
		{
			openbis.logout();
		}

		return true;
	}
	
	/**
	 * Lists all the datasets of a particular type.
	 * @param dataSetType the type of the dataset (must exists in openBIS!)
	 * @param componentCallerOrNull the Component who has called this method, can be null
	 * @return a List<> of dataset objects
	 */
	public static List<DataSet> listDatasets(String dataSetType, Component componentCallerOrNull)
	{
		getSettingsFromFile();

		// trust any certificate
		SslCertificateHelper.trustAnyCertificate(openBISURL);
		
		// ask for the password, if needed
		if (password.length() == 0 || password == null)
		{
			JDialogOpenBisPassword jDialogOpenBisPassword = new JDialogOpenBisPassword();
			if (componentCallerOrNull != null)
				jDialogOpenBisPassword.setLocationRelativeTo(componentCallerOrNull);
			jDialogOpenBisPassword.setVisible(true);
		}
		
		openbis = createOpenbisFacade();
		
		List<DataSet> result = null;
		
		try
		{
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addMatchClause(MatchClause.createAttributeMatch(MatchClauseAttribute.TYPE, dataSetType));

			SearchCriteria expCriteria = new SearchCriteria();
			expCriteria.addMatchClause(MatchClause.createAttributeMatch(MatchClauseAttribute.CODE, OmegaConstants.OPENBIS_DEFAULT_EXPERIMENT));
			searchCriteria.addSubCriteria(SearchSubCriteria.createExperimentCriteria(expCriteria));
			
			result = ((IOpenbisServiceFacade) openbis).searchForDataSets(searchCriteria);
		}
		catch (Exception e)
		{
			GLogManager.log(String.format("%s: %s", OmegaConstants.ERROR_OPENBIS_LISTDATASETS, e.toString()), Level.SEVERE);
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_OPENBIS_LISTDATASETS, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally
		{
			openbis.logout();
		}
		
		return result;
	}

	/**
	 * Generates a random dataset code.
	 * @return A string representing the dataset code.
	 */
	public static String generateDataSetCode()
	{
		long currentTime = System.currentTimeMillis();
		return Long.toHexString(currentTime).toUpperCase();
	}
	
	/**
	 * Gets the openBIS connection settings from the ini file.
	 */
	private static void getSettingsFromFile()
	{
		try
		{
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		}
		catch (Exception e)
		{
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		try
		{
			openBISURL = configurationManager.readConfig("openBISHost");
			username = configurationManager.readConfig("openBISUsername");
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Creates the NewDataSetDTO object, used for uploading the datasets.
	 * @param dataSetCode a string representing the dataset code
	 * @param dataSetContents the content of the dataset
	 * @param dataSetType the type of the dataset (must exists in openBIS!)
	 * @return a NewDataSetDTO object
	 */
	private static NewDataSetDTO createDataSetDTO(String dataSetCode, File dataSetContents, String dataSetType, OpenBisPropertiesHandler openBisPropertiesHandler)
	{
		createRegistrationPropertiesFile(dataSetContents, dataSetCode, dataSetType);

		// (existing) experiment in the openBIS installation
		DataSetOwner dataSetOwner = new DataSetOwner(DataSetOwnerType.EXPERIMENT, OmegaConstants.OPENBIS_DEFAULT_IDENTIFIER);

		NewDataSetDTO newDataSet = new NewDataSetDTO(dataSetOwner, dataSetContents);
		
		newDataSet.setDataSetTypeOrNull(dataSetType);
		
		// populate the data set with properties existing in your openBIS installation
		Map<String, String> properties = openBisPropertiesHandler.getProperties();
		newDataSet.setProperties(properties);
		
		return newDataSet;
	}

	/**
	 * Creates a "side-car" file inside the data set, where we keep data needed for the registration process. 
	 * You can use the file to pass any key-value property to the Jython Dropox.
	 */
	private static void createRegistrationPropertiesFile(File dataSetContents, String dataSetCode, String dataSetType)
	{
		File registrationPropertiesFile = new File(dataSetContents, "registration.properties");
		try
		{
			FileOutputStream fos = new FileOutputStream(registrationPropertiesFile);
			Properties props = new Properties();
			props.setProperty("data-set-code", dataSetCode);
			props.setProperty("data-set-type", dataSetType);
			props.store(fos, "Meta data parsed by the responsible openBIS Jython Dropbox.");
			fos.close();
		}
		catch (IOException ioex)
		{
			throw new RuntimeException("Cannot create registration properties file: " + registrationPropertiesFile.getAbsolutePath());
		}

	}

	/**
	 * Creates the facade for the connection to the openBIS server.
	 * @return a ISimpleOpenbisServiceFacade facade object
	 */
	private static ISimpleOpenbisServiceFacade createOpenbisFacade()
	{
		long twentySecondsTimeout = 20 * 1000;

		return OpenbisServiceFacadeFactory.tryCreate(username, password, openBISURL, twentySecondsTimeout);
	}
}