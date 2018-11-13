package ch.supsi.omega.segmentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.UndoManager;
import ch.supsi.omega.openbis.JDialogOpenBisSaveData;
import ch.supsi.omega.openbis.JFrameOpenBisLoadData;
import ch.supsi.omega.segmentation.label.LabelsFileExporter;
import ch.supsi.omega.segmentation.label.LabelsFileImporter;
import ch.supsi.omega.segmentation.label.LabelsImporter;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;

public class SegmentationMenu
{
	private SegmentationFrame	segmentationFrame	= null;

	private JMenuBar				menuBar;

	private JMenuItem				undoAction			= null;
	private JMenuItem				redoAction			= null;

	private String					currentFileName	= "";

	public JMenuBar getMenuBar()
	{
		return menuBar;
	}

	public JMenuItem getUndoAction()
	{
		return undoAction;
	}

	public JMenuItem getRedoAction()
	{
		return redoAction;
	}
	
	public String getCurrentFileName()
	{
		return currentFileName;
	}

	public SegmentationMenu(final SegmentationFrame segmentationFrame)
	{
		this.segmentationFrame = segmentationFrame;

		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu windowsMenu = new JMenu("Window");
		JMenu openBISMenu = new JMenu("openBIS");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(windowsMenu);
		menuBar.add(openBISMenu);

		// File
		JMenuItem loadLabelsAction = new JMenuItem("Load Segmentation From File...");
		loadLabelsAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

		JMenuItem saveLabelsAction = new JMenuItem("Save Segmentation To File...");
		saveLabelsAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		JMenuItem closeAction = new JMenuItem("Close");
		closeAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		fileMenu.add(loadLabelsAction);
		fileMenu.add(saveLabelsAction);
		fileMenu.addSeparator();
		fileMenu.add(closeAction);

		// Edit
		undoAction = new JMenuItem("Undo");
		undoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

		redoAction = new JMenuItem("Redo");
		redoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

		JMenuItem resetAction = new JMenuItem("Reset Segmentation of the Current Trajectory");
		resetAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

		JMenuItem resetAllAction = new JMenuItem("Reset Segmentation of the All Trajectories");
		resetAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		editMenu.add(undoAction);
		editMenu.add(redoAction);
		editMenu.addSeparator();
		editMenu.add(resetAction);
		editMenu.add(resetAllAction);

		undoAction.setEnabled(false);
		redoAction.setEnabled(false);

		// Window
		JMenuItem overviewAction = new JMenuItem("Overview...");
		overviewAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		windowsMenu.add(overviewAction);

		JMenuItem patternsAction = new JMenuItem("Available Motion Types...");
		patternsAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		windowsMenu.add(patternsAction);

		// check if we need to enable the menus
		if (segmentationFrame.isCanSegment())
		{
			loadLabelsAction.setEnabled(true);
			saveLabelsAction.setEnabled(true);
			resetAction.setEnabled(true);
			patternsAction.setEnabled(true);
		}
		else
		{
			loadLabelsAction.setEnabled(false);
			saveLabelsAction.setEnabled(false);
			resetAction.setEnabled(false);
			patternsAction.setEnabled(false);
		}

		// openBIS
		JMenuItem saveTracksAction = new JMenuItem("Save trajectories to openBIS...");
		saveTracksAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		openBISMenu.add(saveTracksAction);

		JMenuItem saveSegmentationAction = new JMenuItem("Save segmentation to openBIS...");
		saveSegmentationAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		openBISMenu.add(saveSegmentationAction);

		JMenuItem loadSegmentationAction = new JMenuItem("Load segmentation from openBIS...");
		loadSegmentationAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		openBISMenu.add(loadSegmentationAction);

		if (segmentationFrame.isCanSegment())
		{
			saveSegmentationAction.setEnabled(true);
			loadSegmentationAction.setEnabled(true);
		}
		else
		{
			saveSegmentationAction.setEnabled(false);
			loadSegmentationAction.setEnabled(false);
		}

		// listeners
		undoAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				undoOrRedo("UNDO");
			}
		});
		redoAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				undoOrRedo("REDO");
			}
		});

		loadLabelsAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				List<Trajectory> trajectories = new ArrayList<Trajectory>();

				trajectories = segmentationFrame.getTrajectories();

				LabelsFileImporter labelsImporter = new LabelsFileImporter();
				labelsImporter.setTrajectories(trajectories);
				labelsImporter.loadLabels();
				
				currentFileName = labelsImporter.getCurrentFileName();
				
				segmentationFrame.setCurrentTrajectory();
			}
		});

		saveLabelsAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				List<Trajectory> trajectories = new ArrayList<Trajectory>();

				trajectories = segmentationFrame.getTrajectories();

				LabelsFileExporter labelsExporter = new LabelsFileExporter();
				labelsExporter.setTrajectories(trajectories);
				labelsExporter.saveLabels();
				
				currentFileName = labelsExporter.getCurrentFileName();
				
				segmentationFrame.setCurrentTrajectory();
			}
		});

		closeAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				segmentationFrame.dispose();
			}
		});

		resetAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int[] labels = segmentationFrame.getCurrentTrajectory().getLabels();
				for (int i = 0; i < labels.length; i++)
					labels[i] = 0;
				segmentationFrame.repaint();

				// undo manager
				UndoManager<int[]> currentUndoManager = segmentationFrame.getCurrentUndoManager();
				currentUndoManager.addToStack(labels.clone());
				segmentationFrame.setUndoRedoMenuItemsStatus();
			}
		});

		resetAllAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				List<Trajectory> getTrajectories = segmentationFrame.getTrajectories();

				int trajectoryIndex = 0;

				for (Trajectory trajectory : getTrajectories)
				{
					int[] labels = trajectory.getLabels();

					for (int i = 0; i < labels.length; i++)
						labels[i] = 0;

					segmentationFrame.repaint();

					// undo managers
					UndoManager<int[]> currentUndoManager = segmentationFrame.getUndoManagers().get(trajectoryIndex);
					currentUndoManager.addToStack(labels.clone());
					trajectoryIndex++;
				}
				segmentationFrame.setUndoRedoMenuItemsStatus();
			}
		});

		overviewAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				segmentationFrame.getSegmentationOverviewFrame().setVisible(true);
			}
		});

		patternsAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				segmentationFrame.showPatternsInfo();
			}
		});

		// openBIS actions
		saveTracksAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				openSaveDialog(segmentationFrame, OmegaConstants.OPENBIS_TRAJECTORY_TYPE, segmentationFrame.getTrajectoriesDirectory());
			}
		});

		saveSegmentationAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// the current segmentation need to be saved in the temp directory
				String sysTempDirectory = System.getProperty("java.io.tmpdir");
				String tempDirectory = sysTempDirectory + "UopenBISLabels";
				String tempLabelsFile = tempDirectory + System.getProperty("file.separator") + OmegaConstants.LABELS_FILE_NAME + "." + OmegaConstants.LABELS_FILES_EXTENSION;

				// create a directory (if not existing)
				FileHelper.createDirectory(tempDirectory);
				// empty if
				FileHelper.emptyDirectory(tempDirectory);

				// save the trajectories in the temop file
				List<Trajectory> trajectories = new ArrayList<Trajectory>();

				trajectories = segmentationFrame.getTrajectories();

				// export the labels in the temp directory
				LabelsFileExporter labelsExporter = new LabelsFileExporter();
				labelsExporter.setTrajectories(trajectories);
				labelsExporter.saveLabels(tempLabelsFile);

				// open the dialog
				openSaveDialog(segmentationFrame, OmegaConstants.OPENBIS_LABEL_TYPE, tempDirectory);
			}
		});
		loadSegmentationAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// show the choose data JFrame
				JFrameOpenBisLoadData jFrameOpenBisLoadData = new JFrameOpenBisLoadData();

				// load the datasets
				jFrameOpenBisLoadData.loadDatasets(OmegaConstants.OPENBIS_LABEL_TYPE);

				// try to set the correct selected items in the JComboBoxs
				SPTExecutionInfoHandler sptExecutionInfoHandler = segmentationFrame.getSptExecutionInfo();

				if (sptExecutionInfoHandler != null)
				{
					String imageName = sptExecutionInfoHandler.getImageData().getImageName();
					String datasetName = sptExecutionInfoHandler.getImageData().getImageDatasetName();

					jFrameOpenBisLoadData.setSelectedItems(datasetName, imageName);
				}

				// display the frame
				jFrameOpenBisLoadData.setLocationRelativeTo(segmentationFrame);
				jFrameOpenBisLoadData.setVisible(true);

				// if data is not loaded return
				if (!jFrameOpenBisLoadData.isDataLoaded())
					return;
				else
				{
					// load the labels from the temp directory
					String selectedDirectory = jFrameOpenBisLoadData.getSaveDirectory();

					// set the labels
					List<Trajectory> trajectories = new ArrayList<Trajectory>();

					trajectories = segmentationFrame.getTrajectories();
					LabelsImporter labelsImporter = new LabelsFileImporter();
					labelsImporter.setFileName(selectedDirectory + System.getProperty("file.separator") + OmegaConstants.LABELS_FILE_NAME + "." + OmegaConstants.LABELS_FILES_EXTENSION);
					labelsImporter.setTrajectories(trajectories);
					labelsImporter.loadLabels();
					segmentationFrame.setCurrentTrajectory();
				}
			}
		});
	}

	private void undoOrRedo(String action)
	{
		UndoManager<int[]> currentUndoManager = segmentationFrame.getCurrentUndoManager();

		if (action.equals("UNDO"))
			currentUndoManager.undo();
		else
			currentUndoManager.redo();

		int[] stackLabelsClone = currentUndoManager.getCurrentState().clone();

		int[] labels = segmentationFrame.getCurrentTrajectory().getLabels();
		for (int i = 0; i < labels.length; i++)
			labels[i] = stackLabelsClone[i];

		// set menu status
		if (action.equals("UNDO"))
		{
			undoAction.setEnabled(currentUndoManager.isCanUndo());
			redoAction.setEnabled(true);
		}
		else
		{
			undoAction.setEnabled(true);
			redoAction.setEnabled(currentUndoManager.isCanRedo());
		}

		segmentationFrame.repaint();
	}

	private void openSaveDialog(SegmentationFrame segmentationFrame, String dataSetType, String directory)
	{
		// instantiate the JDialog in oder to save the information in openBIS
		JDialogOpenBisSaveData jDialogOpenBisSaveData = new JDialogOpenBisSaveData();
		jDialogOpenBisSaveData.setLocationRelativeTo(segmentationFrame);

		// set the information needed for saving
		jDialogOpenBisSaveData.setDataSetType(dataSetType);
		jDialogOpenBisSaveData.setDirectory(directory);

		// the SPTExecutionInfoHandler object with the image information
		SPTExecutionInfoHandler sptExecutionInfoHandler = segmentationFrame.getSptExecutionInfo();

		if (sptExecutionInfoHandler != null)
		{
			String imageName = sptExecutionInfoHandler.getImageData().getImageName();
			String datasetName = sptExecutionInfoHandler.getImageData().getImageDatasetName();

			if (imageName != null && imageName.length() > 0)
				jDialogOpenBisSaveData.setOmeroImageName(imageName);

			if (datasetName != null && datasetName.length() > 0)
				jDialogOpenBisSaveData.setOmeroDatasetName(datasetName);
		}

		jDialogOpenBisSaveData.setVisible(true);
	}
}
