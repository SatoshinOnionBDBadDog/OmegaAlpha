package ch.supsi.omega.tracking.parameters;

public class ImageDataHandler {
	private String imageName = "";
	private String imageDatasetName = "";
	private Long pixelsID = null;
	private int T = 0;
	private int X = 0;
	private int Y = 0;
	private double sizeX = 0.0;
	private double sizeY = 0.0;
	private double sizeT = 0.0;

	public ImageDataHandler(final String imageName,
	        final String imageDatasetName, final Long pixelsID, final int t,
	        final int x, final int y, final double sizeX, final double sizeY,
	        final double sizeT) {
		super();
		this.imageName = imageName;
		this.imageDatasetName = imageDatasetName;
		this.pixelsID = pixelsID;
		this.T = t;
		this.X = x;
		this.Y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeT = sizeT;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	public String getImageDatasetName() {
		return this.imageDatasetName;
	}

	public void setImageDatasetName(final String imageDatasetName) {
		this.imageDatasetName = imageDatasetName;
	}

	public Long getPixelsID() {
		return this.pixelsID;
	}

	public void setPixelsID(final Long pixelsID) {
		this.pixelsID = pixelsID;
	}

	public int getT() {
		return this.T;
	}

	public void setT(final int t) {
		this.T = t;
	}

	public int getX() {
		return this.X;
	}

	public void setX(final int x) {
		this.X = x;
	}

	public int getY() {
		return this.Y;
	}

	public void setY(final int y) {
		this.Y = y;
	}

	public double getSizeX() {
		return this.sizeX;
	}

	public void setSizeX(final double sizeX) {
		this.sizeX = sizeX;
	}

	public double getSizeY() {
		return this.sizeY;
	}

	public void setSizeY(final double sizeY) {
		this.sizeY = sizeY;
	}

	public double getSizeT() {
		return this.sizeT;
	}

	public void setSizeT(final double sizeT) {
		this.sizeT = sizeT;
	}

	public ImageDataHandler() {
		super();
	}
}
