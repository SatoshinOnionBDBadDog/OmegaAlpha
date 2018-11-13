package ch.supsi.omega.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.chartframes.JFrameMSSMSD;
import ch.supsi.omega.exploration.chartframes.JFrameSMSSvsD;
import ch.supsi.omega.exploration.chartframes.JFrameVelocities;
import ch.supsi.omega.exploration.common.JDialogResultsSelector;
import ch.supsi.omega.exploration.common.JPanelSeparator;
import ch.supsi.omega.exploration.common.imagedata.OmeroDataHelper;
import ch.supsi.omega.exploration.processing.FileIn;
import ch.supsi.omega.exploration.processing.Stats;
import ch.supsi.omega.gui.common.InfoLabel;
import ch.supsi.omega.tracking.stats.SPTStatsFileReader;

public class JPanelExploration extends JPanel {
	private static final long serialVersionUID = -7208162713765396355L;

	private static JPanelExploration INSTANCE = null;

	private MainFrame mainFrame = null;

	/**
	 * Tracking and trajectory: the directory with the images
	 */
	private String imagesDirectory = null;

	/**
	 * The current trajectories Stats[]
	 */
	private Stats[] stats = null;

	/**
	 * The MSD / MSS frames
	 */
	private final JFrameMSSMSD[] frames = new JFrameMSSMSD[2];

	/**
	 * Motion frequencies: the JDialog for choosing the datasets
	 */
	private JDialogResultsSelector jFrameResultsSelector = null;

	/**
	 * Motion frequencies: the HashMap containing the information for the
	 * JFreeChart frequencies charts.
	 */
	private HashMap<String, List<String>> motionFrequenciesDSMap = null;

	public JFrameMSSMSD[] getFrames() {
		return this.frames;
	}

	// gui
	private JPanel jPanelTitle0;
	private JLabel jLabelTitle0;
	private JButton jButtonTitle0;
	private JPanel jPanelMSD;
	private JButton jButtonMSD;
	private JLabel jLabelMSD;
	private JPanel jPanelTitle;
	private JLabel jLabelTitle;
	private JButton jButtonChooseDatasetForMotions;
	private JPanel jPanelTitleOne;
	private JLabel jLabelTitleOne;
	private JPanel jPanelMSSvSD;
	private JButton jButtonMSSvSD;
	private JLabel jLabelMSSvSD;
	private JPanel jPanelVelocities;
	private JButton jButtonVelocities;
	private JLabel jLabelVelocities;

	public JPanelExploration(final MainFrame mainFrame) {
		JPanelExploration.INSTANCE = this;
		this.mainFrame = mainFrame;
		this.initComponents();
	}

	private void initComponents() {
		// @formatter:off
		this.jPanelTitle0 = new JPanel();
		this.jLabelTitle0 = new JLabel();
		this.jButtonTitle0 = new JButton();
		this.jPanelMSD = new JPanel();
		this.jLabelMSD = new JLabel();
		this.jButtonMSD = new JButton();
		this.jPanelTitle = new JPanel();
		this.jLabelTitle = new JLabel();
		this.jButtonChooseDatasetForMotions = new JButton();
		this.jPanelTitleOne = new JPanel();
		this.jLabelTitleOne = new JLabel();
		this.jPanelMSSvSD = new JPanel();
		this.jButtonMSSvSD = new JButton();
		this.jLabelMSSvSD = new JLabel();
		this.jPanelVelocities = new JPanel();
		this.jButtonVelocities = new JButton();
		this.jLabelVelocities = new JLabel();
		// @formatter:on

		this.setLayout(new java.awt.GridLayout(21, 1));

		// =============
		// first title
		this.jLabelTitle0.setText("Motion analysis");
		this.jLabelTitle0.setPreferredSize(new Dimension(90, 20));
		this.jPanelTitle0.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.jPanelTitle0.add(this.jLabelTitle0);
		// jPanelTitle0.add(new InfoLabel(this, "TEXT HERE"));
		final JLabel mock2 = new JLabel();
		mock2.setPreferredSize(new Dimension(90, 20));
		this.jPanelTitle0.add(mock2);

		this.jButtonTitle0.setText("choose trajectories");
		this.jButtonTitle0.setPreferredSize(new Dimension(120, 24));
		this.jButtonTitle0.setFont(new java.awt.Font("Tahoma", 0, 10));
		this.jButtonTitle0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				final FileHelper fileHelper = new FileHelper();
				JPanelExploration.this.imagesDirectory = fileHelper.selectFile(
				        "trajectoriesOutDir",
				        OmegaConstants.INFO_SELECTDIR_TRACKS,
				        JFileChooser.DIRECTORIES_ONLY);
			}
		});
		this.jPanelTitle0.add(this.jButtonTitle0);

		this.add(this.jPanelTitle0);

		// velocities
		this.jPanelVelocities.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jButtonVelocities.setText("Velocity");
		this.jButtonVelocities
		        .setPreferredSize(new java.awt.Dimension(170, 25));
		this.jButtonVelocities.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				// if (velocitiesDSMap != null && velocitiesDSMap.size() > 0)
				if ((JPanelExploration.this.imagesDirectory != null)
				        && (JPanelExploration.this.imagesDirectory.length() > 0)) {
					// JFrameVelocities jFrameVelocities = new
					// JFrameVelocities(velocitiesDSMap,
					// mainFrame.getLocation().x + mainFrame.getWidth(),
					// mainFrame.getLocation().y);
					final JFrameVelocities jFrameVelocities = new JFrameVelocities(
					        JPanelExploration.this.imagesDirectory,
					        JPanelExploration.this.mainFrame.getLocation().x
					                + JPanelExploration.this.mainFrame
					                        .getWidth(),
					        JPanelExploration.this.mainFrame.getLocation().y);
					jFrameVelocities.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(JPanelExploration.INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		this.jPanelVelocities.add(this.jButtonVelocities);

		this.jLabelVelocities.setFont(new java.awt.Font("Tahoma", 0, 10));
		this.jLabelVelocities
		        .setText("Calculate and display istantaneous velocities of tracked particles");
		this.jPanelVelocities.add(this.jLabelVelocities);

		this.add(this.jPanelVelocities);

		// MSD
		this.jPanelMSD.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.jButtonMSD.setText("Diffusivity");
		this.jButtonMSD.setPreferredSize(new Dimension(170, 25));
		this.jButtonMSD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				JPanelExploration.this.displayChart();
			}
		});

		this.jPanelMSD.add(this.jButtonMSD);

		this.jLabelMSD.setFont(new Font("Tahoma", 0, 10));

		this.jPanelMSD
		        .add(new InfoLabel(
		                this,
		                "This tool allows you to determine the mode of motion of each individual particle in your\n"
		                        + "dataset. More specifically, it helps distinguish between Pure diffusion (i.e. random walk),\n"
		                        + "Anomalous diffusion (i.e. sub- and super-diffusion), Constrained motion (i.e. immobility)"
		                        + " and\nDirected motion (i.e. flow or ballistic motion). Please note that trajectories shorter than 20\n"
		                        + "frames are in general too short to reliably analyzed and classified.\n\n"
		                        + "To achieve this goal the tools employs two complementary methods:\n\n"
		                        + "1) Mean Square Displacement (MSD): compute the MSD of each particle as it moves along\n"
		                        + "its path further away from its origin and plot the values of MSD over time in a log log scale.\n"
		                        + "The slope of this curve is diagnostic of motion type.\n"
		                        + "References:\n"
		                        + "- Qian et al., 1991. Biophys J. 60:910–921.\n"
		                        + "- Saxton and Jacobson, 1997. Annu Rev Biophys Biomol Struct. 26:373–399.\n\n"
		                        + "2) Moment Scaling Spectrum (MSS): compute the moments of displacement (m) of orders\n"
		                        + "0 to for each trajectory. Evaluate how each moment scales with time by calculating a\n"
		                        + "scaling coefficient for each m. The plot of the scaling coefficient of each moment versus\n"
		                        + "its order is called the Moment Scaling Spectrum.\n"
		                        + "The slope of the MSS is diagnostic of motion type.\n"
		                        + "References:\n"
		                        + "-Ferrari et al., 2001. Physica D: Nonlinear Phenomena. 154:111–137.\n"
		                        + "-Ewers et al., 2005. Proc Natl Acad Sci USA. 102:15110–15115.\n\n"
		                        + "In both cases the slope of the resulting curve is diagnostic of the motion modality. In \n"
		                        + "addition, in the case of MSS the shape of the curve indicates whether the particle is\nmoving along a "
		                        + "homogenous (i.e. the curve will be straight) or along a discontinuous (i.e.\nthe curve will be bent "
		                        + "or 'kinked') path.\n\n"
		                        + "Motion type	           Slope MSS	           Slope of MSD vs t\n"
		                        + "Confined	                 0	                            0\n"
		                        + "Sub-diffusive	         < 0.5	                      < 1\n"
		                        + "Purely diffusive	      = 0.5	                     = 1\n"
		                        + "Super-diffusive	      > 0.5	                     > 1\n"
		                        + "Ballistic	                    = 1	                        >>1"));

		this.jLabelMSD.setText("Perform diffusion analysis by MSD and MSS");
		this.jPanelMSD.add(this.jLabelMSD);

		this.add(this.jPanelMSD);

		// =============

		// space
		this.add(new JPanelSeparator());

		// =============
		// second tiltle
		this.jLabelTitle.setText("Estimation of motion types");
		this.jLabelTitle.setPreferredSize(new Dimension(150, 20));
		this.jPanelTitle.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jPanelTitle.add(this.jLabelTitle);
		final JLabel mock = new JLabel();
		mock.setPreferredSize(new Dimension(30, 20));
		this.jPanelTitle.add(mock);

		this.jButtonChooseDatasetForMotions.setText("choose dataset");
		this.jButtonChooseDatasetForMotions.setPreferredSize(new Dimension(120,
		        24));
		this.jButtonChooseDatasetForMotions.setFont(new java.awt.Font("Tahoma",
		        0, 10));
		this.jButtonChooseDatasetForMotions
		        .addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(final ActionEvent evt) {
				        JPanelExploration.this.displayResultSelector((byte) 1);
			        }
		        });
		this.jPanelTitle.add(this.jButtonChooseDatasetForMotions);

		this.add(this.jPanelTitle);

		// analysis 1 panel
		this.jLabelTitleOne.setText("Analysis method I (Ewers et al. 2005):");
		this.jPanelTitleOne.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jPanelTitleOne.add(this.jLabelTitleOne);

		// add(jPanelTitleOne);

		this.jPanelMSSvSD.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		this.jButtonMSSvSD.setText("D/SMSS scatter");
		this.jButtonMSSvSD.setPreferredSize(new java.awt.Dimension(170, 25));
		this.jButtonMSSvSD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				if ((JPanelExploration.this.motionFrequenciesDSMap != null)
				        && (JPanelExploration.this.motionFrequenciesDSMap
				                .size() > 0)) {
					// check the minimun SNR for all the image in the datasets
					final double minSNR = SPTStatsFileReader
					        .getMinimunAverageSNR(JPanelExploration.this.motionFrequenciesDSMap);

					// if SNR is less than 2.0 warn the user
					// TODO: check if 2.0 is correct
					if (minSNR < 4.0) {
						final int answer = JOptionPane.showConfirmDialog(
						        JPanelExploration.INSTANCE, String.format(
						                OmegaConstants.WARNING_SNR_LESS_THAN_2,
						                minSNR), OmegaConstants.OMEGA_TITLE,
						        JOptionPane.YES_NO_OPTION);

						if (answer == JOptionPane.NO_OPTION)
							return;
					}

					final JFrameSMSSvsD jFrameMSSvsD = new JFrameSMSSvsD(
					        JPanelExploration.this.motionFrequenciesDSMap,
					        JPanelExploration.this.mainFrame.getLocation().x
					                + JPanelExploration.this.mainFrame
					                        .getWidth(),
					        JPanelExploration.this.mainFrame.getLocation().y);
					jFrameMSSvsD.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(JPanelExploration.INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		this.jPanelMSSvSD.add(this.jButtonMSSvSD);

		this.jPanelMSSvSD
		        .add(new InfoLabel(
		                this,
		                "This tool utilizes the analysis method employed by Ewers et al. 2005 to discriminate\n"
		                        + "between the motion behaviour of individual trajectories and trajectory segments and\n"
		                        + "to give a global picture describing the behaviour of the entire dataset.\n"
		                        + "In this method the diffusion constant (D) and the Slope of the MSS curve (Slope MSS)\n"
		                        + "is computed for each trajectory first. Subsequently the D and Slope MSS values from\n"
		                        + "each trajectory are plotted in a D vs Slope MSS 2D space and the position of the\n"
		                        + "resulting point in the graph gives information about the underlying motion pattern.\n"
		                        + "In other words the position of the point along the x-axis reflects the amount of\n"
		                        + "displacement while the position along the y-axis reflects the overall geometrical\n"
		                        + "shape of the trajectory."));

		this.jLabelMSSvSD.setFont(new java.awt.Font("Tahoma", 0, 10));
		this.jLabelMSSvSD
		        .setText("Scatter plot of overall D2 ans SMSS for all trajectories");
		this.jPanelMSSvSD.add(this.jLabelMSSvSD);

		this.add(this.jPanelMSSvSD);

		// space
		// add(new JPanel());
		// add(new JPanelSeparator());

		// =============

		/*
		 * add(new JPanel()); add(new JPanel()); add(new JPanel()); // info
		 * panel JPanel info = new JPanel(); info.setLayout(new
		 * java.awt.FlowLayout(java.awt.FlowLayout.RIGHT)); JButton infoButton =
		 * new JButton(); infoButton.setText("info");
		 * infoButton.setPreferredSize(new java.awt.Dimension(100, 25));
		 * infoButton.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * JOptionPane.showMessageDialog(null, "" +
		 * "Analysis method I is described in:\n" +
		 * "Ewers et al. Single-particle tracking of murine polyoma virus-like particles on live\n"
		 * +
		 * "cells and artificial membranes. Proc Natl Acad Sci USA (2005) vol. 102 (42) pp. 15110-5.\n\n"
		 * + "Analysis method II is described in:\n" +
		 * "Helmuth et al. A novel supervised trajectory segmentation algorithm identifies distinct\n"
		 * +
		 * "types of human adenovirus motion in host cells. J Struct Biol (2007) vol. 159 (3) pp. 347-358."
		 * , OmegaConstants.OMEGA_TITLE, JOptionPane.INFORMATION_MESSAGE); } });
		 * info.add(infoButton); add(info);
		 */
	}

	/**
	 * Display a new JFrame in order to let the User choose the DS he wants to
	 * analyze
	 * 
	 * @param mapID
	 */
	private void displayResultSelector(final byte mapID) {
		if (this.jFrameResultsSelector == null) {
			this.jFrameResultsSelector = new JDialogResultsSelector();
			this.jFrameResultsSelector.setLocationRelativeTo(this);
		}

		this.jFrameResultsSelector.setVisible(true);

		if (mapID == 1) {
			this.motionFrequenciesDSMap = this.jFrameResultsSelector.getMap();
			// if (mapID == 2)
			// velocitiesDSMap = jFrameResultsSelector.getMap();
		}
	}

	/**
	 * Displays the MSD / MSS charts
	 * 
	 * @param chartType
	 */
	private void displayChart() {
		if ((this.imagesDirectory != null)
		        && (this.imagesDirectory.length() > 0)) {
			try {
				// List<String> temp =
				// motionFrequenciesDSMap.entrySet().iterator().next().getValue();
				// imageDir = temp.get(0);
				this.processTracks(this.imagesDirectory);
			} catch (final Exception e) {
			}

			if (this.stats != null) {
				final JFrameMSSMSD jFrameMSD = new JFrameMSSMSD(this, "MSD",
				        this.stats, this.mainFrame.getLocation().x
				                + this.mainFrame.getWidth(),
				        this.mainFrame.getLocation().y);

				final JFrameMSSMSD jFrameMSS = new JFrameMSSMSD(this, "MSS",
				        this.stats, this.mainFrame.getLocation().x
				                + this.mainFrame.getWidth() + 600,
				        this.mainFrame.getLocation().y);

				this.frames[0] = jFrameMSD;
				this.frames[1] = jFrameMSS;

				// get the "image name"
				final String[] temp = this.imagesDirectory.split("\\\\");
				final String imageName = temp[temp.length - 1];
				jFrameMSD.setCurrentImageName(imageName);
				jFrameMSS.setCurrentImageName(imageName);

				jFrameMSD.setVisible(true);
				jFrameMSS.setVisible(true);
			}
		} else {
			JOptionPane
			        .showMessageDialog(JPanelExploration.INSTANCE,
			                OmegaConstants.INFO_SELECT_TRACKS_LABELS,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public Stats[] processTracks(final String trajectoriesDir)
	        throws IOException {
		// the trajectories folder
		final File dir = new File(trajectoriesDir);

		// filter the folder
		final FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".out");
			}
		};

		// get the trajectories
		final String[] children = dir.list(filter);

		// the number of the tracks
		final int tracksNumber = children.length;

		// istantiate the stats
		this.stats = new Stats[tracksNumber];

		// get the original size (if present)
		final OmeroDataHelper omeroDataHelper = new OmeroDataHelper(
		        this.mainFrame);
		final double[] originalSizes = omeroDataHelper
		        .getOriginalSizes(trajectoriesDir);

		for (int trajIx = 0; trajIx < tracksNumber; ++trajIx) {
			final double[][] trajectory = FileIn.readTrajectory(String.format(
			        "%s\\%s", trajectoriesDir, children[trajIx]));

			this.stats[trajIx] = new Stats(trajectory[0], trajectory[1],
			        originalSizes[2], null);
		}

		return this.stats;
	}
}
