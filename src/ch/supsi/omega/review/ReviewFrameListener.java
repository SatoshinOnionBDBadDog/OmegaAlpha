package ch.supsi.omega.review;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ReviewFrameListener implements WindowListener
{
	private ReviewFrame	reviewFrame	= null;

	public ReviewFrameListener(ReviewFrame reviewFrame)
	{
		super();
		this.reviewFrame = reviewFrame;
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{

	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		try
		{
			reviewFrame.getEngine().close();
		}
		catch (Exception e)
		{
			// nothing we can do...
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
	}
}
