package ch.supsi.omega.segmentation.overview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JPanel;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;
import ch.supsi.omega.common.science.Greeks;
import ch.supsi.omega.segmentation.TPoint;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class SegmentationOverviewPanel extends JPanel
{
	private static final long				serialVersionUID				= 5053540174048391329L;

	private SegmentationOverviewFrame	segmentationOverviewFrame	= null;

	private Trajectory						currentTrajectory				= null;

	public void setCurrentTrajectory(Trajectory currentTrajectory)
	{
		this.currentTrajectory = currentTrajectory;
	}

	public SegmentationOverviewPanel(SegmentationOverviewFrame segmentationOverviewFrame)
	{
		this.segmentationOverviewFrame = segmentationOverviewFrame;

		addMouseListener(new SegmentationOverviewPanelListener(this));
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		int pointSize = OmegaConstants.DRAWING_POINTSIZE;

		// can this be handled better?
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setColor(SystemColor.control);
		g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		g2D.setColor(Color.BLACK);

		drawAxys(g2D);
				
		if (currentTrajectory != null)
		{
			List<TPoint> points = currentTrajectory.getPoints();

			for (int i = 0; i < points.size() - 1; i++)
			{
				TPoint one = points.get(i);
				TPoint two = points.get(i + 1);
		
				double X1 = one.getX() / segmentationOverviewFrame.getImageOriginalX() * getWidth();
				double Y1 = one.getY() / segmentationOverviewFrame.getImageOriginalY() * getHeight(); 
				double X2 = two.getX() / segmentationOverviewFrame.getImageOriginalX() * getWidth();  
				double Y2 = two.getY() / segmentationOverviewFrame.getImageOriginalY() * getHeight(); 
												
				g2D.draw(new Line2D.Double(X1 + pointSize / 2, Y1 + pointSize / 2, X2 + pointSize / 2, Y2 + pointSize / 2));
			}
		}
	}

	private void drawAxys(Graphics2D g)
	{
		g.setColor(Color.GRAY);
		
		int x1, y1, x2, y2;
		
		int pFactor = 50;
		
		x1 = getWidth() / pFactor;
		y1 = getHeight() / pFactor;
		x2 = getWidth() / pFactor;
		y2 = getHeight() - getHeight() / pFactor;
		
		drawArrow(g, x1, y1, x2, y2);
		
		// information on Y pixels
		String yInformation;

		if(segmentationOverviewFrame.getImageOriginalSizeY() != 0.0)
		{
			yInformation = String.format("%s %sm", 
					StringHelper.DoubleToString(segmentationOverviewFrame.getImageOriginalSizeY() * segmentationOverviewFrame.getImageOriginalY(), 3),
					Greeks.MU
					);
		}
		else
			yInformation = String.format("%d px", segmentationOverviewFrame.getImageOriginalY());
		
		g.drawString(yInformation, x2+4, y2);
		
		
		x1 = getWidth() / pFactor;
		y1 = getHeight() / pFactor;
		x2 = getWidth() - getWidth() / pFactor;
		y2 = getHeight() / pFactor;
		
		drawArrow(g, x1, y1, x2, y2);
		
		// information on X pixels
		String xInformation;
				
		if(segmentationOverviewFrame.getImageOriginalSizeX() != 0.0)
		{
			xInformation = String.format("%s %sm", 
					StringHelper.DoubleToString(segmentationOverviewFrame.getImageOriginalSizeX() * segmentationOverviewFrame.getImageOriginalX(), 3),
					Greeks.MU
					);
			g.drawString(xInformation, x2-65, y1+15);
		}
		else
			g.drawString(String.valueOf(segmentationOverviewFrame.getImageOriginalX()) + " px", x2-40, y1+15);
		
		g.setColor(Color.BLACK);
	}
	
	private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2)
	{
		float arrowWidth = 10.0f;
		float theta = 0.423f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength;
		float th;
		float ta;
		float baseX, baseY;

		xPoints[0] = x2;
		yPoints[0] = y2;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - x1;
		vecLine[1] = (float) yPoints[0] - y1;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		g.drawLine(x1, y1, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
	}
}
