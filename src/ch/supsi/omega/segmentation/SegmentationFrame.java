package ch.supsi.omega.segmentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.UndoManager;
import ch.supsi.omega.review.ImageCanvas;
import ch.supsi.omega.segmentation.overview.SegmentationOverviewFrame;
import ch.supsi.omega.segmentation.pattern.LoadedPatternsFrame;
import ch.supsi.omega.segmentation.pattern.Pattern;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.segmentation.trajectory.TrajectoryNormalizer;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;

import com.galliva.gallibrary.GLogManager;

public class SegmentationFrame extends JFrame implements ChangeListener {
	private static final long serialVersionUID = -1919124157327436468L;

	private static final String TITLE = OmegaConstants.OMEGA_SEGMENTATION_TITLE;

	/**
	 * JMenu of this JFrame.
	 */
	private SegmentationMenu segmentationMenu = null;

	/**
	 * JPanel who displays the trajectories.
	 */
	private SegmentationPanel segmentationPanel = new SegmentationPanel(this);
	/**
	 * JSlider who lets the user switch between the trajectories.
	 */
	private JSlider trajectorySlider = new JSlider();

	/**
	 * JFrame who shows an overview of the current trajectory in the original
	 * image.
	 */
	private SegmentationOverviewFrame segmentationOverviewFrame = null;

	/**
	 * JFrame who shows all the loaded patterns.
	 */
	private LoadedPatternsFrame loadedPatternsFrame = null;

	/**
	 * The directory from where the trajectories where loaded.
	 */
	private String trajectoriesDirectory = null;

	/**
	 * ArrayList of the Trajectories.
	 */
	private List<Trajectory> trajectories = new ArrayList<Trajectory>();

	/**
	 * UndoManagers for the Trajectories.
	 */
	private List<UndoManager<int[]>> undoManagers = null;

	/**
	 * The Trajectory currently visualized.
	 */
	private Trajectory currentTrajectory = null;

	/**
	 * The UndoManager for the Trajectory currently visualized.
	 */
	private UndoManager<int[]> currentUndoManager = null;

	/**
	 * Info about the SPT execution who has generate this Trajectories.
	 */
	private SPTExecutionInfoHandler sptExecutionInfo = null;

	/**
	 * ArrayList with the loaded Patterns.
	 */
	private List<Pattern> patterns = new ArrayList<Pattern>();

	/**
	 * Enable or disable the segmentation in the segmentationPanel.
	 */
	private boolean canSegment = false;

	/**
	 * Listener of this frame.
	 */
	private KeyListener listener = new KeyListener() {
		public void keyPressed(KeyEvent evt) {
			char ch = evt.getKeyChar();

			switch (ch) {
			case 'n':
				segmentationPanel.setDrawFrames(!segmentationPanel
				        .isDrawFrames());
				break;
			case 'l':
				segmentationPanel.setDrawLine(!segmentationPanel.isDrawLine());
				break;
			case 'p':
				segmentationPanel.setDrawPoints(!segmentationPanel
				        .isDrawPoints());
				break;
			case 't':
				segmentationPanel.setDrawLabels(!segmentationPanel
				        .isDrawLabels());
				break;
			case 'u':
				segmentationPanel.undoMouseClicks();
				break;
			case '0':
				setLabels(0);
				break;
			case '1':
				setLabels(1);
				break;
			case '2':
				setLabels(2);
				break;
			case '3':
				setLabels(3);
				break;
			case '4':
				setLabels(4);
				break;
			}
			segmentationPanel.calculateDrawedTrajectory();
			segmentationPanel.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// no
			// override
			// here
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// no
			// override
			// here
		}
	};

	public SegmentationOverviewFrame getSegmentationOverviewFrame() {
		return segmentationOverviewFrame;
	}

	public String getTrajectoriesDirectory() {
		return trajectoriesDirectory;
	}

	public List<Trajectory> getTrajectories() {
		return trajectories;
	}

	public List<UndoManager<int[]>> getUndoManagers() {
		return undoManagers;
	}

	public Trajectory getCurrentTrajectory() {
		return currentTrajectory;
	}

	public UndoManager<int[]> getCurrentUndoManager() {
		return currentUndoManager;
	}

	public SPTExecutionInfoHandler getSptExecutionInfo() {
		return sptExecutionInfo;
	}

	public void setSptExecutionInfo(SPTExecutionInfoHandler sptExecutionInfo) {
		this.sptExecutionInfo = sptExecutionInfo;
	}

	public List<Pattern> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
	}

	public boolean isCanSegment() {
		return canSegment;
	}

	public void setCanSegment(boolean canSegment) {
		this.canSegment = canSegment;
	}

	/**
	 * Inizializes the Main OMEGA frame.
	 * 
	 * @param trajectories
	 *            a list of Trajectory
	 */
	public SegmentationFrame(String trajectoriesDirectory,
	        List<Trajectory> trajectories,
	        SPTExecutionInfoHandler sptExecutionInfo, List<Pattern> patterns) {
		this.trajectoriesDirectory = trajectoriesDirectory;
		this.trajectories = trajectories;
		this.sptExecutionInfo = sptExecutionInfo;
		this.patterns = patterns;

		undoManagers = new ArrayList<UndoManager<int[]>>(trajectories.size());

		initOmegaFrame();
		buildGUI();

		setCurrentTrajectory();
	}

	/**
	 * Inizializes the Main OMEGA frame.
	 * 
	 * @param trajectories
	 *            a list of Trajectory
	 * @param xPosition
	 *            the x position of the frame
	 * @param yPosition
	 *            the y position of the frame
	 */
	public SegmentationFrame(String trajectoriesDirectory,
	        List<Trajectory> trajectories,
	        SPTExecutionInfoHandler sptExecutionInfo, List<Pattern> patterns,
	        int xPosition, int yPosition) {
		this.setLocation(xPosition, yPosition);

		this.trajectoriesDirectory = trajectoriesDirectory;
		this.trajectories = trajectories;
		this.sptExecutionInfo = sptExecutionInfo;
		this.patterns = patterns;

		// for each Trajectory, create an UndoManager with initial state, and
		// add it to the undoManagers ArrayList
		undoManagers = new ArrayList<UndoManager<int[]>>();
		for (int i = 0; i < trajectories.size(); i++) {
			UndoManager<int[]> umt = new UndoManager<int[]>(trajectories.get(i)
			        .getLabels().clone());
			undoManagers.add(umt);
		}
		currentUndoManager = undoManagers.get(0);

		initOmegaFrame();
		buildGUI();

		// if we have the SPT info file, we can display the OverviewFrame
		if (sptExecutionInfo != null) {
			segmentationOverviewFrame = new SegmentationOverviewFrame(
			        getLocation().x + getWidth(), getLocation().y,
			        sptExecutionInfo);
			segmentationOverviewFrame.setVisible(true);
		}

		setCurrentTrajectory();

		setTracksOnImageCanvas();
	}

	private void initOmegaFrame() {
		setTitle(TITLE);

		// close event handling
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (segmentationOverviewFrame != null)
					segmentationOverviewFrame.dispose();
			}
		});

		// minimun size
		setMinimumSize(OmegaConstants.SEGMENTATION_FRAME_MINIMUM_SIZE);
		// current (preferred) size, calculated accordingly to the inside JPanel
		// size + "borders"
		Dimension preferred = new Dimension(
		        OmegaConstants.SEGMENTATION_PANEL_WIDTH
		                + OmegaConstants.JFRAME_ADDITIONAL_WIDTH,
		        OmegaConstants.SEGMENTATION_PANEL_HEIGHT
		                + OmegaConstants.JFRAME_ADDITIONAL_HEIGHT);
		setPreferredSize(preferred);

		trajectorySlider.setMinimum(1);
		trajectorySlider.setMaximum(trajectories.size());
		trajectorySlider.setValue(1);
		trajectorySlider.addChangeListener(this);
		trajectorySlider.addKeyListener(listener);
	}

	private void buildGUI() {
		JScrollPane jScrollPaneSegmentation = new JScrollPane(segmentationPanel);
		jScrollPaneSegmentation
		        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPaneSegmentation
		        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// getContentPane().add(segmentationPanel, BorderLayout.CENTER);
		getContentPane().add(jScrollPaneSegmentation, BorderLayout.CENTER);
		getContentPane().add(trajectorySlider, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	/**
	 * Sets the menu (to be done separatly cause of canSegment variable)
	 */
	public void setMenu() {
		segmentationMenu = new SegmentationMenu(this);
		setJMenuBar(segmentationMenu.getMenuBar());
	}

	private void setLabels(int label) {
		try {
			if (segmentationPanel.getClickNumber() == 2) {
				// check if a Pattern with ID=label (the pressed key) is loaded
				Pattern pattern = null;
				for (Pattern p : patterns) {
					if (p.getId() == label) {
						pattern = p;
						break;
					}
				}

				if (pattern == null) {
					JOptionPane.showMessageDialog(null,
					        OmegaConstants.INFO_NOPATTERNFORID,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
				} else {
					int[] labels = currentTrajectory.getLabels();

					for (int i = segmentationPanel.getIndexOne(); i < segmentationPanel
					        .getIndexTwo(); i++)
						labels[i] = pattern.getId();

					currentTrajectory.setLabels(labels);

					// undo manager
					currentUndoManager.addToStack(labels.clone());
					setUndoRedoMenuItemsStatus();

					segmentationPanel.undoMouseClicks();
				}
			}
		} catch (NullPointerException e) {
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.INFO_NOPATTERNFORID,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void stateChanged(ChangeEvent e) {
		setCurrentTrajectory();
		currentUndoManager = undoManagers.get(trajectorySlider.getValue() - 1);
		setUndoRedoMenuItemsStatus();

		setTracksOnImageCanvas();
	}

	private void setTracksOnImageCanvas() {
		if (ImageCanvas.getINSTANCE() != null) {
			ImageCanvas.getINSTANCE().setTrajectoryToDraw(
			        trajectorySlider.getValue() - 1);
			ImageCanvas.getINSTANCE().callRevalidate();
			ImageCanvas.getINSTANCE().repaint();
		}
	}

	public void setUndoRedoMenuItemsStatus() {
		segmentationMenu.getUndoAction().setEnabled(
		        currentUndoManager.isCanUndo());
		segmentationMenu.getRedoAction().setEnabled(
		        currentUndoManager.isCanRedo());
	}

	public void setCurrentTrajectory() {
		try {
			currentTrajectory = (Trajectory) (trajectories.get(trajectorySlider
			        .getValue() - 1).clone());
		} catch (CloneNotSupportedException e) {
			// nothing we can do...
		}

		if (currentTrajectory != null) {
			// clone the trajectory and set it in the OverviewFrame
			if (segmentationOverviewFrame != null) {
				try {
					segmentationOverviewFrame.getSegmentationOverviewPanel()
					        .setCurrentTrajectory(
					                (Trajectory) currentTrajectory.clone());
				} catch (Exception e) {
					segmentationOverviewFrame.getSegmentationOverviewPanel()
					        .setCurrentTrajectory(null);
					GLogManager.log(String.format("%s: %s",
					        "error in cloning the trajectory", e.toString()),
					        Level.SEVERE);
				}
			}

			// normalize the trajectory
			if (!currentTrajectory.getTrajectoryInformation().isNormalized())
				currentTrajectory = TrajectoryNormalizer
				        .normalizeTrajectory(currentTrajectory);
		}

		if (currentTrajectory != null) {
			segmentationPanel.setCurrentTrajectory(currentTrajectory);

			segmentationPanel.resetView();
			segmentationPanel.setClickNumber(0);
			segmentationPanel.setIndexOne(-1);
			segmentationPanel.setIndexTwo(-1);

			segmentationPanel.calculateDrawedTrajectory();

			// set the titles
			String imageName = "unknown image";
			if (sptExecutionInfo != null)
				imageName = sptExecutionInfo.getImageData().getImageName();

			// set the current segmentation file name if available
			String currentSegmentationFileName = "";
			try {
				if (segmentationMenu.getCurrentFileName().length() > 1)
					currentSegmentationFileName = " - "
					        + segmentationMenu.getCurrentFileName();
			} catch (Exception e) {
				// nothing to do...
			}

			setTitle(String.format("%s - %s - trajectory %d / %d%s", TITLE,
			        imageName, trajectorySlider.getValue(),
			        trajectorySlider.getMaximum(), currentSegmentationFileName));

			if (segmentationOverviewFrame != null)
				segmentationOverviewFrame.setTitle(String.format(
				        "%s %s - %d / %d", "overview - ", imageName,
				        trajectorySlider.getValue(),
				        trajectorySlider.getMaximum()));

			// repaint the "main" panel
			segmentationPanel.repaint();

			// repaint the panel in the overview frame
			if (segmentationOverviewFrame != null)
				segmentationOverviewFrame.getSegmentationOverviewPanel()
				        .repaint();
		}
	}

	/**
	 * Show the loaded patterns frame.
	 */
	public void showPatternsInfo() {
		if (loadedPatternsFrame == null) {
			loadedPatternsFrame = new LoadedPatternsFrame(patterns);
			loadedPatternsFrame.setLocationRelativeTo(this);
		}
		loadedPatternsFrame.addMotionTypes();
		loadedPatternsFrame.setVisible(true);
	}

	/**
	 * Close this frame.
	 */
	public void closeFrame() {
		this.dispose();
	}
}
