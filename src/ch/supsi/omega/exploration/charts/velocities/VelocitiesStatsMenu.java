package ch.supsi.omega.exploration.charts.velocities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class VelocitiesStatsMenu
{
	private JMenuBar	menuBar;

	public JMenuBar getMenuBar()
	{
		return menuBar;
	}

	public VelocitiesStatsMenu(final JFrameVelocitiesStats jFrameVelocitiesStats)
	{
		menuBar = new JMenuBar();

		JMenu dataMenu = new JMenu("Data");

		menuBar.add(dataMenu);

		JMenuItem saveAction = new JMenuItem("Save data...");
		saveAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		dataMenu.add(saveAction);

		// listeners
		saveAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				jFrameVelocitiesStats.saveData();
			}
		});
	}
}
