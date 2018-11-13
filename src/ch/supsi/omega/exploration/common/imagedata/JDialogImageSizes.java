package ch.supsi.omega.exploration.common.imagedata;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.science.Greeks;

public class JDialogImageSizes extends javax.swing.JDialog
{
	private static final long	serialVersionUID	= 7389577619501703807L;

	private JPanel					jPanelBottom;
	private JPanel					jPanelLeft;
	private JPanel					jPanelRight;
	private JPanel					jPanelTop;
	private JLabel					jLabelImage;
	private JLabel					jLabelImage1;
	private JLabel					jLabelSizeX;
	private JLabel					jLabelSizeY;
	private JLabel					jLabelSizeT;
	private JTextField			jTextFieldSizeX;
	private JTextField			jTextFieldSizeY;
	private JTextField			jTextFieldSizeT;
	private JButton				jButtonOK;

	private String					imageName			= null;
	private double					originalSizes[]	= null;

	public JDialogImageSizes(String imageName, double originalSizes[])
	{
		this.imageName = imageName;
		this.originalSizes = originalSizes;
		initComponents();
	}

	private void initComponents()
	{
		jPanelTop = new javax.swing.JPanel();
		jPanelLeft = new javax.swing.JPanel();
		jLabelImage = new javax.swing.JLabel();
		jLabelSizeX = new javax.swing.JLabel();
		jLabelSizeY = new javax.swing.JLabel();
		jLabelSizeT = new javax.swing.JLabel();
		jPanelRight = new javax.swing.JPanel();
		jLabelImage1 = new javax.swing.JLabel();
		jTextFieldSizeX = new javax.swing.JTextField();
		jTextFieldSizeY = new javax.swing.JTextField();
		jTextFieldSizeT = new javax.swing.JTextField();
		jPanelBottom = new javax.swing.JPanel();
		jButtonOK = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(OmegaConstants.OMEGA_TITLE);
		setIconImage(null);
		setModal(true);
		getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

		jLabelImage1.setText(imageName);
		
		jTextFieldSizeX.setText(String.valueOf(originalSizes[0]));
		jTextFieldSizeY.setText(String.valueOf(originalSizes[1]));
		jTextFieldSizeT.setText(String.valueOf(originalSizes[2]));

		jPanelTop.setPreferredSize(new java.awt.Dimension(250, 95));
		jPanelTop.setRequestFocusEnabled(false);
		jPanelTop.setLayout(new java.awt.BorderLayout(5, 0));

		jPanelLeft.setMinimumSize(new java.awt.Dimension(61, 50));
		jPanelLeft.setPreferredSize(new java.awt.Dimension(110, 60));
		jPanelLeft.setLayout(new java.awt.GridLayout(4, 0));

		jLabelImage.setText(" ImageName:");
		jPanelLeft.add(jLabelImage);

		jLabelSizeX.setText(String.format(" Pixels size X [%s]:", Greeks.MU));
		jLabelSizeX.setPreferredSize(new java.awt.Dimension(130, 20));
		jPanelLeft.add(jLabelSizeX);

		jLabelSizeY.setText(String.format(" Pixels size Y [%s]:", Greeks.MU));
		jLabelSizeY.setPreferredSize(new java.awt.Dimension(130, 20));
		jPanelLeft.add(jLabelSizeY);

		jLabelSizeT.setText(" Delta T [s]:");
		jLabelSizeT.setPreferredSize(new java.awt.Dimension(130, 20));
		jPanelLeft.add(jLabelSizeT);

		jPanelTop.add(jPanelLeft, java.awt.BorderLayout.WEST);

		jPanelRight.setMinimumSize(new java.awt.Dimension(6, 50));
		jPanelRight.setPreferredSize(new java.awt.Dimension(100, 60));
		jPanelRight.setLayout(new java.awt.GridLayout(4, 0));
		jPanelRight.add(jLabelImage1);

		jTextFieldSizeX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jPanelRight.add(jTextFieldSizeX);

		jTextFieldSizeY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jPanelRight.add(jTextFieldSizeY);

		jTextFieldSizeT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jPanelRight.add(jTextFieldSizeT);

		jPanelTop.add(jPanelRight, java.awt.BorderLayout.CENTER);

		getContentPane().add(jPanelTop, java.awt.BorderLayout.CENTER);

		jPanelBottom.setPreferredSize(new java.awt.Dimension(250, 28));
		jPanelBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButtonOKActionPerformed(evt);
			}
		});
		jPanelBottom.add(jButtonOK);

		getContentPane().add(jPanelBottom, java.awt.BorderLayout.SOUTH);

		getRootPane().setDefaultButton(jButtonOK);
		pack();
	}

	private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)
	{
		try
		{
			double x = Double.parseDouble(jTextFieldSizeX.getText());
			double y = Double.parseDouble(jTextFieldSizeY.getText());
			double t = Double.parseDouble(jTextFieldSizeT.getText());

			if (x == 0.0 || y == 0.0 || t == 0.0)
			{
				JOptionPane.showMessageDialog(this, "The numbers must be greater than 0.", OmegaConstants.OMEGA_TITLE, JOptionPane.WARNING_MESSAGE);
				return;
			}

			originalSizes[0] = x;
			originalSizes[1] = y;
			originalSizes[2] = t;

			dispose();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Values must be numbers.", OmegaConstants.OMEGA_TITLE, JOptionPane.WARNING_MESSAGE);
		}
	}
}
