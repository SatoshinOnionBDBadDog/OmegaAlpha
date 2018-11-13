package ch.supsi.omega.exploration.common;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.gui.JPanelTV;

public class JFrameColorChooser extends JFrame implements ChangeListener
{
	private static final long	serialVersionUID	= 6434309454843164940L;

	private JPanelTV jPanelExploration = null;
	
	private JColorChooser		jColorChooser		= null;


	public JFrameColorChooser(JPanelTV jPanelExploration)
	{
		this.jPanelExploration = jPanelExploration;
		initComponents();
	}

	private void initComponents()
	{
		jColorChooser = new JColorChooser();

		setTitle(OmegaConstants.REVIEW_COLOR_CHOOSER_FRAME);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().add(jColorChooser, java.awt.BorderLayout.CENTER);

		jColorChooser.getSelectionModel().addChangeListener(this);

		pack();
	}

	public void stateChanged(ChangeEvent e)
	{
		Color newColor = jColorChooser.getColor();
		jPanelExploration.setTrajectoryColor(newColor);
	}
}
