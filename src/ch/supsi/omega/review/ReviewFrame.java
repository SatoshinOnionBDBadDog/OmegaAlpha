package ch.supsi.omega.review;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import omero.RDouble;
import omero.api.RenderingEnginePrx;
import omero.model.IObject;
import omero.model.PlaneInfoI;
import pojos.DatasetData;
import pojos.ImageData;
import pojos.PixelsData;
import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;
import ch.supsi.omega.gui.JPanelReview;
import ch.supsi.omega.omero.Gateway;
import ch.supsi.omega.tracking.JDialogSPTBatch;
import ch.supsi.omega.tracking.parameters.ImageDataHandler;
import ch.supsi.omega.tracking.parameters.OmeroParametersHandler;

import com.galliva.gallibrary.GLogManager;

/**
 * Displays the Omero's datasets.
 * 
 * @author galliva
 */
public class ReviewFrame extends JFrame {
	private static final long serialVersionUID = 8195320673246225331L;

	private static final String TITLE = OmegaConstants.OMEGA_TITLE + " - "
	        + OmegaConstants.OMEGA_REVIEW_TITLE;

	// the JPanelReview object who has called this frame
	private JPanelReview jPanelReview = null;

	// the menu of this JPanel
	private ReviewMenu reviewMenu = null;

	// JPanels of this frame
	private JPanel jPanelMain;
	private JPanel jPanelBrowser;
	private JPanelViewer jPanelViewer;

	// entry point to access the various services
	private Gateway gateway;

	// reference to the Rendering Engine
	RenderingEnginePrx engine;

	// all the images owned by the user currently logged on
	List<ImageData> images = null;

	// the component displaying the images
	private JPanelBrowser browserPanel = null;

	// the collection of already loaded datasets
	private Map<Long, ArrayList<ImageInfo>> loadedDatasets = new HashMap<Long, ArrayList<ImageInfo>>();

	// the current browsed dataset
	private String currentBrowsedDataset = "";

	public Gateway getGateway() {
		return gateway;
	}

	public RenderingEnginePrx getEngine() {
		return engine;
	}

	/**
	 * Creates a new ReviewFrame instance.
	 * 
	 * @param gateway
	 *            an OMERO API Gateway object
	 */
	public ReviewFrame(JPanelReview jPanelReview, Gateway gateway) {
		this.jPanelReview = jPanelReview;
		this.gateway = gateway;

		initComponents();

		getOmeroImages();
	}

	/**
	 * Creates a new ReviewFrame instance.
	 * 
	 * @param gateway
	 *            an OMERO API Gateway object
	 * @param xPosition
	 *            the x position of the frame
	 * @param yPosition
	 *            the y position of the frame
	 */
	public ReviewFrame(JPanelReview jPanelReview, Gateway gateway,
	        int xPosition, int yPosition) {
		this.jPanelReview = jPanelReview;
		this.gateway = gateway;

		this.setLocation(xPosition, yPosition);

		initComponents();

		getOmeroImages();
	}

	private void initComponents() {
		setTitle(TITLE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(750, 400));

		reviewMenu = new ReviewMenu(this);
		setJMenuBar(reviewMenu.getMenuBar());

		jPanelMain = new JPanel();
		jPanelMain.setSize(OmegaConstants.REVIEW_PANEL_SIZE);
		jPanelMain.setPreferredSize(OmegaConstants.REVIEW_PANEL_SIZE);
		jPanelMain.setLayout(new CardLayout());
		getContentPane().add(jPanelMain, BorderLayout.CENTER);

		jPanelBrowser = displayDatasets();
		jPanelViewer = new JPanelViewer(this);

		jPanelMain.add(jPanelBrowser, "A");
		jPanelMain.add(jPanelViewer, "B");

		addWindowListener(new ReviewFrameListener(this));

		pack();
		setVisible(true);
	}

	/**
	 * Gets all the images owned by the user currently logged on.
	 */
	private void getOmeroImages() {
		try {
			images = gateway.getImages();
		} catch (Exception e) {
			GLogManager.log(
			        "unable to load the images from the gateway: "
			                + e.toString(), Level.SEVERE);
		}
	}

	/**
	 * Displays the browser panel.
	 */
	public void displayBrowser() {
		CardLayout cl = (CardLayout) (jPanelMain.getLayout());
		cl.show(jPanelMain, "A");
		setTitle(TITLE);
		reviewMenu.getRunAction().setEnabled(false);
		reviewMenu.getBatchAction().setEnabled(true);
		reviewMenu.getExploreAction().setEnabled(false);
		reviewMenu.getDisplayAll().setEnabled(false);
	}

	/**
	 * Displays the viewer panel.
	 * 
	 * @param imageID
	 *            the ID of the image to display
	 */
	public void displayViewer(Long imageID) {
		if (viewImage(imageID)) {
			CardLayout cl = (CardLayout) (jPanelMain.getLayout());
			cl.show(jPanelMain, "B");
			reviewMenu.getRunAction().setEnabled(true);
			reviewMenu.getBatchAction().setEnabled(false);
			reviewMenu.getExploreAction().setEnabled(true);
			reviewMenu.getDisplayAll().setEnabled(true);
		}
	}

	private boolean viewImage(Long imageID) {
		if (images == null) {
			JOptionPane.showMessageDialog(null,
			        OmegaConstants.ERROR_UNABLE_TO_DISPLAY_IMAGE,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Iterator<ImageData> i = images.iterator();

			while (i.hasNext()) {
				ImageData image = i.next();
				if (image.getId() == imageID) {
					engine = gateway.loadRenderingControl(image
					        .getDefaultPixels().getId());
					jPanelViewer.setRenderingControl(image, engine);
					setTitle(TITLE + " - "
					        + StringHelper.getImageName(image.getName()));
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
			        OmegaConstants.ERROR_UNABLE_TO_DISPLAY_IMAGE,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			GLogManager.log("unable to find the imagedata: " + e.toString(),
			        Level.SEVERE);
			return false;
		}
		return true;
	}

	/**
	 * Loads and displays the datasets owned by the user currently logged in.
	 * 
	 * @return See above.
	 */
	private JPanel displayDatasets() {
		// retrieve the datasets
		List<DatasetData> datasets = null;
		try {
			datasets = gateway.getDatasets(null);
		} catch (Exception e) {

		}

		// iterate all the datasets
		Iterator<DatasetData> i = datasets.iterator();
		DatasetData dataset;

		final DatasetInfo[] datasetInfo = new DatasetInfo[datasets.size()];

		int index = 0;
		while (i.hasNext()) {
			dataset = i.next();
			datasetInfo[index] = new DatasetInfo(dataset.getId(),
			        dataset.getName());
			index++;
		}

		browserPanel = new JPanelBrowser(this);

		JComboBox jComboBoxDatasets = new JComboBox(datasetInfo);

		jComboBoxDatasets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox box = (JComboBox) e.getSource();
				currentBrowsedDataset = datasetInfo[box.getSelectedIndex()]
				        .toString();
				browseDataset(datasetInfo[box.getSelectedIndex()].getId());
			}
		});

		// when the Frame is loaded, display the first dataset
		jComboBoxDatasets.setSelectedIndex(0);

		// top panel
		JPanel jPanelTop = new JPanel();
		jPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanelTop.add(new JLabel("Select image dataset you wish to analyze:"));
		jPanelTop.add(jComboBoxDatasets);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(jPanelTop, BorderLayout.NORTH);

		JScrollPane jScrollPane = new JScrollPane(browserPanel);
		jScrollPane
		        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane
		        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		content.add(jScrollPane, BorderLayout.CENTER);

		return content;
	}

	/**
	 * Browses the specified datasets.
	 * 
	 * @param id
	 *            The identifier of the dataset.
	 */
	@SuppressWarnings({ "unchecked" })
	private void browseDataset(long id) {
		boolean error = false;

		// check if dataset was already loaded
		if (loadedDatasets.containsKey(id)) {
			browserPanel.setImages(loadedDatasets.get(id));
			return;
		}

		// load the image in the dataset
		List<Long> ids = new ArrayList<Long>(1);
		ids.add(id);
		List<DatasetData> datasets = null;

		try {
			datasets = gateway.getDatasets(ids);
		} catch (Exception e) {
			// handle exception
		}

		if (datasets == null || datasets.size() != 1) {
			JOptionPane.showMessageDialog(this,
			        "Cannot browse the selected dataset.");
			return;
		}

		DatasetData d = datasets.get(0);
		Collection<ImageData> images = d.getImages();

		// creates an ArrayList of ImageInfo
		ArrayList<ImageInfo> imageInfo = new ArrayList<ImageInfo>();

		Iterator<ImageData> i = images.iterator();
		while (i.hasNext()) {
			ImageData image = i.next();

			Long pixel = image.getDefaultPixels().getId();

			List<Long> pixels = new ArrayList<Long>();
			pixels.add(pixel);

			List<BufferedImage> l = null;

			try {
				l = gateway.getThumbnailSet(pixels,
				        OmegaConstants.THUMBNAIL_SIZE);
				imageInfo.add(new ImageInfo(pixel, image, image.getName(), l
				        .get(0)));
			} catch (Exception e) {
				error = true;
			}
		}

		if (error) {
			JOptionPane.showMessageDialog(null,
			        OmegaConstants.ERROR_LOADING_THE_DS,
			        OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			loadedDatasets.put(id, imageInfo);
			browserPanel.setImages(imageInfo);
		} catch (Exception e) {
		}
	}

	/**
	 * Call the SPT module, for 1 image.
	 */
	public void callSPT() {
		ImageData temp = jPanelViewer.getImage();

		if (temp != null) {
			PixelsData pixels = temp.getDefaultPixels();

			// image name
			String name = StringHelper.getImageName(temp.getName());

			// image ID
			long pixelsID = pixels.getId();

			// channel
			JCheckBox[] channels = jPanelViewer.getChannels();

			int C = 0;

			for (int i = 0; i < channels.length; i++)
				if (channels[i].isSelected())
					C++;

			if (C == 0 || C > 1) {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.INFO_SELECT_ONE_CHANNEL,
				        OmegaConstants.OMEGA_TITLE,
				        JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				for (int i = 0; i < channels.length; i++)
					if (channels[i].isSelected()) {
						C = i;
						break;
					}
			}

			// Z
			int Z = 0;

			JSlider zSLider = jPanelViewer.getzSlider();

			if (zSLider.isEnabled())
				Z = zSLider.getValue();
			else
				Z = 0;

			// T
			int T = pixels.getSizeT();

			if (T < 2) {
				JOptionPane.showMessageDialog(null,
				        OmegaConstants.INFO_T_MUST_BE_GREATER_THAN_ONE,
				        OmegaConstants.OMEGA_TITLE,
				        JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// X
			int X = pixels.getSizeX();

			// Y
			int Y = pixels.getSizeY();

			// get the pixels size for the X and the Y dimension, as well as the
			// TOTAL movie duration
			double sizeX = pixels.getPixelSizeX();
			double sizeY = pixels.getPixelSizeY();
			double sizeT = getTotalT(pixelsID, C, Z, T);

			// informational log
			if (sizeX == 0.0)
				GLogManager.log("no sizeX found", Level.INFO);
			if (sizeY == 0.0)
				GLogManager.log("no sizeY found", Level.INFO);
			if (sizeT == 0.0)
				GLogManager.log("no sizeT found", Level.INFO);

			ImageDataHandler sptImageHandler = new ImageDataHandler(name,
			        currentBrowsedDataset, pixelsID, T, X, Y, sizeX, sizeY,
			        sizeT);

			OmeroParametersHandler sptParametersHandler = new OmeroParametersHandler(
			        gateway, Z, C);
			sptParametersHandler.addImage(sptImageHandler);

			jPanelReview.getMainFrame().displaySPTPanel(sptParametersHandler);
		}
	}

	/**
	 * Call the SPT module, for more images.
	 */
	public void callBatchSPT() {
		ArrayList<ImageData> imageData = browserPanel.getToBeProcessed();

		JDialogSPTBatch jDialogSPTBatch = null;

		if (imageData.size() < 1) {
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.INFO_SELECT_AT_LEAST_ONE_IMAGE,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			jDialogSPTBatch = new JDialogSPTBatch();
			jDialogSPTBatch.setModalityType(ModalityType.APPLICATION_MODAL);
			jDialogSPTBatch.setLocationRelativeTo(this);
			jDialogSPTBatch.setVisible(true);
		}

		if (!jDialogSPTBatch.isRun())
			return;

		int choosenZ = jDialogSPTBatch.getZ();
		int choosenC = jDialogSPTBatch.getC();

		OmeroParametersHandler sptParametersHandler = new OmeroParametersHandler(
		        gateway, choosenZ, choosenC);

		for (ImageData temp : imageData) {
			if (temp != null) {
				PixelsData pixels = temp.getDefaultPixels();
				// image name
				String name = StringHelper.getImageName(temp.getName());
				// image ID
				long pixelsID = pixels.getId();
				// T
				int T = pixels.getSizeT();
				// X
				int X = pixels.getSizeX();
				// Y
				int Y = pixels.getSizeY();

				// get the pixels size for the X and the Y dimension
				double sizeX = pixels.getPixelSizeX();
				double sizeY = pixels.getPixelSizeY();

				// get the delta T between frames
				double sizeT = getTotalT(pixelsID, choosenC, choosenZ, T);

				// informational log
				if (sizeX == 0.0)
					GLogManager.log("no sizeX found", Level.INFO);
				if (sizeY == 0.0)
					GLogManager.log("no sizeY found", Level.INFO);
				if (sizeT == 0.0)
					GLogManager.log("no sizeT found", Level.INFO);

				ImageDataHandler sptImageHandler = new ImageDataHandler(name,
				        currentBrowsedDataset, pixelsID, T, X, Y, sizeX, sizeY,
				        sizeT);

				sptParametersHandler.addImage(sptImageHandler);
			}
		}
		jPanelReview.getMainFrame().displaySPTPanel(sptParametersHandler);
	}

	/**
	 * Reads the total duration of an image for a given plane (i.e.: pixels ID,
	 * channel, plane)
	 * 
	 * @param pixelsID
	 * @param C
	 * @param Z
	 * @param maxT
	 * @return
	 */
	private double getTotalT(long pixelsID, int C, int Z, int maxT) {
		GLogManager.log("maxT is: " + maxT);

		double sizeT = 0.0;

		try {
			List<IObject> planeInfoObjects = gateway.loadPlaneInfo(pixelsID, Z,
			        maxT - 1, C);

			if (planeInfoObjects.size() > 0) {
				PlaneInfoI pi = (PlaneInfoI) planeInfoObjects.get(0);

				RDouble tTemp = pi.getDeltaT();

				if (tTemp != null)
					sizeT = tTemp.getValue();
			}
		} catch (Exception e) {
			GLogManager.log("exception in calculateSizeT: " + e.toString());
		}

		return sizeT;
	}

	/**
	 * Call the Data Exploration module for the current image
	 */
	public void callDataExploration() {
		jPanelReview.getMainFrame().callDataExplorationModule(
		        jPanelViewer.getCanvas());
	}

	private class DatasetInfo {
		private Long id;
		private String name;

		public DatasetInfo(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public Long getId() {
			return id;
		}
	}
}
