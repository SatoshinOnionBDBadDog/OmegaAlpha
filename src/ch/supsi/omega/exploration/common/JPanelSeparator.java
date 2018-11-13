package ch.supsi.omega.exploration.common;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JPanelSeparator extends javax.swing.JPanel
{
	private static final long	serialVersionUID	= 9087223031127163266L;

	private JLabel					jLabelSeparation	= new JLabel();

	public JPanelSeparator()
	{
		initComponents();
	}

	private void initComponents()
	{
		setLayout(new BorderLayout());
		jLabelSeparation.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSeparation.setFont(new java.awt.Font("Cordia New", 0, 11));
		jLabelSeparation.setForeground(new Color(140, 140, 140));
		jLabelSeparation.setText("" +
				"--------------------------------------------------------------------------------------------------------------" +
				"--------------------------------------------------------------------------------------------------------------");
		add(jLabelSeparation, BorderLayout.CENTER);
	}
}
