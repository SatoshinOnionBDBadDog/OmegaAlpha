package ch.supsi.omega.gui;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.TrajectoryVerifier;
import ch.supsi.omega.dll.TSCaller;
import ch.supsi.omega.exploration.common.JPanelSeparator;
import ch.supsi.omega.gui.common.InfoLabel;
import ch.supsi.omega.openbis.JFrameOpenBisLoadData;
import ch.supsi.omega.segmentation.SegmentationFrame;
import ch.supsi.omega.segmentation.label.LabelsFileImporter;
import ch.supsi.omega.segmentation.label.LabelsImporter;
import ch.supsi.omega.segmentation.pattern.Pattern;
import ch.supsi.omega.segmentation.pattern.PatternsCodedLoader;
import ch.supsi.omega.segmentation.pattern.PatternsLoader;
import ch.supsi.omega.segmentation.trajectory.TrajectoriesLoader;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;
import ch.supsi.omega.tracking.parameters.SPTInformationFileLoader;
import ch.supsi.omega.tracking.parameters.SPTInformationLoader;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class JPanelTS extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame = null;

	private static GConfigurationManager configurationManager = new GConfigurationManager();

	private List<Trajectory> trajectories = null;
	private List<Pattern> patterns = null;

	private String currentTrajectoriesDir = null;
	private String currentResultFile = null;

	private JButton jButtonManuallySegment;
	private JButton jButtonSegmentTracks;
	private JButton jButtonResults;
	private JLabel jLabelTitle;
	private JLabel jLabelTitle2;
	private JLabel jLabel3;
	private JLabel jLabelManuallySegment;
	private JLabel jLabelSegmentTracks;
	private JLabel jLabelResults;
	private JLabel jLabelStatus;
	private JLabel jLabelStatusDetails;
	private JPanel jPanelTitle;
	private JPanel jPanelTitle2;
	private JPanel jPanelEmpty2;
	private JPanel jPanelEmpty3;
	private JPanel jPanelManuallySegment;
	private JPanel jPanelSegmentTracks;
	private JPanel jPanelResults;
	private JPanel jPanelStatus;

	// private JLabel jLabelTrainSVM;
	// private JPanel jPanelTrainSVM;
	// private JLabel jLabel1;
	// private JLabel jLabel2;
	// private JPanel jPanelEmpty;
	// private JButton jButtonTrainSVM;

	public JPanelTS(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		try {
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		} catch (Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		initComponents();
	}

	private void initComponents() {
		jPanelTitle = new javax.swing.JPanel();
		jPanelTitle2 = new javax.swing.JPanel();
		jPanelManuallySegment = new javax.swing.JPanel();
		jPanelResults = new javax.swing.JPanel();
		jButtonManuallySegment = new javax.swing.JButton();
		jButtonResults = new javax.swing.JButton();
		jLabelManuallySegment = new javax.swing.JLabel();
		jLabelResults = new javax.swing.JLabel();
		jLabelTitle = new javax.swing.JLabel();
		jLabelTitle2 = new javax.swing.JLabel();
		jPanelEmpty2 = new javax.swing.JPanel();
		jPanelEmpty3 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jPanelSegmentTracks = new javax.swing.JPanel();
		jPanelStatus = new javax.swing.JPanel();
		jButtonSegmentTracks = new javax.swing.JButton();
		jLabelSegmentTracks = new javax.swing.JLabel();
		jLabelStatus = new javax.swing.JLabel();
		jLabelStatusDetails = new javax.swing.JLabel();
		// jLabelTrainSVM = new javax.swing.JLabel();
		// jLabel2 = new javax.swing.JLabel();
		// jPanelTrainSVM = new javax.swing.JPanel();
		// jPanelEmpty = new javax.swing.JPanel();
		// jLabel1 = new javax.swing.JLabel();
		// jButtonTrainSVM = new javax.swing.JButton();

		setLayout(new java.awt.GridLayout(20, 1));

		jPanelTitle
		        .setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabelTitle.setText("Manual segmentation:");
		jLabelTitle.setPreferredSize(new java.awt.Dimension(400, 25));
		jPanelTitle.add(jLabelTitle);

		add(jPanelTitle);

		jPanelManuallySegment.setPreferredSize(new java.awt.Dimension(337, 45));
		jPanelManuallySegment.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jButtonManuallySegment.setText("Manually segment");
		jButtonManuallySegment
		        .setPreferredSize(new java.awt.Dimension(160, 25));
		jButtonManuallySegment
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonManuallySegmentActionPerformed(evt);
			        }
		        });
		jPanelManuallySegment.add(jButtonManuallySegment);
		jPanelManuallySegment
		        .add(new InfoLabel(
		                this,
		                "This tool allows you to manually subdivide each trajectory in segments during which\n"
		                        + "the particle is moving in a recognizably homogeneous manner. The purpose is to identify\n"
		                        + "transient motion patterns (i.e. similar velocity, directionality, diffusivity etc.) or\n"
		                        + "'trajectory motifs', which 'provide insight into the interactions of the moving object\n"
		                        + "with its immediate environment'. Note: Manually segmented datasets can be used as\n"
		                        + "training sets for automatic segmentation."));

		jLabelManuallySegment.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelManuallySegment
		        .setText("Manually segment trajectories in your dataset.");
		jPanelManuallySegment.add(jLabelManuallySegment);

		add(jPanelManuallySegment);
		/*
		 * jPanelEmpty.setPreferredSize(new java.awt.Dimension(25, 35));
		 * 
		 * jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 * "/ch/supsi/omega/gui/resources/arrow35px.png"))); // NOI18N
		 * jLabel1.setPreferredSize(new java.awt.Dimension(15, 35));
		 * jPanelEmpty.add(jLabel1);
		 * 
		 * add(jPanelEmpty);
		 * 
		 * jPanelTrainSVM.setLayout(new
		 * java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		 * 
		 * jButtonTrainSVM.setText("Train TS Algorithm");
		 * jButtonTrainSVM.setPreferredSize(new java.awt.Dimension(160, 25));
		 * jButtonTrainSVM.addActionListener(new java.awt.event.ActionListener()
		 * { public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * jButtonTrainSVMActionPerformed(evt); } });
		 * jPanelTrainSVM.add(jButtonTrainSVM);
		 * 
		 * jLabelTrainSVM.setFont(new java.awt.Font("Tahoma", 0, 10));
		 * jLabelTrainSVM .setText(
		 * "Train the TS algorithm using the manually segmented trajectories" );
		 * jPanelTrainSVM.add(jLabelTrainSVM);
		 * 
		 * add(jPanelTrainSVM);
		 * 
		 * jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		 * "/ch/supsi/omega/gui/resources/arrow35px.png"))); // NOI18N
		 * jPanelEmpty1.add(jLabel2);
		 */

		// add(jPanelEmpty1);
		add(new JPanelSeparator());

		jPanelTitle2
		        .setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabelTitle2.setText("Automatic segmentation:");
		jLabelTitle2.setPreferredSize(new java.awt.Dimension(145, 25));
		jPanelTitle2.add(jLabelTitle2);

		JLabel u = new JLabel();
		u.setText("(this feature is unstable and released uniquely as an \"alpha\" version)");
		u.setFont(new java.awt.Font("Tahoma", 0, 10));
		// jPanelTitle2.add(u);

		add(jPanelTitle2);

		jPanelSegmentTracks.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jButtonSegmentTracks.setText("Segment");
		jButtonSegmentTracks.setPreferredSize(new java.awt.Dimension(160, 25));
		jButtonSegmentTracks
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonSegmentTracksActionPerformed(evt);
			        }
		        });
		jPanelSegmentTracks.add(jButtonSegmentTracks);
		jPanelSegmentTracks
		        .add(new InfoLabel(
		                this,
		                "This tool employs the supervised trajectory segmentation algorithm developed by Helmuth\n"
		                        + "and co-workers (Helmuth, et al. 2007. J Struct Biol. 159:347), to automatically recognize\n"
		                        + "motion pattern within each trajectory in a dataset. In order for this tool to work a\n"
		                        + "'training' set of trajectories has to be manually segmented first in order to train the\n"
		                        + "algorithm to assign motion patterns to individual trajectory segments. Please refer to\n"
		                        + "Helmuth et al. 2007 for more details on how to use this feature."));

		jLabelSegmentTracks.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelSegmentTracks
		        .setText("Automatically segment trajectories in your dataset.");
		jPanelSegmentTracks.add(jLabelSegmentTracks);

		add(jPanelSegmentTracks);

		jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		        "/ch/supsi/omega/gui/resources/arrow35px.png"))); // NOI18N
		// jPanelEmpty3.add(jLabel3);

		add(jPanelEmpty3);

		jPanelResults.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jButtonResults.setEnabled(false);
		jButtonResults.setText("Display result");
		jButtonResults.setPreferredSize(new java.awt.Dimension(160, 25));
		jButtonResults.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonResultActionPerformed(evt);
			}
		});
		// jPanelResults.add(jButtonResults);

		jLabelResults.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelResults
		        .setText("Display the results of the trajectory segmentation");
		jPanelResults.add(jLabelResults);

		// add(jPanelResults);

		add(jPanelEmpty2);

		jPanelStatus
		        .setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabelStatus.setText("Status:");
		jLabelStatus.setPreferredSize(new java.awt.Dimension(50, 25));
		jPanelStatus.add(jLabelStatus);

		jLabelStatusDetails.setText("ready");
		jPanelStatus.add(jLabelStatusDetails);

		// add(jPanelStatus);

		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());
		add(new JPanel());

		// info panel
		JPanel info = new JPanel();
		info.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		JButton infoButton = new JButton();

		infoButton.setText("info");
		infoButton.setPreferredSize(new java.awt.Dimension(100, 25));
		infoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane
				        .showMessageDialog(
				                null,
				                ""
				                        + "The algorithm utilized in this program is described in:\n"
				                        + "J. A. Helmuth, C. J. Burckhardt, P. Koumoutsakos, U. F. Greber, and I. F. Sbalzarini.\n"
				                        + "A novel supervised trajectory segmentation algorithm identifies distinct types of human\n"
				                        + "adenovirus motion in host cells. Journal of Structural Biology, 159(3):347–358, 2007.\n\n"
				                        + "For more information:\nhttp://www.mosaic.ethz.ch/Downloads/ParticleTrackerCSourceAndClient",
				                OmegaConstants.OMEGA_TITLE,
				                JOptionPane.INFORMATION_MESSAGE);
			}
		});
		info.add(infoButton);
		// add(info);
	}

	/**
	 * Displays the manual segmentation frame.
	 * 
	 * @param evt
	 *            the ActionEvent who called this method
	 */
	private void jButtonManuallySegmentActionPerformed(
	        java.awt.event.ActionEvent evt) {
		// ask the user if he wants to load data from openBIS
		// int answer = JOptionPane.showConfirmDialog(this,
		// OmegaConstants.INFO_OPENBIS_LOAD_QUESTION,
		// OmegaConstants.OMEGA_TITLE, JOptionPane.YES_NO_OPTION);
		int answer = JOptionPane.NO_OPTION;

		String selectedDirectory = "";

		// if openBIS
		if (answer == JOptionPane.YES_OPTION) {
			// show the choose data JFrame
			JFrameOpenBisLoadData jFrameOpenBisLoadData = new JFrameOpenBisLoadData(
			        this);

			jFrameOpenBisLoadData
			        .loadDatasets(OmegaConstants.OPENBIS_TRAJECTORY_TYPE);

			jFrameOpenBisLoadData.setLocationRelativeTo(this);
			jFrameOpenBisLoadData.setVisible(true);

			// if data is not loaded return
			if (!jFrameOpenBisLoadData.isDataLoaded())
				return;
			else {
				// load the trajectories from the temp directory
				selectedDirectory = jFrameOpenBisLoadData.getSaveDirectory();
				TrajectoriesLoader trajectoriesLoader = new TrajectoriesLoader(
				        selectedDirectory
				                + System.getProperty("file.separator"),
				        OmegaConstants.TRACKS_FILES_EXTENSION);
				trajectories = trajectoriesLoader.loadTrajectories();
			}
		}
		// if File System
		else {
			FileHelper fileChooserHelper = new FileHelper();

			trajectories = fileChooserHelper.loadTracks("trajectoriesTrainDir");

			// the directory selected by the user
			selectedDirectory = fileChooserHelper.getSelectedDirectory();
		}

		if (trajectories != null && !(trajectories.size() == 0)) {
			// load the SPT execution information
			SPTExecutionInfoHandler sptExecutionInfo = null;
			try {
				SPTInformationLoader sptInformationLoader = new SPTInformationFileLoader(
				        selectedDirectory
				                + System.getProperty("file.separator")
				                + OmegaConstants.SPT_INFORMATION_FILE);
				sptInformationLoader.initLoader();
				sptExecutionInfo = sptInformationLoader.loadInformation();
				sptInformationLoader.closeLoader();
			} catch (Exception e) {
				// already handled in the SPTInformationLoader class
			}

			// load the patterns (currently hard-coded)
			loadPatterns();

			// start the segmentation frame
			SegmentationFrame segmentationFrame = new SegmentationFrame(
			        selectedDirectory, trajectories, sptExecutionInfo,
			        patterns, mainFrame.getLocation().x + mainFrame.getWidth(),
			        mainFrame.getLocation().y);
			segmentationFrame.setCanSegment(true);
			segmentationFrame.setMenu();
		} else
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.INFO_LOADTRAJECTORIES,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
	}

	private void loadPatterns() {
		PatternsLoader patternsLoader = new PatternsCodedLoader();
		patternsLoader.loadPatterns();
		patterns = patternsLoader.getPatterns();
	}

	/**
	 * Calls the SVM training in the TS DLL.
	 * 
	 * @param evt
	 *            the ActionEvent who called this method
	 */
	/*
	 * private void jButtonTrainSVMActionPerformed(java.awt.event.ActionEvent
	 * evt) { String trajectoriesDir = null;
	 * 
	 * FileHelper fileChooserHelper = new FileHelper();
	 * 
	 * trajectoriesDir = fileChooserHelper.selectFile("SVMDir",
	 * OmegaConstants.INFO_SELECTDIR_SVM, JFileChooser.DIRECTORIES_ONLY);
	 * 
	 * if (trajectoriesDir != null) { TrainThread trainThread = new
	 * TrainThread(trajectoriesDir, "", "", ""); trainThread.start(); } //
	 * String trajectoriesDir = null; // String labelsFile = null; // String
	 * patternDir = null; // String patternOutDir = null; // // FileHelper
	 * fileChooserHelper = new FileHelper(); // // trajectoriesDir =
	 * fileChooserHelper.selectFile("SVMDir", OmegaConstants.INFO_SELECTDIR_SVM,
	 * JFileChooser.DIRECTORIES_ONLY); // // if (trajectoriesDir != null) // {
	 * // labelsFile = fileChooserHelper.selectFile("labelsFile",
	 * OmegaConstants.INFO_SELECT_LABEL_FILE, JFileChooser.FILES_ONLY); // } //
	 * if (labelsFile != null) // { // patternDir =
	 * fileChooserHelper.selectFile("patternDir",
	 * OmegaConstants.INFO_SELECTDIR_LOAD_PARAMETERS,
	 * JFileChooser.DIRECTORIES_ONLY); // } // if (patternDir != null) // { //
	 * patternOutDir = fileChooserHelper.selectFile("patternOutDir",
	 * OmegaConstants.INFO_SELECTDIR_SAVE_PATTERNS,
	 * JFileChooser.DIRECTORIES_ONLY); // } // if (patternOutDir != null) // {
	 * // TrainThread trainThread = new TrainThread(trajectoriesDir, labelsFile,
	 * patternDir, patternOutDir); // trainThread.start(); //
	 * TSCaller.callTrainSVM(trajectoriesDir, labelsFile, patternDir,
	 * patternOutDir); // } }
	 */

	/**
	 * Calls the automatic segmentation in the TS DLL.
	 * 
	 * @param evt
	 *            the ActionEvent who called this method
	 */
	private void jButtonSegmentTracksActionPerformed(
	        java.awt.event.ActionEvent evt) {
		String trajectoriesDir = null;
		String patternOutDir = null;
		String trajectoriesOutDir = null;

		FileHelper fileChooserHelper = new FileHelper();

		trajectoriesDir = fileChooserHelper.selectFile("trajectoriesDir",
		        OmegaConstants.INFO_SELECTDIR_TRACKS,
		        JFileChooser.DIRECTORIES_ONLY);

		if (trajectoriesDir != null) {
			if (!TrajectoryVerifier.verifyTrajectories(trajectoriesDir))
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.ERROR_TS_NOT_ENOUGH_POINTS,
				        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			else {
				patternOutDir = fileChooserHelper.selectFile("patternOutDir",
				        OmegaConstants.INFO_SELECTDIR_LOAD_PATTERNS,
				        JFileChooser.DIRECTORIES_ONLY);
			}
		}
		if (patternOutDir != null) {
			trajectoriesOutDir = fileChooserHelper.selectFile("resultDir",
			        OmegaConstants.INFO_SELECTDIR_RESULT,
			        JFileChooser.DIRECTORIES_ONLY);
		}
		if (trajectoriesOutDir != null) {
			currentTrajectoriesDir = trajectoriesDir;
			currentResultFile = String.format("%s\\%s.%s", trajectoriesOutDir,
			        OmegaConstants.SEGMENTED_LABELS_FILE_NAME,
			        OmegaConstants.LABELS_FILES_EXTENSION);

			SegmentThread segmentThread = new SegmentThread(trajectoriesDir,
			        patternOutDir, trajectoriesOutDir);
			segmentThread.start();
		}
	}

	private void jButtonResultActionPerformed(java.awt.event.ActionEvent evt) {
		TrajectoriesLoader trajectoriesLoader = new TrajectoriesLoader(
		        currentTrajectoriesDir + System.getProperty("file.separator"),
		        OmegaConstants.TRACKS_FILES_EXTENSION);

		List<Trajectory> trajectories = trajectoriesLoader.loadTrajectories();

		if (trajectories != null && !(trajectories.size() == 0)) {
			// load the SPT execution information
			SPTExecutionInfoHandler sptExecutionInfo = null;
			try {
				SPTInformationLoader sptInformationLoader = new SPTInformationFileLoader(
				        currentTrajectoriesDir
				                + System.getProperty("file.separator")
				                + OmegaConstants.SPT_INFORMATION_FILE);
				sptInformationLoader.initLoader();
				sptExecutionInfo = sptInformationLoader.loadInformation();
				sptInformationLoader.closeLoader();
			} catch (Exception e) {
			}

			// load the patterns (currently hard-coded)
			loadPatterns();

			// load the labels
			LabelsImporter labelsImporter = new LabelsFileImporter();
			labelsImporter.setFileName(currentResultFile);
			labelsImporter.setTrajectories(trajectories);
			labelsImporter.loadLabels();

			// start the segmentation frame
			SegmentationFrame segmentationFrame = new SegmentationFrame(
			        currentTrajectoriesDir, trajectories, sptExecutionInfo,
			        patterns, mainFrame.getLocation().x + mainFrame.getWidth(),
			        mainFrame.getLocation().y);

			segmentationFrame.setCanSegment(true);
			segmentationFrame.setMenu();
		} else
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.INFO_LOADTRAJECTORIES,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Switch the status (enabled / disabled) of the controls.
	 */
	/*
	 * private void switchControlsStatus() {
	 * jButtonManuallySegment.setEnabled(!jButtonManuallySegment.isEnabled());
	 * jButtonTrainSVM.setEnabled(!jButtonTrainSVM.isEnabled());
	 * jButtonSegmentTracks.setEnabled(!jButtonSegmentTracks.isEnabled()); }
	 */

	class SegmentThread extends Thread {
		private String trajectoriesDir, patternDir, trajectoriesOutDir;

		public SegmentThread(String trajectoriesDir, String patternDir,
		        String trajectoriesOutDir) {
			super();
			this.trajectoriesDir = trajectoriesDir;
			this.patternDir = patternDir;
			this.trajectoriesOutDir = trajectoriesOutDir;
		}

		public void run() {
			jButtonSegmentTracks.setEnabled(false);
			jButtonResults.setEnabled(false);

			jLabelStatusDetails.setText("segmentation running...");

			// call the train algorithm
			TSCaller.callSegment(trajectoriesDir, patternDir,
			        trajectoriesOutDir);

			// check the results
			String result = "done";

			boolean exists = false;
			try {
				exists = new File(currentResultFile).exists();
			} catch (Exception e) {
				System.out.println(e);
				// nothing we can do...
			}

			if (!exists)
				result = result + " - no results";
			else
				result = result + " - results saved";

			jLabelStatusDetails.setText(result);

			jButtonSegmentTracks.setEnabled(true);
			jButtonResults.setEnabled(true);
		}
	}

	/*
	 * class TrainThread extends Thread { private String trajectoriesDir,
	 * labelsFile, patternDir, patternOutDir;
	 * 
	 * public TrainThread(String trajectoriesDir, String labelsFile, String
	 * patternDir, String patternOutDir) { super(); this.trajectoriesDir =
	 * trajectoriesDir; this.labelsFile = labelsFile; this.patternDir =
	 * patternDir; this.patternOutDir = patternOutDir; }
	 * 
	 * public void run() { switchControlsStatus();
	 * jLabelStatusDetails.setText("training the algorithm...");
	 * 
	 * // empty the output directory try {
	 * FileHelper.emptyDirectory(patternOutDir); } catch (Exception e) { //
	 * already handled... }
	 * 
	 * // call the train algorithm TSCaller.callTrainSVM(trajectoriesDir,
	 * labelsFile, patternDir, patternOutDir);
	 * 
	 * // check the results String result = "done";
	 * 
	 * int filesNumber = 0; try { filesNumber = new
	 * File(patternOutDir).listFiles().length; } catch (Exception e) { //
	 * nothing we can do... }
	 * 
	 * if (filesNumber == 0) result = result + " with errors";
	 * 
	 * jLabelStatusDetails.setText(result); switchControlsStatus(); } }
	 */
}
