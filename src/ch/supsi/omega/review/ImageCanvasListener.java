package ch.supsi.omega.review;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ImageCanvasListener extends MouseAdapter
{
	private ImageCanvas	imageCanvas	= null;
	private JPopupMenu	menu			= new JPopupMenu();

	public ImageCanvasListener(ImageCanvas imageCanvas)
	{
		this.imageCanvas = imageCanvas;
		createPopupMenu();
	}

	private void createPopupMenu()
	{
		JMenuItem item = new JMenuItem("Zoom IN");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{		
				imageCanvas.setScale(imageCanvas.getScale() * 2.0);
				
				imageCanvas.callRevalidate();
				
				imageCanvas.scaleTrajectories();
				imageCanvas.repaint();
			}
		});
		menu.add(item);

		item = new JMenuItem("Zoom OUT");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				imageCanvas.setScale(imageCanvas.getScale() / 2.0);
				
				imageCanvas.callRevalidate();
				
				imageCanvas.scaleTrajectories();
				imageCanvas.repaint();
			}
		});
		menu.add(item);
		
		item = new JMenuItem("Save image...");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				imageCanvas.saveImage();
			}
		});
		menu.add(item);
		
//		item = new JMenuItem("Save movie...");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{				
//				imageCanvas.saveMovie();
//			}
//		});
//		menu.add(item);
	}

	/**
	 * Manages the mouse clicks.
	 */
	public void mousePressed(MouseEvent ev)
	{
		// left click: back to the browser
		if (ev.getButton() == 1 & ev.getClickCount() == 2)
		{
			imageCanvas.getjPanelViewer().getReviewFrame().displayBrowser();
			imageCanvas.setTrajectories(null);
			imageCanvas.setTrajectoriesScaled(null);
			imageCanvas.setScale(1.0);
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		// right click
		if (e.isPopupTrigger())
			menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
