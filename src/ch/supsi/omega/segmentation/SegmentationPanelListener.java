package ch.supsi.omega.segmentation;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class SegmentationPanelListener extends MouseAdapter
{
	private SegmentationPanel	segmentationPanel	= null;
	private JPopupMenu			menu					= new JPopupMenu();

	public SegmentationPanelListener(SegmentationPanel segmentationPanel)
	{
		this.segmentationPanel = segmentationPanel;
		createPopupMenu();
	}

	private void createPopupMenu()
	{
		JMenuItem item = new JMenuItem("Zoom IN");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				segmentationPanel.setZoomFactor(segmentationPanel.getZoomFactor() * 1.50);
				centerAndRepaint();
			}
		});
		menu.add(item);

		item = new JMenuItem("Zoom OUT");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				segmentationPanel.setZoomFactor(segmentationPanel.getZoomFactor() / 1.50);
				centerAndRepaint();
			}
		});
		menu.add(item);

		item = new JMenuItem("Reset view");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				segmentationPanel.setOffsetX(0.0);
				segmentationPanel.setOffsetY(0.0);
				segmentationPanel.setZoomFactor(1.0);
				segmentationPanel.callRevalidate();
				segmentationPanel.calculateDrawedTrajectory();
				segmentationPanel.repaint();
			}
		});
		menu.add(item);
	}

	private void centerAndRepaint()
	{
		segmentationPanel.callRevalidate();
		segmentationPanel.calculateDrawedTrajectory();
		segmentationPanel.repaint();
	}

	/**
	 * Manages the mouse clicks.
	 */
	public void mousePressed(MouseEvent ev)
	{
		// if we are just plotting tracks, and not segment them
		if (!segmentationPanel.getSegmentationFrame().isCanSegment())
			return;

		// left click
		if (ev.getButton() == 1)
		{
			Point p = ev.getPoint();

			int temp = PointFinder.findPoint(segmentationPanel.getDrawingTrajectory().getPoints(), p.x, p.y);

			segmentationPanel.setClickedPoint(temp);
		}
		// right click
		if (ev.isPopupTrigger())
			menu.show(ev.getComponent(), ev.getX(), ev.getY());
	}

	public void mouseReleased(MouseEvent e)
	{
		// right click
		if (e.isPopupTrigger())
			menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
