package ch.supsi.omega.openbis;

import java.util.HashMap;
import java.util.Map;

public class OpenBisPropertiesHandler
{
	private static final String[]	propertiesKeys	= new String[] { "OMERO-DATASET-NAME", "OMERO-IMAGE-NAME", "USER-DATASET-NAME", "USER-DESCRIPTION" };

	private Map<String, String>	properties		= new HashMap<String, String>();

	public Map<String, String> getProperties()
	{
		return properties;
	}

	public OpenBisPropertiesHandler(String omeroDatasetName, String omeroImageName, String userDatasetName, String userDescription)
	{
		// checks
		if (omeroDatasetName == null || omeroDatasetName.length() < 1)
			omeroDatasetName = "";
		if (omeroImageName == null || omeroImageName.length() < 1)
			omeroImageName = "";
		if (userDatasetName == null || userDatasetName.length() < 1)
			userDatasetName = "";
		if (userDescription == null || userDescription.length() < 1)
			userDescription = "";

		properties.put(propertiesKeys[0], omeroDatasetName);
		properties.put(propertiesKeys[1], omeroImageName);
		properties.put(propertiesKeys[2], userDatasetName);
		properties.put(propertiesKeys[3], userDescription);
	}
}
