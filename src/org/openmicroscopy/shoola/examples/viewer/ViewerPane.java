/*
 * org.openmicroscopy.shoola.examples.viewer.ViewerPane 
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2010 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.examples.viewer;

//Java imports
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//Third-party libraries

//Application-internal dependencies
import omero.api.RenderingEnginePrx;
import omero.romio.PlaneDef;

import ch.supsi.omega.dll.SPTCaller;
import ch.supsi.omega.omero.Gateway;
import pojos.ImageData;
import pojos.PixelsData;
import sun.awt.image.IntegerInterleavedRaster;

/**
 * Displays the image and controls.
 */
class ViewerPane extends JPanel implements ChangeListener
{
	private static final long serialVersionUID = -2795661944564940446L;

	/** The compression level. */
	private static final float COMPRESSION = 0.5f;

	/** The red mask. */
	private static final int RED_MASK = 0x00ff0000;

	/** The green mask. */
	private static final int GREEN_MASK = 0x0000ff00;

	/** The blue mask. */
	private static final int BLUE_MASK = 0x000000ff;

	/** The RGB masks. */
	private static final int[] RGB = { RED_MASK, GREEN_MASK, BLUE_MASK };

	/** Reference to the gateway */
	private Gateway gateway;
	
	/** Reference to the rendering engine. */
	private RenderingEnginePrx engine;

	/** The slider to select the z-section. */
	private JSlider zSlider;

	/** The slider to select the z-section. */
	private JSlider tSlider;

	/** Box indicating to render the image as compressed or not. */
	private JCheckBox compressed;

	/** The image canvas. */
	private ImageCanvas canvas;

	/** The image currently viewed. */
	private ImageData image;

	/** JPanel displaying all the available channels **/
	private JPanel channelsPane;
	
	/** Number of channels of the image **/
	private int channelsNumber;
	
	/** Checkboxs rappresenting the channels**/
	private JCheckBox[] channels;
	
	/** Button to read the byte[] of the image **/
	private JButton readButton;

	
	
	/** Creates a new instance. */
	ViewerPane()
	{
		initComponents();
		buildGUI();
	}
	
	/** Initializes the components. */
	private void initComponents()
	{
		compressed = new JCheckBox("Compressed Image");
		compressed.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				render();
			}
		});
		canvas = new ImageCanvas();
		zSlider = new JSlider();
		zSlider.setMinimum(0);
		zSlider.setEnabled(false);
		zSlider.addChangeListener(this);
		tSlider = new JSlider();
		tSlider.setMinimum(0);
		tSlider.setEnabled(false);
		tSlider.addChangeListener(this);
		
		readButton = new JButton("Read byte[]");
		readButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				long pixelsID = image.getDefaultPixels().getId();
				getByteArray(pixelsID, zSlider.getValue() - 1, tSlider.getValue() - 1, 0);
			}
		});
	}

	/** Builds and lays out the component. */
	private void buildGUI()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		// read byte[] panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.add(readButton);
		
		p.add(bottomPanel);
		
		// sliders panel
		JPanel row = new JPanel();
		row.setLayout(new FlowLayout(FlowLayout.LEFT));
		row.add(new JLabel("Z"));
		row.add(zSlider);
		row.add(Box.createHorizontalStrut(5));
		row.add(new JLabel("T"));
		row.add(tSlider);

		p.add(row);

		// compressed panel
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout(FlowLayout.LEFT));
		content.add(compressed);
		
		p.add(content);
		
		// channels panel
		channelsPane = new JPanel();
		channelsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		p.add(channelsPane);
		
		JPanel pp = new JPanel();
		pp.setLayout(new FlowLayout(FlowLayout.LEFT));
		pp.add(p);
		add(new JScrollPane(canvas));
		add(pp);
	}
	
	/**
	 * Builds the channel component.
	 */
	private void buildChannelsPane(int n)
	{		
		try
		{
			channelsPane.removeAll();
			remove(channelsPane);
			
			channels = new JCheckBox[n];
			
			for (int i = 0; i < n; i ++)
			{
				channels[i] = new JCheckBox("Channel " + i);
				
				channels[i].setSelected(true);
				         
				channels[i].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						setActiveChannels(e);
					}
				});
				
				channelsPane.add(channels[i]);
			}

			add(channelsPane);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Sets the rendering engine.
	 * @param imagen The image.
	 * @param engine The engine.
	 */
	void setRenderingControl(ImageData image, RenderingEnginePrx engine, Gateway gateway)
	{
		this.engine = engine;
		this.image = image;
		this.gateway = gateway;

		try
		{
			this.engine.setCompressionLevel(COMPRESSION);
		}
		catch (Exception e)
		{
			// nothing we can do
		}

		PixelsData pixels = image.getDefaultPixels();
		Dimension d = new Dimension(pixels.getSizeX(), pixels.getSizeY());
		canvas.setPreferredSize(d);
		canvas.setSize(d);
		zSlider.removeChangeListener(this);
		tSlider.removeChangeListener(this);
		zSlider.setMaximum(pixels.getSizeZ());
		zSlider.setEnabled(pixels.getSizeZ() > 1);
		tSlider.setMaximum(pixels.getSizeT());
		tSlider.setEnabled(pixels.getSizeT() > 1);
		try
		{
			zSlider.setValue(engine.getDefaultZ() + 1);
			tSlider.setValue(engine.getDefaultT() + 1);
		}
		catch (Exception e)
		{
			// nothing to do here
		}
		zSlider.addChangeListener(this);
		tSlider.addChangeListener(this);
		
		
		// number of channels in the image (RGB)
		channelsNumber = image.getDefaultPixels().getSizeC();
		
		System.out.println("Frames            : " + pixels.getSizeT());
		System.out.println("Width             : " + pixels.getSizeX());
		System.out.println("Height            : " + pixels.getSizeY());
		System.out.println("Pixels type       : " + pixels.getPixelType());
		System.out.println("Available channels: " + channelsNumber);
				
		buildChannelsPane(channelsNumber);
		
		render();
	}

	/** Renders a plane. */
	private void render()
	{		
		try
		{
			PlaneDef pDef = new PlaneDef();

			// time choice (sliding)
			pDef.t = engine.getDefaultT();

			// Z-plan choice
			pDef.z = engine.getDefaultZ();
			
			// display the XY plane
			pDef.slice = omero.romio.XY.value;
			
			// now render the image, possible to render it compressed or not compressed
			BufferedImage img = null;

			int sizeX = image.getDefaultPixels().getSizeX();
			int sizeY = image.getDefaultPixels().getSizeY();
			
			if (compressed.isSelected())
			{
				int[] buf = engine.renderAsPackedInt(pDef);
				img = createImage(buf, 32, sizeX, sizeY);
			}
			else
			{
				byte[] values = engine.renderCompressed(pDef);
				
				ByteArrayInputStream stream = new ByteArrayInputStream(values);
				img = ImageIO.read(stream);
				img.setAccelerationPriority(1f);
			}
			canvas.setImage(img);
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Creates a buffer image from the specified <code>array</code> of integers.
	 * 
	 * @param buf   The array to handle.
	 * @param bits  The number of bits in the pixel values.
	 * @param sizeX The width (in pixels) of the region of image data described.
	 * @param sizeY The height (in pixels) of the region of image data described.
	 * @return See above.
	 */
	private BufferedImage createImage(int[] buf, int bits, int sizeX, int sizeY)
	{
		if (buf == null)
			return null;
		
		DataBuffer j2DBuf = new DataBufferInt(buf, sizeX * sizeY);
		
		SinglePixelPackedSampleModel sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, sizeX, sizeY, sizeX, RGB);
		
		WritableRaster raster = new IntegerInterleavedRaster(sampleModel, j2DBuf, new Point(0, 0));

		ColorModel colorModel = new DirectColorModel(bits, RGB[0], RGB[1], RGB[2]);
		BufferedImage image = new BufferedImage(colorModel, raster, false, null);
		image.setAccelerationPriority(1f);
		return image;
	}
	
	/**
	 * Set or unset a channel.
	 */
	private void setActiveChannels(ActionEvent evt)
	{
		try
		{
			for (int i = 0; i < channelsNumber; i ++)
			{
				if (channels[i].isSelected())
					engine.setActive(i, true);
				else 
					engine.setActive(i, false);
			}
			render();
		}
		catch(Exception e)
		{
		}
	}

	/** 
	 * Sets the z-section or time-point.
	 */
	public void stateChanged(ChangeEvent e)
	{
		Object src = e.getSource();
		try
		{
			if (src == zSlider)
			{
				engine.setDefaultZ(zSlider.getValue() - 1);
				render();
			}
			else if (src == tSlider)
			{
				engine.setDefaultT(tSlider.getValue() - 1);
				render();
			}
		}
		catch (Exception e2)
		{

		}
	}
	
	public void getByteArray(long pixelsID, int z, int t, int c)
	{
		System.out.printf("Reading bytes[] for ID %d (z=%d, t=%d, c=%d)\n", pixelsID, z, t, c);
		
		// pixels id, z, t, c
		try
		{
			//byte[] pixels = gateway.getPlane(pixelsID, z, t, c);
			//System.out.println("size: " + pixels.length);
			
//			System.out.println("pixel number     0: " + pixels[0]);
//			System.out.println("pixel number 10000: " + pixels[10000]);
//			System.out.println("pixel number 20000: " + pixels[20000]);
//			System.out.println("pixel number 30000: " + pixels[30000]);
//			System.out.println("pixel number 40000: " + pixels[40000]);
//			System.out.println("pixel number 50000: " + pixels[50000]);
			

			
//			System.out.println(max + " " + min);
			
			//SPTCaller.callInitRunner();
			
			for(int i = 0; i < 41; i++)
			{
				byte[] pixels = gateway.getPlane(pixelsID, 0, i, 0);
				
				System.out.println(pixels);
				
				int[] data = new int[pixels.length / 2];
				for (int j = 0; j < data.length; j++)
				{
					data[j] = (pixels[2*j] & 0xff) << 8 | pixels[2*j + 1] & 0xff;
				}

//				int max = Integer.MIN_VALUE;
//				int min = Integer.MAX_VALUE;
//				for (int j = 0; j < data.length; j++)
//				{
//					if (data[j] > max)
//						max = data[j];
//					
//					if (data[j] < min)
//						min = data[j];
//				}
//				System.out.println(max + " " + min);
				
				System.out.print(String.format("sending T=%d\n", i));
				SPTCaller.callLoadImage(data);
			}
			
//			SPTCaller.callDisposeRunner();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
