package ch.supsi.omega.math.interpolation4d;

import java.util.ArrayList;

/**
 * 4-dimension interpolation class. Formulas are referring
 * http://paulbourke.net/miscellaneous/interpolation/
 * 
 * @author galliva
 */
public class Interpolator4d {
	private FileLoader fileLoader = null;

	public Interpolator4d(InterpolationType interpolationType) {
		fileLoader = new FileLoader();
		fileLoader.loadHyperPoints(interpolationType);
	}

	public Double interpolate(double sNR, int l, double sMSS, double d) {
		ArrayList<Double> SNRValues = fileLoader.getSNRValues();
		ArrayList<Double> LValues = fileLoader.getLValues();
		ArrayList<Double> SMSSValues = fileLoader.getSMSSValues();
		ArrayList<Double> DValues = fileLoader.getDValues();

		if (sNR < SNRValues.get(0) || sNR > SNRValues.get(SNRValues.size() - 1))
			throw new IllegalArgumentException("SNR out of range!");
		if (l < LValues.get(0) || l > LValues.get(LValues.size() - 1))
			throw new IllegalArgumentException("L out of range!");
		if (sMSS < SMSSValues.get(0)
		        || sMSS > SMSSValues.get(SMSSValues.size() - 1))
			throw new IllegalArgumentException("SMSS out of range!");
		if (d < DValues.get(0) || d > DValues.get(DValues.size() - 1))
			throw new IllegalArgumentException("D out of range!");

		Double[] SNRs = getClosestPoint(SNRValues, sNR);
		Double[] Ls = getClosestPoint(LValues, l);
		Double[] SMSSs = getClosestPoint(SMSSValues, sMSS);
		Double[] Ds = getClosestPoint(DValues, d);

		Double X_SNR = normalizeValue(sNR, SNRs);
		Double Y_L = normalizeValue(l, Ls);
		Double Z_SMSS = normalizeValue(sMSS, SMSSs);
		Double W_D = normalizeValue(d, Ds);

		Double result = 0.0;

		for (int iX = 0; iX < 2; iX++)
			for (int iY = 0; iY < 2; iY++)
				for (int iZ = 0; iZ < 2; iZ++)
					for (int iW = 0; iW < 2; iW++) {
						double XM = (iX == 0) ? (1 - X_SNR) : X_SNR;
						double YM = (iY == 0) ? (1 - Y_L) : Y_L;
						double ZM = (iZ == 0) ? (1 - Z_SMSS) : Z_SMSS;
						double WM = (iW == 0) ? (1 - W_D) : W_D;

						result = result
						        + fileLoader.getHyperPoint(SNRs[iX], Ls[iY],
						                SMSSs[iZ], Ds[iW]).value * XM * YM * ZM
						        * WM;
					}

		return result;
	}

	public Double[] getClosestPoint(ArrayList<Double> array, double value) {
		double minDist1 = Double.MAX_VALUE;
		double minDist2 = Double.MAX_VALUE;

		Double point1 = null;
		Double point2 = null;

		for (Double double1 : array) {
			double currentDistance = StrictMath.abs(double1 - value);

			if (double1 <= value && currentDistance < minDist1) {
				point1 = double1;
				minDist1 = currentDistance;
			}

			if (double1 > value && currentDistance < minDist2) {
				point2 = double1;
				minDist2 = currentDistance;
			}
		}

		if (point1 == null || point2 == null)
			return null;

		Double result[] = { point1, point2 };

		return result;
	}

	public Double normalizeValue(double value, Double[] values) {
		if (values[1] - values[0] == 0.0)
			throw new IllegalArgumentException();

		return (value - values[0]) / (values[1] - values[0]);
	}
}
