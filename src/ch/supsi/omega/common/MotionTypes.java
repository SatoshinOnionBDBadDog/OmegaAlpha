package ch.supsi.omega.common;

import java.util.logging.Level;

import ch.supsi.omega.gui.OMEGA;

import com.galliva.gallibrary.GConfigurationManager;
import com.galliva.gallibrary.GLogManager;

public class MotionTypes {
	public static void getMotionsTypeFromINI() {
		GConfigurationManager configurationManager = new GConfigurationManager();

		try {
			configurationManager.setIniFile(OmegaConstants.INIFILE);
		} catch (Exception e) {
			GLogManager.log(OmegaConstants.LOG_SET_INI_FAILED, Level.WARNING);
		}

		try {
			String motions = configurationManager.readConfig("motions");

			String m[] = motions.split(";");

			if (m.length != 5)
				return;

			OMEGA.MOTIONTYPES = m;
		} catch (Exception e) {
			// nothing to do, leave the standard
		}
	}
}
