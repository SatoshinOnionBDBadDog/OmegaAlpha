package ch.supsi.omega.segmentation.overview;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import javax.swing.JMenuItem;
//import javax.swing.JPopupMenu;

public class SegmentationOverviewPanelListener extends MouseAdapter
{
//	private SegmentationOverviewPanel	segmentationOverviewPanel	= null;
//	private JPopupMenu						menu								= new JPopupMenu();
//
//	private int									currentX							= 0;
//	private int									currentY							= 0;

	public SegmentationOverviewPanelListener(SegmentationOverviewPanel segmentationOverviewPanel)
	{
		//this.segmentationOverviewPanel = segmentationOverviewPanel;
		//createPopupMenu();
	}
//
//	private void createPopupMenu()
//	{
//		JMenuItem item = new JMenuItem("Center");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				double newOffsetX = segmentationOverviewPanel.getOffsetX() + segmentationOverviewPanel.getWidth()  / 2 - currentX;
//				double newOffsetY = segmentationOverviewPanel.getOffsetY() + segmentationOverviewPanel.getHeight() / 2 - currentY;
//
//				newOffsetX = newOffsetX / segmentationOverviewPanel.getZoomFactor();
//				newOffsetY = newOffsetY / segmentationOverviewPanel.getZoomFactor();
//				
//				segmentationOverviewPanel.setOffsetX(newOffsetX);
//				segmentationOverviewPanel.setOffsetY(newOffsetY);
//				segmentationOverviewPanel.repaint();
//			}
//		});
//		menu.add(item);
//
//		item = new JMenuItem("Zoom IN");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{											
////				double newOffsetX = segmentationOverviewPanel.getWidth()  / 2 - currentX * segmentationOverviewPanel.getZoomFactor();
////				double newOffsetY = segmentationOverviewPanel.getHeight() / 2 - currentY * segmentationOverviewPanel.getZoomFactor();
//				segmentationOverviewPanel.setZoomFactor(segmentationOverviewPanel.getZoomFactor() * 2.0);
//				segmentationOverviewPanel.repaint();
//			}
//		});
//		menu.add(item);
//
//		item = new JMenuItem("Zoom OUT");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				segmentationOverviewPanel.setZoomFactor(segmentationOverviewPanel.getZoomFactor() / 2.0);
//				segmentationOverviewPanel.repaint();
//			}
//		});
//		menu.add(item);
//
//		item = new JMenuItem("Reset view");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				segmentationOverviewPanel.setOffsetX(0.0);
//				segmentationOverviewPanel.setOffsetY(0.0);
//				segmentationOverviewPanel.setZoomFactor(1.0);
//				segmentationOverviewPanel.repaint();
//			}
//		});
//		menu.add(item);
//	}
//
//	/**
//	 * Manages the mouse clicks.
//	 */
//	public void mousePressed(MouseEvent ev)
//	{
//		// right click
//		if (ev.isPopupTrigger())
//		{
//			currentX = ev.getX();
//			currentY = ev.getY();
//			menu.show(ev.getComponent(), ev.getX(), ev.getY());
//		}
//	}
//
//	public void mouseReleased(MouseEvent e)
//	{
//		// right click
//		if (e.isPopupTrigger())
//		{
//			currentX = e.getX();
//			currentY = e.getY();
//			menu.show(e.getComponent(), e.getX(), e.getY());
////			System.out.println("x: " + currentX);
////			System.out.println("y: " + currentY);
//		}
//	}
}
