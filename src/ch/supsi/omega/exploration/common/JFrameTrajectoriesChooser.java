package ch.supsi.omega.exploration.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.supsi.omega.exploration.chartframes.JFrameVelocities;

public class JFrameTrajectoriesChooser extends javax.swing.JFrame
{
	private static final long	serialVersionUID	= 577144617958845881L;

	private JFrameVelocities	jFrameVelocities	= null;

	private JScrollPane			mainScrollPane		= null;

	private JPanel					mainPanel			= null;

	private int						trackIndex			= 1;

	final JCheckBox				jCheckBoxAll		= new JCheckBox();

	private List<JCheckBox>		jCheckBoxs			= null;

	private boolean				processDataset		= true;

	public List<JCheckBox> getjCheckBoxs()
	{
		return jCheckBoxs;
	}

	public JFrameTrajectoriesChooser(JFrameVelocities jFrameVelocities)
	{
		this.jFrameVelocities = jFrameVelocities;
		jCheckBoxs = new ArrayList<JCheckBox>();
		setLocation(jFrameVelocities.getLocation().x + jFrameVelocities.getWidth(), jFrameVelocities.getLocation().y);
		initComponents();
	}

	private void initComponents()
	{
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		jCheckBoxAll.setText("all trajectories     ");
		jCheckBoxAll.setSelected(true);
		jCheckBoxAll.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				processDataset = false;
				
				for (JCheckBox j : jCheckBoxs)
					j.setSelected(jCheckBoxAll.isSelected());
				
				processDataset = true;
				
				processDataset();
			}
		});

		mainPanel.add(jCheckBoxAll);
		
		setPreferredSize(new Dimension(180, 500));
	}

	public void addCheckBoxs(int checkboxNumber)
	{
		for (int i = 0; i < checkboxNumber; i++)
		{
			JCheckBox jCheckBox = new JCheckBox();
			jCheckBox.setText("trajectory " + trackIndex++);
			jCheckBox.setSelected(true);

			jCheckBox.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent e)
				{
					processDataset();
				}
			});

			jCheckBoxs.add(jCheckBox);
		}

		for (JCheckBox j : jCheckBoxs)
			mainPanel.add(j);

		mainScrollPane = new JScrollPane(mainPanel);
		getContentPane().add(mainScrollPane, BorderLayout.CENTER);

		pack();
	}
	
	private void processDataset()
	{
		if(processDataset)
			jFrameVelocities.processDataset();
	}
	
	public void setIndexFromExternalFrame(int index)
	{
		processDataset = false;
		
		jCheckBoxAll.setSelected(false);
		
		for (JCheckBox j : jCheckBoxs)
			j.setSelected(false);
		
		jCheckBoxs.get(index).setSelected(true);
		
		processDataset = true;
		
		processDataset();
		
	}
}
