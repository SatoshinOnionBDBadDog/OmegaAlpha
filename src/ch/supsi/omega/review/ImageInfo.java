package ch.supsi.omega.review;

import java.awt.image.BufferedImage;

import pojos.ImageData;

public class ImageInfo
{
	private Long				imageID;
	private ImageData			imageData;
	private String				imageName;
	private BufferedImage	image;

	public Long getImageID()
	{
		return imageID;
	}

	public ImageData getImageData()
	{
		return imageData;
	}

	public String getImageName()
	{
		return imageName;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public ImageInfo(Long imageID, ImageData imageData, String imageName, BufferedImage image)
	{
		this.imageID = imageID;
		this.imageData = imageData;
		this.imageName = imageName;
		this.image = image;
	}
}