
package ch.supsi.omega.openbis;

import java.util.HashMap;
import java.util.List;

import ch.systemsx.cisd.openbis.dss.client.api.v1.DataSet;

public class UploadDownloadDataSetTest
{
	public static void main(String[] args)
	{
		String dataSetType = "TYPE-LABEL";

		// upload dataset
		String dataSetCode = OpenBisHelper.generateDataSetCode();

		OpenBisPropertiesHandler openBisPropertiesHandler = new OpenBisPropertiesHandler("DS1", "Artificial.tiff", "Run 13.02.2012", "Test run by galliva");

		OpenBisHelper.uploadDataSet(dataSetCode, dataSetType, openBisPropertiesHandler, "C:\\Users\\galliva\\Desktop\\trash\\openbis-test\\upload", null);

		// download dataset
		OpenBisHelper.downloadDataSet(dataSetCode, "C:\\Users\\galliva\\Desktop\\trash\\openbis-test\\download", null);

		// list datasets
		List<DataSet> datasets = OpenBisHelper.listDatasets(dataSetType, null);

		for (DataSet dataSet : datasets)
		{
			HashMap<String, String> properties = dataSet.getProperties();

			String dataSetInfo = String.format("%s %s %s\n", dataSet.getCode(), properties.get("USER-DATASET-NAME"), properties.get("USER-DESCRIPTION"));

			System.out.println(dataSetInfo);
		}
	}
}