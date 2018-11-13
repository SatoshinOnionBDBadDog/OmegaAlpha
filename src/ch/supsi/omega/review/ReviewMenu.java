package ch.supsi.omega.review;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ReviewMenu {
	// private static final long serialVersionUID = 1L;

	private JMenuBar menuBar = null;
	private JMenuItem runAction = null;
	private JMenuItem batchAction = null;
	private JMenuItem exploreAction = null;
	private JMenuItem displayAllAction = null;

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public JMenuItem getRunAction() {
		return runAction;
	}

	public JMenuItem getBatchAction() {
		return batchAction;
	}

	public JMenuItem getExploreAction() {
		return exploreAction;
	}

	public JMenuItem getDisplayAll() {
		return displayAllAction;
	}

	public ReviewMenu(final ReviewFrame reviewFrame) {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenu sptMenu = new JMenu("SPT");
		menuBar.add(sptMenu);

		JMenu explorationMenu = new JMenu("Data Exploration");
		menuBar.add(explorationMenu);

		// File
		JMenuItem closeAction = new JMenuItem("Close");
		closeAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
		        ActionEvent.CTRL_MASK));
		fileMenu.add(closeAction);

		closeAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reviewFrame.dispose();
			}
		});

		// SPT
		runAction = new JMenuItem("Run SPT on one image");
		runAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
		        ActionEvent.CTRL_MASK));
		sptMenu.add(runAction);

		runAction.setEnabled(false);

		runAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reviewFrame.callSPT();
			}
		});

		batchAction = new JMenuItem("Run SPT batch processing");
		batchAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
		        ActionEvent.CTRL_MASK));
		sptMenu.add(batchAction);

		batchAction.setEnabled(true);

		batchAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reviewFrame.callBatchSPT();
			}
		});

		// Explore
		exploreAction = new JMenuItem("Display SPT tracks on this image");
		exploreAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
		        ActionEvent.CTRL_MASK));
		explorationMenu.add(exploreAction);

		exploreAction.setEnabled(false);

		exploreAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reviewFrame.callDataExploration();
			}
		});

		displayAllAction = new JMenuItem("Display all trajectories");
		displayAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
		        ActionEvent.CTRL_MASK));
		explorationMenu.add(displayAllAction);

		displayAllAction.setEnabled(false);

		displayAllAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ImageCanvas.getINSTANCE() != null) {
					ImageCanvas.getINSTANCE().setTrajectoryToDraw(-1);
					ImageCanvas.getINSTANCE().callRevalidate();
					ImageCanvas.getINSTANCE().repaint();
				}
			}
		});
	}
}
