package ch.supsi.omega.common;

import java.awt.Dimension;

public interface OmegaConstants {
	/**
	 * Build and info
	 */
	public String OMEGA_TITLE = "OMEGA public alpha release";
	public String OMEGA_BUILD = "build 20121220";
	public String OMEGA_WEBSITE = "http://www.supsi.ch";
	public String OMEGA_AUTHOR = "Supsi";
	public String OMEGA_DESCRIPTION = "<html>Open Microscopy Environment inteGrated Analysis</html>";

	/**
	 * Files and directories
	 */
	public String OMEGA_TS_DLL = "omega-ts";
	public String OMEGA_SPT_DLL = "omega-spt-stats";

	public String INIFILE = System.getProperty("user.home") + "/.omega.ini";
	public String ORIGINALINIFILE = "/ch/supsi/omega/common/resources/default.ini";

	public String SPT_INFORMATION_FILE = "SPT_info.txt";
	public String SPT_INFORMATION_SEPARATOR = "=";

	public String TRACKS_FILES_EXTENSION = "out";
	public String TRACKS_FILES_LINE_SEPARATOR = " ";

	public String PATTERNS_FILES_EXTENSION = "dat";

	public String LABELS_FILE_NAME = "labels";
	public String SEGMENTED_LABELS_FILE_NAME = "t_segmented";
	public String LABELS_FILES_EXTENSION = "dat";
	public String LABELS_FILES_LINE_SEPARATOR = " ";

	public int TRAJECTORY_FILE_COLUMN_NUMBER = 10;
	public int STATS_FILE_COLUMN_NUMBER = 6;

	public String SMSS_INTERPOLATION_FILE = "/ch/supsi/omega/common/resources/SMSS_interpolation_data.csv";
	public String D_INTERPOLATION_FILE = "/ch/supsi/omega/common/resources/D_interpolation_data.csv";

	/**
	 * openBIS
	 */
	public String OPENBIS_DEFAULT_EXPERIMENT = "OMEGA-CLIENT";
	public String OPENBIS_DEFAULT_IDENTIFIER = String.format("%s%s",
			"/SUPSI/OMEGA/", OPENBIS_DEFAULT_EXPERIMENT);

	public String OPENBIS_TRAJECTORY_TYPE = "TYPE-TRAJECTORY";
	public String OPENBIS_LABEL_TYPE = "TYPE-LABEL";

	/**
	 * Frames titles
	 */
	public String OMEGA_SEGMENTATION_TITLE = "TS Module";
	public String OMEGA_REVIEW_TITLE = "Image selection and review Module";
	public String OMEGA_AVAILABLE_MOTIONS_TITLE = "Available Motions";

	public String OMEGA_OPENBIS_SETTINGS_TITLE = "Connection settings for openBIS";
	public String OMEGA_OPENBIS_PASSWORD_TITLE = "Password for the openBIS server";
	public String OMEGA_OPENBIS_SAVEDATA_TITLE = "Save data in openBIS";

	public String REVIEW_COLOR_CHOOSER_FRAME = "Choose the color used to display the trajectories";

	/**
	 * Windows and dimensions
	 */
	public int JFRAME_ADDITIONAL_WIDTH = 34;
	public int JFRAME_ADDITIONAL_HEIGHT = 95;

	public Dimension SEGMENTATION_FRAME_MINIMUM_SIZE = new Dimension(400, 400);
	public int SEGMENTATION_PANEL_WIDTH = 600;
	public int SEGMENTATION_PANEL_HEIGHT = 600;

	public Dimension REVIEW_PANEL_SIZE = new Dimension(600, 600);
	public int THUMBNAIL_SIZE = 100;
	public int DRAWING_POINTSIZE = 4;

	/**
	 * Various
	 */
	public boolean INVERT_TRAJECTORY_POINTS = true;

	public int TS_MINIMUN_NUMBER_OF_POINTS = 100;

	/**
	 * INFO
	 */
	public String INFO_OMERO_CONNECTION_OK = "connected to %s";
	public String INFO_SELECT_ONE_CHANNEL = "Please select exactly one channel to be processed";
	public String INFO_SELECT_AT_LEAST_ONE_IMAGE = "Please select at least one image to be processed";
	public String INFO_SELECT_IMAGE_DIRECTOTY = "Please select the directory where to save the image";
	public String INFO_T_MUST_BE_GREATER_THAN_ONE = "Please select an image with a number of frames (T) > 1";

	public String INFO_OPENBIS_CONNECTION_OK = "Connection to openBIS succeeded.";
	public String INFO_OPENBIS_UPLOAD_OK = "Data uploaded to openBIS.";
	public String INFO_OPENBIS_LOAD_QUESTION = "Would you like to load the tracks from openBIS?";
	public String INFO_OPENBIS_LOAD_TITLE = "Please choose the data to load";

	public String INFO_SPT_RUNNING = "processing %s";

	public String INFO_TRACKSLOADED = " trajectories loaded";
	public String INFO_PATTERNSSLOADED = " patterns loaded";
	public String INFO_LABELS_SAVED = " trajectories labels saved";

	public String INFO_LOADTRAJECTORIES = "Please load at least one trajectory.";

	public String INFO_NOPATTERNFORID = "No Pattern loaded for this ID.";

	public String INFO_SELECTDIR_SVM = "Please select the directory with the training files";
	public String INFO_SELECTDIR_OUTPUT_TRACKS = "Please select the directory where to save the trajectories files";
	public String INFO_SELECTDIR_TRACKS = "Please select the directory with the trajectories files";
	public String INFO_SELECT_LABEL_FILE = "Please select the labels file";
	public String INFO_SELECTDIR_LOAD_PARAMETERS = "Please select the directory with the parameters files";
	public String INFO_SELECTDIR_SAVE_PATTERNS = "Please select the directory where to save the trained patterns files";
	public String INFO_SELECTDIR_LOAD_PATTERNS = "Please select the directory with the trained patterns files";
	public String INFO_SELECTDIR_LABELS = "Please select the directory where to save the labels file";
	public String INFO_SELECTDIR_RESULT = "Please select the directory where to save the segmentation results";
	public String INFO_OPEN_REVIEW_MODULE = "Please first choose the image you want to display the\ntrajectories on by selecting 'Display SPT track on this\nimage' from the Image and Selection module menu.";
	public String INFO_SELECTDIR_DATASET = "Please select the directory with the tracking / segmentation results";
	public String INFO_NO_DATASET = "Please select at least one dataset.";
	public String INFO_SELECT_TRACKS_LABELS = "Please select the trajectories directory and the labels file.";

	/**
	 * WARNINGS
	 */
	public String WARNING_LABELS_NUMBER_NOT_CORRECT = "Current trajectories are %d, but %d trajectories labels where loaded";
	public String WARNING_SNR_LESS_THAN_2 = "The average minimum SNR found in the selected images is %.4f. Do you want to continue?";

	/**
	 * ERRORS
	 */
	public String ERROR_PORT_IS_NUMBER = "Port must be a number!";
	public String ERROR_CANNOT_CONNECT_TO_OMERO = "cannot connect to server";
	public String ERROR_LOADING_THE_DS = "Unable to load the Dataset!";
	public String ERROR_UNABLE_TO_DISPLAY_IMAGE = "Unable to display the image!";
	public String ERROR_SAVE_IMAGE = "Unable to save the image!";

	public String ERROR_C_Z_MUST_BE_NUMBERS = "C and Z must be numbers!";

	public String ERROR_SPT_MAX_VALUE = "This value must be a number!";
	public String ERROR_INIT_SPT_RUN = "Error during the initialization of the SPT algorithm!";
	public String ERROR_DURING_SPT_RUN = "Error during the run of the SPT algorithm!";
	public String ERROR_SPT_SAVE_RESULTS = "Error saving the SPT results!";

	public String ERROR_NOTRAJECTORIES = "Unable to load any trajectory!";
	public String ERROR_NO_SPT_INFORMATION = "Unable to load the image's information coming from the SPT module!";
	public String ERROR_NOPATTERNS = "Unable to load any pattern!";
	public String ERROR_LOADING_SEGMENTATION = "Error during the segmentation loading!";
	public String ERROR_DRAWING = "Unable to draw the Trajectory!";
	public String ERROR_SAVE_LABELS = "Unable to save the trajectories labels!";
	public String ERROR_SAVE_CSV = "Unable to save the CSV file!";

	public String ERROR_TS_NOT_ENOUGH_POINTS = "Each trajectory must have at least 100 points to be processed!";

	public String ERROR_NODLL = "No Omega DLL found or DLL error: ";

	public String ERROR_OPENBIS_CONNECTION_FAIL = "Unable to connect to openBIS, please check your settings!";
	public String ERROR_OPENBIS_UPLOAD = "Unable to upload the dataset to openBIS!";
	public String ERROR_OPENBIS_DOWNLOAD = "Unable to download the dataset from openBIS!";
	public String ERROR_OPENBIS_LISTDATASETS = "Unable to load the dataset's list from openBIS!";

	public String ERROR_STATISTICAL_CALCULATION = "Something went wrong during the statistical calculation.\nStats not available.";

	public String ERROR_INTERPOLATION_CALCULATION = "Something went wrong during the bilinear interpolation.";
	public String ERROR_INTERPOLATION_CALCULATION_SNR = "The SNR is out of range. Impossible to interpolate.";
	public String ERROR_INTERPOLATION_CALCULATION_L = "The length is out of range. Impossible to interpolate.";

	/**
	 * LOGS
	 */
	public String LOG_TRAIN_CALLED = "DLL train method called";
	public String LOG_SEGMENT_CALLED = "DLL segment method called";
	public String LOG_SET_INI_FAILED = "Cannot set the INI file";
}
