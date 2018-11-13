package ch.supsi.omega.dll;

import java.util.logging.Level;

import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;

import com.galliva.gallibrary.GLogManager;

public class TSCaller
{
	// native methods
	private native void trainSVM(String trajectoriesDir, String labelsFile, String patternDir, String patternOutDir);
	private native void segment(String trajectoriesDir, String patternOutDir, String trajectoriesOut);
	
	/**
	 * Calls the train svm method in the TS DLL.
	 * @param trajectoriesTrainingSetDirectory the directory containing the trajectories training set
	 * @param labelsFile								 the file with the trajectories labels
	 * @param parametersDirectory					 the directory containing the parameters - if this version 2 types of movement = 2 files
	 * @param patternsOutDirectory					 the directory where to save the trained patterns - 2 files in this version
	 */
	public static void callTrainSVM(String trajectoriesTrainingSetDirectory, String labelsFile, String parametersDirectory, String patternsOutDirectory)
	{
		try
		{
			new TSCaller().trainSVM(
					trajectoriesTrainingSetDirectory + System.getProperty("file.separator"), 
					labelsFile, 
					parametersDirectory + System.getProperty("file.separator"), 
					patternsOutDirectory + System.getProperty("file.separator")
			);
			
			GLogManager.log(OmegaConstants.LOG_TRAIN_CALLED);
		}
		catch(UnsatisfiedLinkError e)
		{
			GLogManager.log(OmegaConstants.ERROR_NODLL, Level.SEVERE);
			JOptionPane.showMessageDialog(null, OmegaConstants.ERROR_NODLL, OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Calls the segmentation method in the TS DLL.
	 * @param trajectoriesDirectory	  the directory containing the trajectories to be segmented
	 * @param trainedPatternsDirectory the directory containing the trained patterns - 2 files in this version
	 * @param resultsDirectory			  the directory where to save the results
	 */
	public static void callSegment(String trajectoriesDirectory, String trainedPatternsDirectory, String resultDirectory)
	{
		try
		{
			new TSCaller().segment(
					trajectoriesDirectory + System.getProperty("file.separator"), 
					trainedPatternsDirectory + System.getProperty("file.separator"),
					resultDirectory + System.getProperty("file.separator")
			);
			GLogManager.log(OmegaConstants.LOG_SEGMENT_CALLED);
		}
		catch(UnsatisfiedLinkError e)
		{
			GLogManager.log(OmegaConstants.ERROR_NODLL, Level.SEVERE);
		}
	}
	static
	{
		try
		{
			System.loadLibrary(OmegaConstants.OMEGA_TS_DLL);
		}
		catch(UnsatisfiedLinkError e)
		{
			GLogManager.log(OmegaConstants.ERROR_NODLL, Level.SEVERE);
		}
	}
}
