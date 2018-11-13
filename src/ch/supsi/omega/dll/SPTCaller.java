package ch.supsi.omega.dll;

import java.util.logging.Level;

import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;

import com.galliva.gallibrary.GLogManager;

public class SPTCaller {
	// native methods
	private native void setOutputPath(String path);

	private native void initRunnner();

	private native void setParameter(String pNum, String pValue);

	private native void setMinPoints(int num);

	private native void startRunnner();

	private native void loadImage(int[] imageBytes);

	private native void writeResults();

	private native void disposeRunner();

	public static void callSetOutputPath(String path) {
		try {
			new SPTCaller().setOutputPath(path);
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callInitRunner() {
		try {
			new SPTCaller().initRunnner();
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callSetParameter(String pNumber, String pValue) {
		try {
			new SPTCaller().setParameter(pNumber, pValue);
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callSetMinPoints(int minPoints) {
		try {
			new SPTCaller().setMinPoints(minPoints);
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callStartRunner() {
		try {
			new SPTCaller().startRunnner();
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callLoadImage(int[] imageData) {
		try {
			new SPTCaller().loadImage(imageData);
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callWriteResults() {
		try {
			new SPTCaller().writeResults();
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	public static void callDisposeRunner() {
		try {
			new SPTCaller().disposeRunner();
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
		}
	}

	/**
	 * Load the SPT DLL.
	 */
	static {
		try {
			System.loadLibrary(OmegaConstants.OMEGA_SPT_DLL);
		} catch (UnsatisfiedLinkError e) {
			GLogManager.log(OmegaConstants.ERROR_NODLL + e.toString(),
					Level.SEVERE);
			JOptionPane.showMessageDialog(null,
					OmegaConstants.ERROR_NODLL + e.toString(),
					OmegaConstants.OMEGA_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}

}
