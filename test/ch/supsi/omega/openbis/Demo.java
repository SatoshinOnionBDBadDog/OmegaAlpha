///*
// * Copyright 2011 ETH Zuerich, CISD
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package ch.supsi.omega.openbis;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import ch.supsi.omega.common.OmegaConstants;
//import ch.systemsx.cisd.openbis.dss.client.api.v1.IDataSetDss;
//import ch.systemsx.cisd.openbis.dss.client.api.v1.ISimpleOpenbisServiceFacade;
//import ch.systemsx.cisd.openbis.dss.client.api.v1.OpenbisServiceFacadeFactory;
//import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO;
//import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO.DataSetOwner;
//import ch.systemsx.cisd.openbis.dss.generic.shared.api.v1.NewDataSetDTO.DataSetOwnerType;
//
///**
// * Demonstrates how to upload/download data sets from openBIS.
// * @author Kaloyan Enimanev
// */
public class Demo
{
//	public static final String				OPENBIS_URL				= "https://isin09.dti.supsi.ch:8443/openbis";
//
//	public static final String				USERNAME					= "galliva";
//
//	public static final String				PASSWORD					= "1234";
//
//	public static final String				DATASET_CONTENTS		= "C:\\Users\\galliva\\Desktop\\openbis-test\\upload";
//
//	public static final String				DATASET_DOWNLOAD_DIR	= "C:\\Users\\galliva\\Desktop\\openbis-test\\download";
//
//	private ISimpleOpenbisServiceFacade	openbis					= null;
//
	public static void main(String[] args)
	{
		// @Vanni: the dataSetCode in your case will be an OMERO identifier, i.e. you must not generate it
		//String dataSetCode = generateDataSetCode();
		
		//String dataSetType = "TYPE-TRAJECTORY";

		//new Demo().uploadDataSet(dataSetCode, dataSetType);
		//new Demo().downloadDataSet(dataSetCode);
	}
}
//
//	public void uploadDataSet(String dataSetCode, String dataSetType)
//	{
//		openbis = createOpenbisFacade();
//		
//		try
//		{
//			// this is the directory we want to upload
//			File dataSetContents = new File(DATASET_CONTENTS);
//
//			// upload data set
//			log("Uploading local folder '%s' as data set to openBIS ...", DATASET_CONTENTS);
//			
//			NewDataSetDTO newDataSet = createDataSetDTO(dataSetCode, dataSetContents, dataSetType);
//			openbis.putDataSet(newDataSet, dataSetContents);
//			
//			log("New data set with code '%s' successfully uploaded.", dataSetCode);
//		}
//		catch (Exception e)
//		{
//		}
//		finally
//		{
//			openbis.logout();
//		}
//	}
//
//	public void downloadDataSet(String dataSetCode)
//	{
//		openbis = createOpenbisFacade();
//		
//		try
//		{
//			// download data set
//			IDataSetDss dataSet = openbis.getDataSetDss(dataSetCode);
//			File downloadDir = new File(DATASET_DOWNLOAD_DIR);
//			dataSet.getLinkOrCopyOfContents("/", downloadDir);
//			log("Data set with code '%s' downloaded to local folder '%s'.", dataSetCode, downloadDir.getAbsolutePath());
//		}
//		catch (Exception e)
//		{
//		}
//		finally
//		{
//			openbis.logout();
//		}
//	}
//
//	private NewDataSetDTO createDataSetDTO(String dataSetCode, File dataSetContents, String dataSetType)
//	{
//		createRegistrationPropertiesFile(dataSetContents, dataSetCode, dataSetType);
//
//		// (existing) experiment in the openBIS installation
//		DataSetOwner dataSetOwner = new DataSetOwner(DataSetOwnerType.EXPERIMENT, OmegaConstants.OPENBIS_DEFAULT_IDENTIFIER);
//		
//		NewDataSetDTO newDataSet = new NewDataSetDTO(dataSetOwner, dataSetContents);
//		newDataSet.setDataSetTypeOrNull(dataSetType);
//		Map<String, String> properties = new HashMap<String, String>();
//
//		// populate the data set with properties existing in your openBIS installation
//		// properties.put("DEMO-DATASET-TEXT", "This is a demo property value.");
//		newDataSet.setProperties(properties);
//		return newDataSet;
//	}
//
//	/**
//	 * Creates a "side-car" file inside the data set, where we keep data needed for the registration process. You can use the file to pass any key-value property to the Jython Dropox.
//	 */
//	private void createRegistrationPropertiesFile(File dataSetContents, String dataSetCode, String dataSetType)
//	{
//		File registrationPropertiesFile = new File(dataSetContents, "registration.properties");
//		try
//		{
//			FileOutputStream fos = new FileOutputStream(registrationPropertiesFile);
//			Properties props = new Properties();
//			props.setProperty("data-set-code", dataSetCode);
//			props.setProperty("data-set-type", dataSetType);
//			props.store(fos, "Meta data parsed by the responsible openBIS Jython Dropbox.");
//			fos.close();
//		}
//		catch (IOException ioex)
//		{
//			throw new RuntimeException("Cannot create registration properties file: " + registrationPropertiesFile.getAbsolutePath());
//		}
//
//	}
//
//	private static String generateDataSetCode()
//	{
//		long currentTime = System.currentTimeMillis();
//		return Long.toHexString(currentTime).toUpperCase();
//	}
//
//	private ISimpleOpenbisServiceFacade createOpenbisFacade()
//	{
//		long twentySecondsTimeout = 20 * 1000;
//		return OpenbisServiceFacadeFactory.tryCreate(USERNAME, PASSWORD, OPENBIS_URL, twentySecondsTimeout);
//	}
//
//	private void log(String template, String... parameters)
//	{
//		String logMessage = String.format(template, (Object[]) parameters);
//		System.out.println(logMessage);
//	}
//}
