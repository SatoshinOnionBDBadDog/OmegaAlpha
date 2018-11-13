package ch.supsi.omega.math.interpolation4d;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;
import ch.supsi.omega.math.BilinearInterpolation;

public class FileLoader {
	private final ArrayList<HyperPoint> HyperPoints = new ArrayList<HyperPoint>();

	public ArrayList<HyperPoint> getHyperPoints() {
		return this.HyperPoints;
	}

	public HyperPoint getHyperPoint(final double sNR, final double l,
	        final double sMSS, final double d) {
		for (final HyperPoint h : this.HyperPoints) {
			if ((h.SNR == sNR) && (h.L == l) && (h.SMSS == sMSS) && (h.D == d))
				return h;
		}

		return null;
	}

	public ArrayList<HyperPoint> loadHyperPoints(
	        final InterpolationType interpolationType) {
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			InputStream fstream = null;

			if (interpolationType.equals(InterpolationType.SMSS)) {
				fstream = BilinearInterpolation.class
				        .getResourceAsStream(OmegaConstants.SMSS_INTERPOLATION_FILE);
			} else if (interpolationType.equals(InterpolationType.D)) {
				fstream = BilinearInterpolation.class
				        .getResourceAsStream(OmegaConstants.D_INTERPOLATION_FILE);
			} else
				throw new IllegalArgumentException();

			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			while ((strLine = br.readLine()) != null) {
				final String[] splitted = StringHelper
				        .splitString(strLine, ";");

				if (splitted.length != 5) {
					continue;
				}

				final double SNR = Double.valueOf(splitted[0]);
				final double L = Double.valueOf(splitted[1]);
				final double SMSS = Double.valueOf(splitted[2]);
				final double D = Double.valueOf(splitted[3]);
				final double val = Double.valueOf(splitted[4]);

				final HyperPoint hyperPoint = new HyperPoint(SNR, L, SMSS, D,
				        val);

				this.HyperPoints.add(hyperPoint);
			}
		} catch (final Exception e) {
			System.out.println(e);
		} finally {
			try {
				br.close();
				in.close();
			} catch (final Exception e) {
			}
		}

		return this.HyperPoints;
	}

	public ArrayList<Double> getSNRValues() {
		return this.getInternalArray(1);
	}

	public ArrayList<Double> getLValues() {
		return this.getInternalArray(2);
	}

	public ArrayList<Double> getSMSSValues() {
		return this.getInternalArray(3);
	}

	public ArrayList<Double> getDValues() {
		return this.getInternalArray(4);
	}

	private ArrayList<Double> getInternalArray(final int index) {
		final ArrayList<Double> internalArray = new ArrayList<Double>();

		for (final HyperPoint hyperPoint : this.HyperPoints) {
			switch (index) {
			case 1:
				internalArray.add(hyperPoint.SNR);
				break;
			case 2:
				internalArray.add(hyperPoint.L);
				break;
			case 3:
				internalArray.add(hyperPoint.SMSS);
				break;
			case 4:
				internalArray.add(hyperPoint.D);
				break;
			default:
				break;
			}
		}

		return internalArray;
	}
}
