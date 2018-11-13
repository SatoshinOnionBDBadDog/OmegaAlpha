package ch.supsi.omega.common.science;

public class StringConverter {
	public static String[] getScientificString(String[] s) {
		for (int i = 0; i < s.length; i++)
			s[i] = getScientificString(s[i]);

		return s;
	}

	public static String getScientificString(String s) {
		System.out.println(s);

		s = s.replaceAll("(?i)delta", Greeks.DELTA);
		s = s.replaceAll("(?i)gamma", Greeks.GAMMA);
		s = s.replaceAll("(?i)mu", Greeks.MU);
		s = s.replaceAll("(?i)micro", Greeks.MU);
		s = s.replaceAll("(?i)\\^2", "\u00B2");

		return s;
	}
}
