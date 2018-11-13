package ch.supsi.omega.segmentation.pattern;

import java.awt.Color;

public class PatternsCodedLoader extends PatternsLoader
{
	@Override
	public void loadPatterns()
	{
		patterns.add(new Pattern(0, "undefined motion",     10, 7, Color.LIGHT_GRAY));
		patterns.add(new Pattern(1, "directed motion",      10, 7, patternsColor[0]));
		patterns.add(new Pattern(2, "fast drifting motion", 10, 7, patternsColor[1]));
		patterns.add(new Pattern(3, "slow drifting motion", 10, 7, patternsColor[2]));
		patterns.add(new Pattern(4, "confined motion",      10, 7, patternsColor[3]));
	}
	
	public static String getPatternName(int patternId)
	{
		switch (patternId)
		{
			case 0:
				return "undefined";
			case 1:
				return "directed";
			case 2:
				return "fast drifting";
			case 3:
				return "slow drifting";
			case 4:
				return "confined";
			default:
				return "undefined";
		}
	}
}
