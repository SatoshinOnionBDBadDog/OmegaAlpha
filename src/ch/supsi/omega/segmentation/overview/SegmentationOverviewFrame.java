package ch.supsi.omega.segmentation.overview;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

import ch.supsi.omega.tracking.parameters.SPTExecutionInfoHandler;

public class SegmentationOverviewFrame extends JFrame
{
	private static final long				serialVersionUID				= -86011842615275413L;

	private SegmentationOverviewPanel	segmentationOverviewPanel	= null;

	// image data coming from the SPT_info.txt file
	private int									imageOriginalX					= 0;
	private int									imageOriginalY					= 0;
	private double								imageOriginalSizeX			= 0.0;
	private double								imageOriginalSizeY			= 0.0;

	public SegmentationOverviewPanel getSegmentationOverviewPanel()
	{
		return segmentationOverviewPanel;
	}
	public int getImageOriginalX()
	{
		return imageOriginalX;
	}
	public int getImageOriginalY()
	{
		return imageOriginalY;
	}
	public double getImageOriginalSizeX()
	{
		return imageOriginalSizeX;
	}

	public double getImageOriginalSizeY()
	{
		return imageOriginalSizeY;
	}

	/**
	 * Instantiates this JFrame.
	 * @param xPosition the X position of this frame
	 * @param yPosition the Y position of this frame
	 */
	public SegmentationOverviewFrame(int xPosition, int yPosition, SPTExecutionInfoHandler sptExecutionInfoHandler)
	{
		setLocation(xPosition, yPosition);

		if (sptExecutionInfoHandler != null)
		{
			setTitle(sptExecutionInfoHandler.getImageData().getImageName());

			imageOriginalX = sptExecutionInfoHandler.getImageData().getX();
			imageOriginalY = sptExecutionInfoHandler.getImageData().getY();
			imageOriginalSizeX = sptExecutionInfoHandler.getImageData().getSizeX();
			imageOriginalSizeY = sptExecutionInfoHandler.getImageData().getSizeY();
		}

		initComponents();
	}

	private void initComponents()
	{
		segmentationOverviewPanel = new SegmentationOverviewPanel(this);

		Dimension panelDimension = new Dimension(imageOriginalX, imageOriginalY);

		segmentationOverviewPanel.setPreferredSize(panelDimension);

		GroupLayout jPanel1Layout = new GroupLayout(segmentationOverviewPanel);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
		segmentationOverviewPanel.setLayout(jPanel1Layout);

		getContentPane().add(segmentationOverviewPanel, BorderLayout.CENTER);

		pack();
	}
}