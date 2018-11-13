package ch.supsi.omega.segmentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JPanel;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;
import ch.supsi.omega.segmentation.pattern.Pattern;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

import com.galliva.gallibrary.GLogManager;

class SegmentationPanel extends JPanel implements ComponentListener
{
	private static final long	serialVersionUID	= 4730925328387461319L;

	/**
	 * The SegmentationFrame who has instantiate this SegmentationPanel.
	 */
	private SegmentationFrame	segmentationFrame	= null;

	private Trajectory			currentTrajectory	= null;
	private Trajectory			drawingTrajectory	= null;

	protected int					sizeX					= 600;
	protected int					sizeY					= 600;
	protected double				border				= 20.0;

	private boolean				drawLine				= true;
	private boolean				drawLabels			= true;
	private boolean				drawPoints			= false;
	private boolean				drawFrames			= false;
	// to be calculated at least one time
	private double					scaleX				= 0.0;
	private double					scaleY				= 0.0;

	private double					zoomFactor			= 1.0;
	private double					offsetX				= 0.0;
	private double					offsetY				= 0.0;

	private int						clickNumber			= 0;
	private int						indexOne				= -1;
	private int						indexTwo				= -1;

	public SegmentationFrame getSegmentationFrame()
	{
		return segmentationFrame;
	}

	public void setSegmentationFrame(SegmentationFrame segmentationFrame)
	{
		this.segmentationFrame = segmentationFrame;
	}

	public Trajectory getCurrentTrajectory()
	{
		return currentTrajectory;
	}

	public void setCurrentTrajectory(Trajectory currentTrajectory)
	{
		this.currentTrajectory = currentTrajectory;
	}

	public Trajectory getDrawingTrajectory()
	{
		return drawingTrajectory;
	}

	public boolean isDrawLine()
	{
		return drawLine;
	}

	public void setDrawLine(boolean drawLine)
	{
		this.drawLine = drawLine;
	}

	public boolean isDrawLabels()
	{
		return drawLabels;
	}

	public void setDrawLabels(boolean drawLabels)
	{
		this.drawLabels = drawLabels;
	}

	public boolean isDrawPoints()
	{
		return drawPoints;
	}

	public void setDrawPoints(boolean drawPoints)
	{
		this.drawPoints = drawPoints;
	}

	public boolean isDrawFrames()
	{
		return drawFrames;
	}

	public void setDrawFrames(boolean drawFrames)
	{
		this.drawFrames = drawFrames;
	}

	public double getScaleX()
	{
		return scaleX;
	}

	public void setScaleX(double scaleX)
	{
		this.scaleX = scaleX;
	}

	public double getScaleY()
	{
		return scaleY;
	}

	public void setScaleY(double scaleY)
	{
		this.scaleY = scaleY;
	}

	public double getZoomFactor()
	{
		return zoomFactor;
	}

	public void setZoomFactor(double zoomFactor)
	{
		this.zoomFactor = zoomFactor;
	}

	public double getOffsetX()
	{
		return offsetX;
	}

	public void setOffsetX(double offsetX)
	{
		this.offsetX = offsetX;
	}

	public double getOffsetY()
	{
		return offsetY;
	}

	public void setOffsetY(double offsetY)
	{
		this.offsetY = offsetY;
	}

	public int getClickNumber()
	{
		return clickNumber;
	}

	public void setClickNumber(int clickNumber)
	{
		this.clickNumber = clickNumber;
	}

	public int getIndexOne()
	{
		return indexOne;
	}

	public void setIndexOne(int indexOne)
	{
		this.indexOne = indexOne;
	}

	public int getIndexTwo()
	{
		return indexTwo;
	}

	public void setIndexTwo(int indexTwo)
	{
		this.indexTwo = indexTwo;
	}

	
	/**
	 * Initializes the SegmentationPanel.
	 * @param segmentationFrame the SegmentationFrame who has instantiate this
	 *        SegmentationPanel
	 */
	public SegmentationPanel(SegmentationFrame segmentationFrame)
	{
		super();

		this.segmentationFrame = segmentationFrame;

		Dimension preferred = new Dimension(OmegaConstants.SEGMENTATION_PANEL_WIDTH, OmegaConstants.SEGMENTATION_PANEL_HEIGHT);
		setPreferredSize(preferred);
		
		addComponentListener(this);
		addMouseListener(new SegmentationPanelListener(this));
	}

	public void setClickedPoint(int index)
	{
		if (clickNumber == 0)
		{
			indexOne = index;
			clickNumber = 1;
		}
		else if (clickNumber == 1)
		{
			indexTwo = index;
			swapIndexs();
			clickNumber = 2;
		}
		repaint();
	}

	private void swapIndexs()
	{
		if (indexOne > indexTwo)
		{
			int temp;
			temp = indexOne;
			indexOne = indexTwo;
			indexTwo = temp;
		}
	}

	public void undoMouseClicks()
	{
		clickNumber = 0;
		indexOne = -1;
		indexTwo = -1;
	}

	public void resetView()
	{
		zoomFactor = 1.0;
		offsetX = 0.0;
		offsetY = 0.0;
	}

	/**
	 * Draws the trajectory.
	 */
	public void paint(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int pointSize = OmegaConstants.DRAWING_POINTSIZE;

		List<TPoint> points = drawingTrajectory.getPoints();
		
		// We draw the frame number every 5 points, starting from the first point
		// The showed number will be the frame number of the TPoint!
		int drawIndex = 0;
		
		for (int i = 0; i < points.size(); i++)
		{
			TPoint one = points.get(i);
			
			// drawing of START / END
			if(i==0 && drawLabels)
			{
				g2.setColor(SegmentationColor.TEXT);
				g2.drawString("S", (int)one.getX(), (int)one.getY());
			}
			if(i==points.size()-1 && drawLabels)
			{
				g2.setColor(SegmentationColor.TEXT);
				g2.drawString("E", (int)one.getX(), (int)one.getY());
			}
			
			// drawing of the points
			if (drawPoints)
			{
				// check if the point was clicked
				if (one.getID() == indexOne + 1 || one.getID() == indexTwo + 1)
					g2.setColor(SegmentationColor.POINT_CLICKED);
				else
					g2.setColor(SegmentationColor.POINT);

				g2.fill(new Ellipse2D.Double(one.getX(), one.getY(), pointSize, pointSize));
			}
			
			// drawing of the frames number
			if (drawFrames & (drawIndex % 5) == 0)
			{
				int frame = one.getFrame();
				
				String fText = String.valueOf(frame);
				
				if(segmentationFrame.getSptExecutionInfo() != null)
				{
					double sizeT = segmentationFrame.getSptExecutionInfo().getImageData().getSizeT();				
				
					if(sizeT > 0.0)
						fText = StringHelper.DoubleToString(frame * sizeT, 2) + "s";
				}
				
				g2.setColor(SegmentationColor.TEXT);
				g.drawString(fText, (int) (one.getX() + pointSize * 2), (int) (one.getY()));
			}
			
			// drawing of the labels
			if (drawLine && i < points.size() - 1)
			{
				TPoint two = points.get(i + 1);

				// set the right color for the labels
				g2.setColor(colorLabel(i));

				g2.draw(new Line2D.Double(one.getX() + pointSize / 2, one.getY() + pointSize / 2, two.getX() + pointSize / 2, two.getY() + pointSize / 2));
			}
			
			drawIndex++;
		}
	}

	/**
	 * Calculates the Trajectory drawed on the screen.
	 */
	public void calculateDrawedTrajectory()
	{
		drawingTrajectory = null;

		try
		{
			drawingTrajectory = (Trajectory) currentTrajectory.clone();

			double normalizedMaxX = currentTrajectory.getTrajectoryInformation().getNormalizedMaxX();
			double normalizedMaxY = currentTrajectory.getTrajectoryInformation().getNormalizedMaxY();
			
			double normalizedMax = (normalizedMaxX >= normalizedMaxY) ? normalizedMaxX : normalizedMaxY;
			
			scaleX = (sizeX - 2 * border) / normalizedMax;
			scaleY = (sizeY - 2 * border) / normalizedMax;

			for (TPoint p : drawingTrajectory.getPoints())
			{
				p.setX(p.getX() * scaleX + offsetX + border);
				p.setY(p.getY() * scaleY + offsetY + border);
			}
		}
		catch (NullPointerException e)
		{
			GLogManager.log(OmegaConstants.ERROR_DRAWING + " " + e.toString(), Level.SEVERE);
		}
		catch (CloneNotSupportedException ee)
		{
			GLogManager.log(OmegaConstants.ERROR_DRAWING + " " + ee.toString(), Level.SEVERE);
		}
	}

	/**
	 * Colors the i-label.
	 * @param i the index of the label
	 */
	private Color colorLabel(int i)
	{
		try
		{
			for (Pattern pattern : segmentationFrame.getPatterns())
			{
				if (currentTrajectory.getLabels()[i] == pattern.getId())
					return pattern.getColor();
			}
		}
		catch (Exception e)
		{

		}
		return SegmentationColor.STANDARD;
	}

	public void callRevalidate()
	{
		Dimension newDimension = new Dimension(
				(int)(OmegaConstants.SEGMENTATION_PANEL_WIDTH  * zoomFactor), 
				(int)(OmegaConstants.SEGMENTATION_PANEL_HEIGHT * zoomFactor));
		
		setPreferredSize(newDimension);
		revalidate();
	}
	
	@Override
	public void componentResized(ComponentEvent arg0)
	{
		sizeX = getWidth();
		sizeY = getHeight();
		calculateDrawedTrajectory();
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent arg0)
	{
		// no override here
	}

	@Override
	public void componentMoved(ComponentEvent arg0)
	{
		// no override here
	}

	@Override
	public void componentShown(ComponentEvent arg0)
	{
		// no override here
	}
}