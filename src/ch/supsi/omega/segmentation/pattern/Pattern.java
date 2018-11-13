package ch.supsi.omega.segmentation.pattern;

import java.awt.Color;

public class Pattern
{
	private int		id;
	private String	name;
	private int		windowWidht;
	private int		features;
	private Color	color;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getWindowWidht()
	{
		return windowWidht;
	}

	public void setWindowWidht(int windowWidht)
	{
		this.windowWidht = windowWidht;
	}

	public int getFeatures()
	{
		return features;
	}

	public void setFeatures(int features)
	{
		this.features = features;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	
	public Pattern()
	{
	}

	public Pattern(int id, String name, int windowWidht, int features, Color color)
	{
		this.id = id;
		this.name = name;
		this.windowWidht = windowWidht;
		this.features = features;
		this.color = color;
	}

	/**
	 * Returns a string representation of the Pattern.
	 */
	public String toString()
	{
		return String.format("%s: %s", id, name);
	}
}