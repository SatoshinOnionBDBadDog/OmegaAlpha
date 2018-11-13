package ch.supsi.omega.exploration.chartframes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ChartMenu
{
	private JMenuBar	menuBar;
	
	public JMenuBar getMenuBar()
	{
		return menuBar;
	}

	public ChartMenu(final AbstractJFrameForChart chartFrame)
	{
		menuBar = new JMenuBar();

		JMenu fileMenu	= new JMenu("File");
		JMenu dataMenu = new JMenu("Data");
		
		menuBar.add(fileMenu);
		menuBar.add(dataMenu);

		// File
		JMenuItem closeAction = new JMenuItem("Close");
		closeAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

		fileMenu.add(closeAction);

		// Data
		JMenuItem displayAction = new JMenuItem("Display data...");
		displayAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		
		JMenuItem saveAction = new JMenuItem("Save data...");
		saveAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		dataMenu.add(displayAction);
		dataMenu.add(saveAction);

		// listeners
		closeAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				chartFrame.dispose();
			}
		});

		displayAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				chartFrame.displayData();
			}
		});
		
		saveAction.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				chartFrame.saveData();
			}
		});
	}
}
