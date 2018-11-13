package ch.supsi.omega.exploration.common.imagedata;

import java.util.HashMap;

import javax.swing.JFrame;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;
import ch.supsi.omega.tracking.parameters.SPTInformationFileLoader;
import ch.supsi.omega.tracking.parameters.SPTInformationLoader;

public class OmeroDataHelper {

	/**
	 * The Frame calling this class, if available.
	 */
	private JFrame frame = null;

	/**
	 * An HashMap used to cache the original sizes.
	 */
	private final HashMap<String, double[]> sizesHashMap = new HashMap<String, double[]>();

	public OmeroDataHelper(final JFrame frame) {
		this.frame = frame;
	}

	public double[] getOriginalSizes(final String folder) {
		// check if we have a cached object for this folder
		double originalSizes[] = this.sizesHashMap.get(folder);

		if (originalSizes != null)
			return originalSizes;
		else {
			originalSizes = new double[3];

			// ====
			// find the SPT_info file and get the pixels sizes
			SPTExecutionInfoHandler sptExecutionInfo = null;
			try {
				final SPTInformationLoader sptInformationLoader = new SPTInformationFileLoader(
				        folder + System.getProperty("file.separator")
				                + OmegaConstants.SPT_INFORMATION_FILE);
				sptInformationLoader.initLoader();
				sptExecutionInfo = sptInformationLoader.loadInformation();
				sptInformationLoader.closeLoader();

				originalSizes[0] = sptExecutionInfo.getImageData().getSizeX();
				originalSizes[1] = sptExecutionInfo.getImageData().getSizeY();
				originalSizes[2] = sptExecutionInfo.getImageData().getSizeT();
			} catch (final Exception e) {
				// already handled in the SPTInformationLoader class
				originalSizes[0] = 0.0;
				originalSizes[1] = 0.0;
				originalSizes[2] = 0.0;
			}

			if ((originalSizes[0] == 0.0) || (originalSizes[1] == 0.0)
			        || (originalSizes[2] == 0.0)) {
				// get the "image name"
				final String[] temp = folder.split("\\\\");
				final String imageName = temp[temp.length - 1];

				final JDialogImageSizes jDialogImageSizes = new JDialogImageSizes(
				        imageName, originalSizes);

				if (this.frame != null) {
					jDialogImageSizes.setLocationRelativeTo(this.frame);
				}

				jDialogImageSizes.setVisible(true);
			}
		}

		this.sizesHashMap.put(folder, originalSizes);

		return originalSizes;
	}
}
