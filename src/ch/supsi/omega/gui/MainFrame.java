package ch.supsi.omega.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ch.supsi.omega.common.JFrameEditMotions;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.openbis.JDialogOpenBisPassword;
import ch.supsi.omega.openbis.JDialogOpenBisSettings;
import ch.supsi.omega.openbis.OpenBisHelper;
import ch.supsi.omega.review.ImageCanvas;
import ch.supsi.omega.tracking.parameters.OmeroParametersHandler;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static GConfigurationManager configurationManager = new GConfigurationManager();

	JPanelReview jPanelReviewRight = new JPanelReview(this);
	JPanelSPT jPanelSPTRight = new JPanelSPT(this);
	JPanelTV jPanelTVRight = new JPanelTV(this);
	JPanelTS jPanelTSRight = new JPanelTS(this);
	JPanelExploration jPanelExploration = new JPanelExploration(this);
	JPanelSA jPanelSARight = new JPanelSA(this);

	AboutBox aboutbox = null;

	public MainFrame() {
		try {
			configurationManager.setIniFile(OmegaConstants.INIFILE);
			configurationManager
			        .checkConfigFile(OmegaConstants.ORIGINALINIFILE);
		} catch (Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		initComponents();

		this.setLocation(0, this.getLocation().y);

		jPanelRight.add(jPanelReviewRight, "A");
		jPanelRight.add(jPanelSPTRight, "B");
		jPanelRight.add(jPanelTVRight, "C");
		jPanelRight.add(jPanelTSRight, "D");
		jPanelRight.add(jPanelExploration, "E");
		jPanelRight.add(jPanelSARight, "F");
	}

	private void initComponents() {
		jPanelLeft = new javax.swing.JPanel();
		jPanelLeftTop = new javax.swing.JPanel();
		jPanel6 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jButtonDisplayReviewPanel = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jButtonDisplaySPTPanel = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		jPanelTS = new javax.swing.JPanel();
		jPanelSA = new javax.swing.JPanel();
		jPanelTV = new javax.swing.JPanel();
		jButtonDisplayTSPanel = new javax.swing.JButton();
		jButtonDisplaySAPanel = new javax.swing.JButton();
		jButtonDisplayTVPanel = new javax.swing.JButton();
		jLabelTS = new javax.swing.JLabel();
		jLabelSA = new javax.swing.JLabel();
		jLabelTV = new javax.swing.JLabel();
		jPanelDataExploration = new javax.swing.JPanel();
		jButtonDisplayDataExplorationPanel = new javax.swing.JButton();
		jLabel6 = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		jPanelLeftBottom = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jPanelRight = new javax.swing.JPanel();
		jMenuMain = new javax.swing.JMenuBar();
		jMenuFile = new javax.swing.JMenu();
		jMenuEdit = new javax.swing.JMenu();
		jMenuSettings = new javax.swing.JMenu();
		jMenuHelp = new javax.swing.JMenu();
		jMenuItemExit = new javax.swing.JMenuItem();
		jMenuItemOpenBis = new javax.swing.JMenuItem();
		jMenuItemOpenBisPassword = new javax.swing.JMenuItem();
		jMenuItemOpenBisTest = new javax.swing.JMenuItem();
		jMenuItemAbout = new javax.swing.JMenuItem();
		jMenuItemEditMotions = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle(OmegaConstants.OMEGA_TITLE);

		setMinimumSize(new Dimension(700, 900));
		setPreferredSize(new Dimension(700, 900));

		jPanelLeft.setBackground(new java.awt.Color(255, 255, 255));
		jPanelLeft.setPreferredSize(new java.awt.Dimension(175, 750));
		jPanelLeft.setLayout(new java.awt.BorderLayout());

		// jpanel left components
		jPanelLeftTop.setBackground(new java.awt.Color(49, 51, 153));
		jPanelLeftTop.setLayout(new java.awt.BorderLayout());

		jPanel6.setBackground(new java.awt.Color(49, 51, 153));
		jPanel6.setPreferredSize(new java.awt.Dimension(321, 800));
		jPanel6.setLayout(new java.awt.GridLayout(6, 1));

		// image review
		jPanel1.setBackground(new java.awt.Color(49, 51, 153));
		jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
		jPanel1.setPreferredSize(new java.awt.Dimension(150, 125));
		jPanel1.setLayout(new java.awt.BorderLayout());

		jButtonDisplayReviewPanel.setLayout(new BorderLayout());
		JLabel label3 = new JLabel("Image Selection and");
		label3.setHorizontalAlignment(0);
		JLabel label4 = new JLabel("Review");
		label4.setHorizontalAlignment(0);
		jButtonDisplayReviewPanel.add(BorderLayout.NORTH, label3);
		jButtonDisplayReviewPanel.add(BorderLayout.SOUTH, label4);

		jButtonDisplayReviewPanel
		        .setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplayReviewPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonReviewActionPerformed(evt);
			        }
		        });
		jPanel1.add(jButtonDisplayReviewPanel, java.awt.BorderLayout.NORTH);

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("<html><left>Connect to your OMERO server, browse your datasets and select the images you wish to subject to image analysis.</left></html>");
		jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

		jPanel6.add(jPanel1);

		jPanel3.setBackground(new java.awt.Color(49, 51, 153));
		jPanel3.setMinimumSize(new java.awt.Dimension(0, 0));
		jPanel3.setPreferredSize(new java.awt.Dimension(150, 125));
		jPanel3.setLayout(new java.awt.BorderLayout());

		jButtonDisplaySPTPanel.setText("Single Particle Tracking");
		jButtonDisplaySPTPanel.setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplaySPTPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonSPTActionPerformed(evt);
			        }
		        });
		jPanel3.add(jButtonDisplaySPTPanel, java.awt.BorderLayout.NORTH);

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel4.setForeground(new java.awt.Color(255, 255, 255));
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel4.setText("<html><left>Identify the trajectories followed by your particles of interest</left></html>");
		jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanel3.add(jLabel4, java.awt.BorderLayout.CENTER);

		jPanel6.add(jPanel3);

		jPanelTS.setBackground(new java.awt.Color(49, 51, 153));
		jPanelTS.setPreferredSize(new java.awt.Dimension(150, 125));
		jPanelTS.setLayout(new java.awt.BorderLayout());

		// trajectory visualization
		jPanelTV.setBackground(new java.awt.Color(49, 51, 153));
		jPanelTV.setPreferredSize(new java.awt.Dimension(150, 125));
		jPanelTV.setLayout(new java.awt.BorderLayout());

		jButtonDisplayTVPanel.setText("Trajectory Visualization");
		jButtonDisplayTVPanel.setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplayTVPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonTVctionPerformed(evt);
			        }
		        });
		jPanelTV.add(jButtonDisplayTVPanel, java.awt.BorderLayout.NORTH);
		jLabelTV.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabelTV.setForeground(new java.awt.Color(255, 255, 255));
		jLabelTV.setText("<html><left>Display identified trajectories on the original image, present a summary of the tracking results and display intensity profiles of found trajectories</left></html>");
		jLabelTV.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanelTV.add(jLabelTV, java.awt.BorderLayout.CENTER);
		jPanel6.add(jPanelTV);

		// trajectory segmentation
		jButtonDisplayTSPanel.setText("Trajectory Segmentation");
		jButtonDisplayTSPanel.setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplayTSPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonTSActionPerformed(evt);
			        }
		        });
		jPanelTS.add(jButtonDisplayTSPanel, java.awt.BorderLayout.NORTH);
		jLabelTS.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabelTS.setForeground(new java.awt.Color(255, 255, 255));
		jLabelTS.setText("<html><left>Subdivide your trajectories in uniform motion type segments</left></html>");
		jLabelTS.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanelTS.add(jLabelTS, java.awt.BorderLayout.CENTER);
		jPanel6.add(jPanelTS);

		jPanelDataExploration.setBackground(new java.awt.Color(49, 51, 153));
		jPanelDataExploration
		        .setPreferredSize(new java.awt.Dimension(150, 125));
		jPanelDataExploration.setLayout(new java.awt.BorderLayout());

		// result visualization and exploration
		// jButtonDisplayDataExplorationPanel.setLayout(new BorderLayout());
		// JLabel label1 = new JLabel("Motion Analysis");
		// label1.setHorizontalAlignment(0);
		// JLabel label2 = new JLabel("Exploration");
		// label2.setHorizontalAlignment(0);
		// jButtonDisplayDataExplorationPanel.add(BorderLayout.NORTH,label1);
		// jButtonDisplayDataExplorationPanel.add(BorderLayout.SOUTH,label2);
		jButtonDisplayDataExplorationPanel.setText("Motion Analysis");
		jButtonDisplayDataExplorationPanel
		        .setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplayDataExplorationPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonDisplayDataExplorationActionPerformed(evt);
			        }
		        });
		jPanelDataExploration.add(jButtonDisplayDataExplorationPanel,
		        java.awt.BorderLayout.NORTH);

		jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabel6.setForeground(new java.awt.Color(255, 255, 255));
		jLabel6.setText("<html><left>Compute velocity and diffusivity measures describing individual trajectory segments and globally analyze the motion behaviour of all trajectories in the dataset</left></html>");
		jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanelDataExploration.add(jLabel6, java.awt.BorderLayout.CENTER);

		jPanel6.add(jPanelDataExploration);

		jPanelLeftTop.add(jPanel6, java.awt.BorderLayout.NORTH);

		// Statistical analysis
		jPanelSA.setBackground(new java.awt.Color(49, 51, 153));
		jPanelSA.setPreferredSize(new java.awt.Dimension(150, 125));
		jPanelSA.setLayout(new java.awt.BorderLayout());

		jButtonDisplaySAPanel.setText("Statistic Analysis");
		jButtonDisplaySAPanel.setPreferredSize(new java.awt.Dimension(0, 40));
		jButtonDisplaySAPanel
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        jButtonSActionPerformed(evt);
			        }
		        });
		jPanelSA.add(jButtonDisplaySAPanel, java.awt.BorderLayout.NORTH);
		jLabelSA.setFont(new java.awt.Font("Tahoma", 0, 12));
		jLabelSA.setForeground(new java.awt.Color(255, 255, 255));
		jLabelSA.setText("<html><left>Statistically analyze your manual segmentation results and produce charts displaying motion type frequencies</left></html>");
		jLabelSA.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanelSA.add(jLabelSA, java.awt.BorderLayout.CENTER);
		jPanel6.add(jPanelSA);

		jPanel7.setBackground(new java.awt.Color(49, 51, 153));

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
		        jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 175,
		        Short.MAX_VALUE));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 24,
		        Short.MAX_VALUE));

		jPanelLeftTop.add(jPanel7, java.awt.BorderLayout.CENTER);

		jPanelLeft.add(jPanelLeftTop, java.awt.BorderLayout.CENTER);

		jPanelLeftBottom.setBackground(new java.awt.Color(204, 204, 255));
		jPanelLeftBottom.setPreferredSize(new java.awt.Dimension(150, 50));

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		        "/ch/supsi/omega/gui/resources/OMEGA_logo_blue.jpg")));

		javax.swing.GroupLayout jPanelLeftBottomLayout = new javax.swing.GroupLayout(
		        jPanelLeftBottom);
		jPanelLeftBottom.setLayout(jPanelLeftBottomLayout);
		jPanelLeftBottomLayout.setHorizontalGroup(jPanelLeftBottomLayout
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		                jPanelLeftBottomLayout
		                        .createSequentialGroup()
		                        .addComponent(jLabel1,
		                                javax.swing.GroupLayout.DEFAULT_SIZE,
		                                javax.swing.GroupLayout.DEFAULT_SIZE,
		                                Short.MAX_VALUE).addContainerGap()));
		jPanelLeftBottomLayout.setVerticalGroup(jPanelLeftBottomLayout
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(
		                jPanelLeftBottomLayout
		                        .createSequentialGroup()
		                        .addComponent(jLabel1)
		                        .addContainerGap(
		                                javax.swing.GroupLayout.DEFAULT_SIZE,
		                                Short.MAX_VALUE)));

		jPanelLeft.add(jPanelLeftBottom, java.awt.BorderLayout.SOUTH);

		getContentPane().add(jPanelLeft, java.awt.BorderLayout.WEST);

		jPanelRight.setLayout(new java.awt.CardLayout());
		getContentPane().add(jPanelRight, java.awt.BorderLayout.CENTER);

		// menu
		jMenuFile.setText("File");
		jMenuItemExit.setText("Exit");
		jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_F4,
		        java.awt.event.InputEvent.ALT_MASK));
		jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemExitActionPerformed(evt);
			}
		});
		jMenuFile.add(jMenuItemExit);
		jMenuMain.add(jMenuFile);

		jMenuEdit.setText("Edit");
		jMenuItemEditMotions.setText("Edit Motions Types...");
		jMenuItemEditMotions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_E,
		        java.awt.event.InputEvent.CTRL_MASK));
		jMenuItemEditMotions
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        JFrameEditMotions jFrameEditMotions = new JFrameEditMotions();

				        jFrameEditMotions.getjTextField1().setText(
				                OMEGA.MOTIONTYPES[0]);
				        jFrameEditMotions.getjTextField2().setText(
				                OMEGA.MOTIONTYPES[1]);
				        jFrameEditMotions.getjTextField3().setText(
				                OMEGA.MOTIONTYPES[2]);
				        jFrameEditMotions.getjTextField4().setText(
				                OMEGA.MOTIONTYPES[3]);
				        jFrameEditMotions.getjTextField5().setText(
				                OMEGA.MOTIONTYPES[4]);

				        jFrameEditMotions.setLocationRelativeTo(jPanelRight);
				        jFrameEditMotions.setVisible(true);
			        }
		        });
		jMenuEdit.add(jMenuItemEditMotions);
		jMenuMain.add(jMenuEdit);

		jMenuSettings.setText("openBIS");
		jMenuItemOpenBis.setText("Settings...");
		jMenuItemOpenBis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		        java.awt.event.KeyEvent.VK_S,
		        java.awt.event.InputEvent.CTRL_MASK));
		jMenuItemOpenBis.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JDialogOpenBisSettings jDialogOpenBisSettings = new JDialogOpenBisSettings();
				jDialogOpenBisSettings.setLocationRelativeTo(jPanelRight);
				jDialogOpenBisSettings.setVisible(true);
			}
		});
		jMenuSettings.add(jMenuItemOpenBis);
		jMenuItemOpenBisPassword.setText("Set session password...");
		jMenuItemOpenBisPassword.setAccelerator(javax.swing.KeyStroke
		        .getKeyStroke(java.awt.event.KeyEvent.VK_P,
		                java.awt.event.InputEvent.CTRL_MASK));
		jMenuItemOpenBisPassword
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        JDialogOpenBisPassword jDialogOpenBisPassword = new JDialogOpenBisPassword();
				        jDialogOpenBisPassword
				                .setLocationRelativeTo(jPanelRight);
				        jDialogOpenBisPassword.setVisible(true);
			        }
		        });
		jMenuSettings.add(jMenuItemOpenBisPassword);
		jMenuItemOpenBisTest.setText("Test connection");
		jMenuItemOpenBisTest
		        .addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				        testOpenBisConnection();
			        }
		        });
		jMenuSettings.add(jMenuItemOpenBisTest);
		jMenuMain.add(jMenuSettings);

		jMenuHelp.setText("Help");
		jMenuItemAbout.setText("About");
		jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemAboutActionPerformed(evt);
			}
		});
		jMenuHelp.add(jMenuItemAbout);
		jMenuMain.add(jMenuHelp);

		setJMenuBar(jMenuMain);

		pack();
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
		        .getScreenSize();
		java.awt.Dimension dialogSize = getSize();
		setLocation((screenSize.width - dialogSize.width) / 2,
		        (screenSize.height - dialogSize.height) / 2);
	}

	private void testOpenBisConnection() {
		OpenBisHelper.testConnection(this);
	}

	private void jButtonReviewActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "A");
	}

	private void jButtonSPTActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "B");
	}

	private void jButtonTVctionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "C");
	}

	private void jButtonTSActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "D");
	}

	private void jButtonDisplayDataExplorationActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "E");
	}

	private void jButtonSActionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "F");
	}

	public void callDataExplorationModule(ImageCanvas imageCanvas) {
		jPanelTVRight.setCurrentCanvas(imageCanvas);
		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "C");
	}

	/**
	 * Display the SPT panel (this method should be called from the review
	 * frame).
	 */
	public void displaySPTPanel(OmeroParametersHandler sptParametersHandler) {
		jPanelSPTRight.setSptParametersHandler(sptParametersHandler);
		jPanelSPTRight.checkHandler();

		CardLayout cl = (CardLayout) (jPanelRight.getLayout());
		cl.show(jPanelRight, "B");
	}

	private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {
		if (aboutbox == null) {
			aboutbox = new AboutBox(this);
			aboutbox.setLocationRelativeTo(this);
		}

		aboutbox.setVisible(true);
	}

	private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private JPanel jPanel1;
	private JPanel jPanel3;
	private JPanel jPanel6;
	private JPanel jPanel7;
	private JPanel jPanelLeft;
	private JPanel jPanelLeftBottom;
	private JPanel jPanelLeftTop;
	private JPanel jPanelRight;
	private JPanel jPanelTS;
	private JPanel jPanelSA;
	private JPanel jPanelTV;
	private JPanel jPanelDataExploration;
	private JButton jButtonDisplayReviewPanel;
	private JButton jButtonDisplayTSPanel;
	private JButton jButtonDisplaySAPanel;
	private JButton jButtonDisplayTVPanel;
	private JButton jButtonDisplaySPTPanel;
	private JButton jButtonDisplayDataExplorationPanel;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JLabel jLabel6;
	private JLabel jLabelTS;
	private JLabel jLabelSA;
	private JLabel jLabelTV;
	private JMenu jMenuFile;
	private JMenu jMenuEdit;
	private JMenu jMenuSettings;
	private JMenu jMenuHelp;
	private JMenuItem jMenuItemExit;
	private JMenuItem jMenuItemOpenBis;
	private JMenuItem jMenuItemOpenBisPassword;
	private JMenuItem jMenuItemOpenBisTest;
	private JMenuItem jMenuItemAbout;
	private JMenuItem jMenuItemEditMotions;
	private JMenuBar jMenuMain;
}
