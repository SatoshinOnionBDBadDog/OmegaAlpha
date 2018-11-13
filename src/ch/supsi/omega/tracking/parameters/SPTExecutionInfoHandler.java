package ch.supsi.omega.tracking.parameters;

public class SPTExecutionInfoHandler
{
	private OmeroParametersHandler	omeroParameters	= null;
	private ImageDataHandler			imageData			= null;
	private String							radius				= "";
	private String							cutOff				= "";
	private String							percentile			= "";
	private String							displacement		= "";
	private String							linkRange			= "";

	public OmeroParametersHandler getOmeroParameters()
	{
		return omeroParameters;
	}

	public void setOmeroParameters(OmeroParametersHandler omeroParameters)
	{
		this.omeroParameters = omeroParameters;
	}

	public ImageDataHandler getImageData()
	{
		return imageData;
	}

	public void setImageData(ImageDataHandler imageData)
	{
		this.imageData = imageData;
	}

	public String getRadius()
	{
		return radius;
	}

	public void setRadius(String radius)
	{
		this.radius = radius;
	}

	public String getCutOff()
	{
		return cutOff;
	}

	public void setCutOff(String cutOff)
	{
		this.cutOff = cutOff;
	}

	public String getPercentile()
	{
		return percentile;
	}

	public void setPercentile(String percentile)
	{
		this.percentile = percentile;
	}

	public String getDisplacement()
	{
		return displacement;
	}

	public void setDisplacement(String displacement)
	{
		this.displacement = displacement;
	}

	public String getLinkRange()
	{
		return linkRange;
	}

	public void setLinkRange(String linkRange)
	{
		this.linkRange = linkRange;
	}

	public SPTExecutionInfoHandler()
	{
		super();
		omeroParameters = new OmeroParametersHandler();
		imageData = new ImageDataHandler();
	}

	public SPTExecutionInfoHandler(OmeroParametersHandler omeroParametersHandler, ImageDataHandler imageDataHandler, String radius, String cutOff, String percentile, String displacement, String linkRange)
	{
		super();
		this.omeroParameters = omeroParametersHandler;
		this.imageData = imageDataHandler;
		this.radius = radius;
		this.cutOff = cutOff;
		this.percentile = percentile;
		this.displacement = displacement;
		this.linkRange = linkRange;
	}
}
