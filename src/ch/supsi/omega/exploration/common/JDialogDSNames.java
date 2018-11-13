package ch.supsi.omega.exploration.common;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JDialogDSNames extends javax.swing.JDialog
{
	private static final long			serialVersionUID			= -7119313342748455613L;

	private JDialogResultsSelector	jDialogResultsSelector	= null;

	public JDialogDSNames(JDialogResultsSelector jDialogResultsSelector)
	{
		this.jDialogResultsSelector = jDialogResultsSelector;
		initComponents();
	}

	private void initComponents()
	{
		// @formatter:off
		jPanelCenter 			= new JPanel();
		jTextField1 			= new JTextField();
		jTextField2 			= new JTextField();
		jTextField3			 	= new JTextField();
		jTextField4 			= new JTextField();
		jTextField5 			= new JTextField();
		jPanelCenterBottom 	= new JPanel();
		jButtonOK 				= new JButton();
		jButtonCancel 			= new JButton();
		jPanel4 					= new JPanel();
		jPanel3 					= new JPanel();
		jPanel2 					= new JPanel();
		jPanel1 					= new JPanel();
		// @formatter:on

		setModal(true);
		setMinimumSize(new Dimension(200, 240));
		setResizable(false);
		setTitle("DS names");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// center panel
		jPanelCenter.setPreferredSize(new Dimension(0, 5));
		jPanelCenter.setLayout(new GridLayout(6, 1, 0, 5));

		jPanelCenter.add(jTextField1);
		jPanelCenter.add(jTextField2);
		jPanelCenter.add(jTextField3);
		jPanelCenter.add(jTextField4);
		jPanelCenter.add(jTextField5);

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

		addNames();

		pack();
	}

	private void addNames()
	{
		jTextField1.setText(JDialogResultsSelector.getDefaultDatasets()[0]);
		jTextField2.setText(JDialogResultsSelector.getDefaultDatasets()[1]);
		jTextField3.setText(JDialogResultsSelector.getDefaultDatasets()[2]);
		jTextField4.setText(JDialogResultsSelector.getDefaultDatasets()[3]);
		jTextField5.setText(JDialogResultsSelector.getDefaultDatasets()[4]);
	}

	private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)
	{
		JDialogResultsSelector.getDefaultDatasets()[0] = jTextField1.getText();
		JDialogResultsSelector.getDefaultDatasets()[1] = jTextField2.getText();
		JDialogResultsSelector.getDefaultDatasets()[2] = jTextField3.getText();
		JDialogResultsSelector.getDefaultDatasets()[3] = jTextField4.getText();
		JDialogResultsSelector.getDefaultDatasets()[4] = jTextField5.getText();
		jDialogResultsSelector.changeDatasetsNames();
		dispose();
	}

	private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)
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
	private JTextField	jTextField1;
	private JTextField	jTextField2;
	private JTextField	jTextField3;
	private JTextField	jTextField4;
	private JTextField	jTextField5;
}
