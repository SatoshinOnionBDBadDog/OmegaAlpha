package ch.supsi.omega.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ch.supsi.omega.common.FileHelper;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.dll.SPTCaller;
import ch.supsi.omega.exploration.common.JPanelSeparator;
import ch.supsi.omega.gui.common.InfoLabel;
import ch.supsi.omega.gui.common.SPTParameterPanels;
import ch.supsi.omega.omero.Gateway;
import ch.supsi.omega.segmentation.SegmentationFrame;
import ch.supsi.omega.segmentation.trajectory.Trajectory;
import ch.supsi.omega.tracking.parameters.ImageDataHandler;
import ch.supsi.omega.tracking.parameters.OmeroParametersHandler;
import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;
import ch.supsi.omega.tracking.parameters.SPTInformationFileLoader;
import ch.supsi.omega.tracking.parameters.SPTInformationFileWriter;
import ch.supsi.omega.tracking.parameters.SPTInformationLoader;
import ch.supsi.omega.tracking.parameters.SPTInformationWriter;
import ch.supsi.omega.tracking.stats.JFrameStats;
import ch.supsi.omega.tracking.stats.SPTStatsFileWriter;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class JPanelSPT extends JPanel {
	private static final long serialVersionUID = 9018181537972719399L;

	private MainFrame mainFrame = null;

	private static GConfigurationManager configurationManager = new GConfigurationManager();

	private OmeroParametersHandler sptParametersHandler = null;

	public OmeroParametersHandler getSptParametersHandler() {
		return this.sptParametersHandler;
	}

	public void setSptParametersHandler(
	        final OmeroParametersHandler sptParametersHandler) {
		this.sptParametersHandler = sptParametersHandler;
	}

	public JPanelSPT(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		try {
			JPanelSPT.configurationManager.setIniFile(OmegaConstants.INIFILE);
		} catch (final Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		this.initComponents();
	}

	private SPTParameterPanels width;
	private SPTParameterPanels height;
	private SPTParameterPanels time;

	private JPanel jPanelTitle;
	private JPanel jPanel2;
	private JPanel jPanelChannel;
	private JPanel jPanelPlane;
	private JPanel jPanelRadius;
	private JPanel jPanelRange;
	private JPanel jPanelMinValue;
	private JPanel jPanelMaxValue;
	private JPanel jPanelCutOff;
	private JPanel jPanelPercentile;
	private JPanel jPanelDisplacement;
	private JPanel jPanelPointNumber;
	private JPanel jPanelEmpty;
	private JPanel jPanelResult;
	private JPanel jPanelStatus;
	private JPanel jPanelStatusDetails;
	private JPanel jPanelTrack;
	private JButton jButtonDisplayTracks;
	private JButton jButtonTrack;
	private JTextField jComboBoxRadius;
	private JTextField jComboBoxCutOff;
	private JTextField jComboBoxPercentile;
	private JTextField jComboBoxDisplacement;
	private JTextField jComboBoxLinkRange;
	private JTextField jComboBoxPointNumber;
	private JLabel jLabelPointNumber;
	private JLabel jLabelPointNumberRight;
	private JLabel jLabelEmpty;
	private JCheckBox jCheckBoxEmpty;
	private JLabel jLabelRange;
	private JLabel jLabelMinValue;
	private JLabel jLabelMaxValue;
	private JLabel jLabelCutOff;
	private JLabel jLabelPercentile;
	private JLabel jLabelDisplacement;
	private JLabel jLabelRadius;
	private JLabel jLabel5;
	private JLabel jLabelStatusTitle;
	private JLabel jLabelStatus;
	private JLabel jLabelStatusDetails;
	private JLabel jLabel8;
	private JLabel jLabelTitle;
	private JTextField jTextFieldChannel;
	private JTextField jTextFieldPlane;
	private JTextField jTextFieldMinValue;
	private JTextField jTextFieldMaxValue;

	private void initComponents() {
		this.jPanelTitle = new javax.swing.JPanel();
		this.jLabelTitle = new javax.swing.JLabel();
		this.jPanelChannel = new javax.swing.JPanel();
		this.jLabel5 = new javax.swing.JLabel();
		this.jTextFieldChannel = new javax.swing.JTextField();
		this.jPanelPlane = new javax.swing.JPanel();
		this.jLabel8 = new javax.swing.JLabel();
		this.jTextFieldPlane = new javax.swing.JTextField();
		this.jPanelRange = new javax.swing.JPanel();
		this.jPanelMinValue = new javax.swing.JPanel();
		this.jPanelMaxValue = new javax.swing.JPanel();
		this.jPanelCutOff = new javax.swing.JPanel();
		this.jPanelPercentile = new javax.swing.JPanel();
		this.jPanelDisplacement = new javax.swing.JPanel();
		this.jLabelRange = new javax.swing.JLabel();
		this.jLabelMinValue = new javax.swing.JLabel();
		this.jLabelMaxValue = new javax.swing.JLabel();
		this.jComboBoxLinkRange = new javax.swing.JTextField();
		this.jTextFieldMinValue = new javax.swing.JTextField();
		this.jTextFieldMaxValue = new javax.swing.JTextField();
		this.jPanelRadius = new javax.swing.JPanel();
		this.jLabelRadius = new javax.swing.JLabel();
		this.jLabelCutOff = new javax.swing.JLabel();
		this.jLabelPercentile = new javax.swing.JLabel();
		this.jLabelDisplacement = new javax.swing.JLabel();
		this.jComboBoxRadius = new javax.swing.JTextField();
		this.jComboBoxCutOff = new javax.swing.JTextField();
		this.jComboBoxPercentile = new javax.swing.JTextField();
		this.jComboBoxDisplacement = new javax.swing.JTextField();
		this.jPanelPointNumber = new javax.swing.JPanel();
		this.jLabelPointNumber = new javax.swing.JLabel();
		this.jComboBoxPointNumber = new javax.swing.JTextField();
		this.jLabelPointNumberRight = new javax.swing.JLabel();
		this.jPanelEmpty = new javax.swing.JPanel();
		this.jCheckBoxEmpty = new javax.swing.JCheckBox();
		this.jLabelEmpty = new javax.swing.JLabel();
		this.jPanelTrack = new javax.swing.JPanel();
		this.jButtonTrack = new javax.swing.JButton();
		this.jPanelResult = new javax.swing.JPanel();
		this.jButtonDisplayTracks = new javax.swing.JButton();
		this.jPanel2 = new javax.swing.JPanel();
		this.jPanelStatus = new javax.swing.JPanel();
		this.jLabelStatusTitle = new javax.swing.JLabel();
		this.jLabelStatus = new javax.swing.JLabel();
		this.jPanelStatusDetails = new javax.swing.JPanel();
		this.jLabelStatusDetails = new javax.swing.JLabel();

		this.setLayout(new java.awt.GridLayout(24, 1));

		// title
		this.jPanelTitle.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelTitle.setText("Image details");
		this.jLabelTitle.setPreferredSize(new java.awt.Dimension(80, 25));
		this.jPanelTitle.add(this.jLabelTitle);
		this.add(this.jPanelTitle);

		this.width = new SPTParameterPanels("Image width (X):", "pixels, ",
		        Greeks.MU + "m",
		        String.format("[pixels size (%sm):", Greeks.MU),
		        "The image's width.");
		this.add(this.width);
		this.height = new SPTParameterPanels("Image height (Y):", "pixels, ",
		        Greeks.MU + "m",
		        String.format("[pixels size (%sm):", Greeks.MU),
		        "The image's height.");
		this.add(this.height);
		this.time = new SPTParameterPanels("Image frames (T):", "frames, ",
		        "s", "[avg interval (s):", "The image's frame number.");
		this.add(this.time);

		// image plane
		this.jPanelPlane.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabel8.setText("Image plane (Z):");
		this.jLabel8.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelPlane.add(this.jLabel8);
		this.jTextFieldPlane.setEnabled(false);
		this.jTextFieldPlane.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTextFieldPlane.setPreferredSize(new java.awt.Dimension(40, 25));
		this.jPanelPlane.add(this.jTextFieldPlane);
		this.jPanelPlane.add(new InfoLabel(this,
		        "The image plane that will be processed ('Z' in OMERO)."));
		this.add(this.jPanelPlane);
		// image channel
		this.jPanelChannel.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabel5.setText("Image channel (C):");
		this.jLabel5.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelChannel.add(this.jLabel5);
		this.jTextFieldChannel.setEnabled(false);
		this.jTextFieldChannel.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTextFieldChannel.setPreferredSize(new java.awt.Dimension(40, 25));
		this.jPanelChannel.add(this.jTextFieldChannel);
		this.jPanelChannel.add(new InfoLabel(this,
		        "The image channel that will be processed ('C' in OMERO)."));
		this.add(this.jPanelChannel);

		// min value
		this.jPanelMinValue.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelMinValue.setText("Minimum value:");
		this.jLabelMinValue.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelMinValue.add(this.jLabelMinValue);
		this.jPanelMinValue.setEnabled(false);
		this.jTextFieldMinValue.setText("0");
		this.jTextFieldMinValue.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTextFieldMinValue.setEnabled(false);
		this.jTextFieldMinValue
		        .setPreferredSize(new java.awt.Dimension(50, 25));
		this.jTextFieldMinValue.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent e) {
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				try {
					if (JPanelSPT.this.jTextFieldMinValue.getText().length() > 0) {
						Integer.valueOf(JPanelSPT.this.jTextFieldMinValue
						        .getText());
					}
				} catch (final NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,
					        OmegaConstants.ERROR_SPT_MAX_VALUE,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.ERROR_MESSAGE);
					JPanelSPT.this.jTextFieldMinValue.setText("0");
				}
			}

			@Override
			public void keyPressed(final KeyEvent e) {
			}
		});
		this.jPanelMinValue.add(this.jTextFieldMinValue);
		this.jPanelMinValue
		        .add(new InfoLabel(this,
		                "Minimum intensity value with the image sequence under study."));
		this.add(this.jPanelMinValue);

		// max value
		this.jPanelMaxValue.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelMaxValue.setText("Maximum value:");
		this.jLabelMaxValue.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelMaxValue.add(this.jLabelMaxValue);
		this.jPanelMaxValue.setEnabled(false);
		this.jTextFieldMaxValue.setText("65535");
		this.jTextFieldMaxValue.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTextFieldMaxValue.setEnabled(false);
		this.jTextFieldMaxValue
		        .setPreferredSize(new java.awt.Dimension(50, 25));
		this.jTextFieldMaxValue.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent e) {
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				try {
					if (JPanelSPT.this.jTextFieldMaxValue.getText().length() > 0) {
						Integer.valueOf(JPanelSPT.this.jTextFieldMaxValue
						        .getText());
					}
				} catch (final NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,
					        OmegaConstants.ERROR_SPT_MAX_VALUE,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.ERROR_MESSAGE);
					JPanelSPT.this.jTextFieldMaxValue.setText("65535");
				}
			}

			@Override
			public void keyPressed(final KeyEvent e) {
			}
		});
		this.jPanelMaxValue.add(this.jTextFieldMaxValue);
		this.jPanelMaxValue
		        .add(new InfoLabel(this,
		                "Maximum intensity value with the image sequence under study."));
		this.add(this.jPanelMaxValue);

		// space
		this.add(new JPanelSeparator());

		// new title
		final JPanel spt1 = new JPanel();
		final JLabel spt2 = new JLabel();

		spt1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		spt2.setText("SPT - detection parameters");
		spt2.setPreferredSize(new java.awt.Dimension(160, 25));
		spt1.add(spt2);
		spt1.add(new InfoLabel(
		        this,
		        "For a full ParticleTracker tutorial, please refer to:\nhttp://courses.washington.edu/me333afe/ImageJ_tutorial.html"));
		this.add(spt1);

		// radius
		this.jPanelRadius.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelRadius.setText("Radius:");
		this.jLabelRadius.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelRadius.add(this.jLabelRadius);
		// jComboBoxRadius.setModel(new javax.swing.DefaultComboBoxModel(new
		// String[] { "1", "2", "3", "4", "5", "6" }));
		this.jComboBoxRadius.setText("3");
		this.jComboBoxRadius.setPreferredSize(new java.awt.Dimension(50, 25));
		this.jComboBoxRadius.setEnabled(false);
		this.jPanelRadius.add(this.jComboBoxRadius);
		this.jPanelRadius
		        .add(new InfoLabel(
		                this,
		                "Approximate radius of the particles in the images in units of pixels.\n"
		                        + "The value should be slightly larger than the visible particle radius,\n"
		                        + "but smaller than the smallest inter-particle separation."));
		this.add(this.jPanelRadius);
		// cut-off
		this.jPanelCutOff.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelCutOff.setText("Cut-off:");
		this.jLabelCutOff.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelCutOff.add(this.jLabelCutOff);
		// jComboBoxCutOff.setModel(new javax.swing.DefaultComboBoxModel(new
		// String[] { "0.0", "1.0", "2.0", "3.0", "4.0", "5.0" }));
		this.jComboBoxCutOff.setText("3.0");
		this.jComboBoxCutOff.setPreferredSize(new java.awt.Dimension(50, 25));
		this.jComboBoxCutOff.setEnabled(false);
		this.jPanelCutOff.add(this.jComboBoxCutOff);
		this.jPanelCutOff
		        .add(new InfoLabel(
		                this,
		                "The score cut-off for the discrimination of non-particles.\n"
		                        + "The higher the number in the cut-off field the more suspicious the\n"
		                        + "algorithm is of false particles and therefore the fewer particles\n"
		                        + "will be detected."));
		this.add(this.jPanelCutOff);
		// percentile
		this.jPanelPercentile.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelPercentile.setText("Percentile:");
		this.jLabelPercentile.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelPercentile.add(this.jLabelPercentile);
		// jComboBoxPercentile.setModel(new javax.swing.DefaultComboBoxModel(new
		// String[] { "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8",
		// "0.9", "1.0" }));
		this.jComboBoxPercentile.setText("0.1");
		this.jComboBoxPercentile
		        .setPreferredSize(new java.awt.Dimension(50, 25));
		this.jComboBoxPercentile.setEnabled(false);
		this.jPanelPercentile.add(this.jComboBoxPercentile);
		this.jPanelPercentile
		        .add(new InfoLabel(
		                this,
		                "The percentile (r) that determines which bright pixels are accepted as particles.\n"
		                        + "All local maxima in the upper rth percentile of the image intensity distribution are\n"
		                        + "considered candidate particles. The higher the number in the percentile field - the\n"
		                        + "more particles will be detected.”"));
		this.add(this.jPanelPercentile);
		// particle detection
		/*
		 * JPanel PDp = new JPanel(); PDp.setLayout(new
		 * java.awt.FlowLayout(java.awt.FlowLayout.LEFT)); JLabel PDl = new
		 * JLabel(); PDl.setText("Particle detection:");
		 * PDp.setPreferredSize(new java.awt.Dimension(400, 25)); PDp.add(PDl);
		 * add(PDp);
		 */

		// new title
		final JPanel spt3 = new JPanel();
		final JLabel spt4 = new JLabel();

		spt3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		spt4.setText("SPT - linking parameters");
		spt4.setPreferredSize(new java.awt.Dimension(200, 25));
		spt3.add(spt4);
		this.add(spt3);

		// displacement
		this.jPanelDisplacement.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelDisplacement.setText("Displacement:");
		this.jLabelDisplacement
		        .setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelDisplacement.add(this.jLabelDisplacement);
		// jComboBoxDisplacement.setModel(new
		// javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4",
		// "5", "10", "20", "50" }));
		this.jComboBoxDisplacement.setText("10");
		this.jComboBoxDisplacement.setPreferredSize(new java.awt.Dimension(50,
		        25));
		this.jComboBoxDisplacement.setEnabled(false);
		this.jPanelDisplacement.add(this.jComboBoxDisplacement);
		this.jPanelDisplacement
		        .add(new InfoLabel(
		                this,
		                "The maximum number of pixels a particle is allowed to move between two succeeding frames.\n"
		                        + "Generally, in a movie where particles travel long distance from one frame to the other - a\n"
		                        + "large displacement value should be entered."));
		this.add(this.jPanelDisplacement);
		// link range
		this.jPanelRange.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelRange.setText("Link range:");
		this.jLabelRange.setPreferredSize(new java.awt.Dimension(110, 25));
		this.jPanelRange.add(this.jLabelRange);
		// jComboBoxLinkRange.setModel(new javax.swing.DefaultComboBoxModel(new
		// String[] { "1", "5", "10", "13" }));
		this.jComboBoxLinkRange.setText("2");
		this.jComboBoxLinkRange
		        .setPreferredSize(new java.awt.Dimension(50, 25));
		this.jComboBoxLinkRange.setEnabled(false);
		this.jPanelRange.add(this.jComboBoxLinkRange);
		this.jPanelRange
		        .add(new InfoLabel(
		                this,
		                "The number of subsequent frames that is taken into account to determine the optimal correspondence matching.\n"
		                        + "The link range value is mainly designed to overcome temporary occlusion as well as particle appearance and\n"
		                        + "disappearance from the image region. The larger the values the more successive image frames will be taken\n"
		                        + "into consideration before deciding which linking option is best."));
		this.add(this.jPanelRange);

		this.add(new JPanelSeparator());

		// number of point
		this.jPanelPointNumber.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelPointNumber.setText("Save trajectories with at least");
		this.jLabelPointNumber
		        .setPreferredSize(new java.awt.Dimension(180, 25));
		this.jPanelPointNumber.add(this.jLabelPointNumber);
		// jComboBoxPointNumber.setModel(new
		// javax.swing.DefaultComboBoxModel(new String[] { "1", "10", "20",
		// "50", "100", }));
		this.jComboBoxPointNumber.setText("10");
		this.jComboBoxPointNumber.setPreferredSize(new java.awt.Dimension(50,
		        25));
		this.jComboBoxPointNumber.setEnabled(false);
		this.jPanelPointNumber.add(this.jComboBoxPointNumber);
		this.jLabelPointNumberRight.setText("  points");
		this.jLabelPointNumberRight.setPreferredSize(new java.awt.Dimension(45,
		        25));
		this.jPanelPointNumber.add(this.jLabelPointNumberRight);
		this.add(this.jPanelPointNumber);
		// empty dir
		this.jPanelEmpty.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelEmpty.setText("Clear the output directory before running");
		this.jLabelEmpty.setPreferredSize(new java.awt.Dimension(236, 25));
		this.jPanelEmpty.add(this.jLabelEmpty);
		this.jCheckBoxEmpty.setEnabled(false);
		this.jPanelEmpty.add(this.jCheckBoxEmpty);
		this.add(this.jPanelEmpty);
		// RUN
		this.jPanelTrack.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jButtonTrack.setText("Run SPT");
		this.jButtonTrack.setEnabled(false);
		this.jButtonTrack.setPreferredSize(new java.awt.Dimension(145, 25));
		this.jButtonTrack
		        .addActionListener(new java.awt.event.ActionListener() {
			        @Override
			        public void actionPerformed(
			                final java.awt.event.ActionEvent evt) {
				        JPanelSPT.this.jButtonTrackActionPerformed(evt);
			        }
		        });
		this.jPanelTrack.add(this.jButtonTrack);
		this.add(this.jPanelTrack);
		// RESULT
		this.jPanelResult.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jButtonDisplayTracks.setText("Display tracks");
		this.jButtonDisplayTracks.setEnabled(false);
		this.jButtonDisplayTracks.setPreferredSize(new java.awt.Dimension(145,
		        25));
		this.jButtonDisplayTracks
		        .addActionListener(new java.awt.event.ActionListener() {
			        @Override
			        public void actionPerformed(
			                final java.awt.event.ActionEvent evt) {
				        JPanelSPT.this.jButtonDisplayTracksActionPerformed(evt);
			        }
		        });
		// jPanelResult.add(jButtonDisplayTracks);
		this.add(this.jPanelResult);
		this.jPanel2.setPreferredSize(new java.awt.Dimension(400, 25));
		final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
		        this.jPanel2);
		this.jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
		        Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 33,
		        Short.MAX_VALUE));
		// add(jPanel2);
		// status
		this.jPanelStatus.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelStatusTitle.setText("Status:");
		this.jLabelStatusTitle.setPreferredSize(new java.awt.Dimension(50, 25));
		this.jPanelStatus.add(this.jLabelStatusTitle);
		this.jLabelStatus.setText("please select at least 1 image");
		this.jLabelStatus.setPreferredSize(new java.awt.Dimension(300, 25));
		this.jPanelStatus.add(this.jLabelStatus);
		this.add(this.jPanelStatus);
		this.jPanelStatusDetails.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));
		this.jLabelStatusDetails.setPreferredSize(new java.awt.Dimension(245,
		        25));
		this.jPanelStatusDetails.add(this.jLabelStatusDetails);
		this.add(this.jPanelStatusDetails);
		// info panel
		final JPanel info = new JPanel();
		info.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		final JButton infoButton = new JButton();
		infoButton.setText("info");
		infoButton.setPreferredSize(new java.awt.Dimension(100, 25));
		infoButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				JOptionPane
				        .showMessageDialog(
				                null,
				                ""
				                        + "The algorithm utilized in this program is described in:\n"
				                        + "I. F. Sbalzarini and P. Koumoutsakos. Feature Point Tracking and Trajectory Analysis\n"
				                        + "for Video Imaging in Cell Biology, "
				                        + "Journal of Structural Biology 151(2):182-195, 2005.\n\n"
				                        + "For more information:\nhttp://www.mosaic.ethz.ch/Downloads/ParticleTrackerCSourceAndClient",
				                OmegaConstants.OMEGA_TITLE,
				                JOptionPane.INFORMATION_MESSAGE);
			}
		});
		info.add(infoButton);
		// add(info);

		this.jComboBoxRadius.setHorizontalAlignment(SwingConstants.CENTER);
		this.jComboBoxCutOff.setHorizontalAlignment(SwingConstants.CENTER);
		this.jComboBoxPercentile.setHorizontalAlignment(SwingConstants.CENTER);
		this.jComboBoxDisplacement
		        .setHorizontalAlignment(SwingConstants.CENTER);
		this.jComboBoxLinkRange.setHorizontalAlignment(SwingConstants.CENTER);
		this.jComboBoxPointNumber.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * Displays the found trajectories.
	 */
	private void jButtonDisplayTracksActionPerformed(
	        final java.awt.event.ActionEvent evt) {
		final FileHelper fileChooserHelper = new FileHelper();

		final List<Trajectory> trajectories = fileChooserHelper
		        .loadTracks("trajectoriesOutDir");

		if (trajectories != null) {
			if ((trajectories != null) && !(trajectories.size() == 0)) {
				// the directory selected by the user
				final String selectedDirectory = fileChooserHelper
				        .getSelectedDirectory();

				// load the SPT execution information
				SPTExecutionInfoHandler sptExecutionInfo = null;
				try {
					final SPTInformationLoader sptInformationLoader = new SPTInformationFileLoader(
					        selectedDirectory
					                + System.getProperty("file.separator")
					                + OmegaConstants.SPT_INFORMATION_FILE);
					sptInformationLoader.initLoader();
					sptExecutionInfo = sptInformationLoader.loadInformation();
					sptInformationLoader.closeLoader();
				} catch (final Exception e) {
					// already handled in the SPTInformationLoader class
				}

				final SegmentationFrame segmentationFrame = new SegmentationFrame(
				        selectedDirectory, trajectories, sptExecutionInfo,
				        null, this.mainFrame.getLocation().x
				                + this.mainFrame.getWidth(),
				        this.mainFrame.getLocation().y);
				segmentationFrame.setCanSegment(false);
				segmentationFrame.setMenu();
			} else {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.INFO_LOADTRAJECTORIES,
				        OmegaConstants.OMEGA_TITLE,
				        JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Enables the status of the controls.
	 */
	private void enableControlsStatus() {
		this.jTextFieldMaxValue.setEnabled(true);
		this.jComboBoxRadius.setEnabled(true);
		this.jComboBoxLinkRange.setEnabled(true);
		this.jComboBoxCutOff.setEnabled(true);
		this.jComboBoxPercentile.setEnabled(true);
		this.jComboBoxDisplacement.setEnabled(true);
		this.jComboBoxPointNumber.setEnabled(true);
		this.jCheckBoxEmpty.setEnabled(true);
		this.jButtonTrack.setEnabled(true);
	}

	/**
	 * Switch the status (enabled / disabled) of the controls.
	 */
	private void switchControlsStatus() {
		this.jTextFieldMaxValue
		        .setEnabled(!this.jTextFieldMaxValue.isEnabled());
		this.jComboBoxRadius.setEnabled(!this.jComboBoxRadius.isEnabled());
		this.jComboBoxLinkRange
		        .setEnabled(!this.jComboBoxLinkRange.isEnabled());
		this.jComboBoxCutOff.setEnabled(!this.jComboBoxCutOff.isEnabled());
		this.jComboBoxPercentile.setEnabled(!this.jComboBoxPercentile
		        .isEnabled());
		this.jComboBoxDisplacement.setEnabled(!this.jComboBoxDisplacement
		        .isEnabled());
		this.jComboBoxPointNumber.setEnabled(!this.jComboBoxPointNumber
		        .isEnabled());
		this.jCheckBoxEmpty.setEnabled(!this.jCheckBoxEmpty.isEnabled());
		this.jButtonTrack.setEnabled(!this.jButtonTrack.isEnabled());
	}

	/**
	 * Checks if we have something to process.
	 */
	public void checkHandler() {
		if ((this.sptParametersHandler != null)
		        && (this.sptParametersHandler.getImages().size() > 0)) {
			this.jTextFieldChannel.setText(String
			        .valueOf(this.sptParametersHandler.getC()));
			this.jTextFieldPlane.setText(String
			        .valueOf(this.sptParametersHandler.getZ()));
			// first image to be processed
			this.width.getF1().setText(
			        String.valueOf(this.sptParametersHandler.getImages().get(0)
			                .getX()));
			this.height.getF1().setText(
			        String.valueOf(this.sptParametersHandler.getImages().get(0)
			                .getY()));
			this.time.getF1().setText(
			        String.valueOf(this.sptParametersHandler.getImages().get(0)
			                .getT()));

			// check and add the sizes
			final double widthSize = this.sptParametersHandler.getImages()
			        .get(0).getSizeX();
			final double heightSize = this.sptParametersHandler.getImages()
			        .get(0).getSizeY();
			final double totalTime = this.sptParametersHandler.getImages()
			        .get(0).getSizeT();

			try {
				final double totalWidth = this.sptParametersHandler.getImages()
				        .get(0).getX()
				        * widthSize;
				final double totalHeight = this.sptParametersHandler
				        .getImages().get(0).getY()
				        * heightSize;
				final double avgFrameSize = totalTime
				        / this.sptParametersHandler.getImages().get(0).getT();

				this.width.getF2().setText(
				        totalWidth == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalWidth, 3));
				this.height.getF2().setText(
				        totalHeight == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalHeight, 3));
				this.time.getF2().setText(
				        totalTime == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalTime, 3));

				this.width.getF3().setText(
				        widthSize == 0.0 ? "-" : StringHelper.DoubleToString(
				                widthSize, 3));
				this.height.getF3().setText(
				        heightSize == 0.0 ? "-" : StringHelper.DoubleToString(
				                heightSize, 3));
				this.time.getF3().setText(
				        avgFrameSize == 0.0 ? "-" : StringHelper
				                .DoubleToString(avgFrameSize, 3));
			} catch (final NumberFormatException e) {
				GLogManager.log("exception in checkHandler(): " + e.toString());
			}

			final String imageName = this.sptParametersHandler.getImages()
			        .get(0).getImageName();
			this.jLabelStatus.setText(String.format("%s %s",
			        "ready to process", imageName));

			this.enableControlsStatus();
		} else {
			this.jLabelStatus.setText("please select at least 1 image");
		}
	}

	/**
	 * Calls the SPT thread.
	 */
	private void jButtonTrackActionPerformed(
	        final java.awt.event.ActionEvent evt) {
		String trajectoriesOut = null;

		trajectoriesOut = new FileHelper().selectFile("trajectoriesOutDir",
		        OmegaConstants.INFO_SELECTDIR_OUTPUT_TRACKS,
		        JFileChooser.DIRECTORIES_ONLY);

		if (trajectoriesOut != null) {
			new SPTThread(trajectoriesOut).start();
		}
	}

	class SPTThread extends Thread {
		private final String trajectoriesOut;

		public SPTThread(final String trajectoriesOut) {
			super();
			this.trajectoriesOut = trajectoriesOut;

		}

		@Override
		public void run() {
			JPanelSPT.this.switchControlsStatus();
			JPanelSPT.this.jButtonDisplayTracks.setEnabled(false);

			// ==============================
			// for each image to be processed
			// ==============================
			final ArrayList<ImageDataHandler> images = JPanelSPT.this.sptParametersHandler
			        .getImages();
			final Iterator<ImageDataHandler> it = images.iterator();

			while (it.hasNext()) {
				final ImageDataHandler imageDataHandler = it.next();

				JPanelSPT.this.width.getF1().setText(
				        String.valueOf(imageDataHandler.getX()));
				JPanelSPT.this.height.getF1().setText(
				        String.valueOf(imageDataHandler.getY()));
				JPanelSPT.this.time.getF1().setText(
				        String.valueOf(imageDataHandler.getT()));

				// check and add the pixels sizes
				final double widthSize = imageDataHandler.getSizeX();
				final double heightSize = imageDataHandler.getSizeY();
				final double totalTime = imageDataHandler.getSizeT();

				final double totalWidth = imageDataHandler.getX() * widthSize;
				final double totalHeight = imageDataHandler.getY() * heightSize;
				final double avgFrameSize = totalTime / imageDataHandler.getT();

				JPanelSPT.this.width.getF2().setText(
				        totalWidth == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalWidth, 3));
				JPanelSPT.this.height.getF2().setText(
				        totalHeight == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalHeight, 3));
				JPanelSPT.this.time.getF2().setText(
				        totalTime == 0.0 ? "-" : StringHelper.DoubleToString(
				                totalTime, 3));

				JPanelSPT.this.width.getF3().setText(
				        widthSize == 0.0 ? "-" : StringHelper.DoubleToString(
				                widthSize, 3));
				JPanelSPT.this.height.getF3().setText(
				        heightSize == 0.0 ? "-" : StringHelper.DoubleToString(
				                heightSize, 3));
				JPanelSPT.this.time.getF3().setText(
				        avgFrameSize == 0.0 ? "-" : StringHelper
				                .DoubleToString(avgFrameSize, 3));

				final String outputDir = this.trajectoriesOut
				        + System.getProperty("file.separator")
				        + StringHelper.removeFileExtension(imageDataHandler
				                .getImageName());

				// empty the output directory if requested
				if (JPanelSPT.this.jCheckBoxEmpty.isSelected()) {
					FileHelper.emptyDirectory(outputDir);
				}

				// create the output dir for each image (if not exists)
				FileHelper.createDirectory(outputDir);

				try {
					// init the Runner
					SPTCaller.callInitRunner();
					// set the output directory
					SPTCaller.callSetOutputPath(outputDir);
					// set the Parameters
					// SPTCaller.callSetParameter("p0",
					// String.valueOf(jComboBoxRadius.getSelectedItem()));
					// SPTCaller.callSetParameter("p1",
					// String.valueOf(jComboBoxCutOff.getSelectedItem()));
					// SPTCaller.callSetParameter("p2",
					// String.valueOf(jComboBoxPercentile.getSelectedItem()));
					// SPTCaller.callSetParameter("p3",
					// String.valueOf(jComboBoxDisplacement.getSelectedItem()));
					// SPTCaller.callSetParameter("p4",
					// String.valueOf(jComboBoxLinkRange.getSelectedItem()));
					SPTCaller.callSetParameter("p0", String
					        .valueOf(JPanelSPT.this.jComboBoxRadius.getText()));
					SPTCaller.callSetParameter("p1", String
					        .valueOf(JPanelSPT.this.jComboBoxCutOff.getText()));
					SPTCaller.callSetParameter("p2", String
					        .valueOf(JPanelSPT.this.jComboBoxPercentile
					                .getText()));
					SPTCaller.callSetParameter("p3", String
					        .valueOf(JPanelSPT.this.jComboBoxDisplacement
					                .getText()));
					SPTCaller.callSetParameter("p4", String
					        .valueOf(JPanelSPT.this.jComboBoxLinkRange
					                .getText()));

					SPTCaller.callSetParameter("p5",
					        String.valueOf(imageDataHandler.getT()));
					SPTCaller.callSetParameter("p6",
					        String.valueOf(imageDataHandler.getX()));
					SPTCaller.callSetParameter("p7",
					        String.valueOf(imageDataHandler.getY()));
					SPTCaller.callSetParameter("p8", "0.");

					String mv = "65535";

					if (JPanelSPT.this.jTextFieldMaxValue.getText().length() > 0) {
						mv = JPanelSPT.this.jTextFieldMaxValue.getText();
					}

					SPTCaller.callSetParameter("p9", String.format("%s.", mv)); // 65535
					// SPTCaller.callSetParameter("p9", "255.");

					// set the minimun number of points
					SPTCaller.callSetMinPoints(Integer
					        .parseInt(JPanelSPT.this.jComboBoxPointNumber
					                .getText().toString()));

					// start the Runner
					SPTCaller.callStartRunner();
				} catch (final Exception e) {
					JOptionPane.showMessageDialog(null,
					        OmegaConstants.ERROR_INIT_SPT_RUN,
					        OmegaConstants.OMEGA_TITLE,
					        JOptionPane.ERROR_MESSAGE);
					GLogManager.log(String.format("%s: %s",
					        OmegaConstants.ERROR_INIT_SPT_RUN, e.toString()),
					        Level.SEVERE);
					return;
				}

				JPanelSPT.this.jLabelStatus.setText(String.format(
				        OmegaConstants.INFO_SPT_RUNNING,
				        imageDataHandler.getImageName()));

				final ArrayList<Thread> threads = new ArrayList<Thread>();

				// load the images into the SPT DLL
				final LoaderThread loaderThread = new LoaderThread(
				        imageDataHandler);
				loaderThread.start();
				threads.add(loaderThread);

				// write the results to file
				final WriterThread writerThread = new WriterThread();
				writerThread.start();
				threads.add(writerThread);

				// wait until the two threads are finished before process the
				// next
				// image
				for (final Thread t : threads) {
					try {
						t.join();
					} catch (final InterruptedException e) {
					}
				}

				// when done, write SPT information on file (for each image)
				final SPTExecutionInfoHandler executionInfo = new SPTExecutionInfoHandler(
				        JPanelSPT.this.sptParametersHandler,
				        imageDataHandler,
				        String.valueOf(JPanelSPT.this.jComboBoxRadius.getText()),
				        String.valueOf(JPanelSPT.this.jComboBoxCutOff.getText()),
				        String.valueOf(JPanelSPT.this.jComboBoxPercentile
				                .getText()), String
				                .valueOf(JPanelSPT.this.jComboBoxDisplacement
				                        .getText()), String
				                .valueOf(JPanelSPT.this.jComboBoxLinkRange
				                        .getText()));

				final String infoFile = outputDir
				        + System.getProperty("file.separator")
				        + OmegaConstants.SPT_INFORMATION_FILE;
				final SPTInformationWriter trackingInfoWriter = new SPTInformationFileWriter(
				        infoFile);
				trackingInfoWriter.initWriter();
				final String temp1 = trackingInfoWriter
				        .writeInformation(executionInfo);
				trackingInfoWriter.closeWriter();

				// write stats
				final SPTStatsFileWriter sptStatsFileWriter = new SPTStatsFileWriter(
				        JPanelSPT.this.mainFrame, outputDir,
				        imageDataHandler.getT());
				sptStatsFileWriter.initWriter();
				final String temp2 = sptStatsFileWriter
				        .calculateAndWriteStats();
				sptStatsFileWriter.closeWriter();

				// display the stats JFrame
				final String statsString = temp1 + "\n" + temp2;

				final JFrameStats jfs = new JFrameStats();
				jfs.getjTextArea1().setText(statsString);
				jfs.setLocation(
				        JPanelSPT.this.mainFrame.getX() + 300,
				        (JPanelSPT.this.mainFrame.getY() + (JPanelSPT.this.mainFrame
				                .getHeight() / 2)) - 150);
				jfs.setVisible(true);
			}

			JPanelSPT.this.jLabelStatus.setText("done");
			JPanelSPT.this.switchControlsStatus();
			JPanelSPT.this.jButtonDisplayTracks.setEnabled(true);
		}
	}

	/**
	 * Internal class, it creates a thread used to load the images in the SPT
	 * DLL.
	 * 
	 * @author galliva
	 */
	class LoaderThread extends Thread {
		private ImageDataHandler sptImageHandler = null;

		public LoaderThread(final ImageDataHandler sptImageHandler) {
			this.sptImageHandler = sptImageHandler;
		}

		@Override
		public void run() {
			// same for all the images
			final Gateway gateway = JPanelSPT.this.sptParametersHandler
			        .getGateway();
			// ID of the pixels
			final long pixelsID = this.sptImageHandler.getPixelsID();
			// number of frames for this image
			final int framesNumber = this.sptImageHandler.getT();
			// number of bytes of this image
			final int byteWidth = gateway.getByteWidht(pixelsID);

			boolean error = false;

			GLogManager.log(
			        String.format("processing %d byte per pixel", byteWidth),
			        Level.INFO);

			for (int i = 0; i < framesNumber; i++) {
				JPanelSPT.this.jLabelStatusDetails.setText(String.format(
				        "loading frame %d / %d", i + 1, framesNumber));

				try {
					final byte[] pixels = gateway.getPlane(pixelsID,
					        JPanelSPT.this.sptParametersHandler.getZ(), i,
					        JPanelSPT.this.sptParametersHandler.getC());

					int[] data = null;

					System.out.println("Byte width: " + byteWidth);
					// Manage the right amount of byte per pixels
					switch (byteWidth) {
					case 1:
						// 8 bit image
						data = new int[pixels.length];
						for (int j = 0; j < data.length; j++) {
							final int b0 = pixels[j] & 0xff;
							data[j] = b0 << 0;
						}
						break;
					case 2:
						// 16 bit image
						data = new int[pixels.length / 2];
						for (int j = 0; j < data.length; j++) {
							final int b0 = pixels[2 * j] & 0xff;
							final int b1 = pixels[(2 * j) + 1] & 0xff;
							data[j] = (b0 << 8) | (b1 << 0);
						}
						break;
					case 3:
						// 24 bit image
						data = new int[pixels.length / 3];
						for (int j = 0; j < data.length; j++) {
							final int b0 = pixels[3 * j] & 0xff;
							final int b1 = pixels[(3 * j) + 1] & 0xff;
							final int b2 = pixels[(3 * j) + 2] & 0xff;
							data[j] = (b0 << 16) | (b1 << 8) | (b2 << 0);
						}
						break;
					case 4:
						// 32 bit image
						data = new int[pixels.length / 4];
						for (int j = 0; j < data.length; j++) {
							final int b0 = pixels[4 * j] & 0xff;
							final int b1 = pixels[(4 * j) + 1] & 0xff;
							final int b2 = pixels[(4 * j) + 2] & 0xff;
							final int b3 = pixels[(4 * j) + 3] & 0xff;
							data[j] = (b0 << 24) | (b1 << 16) | (b2 << 8)
							        | (b3 << 0);
						}
						break;
					}
					SPTCaller.callLoadImage(data);
				} catch (final Exception e) {
					error = true;
					GLogManager.log(String.format("%s: %s",
					        OmegaConstants.ERROR_DURING_SPT_RUN, e.toString()),
					        Level.SEVERE);
				}
			}

			if (error) {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.ERROR_DURING_SPT_RUN,
				        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Internal class, it creates a thread used to check the results of the SPT
	 * DLL.
	 * 
	 * @author galliva
	 */
	class WriterThread extends Thread {
		@Override
		public void run() {
			try {
				SPTCaller.callWriteResults();
			} catch (final Exception e) {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.ERROR_SPT_SAVE_RESULTS,
				        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
				GLogManager.log(
				        String.format("%s: %s", "Error writing the results",
				                e.toString()), Level.SEVERE);
			}

			try {
				SPTCaller.callDisposeRunner();
			} catch (final Exception e) {
				GLogManager.log(
				        String.format("%s: %s", "Error disposing the runner",
				                e.toString()), Level.SEVERE);
			}
		}
	}
}
