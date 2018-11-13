package ch.supsi.omega.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.omero.Gateway;
import ch.supsi.omega.omero.LoginCredentials;
import ch.supsi.omega.review.ReviewFrame;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class JPanelReview extends javax.swing.JPanel {
	private static final long serialVersionUID = 7767955395549213076L;

	private MainFrame mainFrame = null;

	private static GConfigurationManager configurationManager = new GConfigurationManager();

	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonConnect;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanelConnect;
	private javax.swing.JPanel jPanelHost;
	private javax.swing.JPanel jPanelPassword;
	private javax.swing.JPanel jPanelPort;
	private javax.swing.JPanel jPanelStatus;
	private javax.swing.JPanel jPanelUsername;
	private javax.swing.JPasswordField jPasswordFieldPassword;
	private javax.swing.JTextField jTextFieldHost;
	private javax.swing.JTextField jTextFieldPort;
	private javax.swing.JTextField jTextFieldUsername;

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public JPanelReview(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		try {
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		} catch (Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		initComponents();

		String host = "";
		String username = "";
		String port = "";

		try {
			host = configurationManager.readConfig("omeroHost");
			username = configurationManager.readConfig("omeroUsername");
			port = configurationManager.readConfig("omeroPort");
		} catch (Exception e) {
			// nothing to do, leave the field empty
		}

		jTextFieldHost.setText(host);
		jTextFieldUsername.setText(username);
		jTextFieldPort.setText(port);

		jTextFieldUsername.setCaretPosition(jTextFieldUsername.getText()
		        .length());
		jTextFieldPort.setCaretPosition(jTextFieldPort.getText().length());
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel8 = new javax.swing.JLabel();
		jPanelHost = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTextFieldHost = new javax.swing.JTextField();
		jPanelUsername = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jTextFieldUsername = new javax.swing.JTextField();
		jPanelPassword = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jPasswordFieldPassword = new javax.swing.JPasswordField();
		jPanelPort = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jTextFieldPort = new javax.swing.JTextField();
		jPanelConnect = new javax.swing.JPanel();
		jButtonConnect = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jPanelStatus = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();

		setLayout(new java.awt.GridLayout(20, 1));

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel8.setText("Connection to an OMERO server");
		jLabel8.setPreferredSize(new java.awt.Dimension(200, 25));
		jPanel1.add(jLabel8);

		add(jPanel1);

		jPanelHost.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel1.setText("Server address:");
		jLabel1.setPreferredSize(new java.awt.Dimension(110, 25));
		jPanelHost.add(jLabel1);

		jTextFieldHost.setPreferredSize(new java.awt.Dimension(200, 25));
		jPanelHost.add(jTextFieldHost);

		JLabel info = new JLabel();
		info.setText("e.g.: test.openmicroscopy.org");
		info.setFont(new java.awt.Font("Tahoma", 0, 10));

		jPanelHost.add(info);

		add(jPanelHost);

		jPanelUsername.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jLabel2.setText("Username:");
		jLabel2.setPreferredSize(new java.awt.Dimension(110, 25));
		jPanelUsername.add(jLabel2);

		jTextFieldUsername.setPreferredSize(new java.awt.Dimension(200, 25));
		jPanelUsername.add(jTextFieldUsername);

		add(jPanelUsername);

		jPanelPassword.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jLabel3.setText("Password:");
		jLabel3.setPreferredSize(new java.awt.Dimension(110, 25));
		jPanelPassword.add(jLabel3);

		jPasswordFieldPassword
		        .setPreferredSize(new java.awt.Dimension(200, 25));
		jPanelPassword.add(jPasswordFieldPassword);

		add(jPanelPassword);

		jPanelPort.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel4.setText("Server port:");
		jLabel4.setPreferredSize(new java.awt.Dimension(110, 25));
		jPanelPort.add(jLabel4);

		jTextFieldPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldPort.setPreferredSize(new java.awt.Dimension(50, 25));
		jPanelPort.add(jTextFieldPort);

		add(jPanelPort);

		jPanelConnect.setLayout(new java.awt.FlowLayout(
		        java.awt.FlowLayout.LEFT));

		jButtonConnect.setText("Connect");
		jButtonConnect.setPreferredSize(new java.awt.Dimension(90, 25));
		jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonConnectActionPerformed(evt);
			}
		});
		jPanelConnect.add(jButtonConnect);

		jButtonCancel.setText("Reset");
		jButtonCancel.setPreferredSize(new java.awt.Dimension(90, 25));
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldHost.setText("");
				jTextFieldUsername.setText("");
				jPasswordFieldPassword.setText("");
			}
		});

		jPanelConnect.add(jButtonCancel);

		add(jPanelConnect);

		jPanel2.setPreferredSize(new java.awt.Dimension(400, 25));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
		        jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
		        Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
		        javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 33,
		        Short.MAX_VALUE));

		add(jPanel2);

		jPanelStatus
		        .setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel6.setText("Status:");
		jLabel6.setPreferredSize(new java.awt.Dimension(90, 25));
		jPanelStatus.add(jLabel6);

		jLabel7.setText("not connected");
		jLabel7.setPreferredSize(new java.awt.Dimension(150, 25));
		jPanelStatus.add(jLabel7);

		add(jPanelStatus);

		jPasswordFieldPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToOmero();
			}
		});
	}

	private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {
		connectToOmero();
	}

	private void connectToOmero() {
		LoginCredentials lc = new LoginCredentials(
		        jTextFieldUsername.getText(), new String(
		                jPasswordFieldPassword.getPassword()),
		        jTextFieldHost.getText());

		int port = 0;
		try {
			port = Integer.parseInt(jTextFieldPort.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
			        OmegaConstants.ERROR_PORT_IS_NUMBER,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}

		lc.setPort(port);

		Gateway gateway = new Gateway();

		boolean connected = false;

		try {
			connected = gateway.login(lc);
		} catch (Exception e) {
			// TODO
		}

		if (!connected)
			jLabel7.setText(OmegaConstants.ERROR_CANNOT_CONNECT_TO_OMERO);
		else {
			configurationManager.writeConfig("omeroHost",
			        jTextFieldHost.getText());
			configurationManager.writeConfig("omeroUsername",
			        jTextFieldUsername.getText());
			configurationManager.writeConfig("omeroPort",
			        jTextFieldPort.getText());

			jLabel7.setText(String.format(
			        OmegaConstants.INFO_OMERO_CONNECTION_OK,
			        jTextFieldHost.getText()));
			new ReviewFrame(this, gateway, mainFrame.getLocation().x
			        + mainFrame.getWidth(), mainFrame.getLocation().y);
		}
	}
}
