package org.openmicroscopy.shoola.examples.viewer;

// java imports
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// third-party libraries

// application-internal dependencies

/**
 * Paints the image.
 */
class ImageCanvas extends JPanel 
{
	private static final long serialVersionUID = -2321440745146284043L;
	
	/** The image to display. */
	private BufferedImage image;

	/** Creates a new instance. */
	ImageCanvas() {
		setDoubleBuffered(true);
	}

	/**
	 * Sets the image.
	 * 
	 * @param image
	 *            The image to paint.
	 */
	void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	/**
	 * Overridden to paint the image.
	 * 
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	public void paintComponent(Graphics g) {
		if (image == null)
			return;
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2D.drawImage(image, null, 0, 0);
	}

}
