package ch.supsi.omega.gui;

import ch.supsi.omega.common.MotionTypes;
import ch.supsi.omega.common.RandomColors;

public class OMEGA {
	public static String[] MOTIONTYPES = { "confined", "slow drifting",
	        "fast drifting", "directed", "undefined" };

	public static void main(String[] args) {
		RandomColors.generateColors();

		MotionTypes.getMotionsTypeFromINI();

		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
