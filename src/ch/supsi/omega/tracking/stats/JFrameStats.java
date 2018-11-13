package ch.supsi.omega.tracking.stats;

import javax.swing.WindowConstants;

import com.lowagie.text.Font;

public class JFrameStats extends javax.swing.JFrame
{
	private static final long	serialVersionUID	= 12966958861725399L;

	private javax.swing.JScrollPane	jScrollPane1;
	private javax.swing.JTextArea		jTextArea1;
	
	public javax.swing.JTextArea getjTextArea1()
	{
		return jTextArea1;
	}
	
	public JFrameStats()
	{
		initComponents();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void initComponents()
	{
		setTitle("SPT - Results summary");
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextArea1.setColumns(50);
		jTextArea1.setRows(25);
		jScrollPane1.setViewportView(jTextArea1);

		java.awt.Font font = new java.awt.Font("Courier", Font.NORMAL, 12);
		jTextArea1.setFont(font);
		
		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pack();
	}
}	
