package ch.supsi.omega.gui.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.supsi.omega.common.OmegaConstants;

public class InfoLabel extends JLabel
{
	private static final long	serialVersionUID	= -3024082821922160439L;

	private JComponent			parentComponent	= null;

	public InfoLabel(final String tooltipText)
	{
		initInfoLabel(tooltipText);
	}

	public InfoLabel(JPanel parentComponent, final String tooltipText)
	{
		this.parentComponent = parentComponent;
		initInfoLabel(tooltipText);
	}

	private void initInfoLabel(final String tooltipText)
	{
		setIcon(new javax.swing.ImageIcon(getClass().getResource("/ch/supsi/omega/gui/resources/info_icon.jpg")));
		//setToolTipText(tooltipText);

		addListener(tooltipText);
	}

	private void addListener(final String tooltipText)
	{
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				JOptionPane.showMessageDialog(parentComponent, tooltipText, OmegaConstants.OMEGA_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}