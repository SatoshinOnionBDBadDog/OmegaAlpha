package ch.supsi.omega.review;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pojos.ImageData;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;

/**
 * JPanel to display the images in the OMERO datasets thumbnailed.
 * 
 * @author galliva
 */
class JPanelBrowser extends JPanel {
	private static final long serialVersionUID = 7625488987526070516L;

	/**
	 * The frame who called this JPanel.
	 */
	private ReviewFrame reviewFrame = null;

	/**
	 * List of Images to be processed by the SPT algorithm.
	 */
	private ArrayList<ImageData> toBeProcessed = null;

	public ArrayList<ImageData> getToBeProcessed() {
		return toBeProcessed;
	}

	/**
	 * Create a new instance of this JPanel.
	 */
	public JPanelBrowser(ReviewFrame reviewFrame) {
		this.reviewFrame = reviewFrame;

		setDoubleBuffered(true);
		setBackground(Color.white);
		Dimension d = new Dimension(400, 400);
		setSize(d);
		setPreferredSize(d);

		setLayout(new FlowLayout(FlowLayout.LEFT));
	}

	/**
	 * Sets the images to display.
	 * 
	 * @param images
	 *            the images to display.
	 */
	void setImages(ArrayList<ImageInfo> imageInfo) {
		removeAll();

		toBeProcessed = new ArrayList<ImageData>();

		Iterator<ImageInfo> iterator = imageInfo.iterator();

		while (iterator.hasNext()) {
			final ImageInfo temp = iterator.next();

			String imageName = StringHelper.getImageName(temp.getImageName());

			JPanel jPanelImageInfo = new JPanel();
			jPanelImageInfo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			jPanelImageInfo.setSize(new Dimension(temp.getImage().getWidth(),
			        OmegaConstants.THUMBNAIL_SIZE + 40));
			jPanelImageInfo.setPreferredSize(new Dimension(temp.getImage()
			        .getWidth(), OmegaConstants.THUMBNAIL_SIZE + 40));

			Dimension d = new Dimension(temp.getImage().getWidth(), 20);

			// image
			SingleImagePanel singleImagePanel = new SingleImagePanel(
			        temp.getImageID(), temp.getImageName(), temp.getImage());

			// image name
			JLabel jLabelName = new JLabel();
			jLabelName.setSize(d);
			jLabelName.setPreferredSize(d);
			jLabelName.setText(imageName);
			jLabelName.setFont(new Font("Tahoma", 0, 10));

			// SPT check
			JPanel jPanelSPTCheck = new JPanel();
			jPanelSPTCheck.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			jPanelSPTCheck.setSize(d);
			jPanelSPTCheck.setPreferredSize(d);
			JCheckBox checked = new JCheckBox();
			checked.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent itemEvent) {
					int state = itemEvent.getStateChange();
					if (state == ItemEvent.SELECTED) {
						toBeProcessed.add(temp.getImageData());
					} else if (state == ItemEvent.DESELECTED) {
						toBeProcessed.remove(temp.getImageData());
					}
				}
			});
			jPanelSPTCheck.add(checked);

			jPanelImageInfo.add(singleImagePanel);
			jPanelImageInfo.add(jLabelName);
			jPanelImageInfo.add(jPanelSPTCheck);

			add(jPanelImageInfo);
		}

		validate();

		repaint();
	}

	private class SingleImagePanel extends JPanel {
		private static final long serialVersionUID = 7924490362486444828L;

		private Long imageID = 0L;
		private BufferedImage image = null;

		public SingleImagePanel(Long imageID, String imageName,
		        BufferedImage image) {
			this.imageID = imageID;
			this.image = image;

			setSize(new Dimension(image.getWidth(),
			        OmegaConstants.THUMBNAIL_SIZE));
			setPreferredSize(new Dimension(image.getWidth(),
			        OmegaConstants.THUMBNAIL_SIZE));

			setToolTipText(imageName);

			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					jPanelRightMouseClicked(evt);
				}
			});
		}

		private void jPanelRightMouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2)
				reviewFrame.displayViewer(imageID);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;

			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setRenderingHint(RenderingHints.KEY_RENDERING,
			        RenderingHints.VALUE_RENDER_QUALITY);
			g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			// layout the images
			g2D.setColor(getBackground());
			g2D.fillRect(0, 0, getWidth(), getHeight());

			int x = 0;
			int y = 0;
			int w = 0;
			int h = 0;
			int width = getWidth();
			int maxY = 0;
			int gap = 2;

			h = image.getHeight();
			w = image.getWidth();

			if (maxY < h)
				maxY = h;

			if (x != 0) {
				if (x + w > width) {
					x = 0;
					y += maxY;
					y += gap;
					maxY = 0;
				}
			}

			g2D.drawImage(image, x, y, null);
			x += w;
			x += gap;
		}
	}
}
