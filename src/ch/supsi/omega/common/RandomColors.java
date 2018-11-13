package ch.supsi.omega.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class RandomColors
{
	private static boolean				generated	= false;

	private static Random				rcolor		= new Random();

	private static ArrayList<Color>	colors		= new ArrayList<Color>();

	public static ArrayList<Color> getColors()
	{
		return colors;
	}

	public static void generateColors()
	{
		if (generated)
			return;

		for (int i = 0; i < 10000; i++)
			colors.add(new Color(rcolor.nextInt(255), rcolor.nextInt(255), rcolor.nextInt(255)));
		
		generated = true;
	}
}
