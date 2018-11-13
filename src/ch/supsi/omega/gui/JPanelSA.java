package ch.supsi.omega.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.exploration.chartframes.JFrameDSFrequencies;
import ch.supsi.omega.exploration.chartframes.JFrameImageFrequencies;
import ch.supsi.omega.exploration.common.JDialogResultsSelector;
import ch.supsi.omega.exploration.common.JPanelSeparator;
import ch.supsi.omega.exploration.processing.StudentTTest;
import ch.supsi.omega.gui.common.InfoLabel;

public class JPanelSA extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

	private static JPanelSA INSTANCE = null;

	private MainFrame mainFrame = null;

	private JPanel jPanelTitleTwo;
	private JLabel jLabelTitleTwo;
	private JPanel jPanelFrequencies;
	private JButton jButtonFrequencies;
	private JLabel jLabelFrequencies;
	private JPanel jPanelFrequenciesPerImage;
	private JButton jButtonFrequenciesPerImage;
	private JLabel jLabelFrequenciesPerImage;
	private JPanel jPanelAdditional;
	private JLabel jLabelAdditional;
	private JPanel jPanelTTest;
	private JButton jButtonTTest;
	private JLabel jLabelTTest;
	private JButton jButtonChooseDatasetForMotions;
	private JDialogResultsSelector jFrameResultsSelector = null;

	/**
	 * Motion frequencies: the HashMap containing the information for the
	 * JFreeChart frequencies charts.
	 */
	private HashMap<String, List<String>> motionFrequenciesDSMap = null;

	public JPanelSA(final MainFrame mainFrame) {
		JPanelSA.INSTANCE = this;
		this.mainFrame = mainFrame;

		this.initComponents();
	}

	private void initComponents() {
		this.setLayout(new java.awt.GridLayout(21, 1));

		this.jPanelTitleTwo = new JPanel();
		this.jLabelTitleTwo = new JLabel();
		this.jPanelFrequencies = new JPanel();
		this.jButtonFrequencies = new JButton();
		this.jLabelFrequencies = new JLabel();
		this.jPanelFrequenciesPerImage = new JPanel();
		this.jButtonFrequenciesPerImage = new JButton();
		this.jLabelFrequenciesPerImage = new JLabel();
		this.jPanelAdditional = new JPanel();
		this.jLabelAdditional = new JLabel();
		this.jPanelTTest = new JPanel();
		this.jButtonTTest = new JButton();
		this.jLabelTTest = new JLabel();
		this.jButtonChooseDatasetForMotions = new JButton();

		// analysis 2 panel
		this.jLabelTitleTwo.setText("Probability test");
		this.jPanelTitleTwo.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jPanelTitleTwo.add(this.jLabelTitleTwo);
		this.jPanelTitleTwo
		        .add(new InfoLabel(
		                this,
		                "Use this tool to test whether two trajectories datasets are significantly\ndistinguishable from each other."));
		final JLabel mock2 = new JLabel();
		mock2.setPreferredSize(new Dimension(40, 20));
		this.jPanelTitleTwo.add(mock2);

		this.jButtonChooseDatasetForMotions.setText("choose dataset");
		this.jButtonChooseDatasetForMotions.setPreferredSize(new Dimension(120,
		        24));
		this.jButtonChooseDatasetForMotions.setFont(new java.awt.Font("Tahoma",
		        0, 10));
		this.jButtonChooseDatasetForMotions
		        .addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(final ActionEvent evt) {
				        JPanelSA.this.displayResultSelector((byte) 1);
			        }
		        });
		this.jPanelTitleTwo.add(this.jButtonChooseDatasetForMotions);

		this.add(this.jPanelTitleTwo);

		// frequencies
		this.jPanelFrequencies.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		this.jButtonFrequencies.setText("Frequency by dataset");
		this.jButtonFrequencies
		        .setPreferredSize(new java.awt.Dimension(170, 25));
		this.jButtonFrequencies.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				if ((JPanelSA.this.motionFrequenciesDSMap != null)
				        && (JPanelSA.this.motionFrequenciesDSMap.size() > 0)) {
					final JFrameDSFrequencies jFrameDSFrequencies = new JFrameDSFrequencies(
					        JPanelSA.this.motionFrequenciesDSMap,
					        JPanelSA.this.mainFrame.getLocation().x
					                + JPanelSA.this.mainFrame.getWidth(),
					        JPanelSA.this.mainFrame.getLocation().y);
					jFrameDSFrequencies.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(JPanelSA.INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		this.jPanelFrequencies.add(this.jButtonFrequencies);

		this.jLabelFrequencies.setFont(new java.awt.Font("Tahoma", 0, 10));
		this.jLabelFrequencies
		        .setText("Compare the frequency of each identified motion type across datasets");
		this.jPanelFrequencies.add(this.jLabelFrequencies);

		this.add(this.jPanelFrequencies);

		// frequencies per image
		this.jPanelFrequenciesPerImage.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		this.jButtonFrequenciesPerImage.setText("Frequency by image");
		this.jButtonFrequenciesPerImage
		        .setPreferredSize(new java.awt.Dimension(170, 25));
		this.jButtonFrequenciesPerImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				if ((JPanelSA.this.motionFrequenciesDSMap != null)
				        && (JPanelSA.this.motionFrequenciesDSMap.size() > 0)) {
					final JFrameImageFrequencies jFrameImageFrequencies = new JFrameImageFrequencies(
					        JPanelSA.this.motionFrequenciesDSMap,
					        JPanelSA.this.mainFrame.getLocation().x
					                + JPanelSA.this.mainFrame.getWidth(),
					        JPanelSA.this.mainFrame.getLocation().y);
					jFrameImageFrequencies.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(JPanelSA.INSTANCE,
					        OmegaConstants.INFO_NO_DATASET,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		this.jPanelFrequenciesPerImage.add(this.jButtonFrequenciesPerImage);

		this.jLabelFrequenciesPerImage.setFont(new java.awt.Font("Tahoma", 0,
		        10));
		this.jLabelFrequenciesPerImage
		        .setText("Compare the frequency of each identified motion type across images");
		this.jPanelFrequenciesPerImage.add(this.jLabelFrequenciesPerImage);

		this.add(this.jPanelFrequenciesPerImage);
		// =============

		// space
		this.add(new JPanelSeparator());

		// =============
		// additional panel
		this.jLabelAdditional.setText("Significance tests");
		this.jLabelAdditional.setPreferredSize(new Dimension(200, 20));
		this.jPanelAdditional.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.jPanelAdditional.add(this.jLabelAdditional);

		/*
		 * jButtonChooseDatasetForVelocites.setText("choose dataset");
		 * jButtonChooseDatasetForVelocites.setPreferredSize(new Dimension(120,
		 * 24)); jButtonChooseDatasetForVelocites.setFont(new
		 * java.awt.Font("Tahoma", 0, 10));
		 * jButtonChooseDatasetForVelocites.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent evt) {
		 * displayResultSelector((byte) 2); } });
		 * jPanelAdditional.add(jButtonChooseDatasetForVelocites);
		 */

		this.add(this.jPanelAdditional);

		// T-Test
		this.jPanelTTest.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jButtonTTest.setText("Student's T test");
		this.jButtonTTest.setPreferredSize(new java.awt.Dimension(170, 25));
		this.jButtonTTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				final FileHelper fileHelper = new FileHelper();
				String datasetOnePath = null;
				String datasetTwoPath = null;

				datasetOnePath = fileHelper.selectFile("trajectoriesOutDir",
				        OmegaConstants.INFO_SELECTDIR_TRACKS,
				        JFileChooser.DIRECTORIES_ONLY);

				if (datasetOnePath == null)
					return;

				datasetTwoPath = fileHelper.selectFile("trajectoriesOutDir",
				        OmegaConstants.INFO_SELECTDIR_TRACKS,
				        JFileChooser.DIRECTORIES_ONLY);

				if (datasetTwoPath == null)
					return;

				final StudentTTest studentTTest = new StudentTTest(
				        JPanelSA.this.mainFrame, datasetOnePath, datasetTwoPath);
				studentTTest.start();
			}
		});
		this.jPanelTTest.add(this.jButtonTTest);

		this.jLabelTTest.setFont(new java.awt.Font("Tahoma", 0, 10));
		this.jLabelTTest
		        .setText("Determine the statistical significance of the difference between datasets");
		this.jPanelTTest.add(this.jLabelTTest);

		this.add(this.jPanelTTest);
	}

	private void displayResultSelector(final byte mapID) {
		if (this.jFrameResultsSelector == null) {
			this.jFrameResultsSelector = new JDialogResultsSelector();
			this.jFrameResultsSelector.setLocationRelativeTo(this);
		}

		this.jFrameResultsSelector.setVisible(true);

		if (mapID == 1) {
			this.motionFrequenciesDSMap = this.jFrameResultsSelector.getMap();
		}
	}
}
