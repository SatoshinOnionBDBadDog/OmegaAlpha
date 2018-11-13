package ch.supsi.omega.openbis;

import java.awt.Dimension;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import ch.supsi.omega.common.OmegaConstants;
import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

/**
 * Window used to set the openBIS connection settings.
 * @author galliva
 */
public class JDialogOpenBisSettings extends JDialog
{
	private static final long					serialVersionUID		= -4718353668893658380L;

	private static JDialogOpenBisSettings	INSTANCE					= null;

	private static GConfigurationManager	configurationManager	= new GConfigurationManager();
	
	public JDialogOpenBisSettings()
	{
		INSTANCE = this;

		setPreferredSize(new Dimension(300, 130));
		setModal(true);
		setTitle(OmegaConstants.OMEGA_OPENBIS_SETTINGS_TITLE);
		
		initComponents();

		try
		{
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		}
		catch (Exception e)
		{
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		String host = "";
		String username = "";

		try
		{
			host = configurationManager.readConfig("openBISHost");
			username = configurationManager.readConfig("openBISUsername");
		}
		catch (Exception e)
		{
			// nothing to do, leave the field empty
		}

		jTextFieldHostname.setText(host);
		jTextFieldUsername.setText(username);
	}

	private void initComponents()
	{
		jLabelHostname     = new JLabel();
		jTextFieldHostname = new JTextField();
		jLabelUsername     = new JLabel();
		jTextFieldUsername = new JTextField();
		jButtonOK          = new JButton();
		jButtonCancel      = new JButton();

		getContentPane().setLayout(new java.awt.GridLayout(3, 2, 5, 5));
		
		jLabelHostname.setText("  openBIS hostname:");
		jLabelHostname.setPreferredSize(new java.awt.Dimension(45, 14));
		getContentPane().add(jLabelHostname);

		jTextFieldHostname.setPreferredSize(new java.awt.Dimension(100, 20));
		getContentPane().add(jTextFieldHostname);

		jLabelUsername.setText("  openBIS username:");
		jLabelUsername.setPreferredSize(new java.awt.Dimension(45, 14));
		getContentPane().add(jLabelUsername);

		jTextFieldUsername.setPreferredSize(new java.awt.Dimension(100, 20));
		getContentPane().add(jTextFieldUsername);

		jButtonOK.setText("OK");
		jButtonOK.setPreferredSize(new java.awt.Dimension(45, 23));
		jButtonOK.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButtonOKActionPerformed(evt);
			}
		});
		getContentPane().add(jButtonOK);

		jButtonCancel.setText("Cancel");
		jButtonCancel.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				INSTANCE.dispose();
			}
		});
		getContentPane().add(jButtonCancel);

		getRootPane().setDefaultButton(jButtonOK);
		
		pack();
	}

	private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)
	{
		configurationManager.writeConfig("openBISHost",     jTextFieldHostname.getText());
		configurationManager.writeConfig("openBISUsername", jTextFieldUsername.getText());
		INSTANCE.dispose();
	}

	private JButton		jButtonCancel;
	private JButton		jButtonOK;
	private JLabel			jLabelHostname;
	private JLabel			jLabelUsername;
	private JTextField	jTextFieldHostname;
	private JTextField	jTextFieldUsername;
}
