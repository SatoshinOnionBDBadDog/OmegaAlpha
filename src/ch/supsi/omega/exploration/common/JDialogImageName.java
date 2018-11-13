package ch.supsi.omega.exploration.common;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ch.supsi.omega.exploration.chartframes.JFrameImageFrequencies;

public class JDialogImageName extends javax.swing.JDialog
{
	private static final long			serialVersionUID			= -7119313342748455613L;

	private JFrameImageFrequencies	jFrameImageFrequencies	= null;

	private String							newImageName				= null;

	public String getNewImageName()
	{
		return newImageName;
	}

	public JDialogImageName(JFrameImageFrequencies jFrameImageFrequencies)
	{
		this.jFrameImageFrequencies = jFrameImageFrequencies;
		initComponents();
	}

	private void initComponents()
	{
		// @formatter:off
		jPanelCenter 			= new JPanel();
		jTextFieldImageName  = new JTextField();
		jPanelCenterBottom 	= new JPanel();
		jButtonOK 				= new JButton();
		jButtonCancel 			= new JButton();
		jPanel4 					= new JPanel();
		jPanel3 					= new JPanel();
		jPanel2 					= new JPanel();
		jPanel1 					= new JPanel();
		// @formatter:on

		jTextFieldImageName.setText(jFrameImageFrequencies.getCurrentImageName());

		setModal(true);
		setMinimumSize(new Dimension(200, 100));
		setResizable(false);
		setTitle("New image name");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// center panel
		jPanelCenter.setPreferredSize(new Dimension(0, 5));
		jPanelCenter.setLayout(new GridLayout(2, 1, 0, 5));

		jPanelCenter.add(jTextFieldImageName);

		jPanelCenterBottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		JLabel space = new JLabel();
		space.setPreferredSize(new Dimension(5, 5));

		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButtonOKActionPerformed(evt);
			}
		});
		jPanelCenterBottom.add(jButtonOK);

		jPanelCenterBottom.add(space);

		jButtonCancel.setText("Cancel");
		jButtonCancel.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButtonCancelActionPerformed(evt);
			}
		});
		jPanelCenterBottom.add(jButtonCancel);

		jPanelCenter.add(jPanelCenterBottom);

		getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

		// "borders"
		jPanel4.setPreferredSize(new java.awt.Dimension(5, 0));
		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 290, Short.MAX_VALUE));
		getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);
		jPanel3.setPreferredSize(new java.awt.Dimension(5, 0));
		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 290, Short.MAX_VALUE));
		getContentPane().add(jPanel3, java.awt.BorderLayout.WEST);
		jPanel2.setPreferredSize(new java.awt.Dimension(0, 5));
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
		jPanel1.setPreferredSize(new java.awt.Dimension(0, 5));
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 5, Short.MAX_VALUE));
		getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
		// ===

		pack();
	}

	private void jButtonOKActionPerformed(ActionEvent evt)
	{
		newImageName = jTextFieldImageName.getText();
		dispose();
	}

	private void jButtonCancelActionPerformed(ActionEvent evt)
	{
		dispose();
	}

	private JButton		jButtonCancel;
	private JButton		jButtonOK;
	private JPanel			jPanel1;
	private JPanel			jPanel2;
	private JPanel			jPanel3;
	private JPanel			jPanel4;
	private JPanel			jPanelCenter;
	private JPanel			jPanelCenterBottom;
	private JTextField	jTextFieldImageName;
}
