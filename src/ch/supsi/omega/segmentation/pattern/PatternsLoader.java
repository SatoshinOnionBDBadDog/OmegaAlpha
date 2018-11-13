package ch.supsi.omega.segmentation.pattern;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class PatternsLoader
{
	protected List<Pattern>		patterns			= new ArrayList<Pattern>();

	protected static Color[]	patternsColor	= { Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY };

	public List<Pattern> getPatterns()
	{
		return patterns;
	}

	public static Color[] getPatternsColor()
	{
		return patternsColor;
	}

	public abstract void loadPatterns();
}
