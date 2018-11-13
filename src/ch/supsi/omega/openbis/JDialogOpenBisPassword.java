package ch.supsi.omega.openbis;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import ch.supsi.omega.common.OmegaConstants;

/**
 * Window used to set the openBIS connection settings.
 * @author galliva
 */
public class JDialogOpenBisPassword extends JDialog
{
	private static final long					serialVersionUID	= -4718353668893658380L;

	private static JDialogOpenBisPassword	INSTANCE				= null;

	public JDialogOpenBisPassword()
	{
		INSTANCE = this;

		setPreferredSize(new Dimension(300, 95));
		setModal(true);
		setTitle(OmegaConstants.OMEGA_OPENBIS_PASSWORD_TITLE);

		initComponents();
	}

	private void initComponents()
	{
		jLabelPassword = new JLabel();
		jPasswordField = new JPasswordField();
		jButtonOK = new JButton();
		jButtonCancel = new JButton();

		getContentPane().setLayout(new java.awt.GridLayout(2, 2, 5, 5));

		jLabelPassword.setText("  openBIS password:");
		jLabelPassword.setPreferredSize(new java.awt.Dimension(45, 14));
		getContentPane().add(jLabelPassword);

		jPasswordField.setPreferredSize(new java.awt.Dimension(100, 20));
		jPasswordField.setText(OpenBisHelper.getPassword());
		getContentPane().add(jPasswordField);

		jButtonOK.setText("OK");
		jButtonOK.setPreferredSize(new java.awt.Dimension(45, 23));
		jButtonOK.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				OpenBisHelper.setPassword(new String(jPasswordField.getPassword()));
				INSTANCE.dispose();
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

	private JLabel			   jLabelPassword;
	private JPasswordField	jPasswordField;
	private JButton		   jButtonCancel;
	private JButton		   jButtonOK;
}
