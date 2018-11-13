package ch.supsi.omega.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.chartframes.JFrameIntensities;
import ch.supsi.omega.exploration.common.JFrameColorChooser;
import ch.supsi.omega.exploration.common.JPanelSeparator;
import ch.supsi.omega.review.ImageCanvas;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;
import ch.supsi.omega.tracking.parameters.SPTInformationFileLoader;

import com.galliva.gallibrary.GLogManager;

public class JPanelTV extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

	private static JPanelTV INSTANCE = null;

	private ImageCanvas imageCanvas = null;

	private Color trajectoryColor = Color.RED;

	private MainFrame mainFrame = null;

	/**
	 * Tracking and trajectory: the directory with the images
	 */
	private String imagesDirectory = null;

	private JButton jButtonTracks;
	private JPanel jPanelVisual;
	private JLabel jLabelVisual;
	private JPanel jPanelTracks;
	private JLabel jLabelTracks;
	private JButton jButtonColor;
	private JCheckBox jCheckBoxRandomColor;

	private JPanel jPanelTitle0;
	private JLabel jLabelTitle0;
	private JButton jButtonTitle0;
	private JPanel jPanelVelocities;
	private JButton jButtonVelocities;
	private JLabel jLabelVelocities;
	private JPanel jPanelVelocities2;
	private JButton jButtonVelocities2;
	private JLabel jLabelVelocities2;

	public Color getTrajectoryColor() {
		return trajectoryColor;
	}

	public JPanelTV(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		INSTANCE = this;
		initComponents();
	}

	private void initComponents() {
		setLayout(new java.awt.GridLayout(21, 1));

		jPanelTitle0 = new JPanel();
		jLabelTitle0 = new JLabel();
		jButtonTitle0 = new JButton();
		jButtonTracks = new JButton();
		jCheckBoxRandomColor = new JCheckBox("Random colors");
		jPanelVisual = new JPanel();
		jLabelVisual = new JLabel();
		jPanelTracks = new JPanel();

		jLabelTracks = new JLabel();
		jButtonColor = new JButton();

		jPanelVelocities = new JPanel();
		jButtonVelocities = new JButton();
		jLabelVelocities = new JLabel();
		jPanelVelocities2 = new JPanel();
		jButtonVelocities2 = new JButton();
		jLabelVelocities2 = new JLabel();

		jLabelVisual.setText("Trajectory visualization");
		jLabelVisual.setPreferredSize(new Dimension(200, 20));
		jPanelVisual.setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanelVisual.add(jLabelVisual);

		jButtonColor.setText("  choose color  ");
		jButtonColor.setPreferredSize(new Dimension(120, 24));
		jButtonColor.setFont(new java.awt.Font("Tahoma", 0, 10));
		jButtonColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				displayChooseColorDialog(evt);
			}
		});
		jPanelVisual.add(jButtonColor);

		jCheckBoxRandomColor.setSelected(true);
		jPanelVisual.add(jCheckBoxRandomColor);

		add(jPanelVisual);

		// tracks panel
		jPanelTracks
		        .setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jButtonTracks.setText("Display trajectories");
		jButtonTracks.setPreferredSize(new java.awt.Dimension(170, 25));
		jButtonTracks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonExploreImageActionPerformed(evt);
			}
		});
		jPanelTracks.add(jButtonTracks);

		jLabelTracks.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelTracks
		        .setText("Display the trajectories identified in the image of interest");
		jPanelTracks.add(jLabelTracks);

		add(jPanelTracks);
		// =============

		add(new JPanelSeparator());

		// second title
		jLabelTitle0.setText("Intensity profile");
		jLabelTitle0.setPreferredSize(new Dimension(105, 20));
		jPanelTitle0.setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanelTitle0.add(jLabelTitle0);
		// jPanelTitle0.add(new InfoLabel(this, "TEXT HERE"));
		JLabel mock2 = new JLabel();
		mock2.setPreferredSize(new Dimension(90, 20));
		jPanelTitle0.add(mock2);

		jButtonTitle0.setText("choose trajectories");
		jButtonTitle0.setPreferredSize(new Dimension(120, 24));
		jButtonTitle0.setFont(new java.awt.Font("Tahoma", 0, 10));
		jButtonTitle0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				FileHelper fileHelper = new FileHelper();
				imagesDirectory = fileHelper.selectFile("trajectoriesOutDir",
				        OmegaConstants.INFO_SELECTDIR_TRACKS,
				        JFileChooser.DIRECTORIES_ONLY);
			}
		});
		jPanelTitle0.add(jButtonTitle0);

		add(jPanelTitle0);

		// intensity 1
		jPanelVelocities.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		jButtonVelocities.setText("Mean Intensity");
		jButtonVelocities.setPreferredSize(new java.awt.Dimension(170, 25));
		jButtonVelocities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (imagesDirectory != null && imagesDirectory.length() > 0) {
					JFrameIntensities jFrameVelocities = new JFrameIntensities(
					        imagesDirectory, mainFrame.getLocation().x
					                + mainFrame.getWidth(), mainFrame
					                .getLocation().y, false);
					jFrameVelocities.setVisible(true);
				} else
					JOptionPane.showMessageDialog(INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
			}
		});
		jPanelVelocities.add(jButtonVelocities);

		jLabelVelocities.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelVelocities
		        .setText("Display the mean intensity of tracked particles over time");
		jPanelVelocities.add(jLabelVelocities);

		add(jPanelVelocities);

		// intensity 2
		jPanelVelocities2.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		jButtonVelocities2.setText("Integrated Intensity");
		jButtonVelocities2.setPreferredSize(new java.awt.Dimension(170, 25));
		jButtonVelocities2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (imagesDirectory != null && imagesDirectory.length() > 0) {
					JFrameIntensities jFrameVelocities = new JFrameIntensities(
					        imagesDirectory, mainFrame.getLocation().x
					                + mainFrame.getWidth(), mainFrame
					                .getLocation().y, true);
					jFrameVelocities.setVisible(true);
				} else
					JOptionPane.showMessageDialog(INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
			}
		});
		jPanelVelocities2.add(jButtonVelocities2);

		jLabelVelocities2.setFont(new java.awt.Font("Tahoma", 0, 10));
		jLabelVelocities2
		        .setText("Display the total intensity of tracked particles over time");
		jPanelVelocities2.add(jLabelVelocities2);

		add(jPanelVelocities2);
	}

	/**
	 * Displays a JDialog for choosing the color of the trajectories to be
	 * displayed on the image.
	 * 
	 * @param evt
	 */
	private void displayChooseColorDialog(ActionEvent evt) {
		JFrameColorChooser jFrameColorChooser = new JFrameColorChooser(this);
		jFrameColorChooser.setLocationRelativeTo(INSTANCE);
		jFrameColorChooser.setVisible(true);
		jCheckBoxRandomColor.setSelected(false);
	}

	private void jButtonExploreImageActionPerformed(ActionEvent evt) {
		if (imageCanvas != null) {
			FileHelper fileChooserHelper = new FileHelper();

			List<Trajectory> trajectories = fileChooserHelper
			        .loadTracks("trajectoriesOutDir");

			// get the used radius and set it into the drawing canvas
			try {
				String currentDirectory = fileChooserHelper
				        .getSelectedDirectory();
				String fn = currentDirectory
				        + System.getProperty("file.separator") + "SPT_info.txt";
				SPTInformationFileLoader sptInformationFileLoader = new SPTInformationFileLoader(
				        fn);
				sptInformationFileLoader.initLoader();
				SPTExecutionInfoHandler t = sptInformationFileLoader
				        .loadInformation();
				int radius = Integer.valueOf(t.getRadius());
				imageCanvas.setRadius(radius);
			} catch (Exception e) {
				GLogManager.log(e.toString(), Level.WARNING);
			}

			if (trajectories != null && !(trajectories.size() == 0)) {
				imageCanvas.setTrajectories(trajectories);

				// set the color, or choose a random one
				if (jCheckBoxRandomColor.isSelected())
					imageCanvas.setTrajectoryColor(null);
				else
					imageCanvas.setTrajectoryColor(trajectoryColor);

				imageCanvas.scaleTrajectories();
				imageCanvas.repaint();

				jButtonTracks.setEnabled(false);
			}
		} else
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.INFO_OPEN_REVIEW_MODULE,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
	}

	public void setCurrentCanvas(ImageCanvas imageCanvas) {
		this.imageCanvas = imageCanvas;
		jButtonTracks.setEnabled(true);
	}

	public void setTrajectoryColor(Color trajectoryColor) {
		this.trajectoryColor = trajectoryColor;
	}
}
