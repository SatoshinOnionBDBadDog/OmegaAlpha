package ch.supsi.omega.exploration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Type1GraphTest
{	
	public static void main(String[] args)
	{
		List<String> one = new ArrayList<String>();
		one.add("C:\\Users\\galliva\\Desktop\\trash\\_DS\\1a");
		one.add("C:\\Users\\galliva\\Desktop\\trash\\_DS\\1b");

		List<String> two = new ArrayList<String>();
		two.add("C:\\Users\\galliva\\Desktop\\trash\\_DS\\2");

		List<String> thr = new ArrayList<String>();
		thr.add("C:\\Users\\galliva\\Desktop\\trash\\_DS\\3");

		HashMap<String, List<String>> m = new HashMap<String, List<String>>();
		m.put("DS1", one);
		m.put("DS2", two);
		m.put("DS3", thr);

//		JFrameImageFrequencies jFrameImageFrequencies = new JFrameImageFrequencies(m);
//		jFrameImageFrequencies.setVisible(true);
	}
}
