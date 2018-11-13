package ch.supsi.omega.tracking.parameters;

import java.util.ArrayList;
import ch.supsi.omega.omero.Gateway;

public class OmeroParametersHandler
{
	/**
	 * Gateway object used to get the images planes (same for all the images processed).
	 */
	private Gateway gateway = null;
		
	/**
	 * Plane of the images to be processed (same for all the images processed).
	 */
	private int Z = 0;
	
	/**
	 * Channel of the images to be processed (same for all the images processed).
	 */
	private int C = 0;

	/**
	 * The images to be processed
	 */
	private ArrayList<ImageDataHandler>				images;
	
	public Gateway getGateway()
	{
		return gateway;
	}

	public void setGateway(Gateway gateway)
	{
		this.gateway = gateway;
	}

	public int getZ()
	{
		return Z;
	}

	public void setZ(int z)
	{
		Z = z;
	}

	public int getC()
	{
		return C;
	}

	public void setC(int c)
	{
		C = c;
	}

	public ArrayList<ImageDataHandler> getImages()
	{
		return images;
	}

	public void setImages(ArrayList<ImageDataHandler> images)
	{
		this.images = images;
	}

	public OmeroParametersHandler()
	{
	}
		
	public OmeroParametersHandler(Gateway gateway, int z, int c)
	{
		super();
		this.gateway = gateway;
		Z = z;
		C = c;
		this.images = new ArrayList<ImageDataHandler>();
	}

	public void addImage(ImageDataHandler sptImageHandler)
	{
		images.add(sptImageHandler);
	}
}
