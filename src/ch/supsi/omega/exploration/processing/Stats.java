package ch.supsi.omega.exploration.processing;

import java.util.ArrayList;
import java.util.Arrays;

public class Stats {
	public static final int MAX_NU = 6;

	private final double[] x;
	private final double[] y;
	private final double Delta_t;
	private final double[][] d; // cache of Euclidean norms (d[0] == null)
	private int[] bounds = null;
	private int[] labels = null;

	public Stats(final double[] x, final double[] y, final double Delta_t,
	        final int[] segmentationOrNull) {
		Stats.checkLengths_x_y(x, y);
		Stats.checkLength_segmentation(x, segmentationOrNull);

		// set the fields
		this.x = x.clone();
		this.y = y.clone();
		this.Delta_t = Delta_t;

		// if there is a segmentation
		if (segmentationOrNull != null) {
			final ArrayList<Integer> list = Stats.bounds(segmentationOrNull);
			this.bounds = new int[list.size()];

			for (int i = 0; i < this.bounds.length; ++i) {
				this.bounds[i] = list.get(i);
			}

			this.labels = new int[this.bounds.length - 1];

			for (int i = 0; i < this.labels.length; ++i) {
				this.labels[i] = segmentationOrNull[this.bounds[i]];
			}

			if (!(this.to(this.labelsLength() - 1) != this.M()))
				throw new AssertionError();
		}
		System.out.println("###BOUNDS###");
		for (final int bound : this.bounds) {
			System.out.println(bound);
		}

		// calculate d (cache)
		this.d = new double[1 + ((this.M()) / 3)][];
		this.norms();
	}

	private static void checkLength_segmentation(final double[] x,
	        final int[] segmentation) {
		if (!((segmentation == null) || (x.length == (segmentation.length + 1))))
			throw new IllegalArgumentException();
	}

	private static void checkLengths_x_y(final double[] x, final double[] y) {
		if (!(x.length == y.length))
			throw new IllegalArgumentException();
	}

	public double Delta_t() {
		return this.Delta_t;
	}

	public int M() {
		return this.x.length;
	}

	public int M(final int segment) {
		return this.intervals(segment) + 1;
	}

	public int intervals() {
		return this.M() - 1;
	}

	public int intervals(final int segment) {
		return this.to(segment) - this.from(segment);
	}

	public double duration() {
		return this.intervals() * this.Delta_t();
	}

	public double duration(final int segment) {
		return this.intervals(segment) * this.Delta_t();
	}

	public int labelsLength() {
		return this.labels.length;
	}

	public int labels(final int at) {
		return this.labels[at];
	}

	/**
	 * Returns the index of the first point in the segment.
	 * 
	 * @param segment
	 * @return
	 */
	public int from(final int segment) {
		return this.bounds[segment];
	}

	/**
	 * Returns the index of the last point in the segment.
	 * 
	 * @param segment
	 * @return
	 */
	public int to(final int segment) {
		return this.from(segment + 1);
	}

	private static ArrayList<Integer> bounds(final int[] segmentation) {
		final ArrayList<Integer> bounds = new ArrayList<Integer>();
		bounds.add(0);
		for (int i = 0; i < segmentation.length;) {
			final int lab = segmentation[i];
			for (; (i < segmentation.length) && (lab == segmentation[i]); ++i) {
				;
			}
			bounds.add(i);
		}
		return bounds;
	}

	private void norms() {
		for (int Delta_n = 1; Delta_n < this.d.length; ++Delta_n) {
			final double[] norms_Delta_n = new double[this.M() - Delta_n];
			for (int i = 0; i < norms_Delta_n.length; ++i) {
				norms_Delta_n[i] = StrictMath.hypot(this.x[i + Delta_n]
				        - this.x[i], this.y[i + Delta_n] - this.y[i]);
			}
			this.d[Delta_n] = norms_Delta_n;
		}
	}

	private static double pow(final double x, final int n) {
		switch (n) {
		case 0:
			return 1.0;
		case 1:
			return x;
		case 2:
			return Stats.pow_2(x);
		case 3:
			return Stats.pow_3(x);
		case 4:
			return Stats.pow_2(Stats.pow_2(x));
		case 5:
			return Stats.pow_2(Stats.pow_2(x)) * x;
		case 6:
			return Stats.pow_2(Stats.pow_3(x));
		}
		throw new AssertionError();
	}

	private static double pow_2(final double x) {
		return x * x;
	}

	private static double pow_3(final double x) {
		return x * x * x;
	}

	public static double mean(final double[] x, final int from, final int to) {
		double tot = 0.0;
		for (int i = from; i <= to; ++i) {
			tot += x[i];
		}
		return tot / ((to + 1) - from);
	}

	public static double mean(final double[] x) {
		return Stats.mean(x, 0, x.length - 1);
	}

	public static double median(final double[] x, final int from, final int to) {
		final double[] x0 = new double[(to + 1) - from];
		System.arraycopy(x, from, x0, 0, x0.length);
		Arrays.sort(x0);
		final int ix0 = (x0.length - 1) >> 1;
		return (x0.length & 0x1) != 0 ? x0[ix0] : (x0[ix0] + x0[ix0 + 1]) / 2.0;
	}

	public static double median(final double[] x) {
		return Stats.median(x, 0, x.length - 1);
	}

	public static double varianceN(final double[] x, final int from,
	        final int to) {
		final double mean = Stats.mean(x, from, to);
		double tot = 0.0;
		for (int i = from; i <= to; ++i) {
			tot += Stats.pow_2(x[i] - mean);
		}
		return tot / ((to + 1) - from);
	}

	public static double varianceN(final double[] x) {
		return Stats.varianceN(x, 0, x.length - 1);
	}

	public static double varianceN1(final double[] x, final int from,
	        final int to) {
		final double mean = Stats.mean(x, from, to);
		double tot = 0.0;
		for (int i = from; i <= to; ++i) {
			tot += Stats.pow_2(x[i] - mean);
		}
		return tot / (to - from);
	}

	public static double varianceN1(final double[] x) {
		return Stats.varianceN1(x, 0, x.length - 1);
	}

	public static double standardDeviationN(final double[] x, final int from,
	        final int to) {
		return StrictMath.sqrt(Stats.varianceN(x, from, to));
	}

	public static double standardDeviationN(final double[] x) {
		return Stats.standardDeviationN(x, 0, x.length - 1);
	}

	public static double standardDeviationN1(final double[] x, final int from,
	        final int to) {
		return StrictMath.sqrt(Stats.varianceN1(x, from, to));
	}

	public static double standardDeviationN1(final double[] x) {
		return Stats.standardDeviationN1(x, 0, x.length - 1);
	}

	public static double t_test(final double[] x, final int xFrom,
	        final int xTo, final double[] y, final int yFrom, final int yTo) {
		return (Stats.mean(x, xFrom, xTo) - Stats.mean(y, yFrom, yTo))
		        / StrictMath
		                .sqrt((Stats.varianceN1(x, xFrom, xTo) / ((xTo + 1) - xFrom))
		                        + (Stats.varianceN1(y, yFrom, yTo) / ((yTo + 1) - yFrom)));
	}

	public static double t_test(final double[] x, final double[] y) {
		return Stats.t_test(x, 0, x.length - 1, y, 0, y.length - 1);
	}

	// TODO riganoa cos'e LINEAR-FIT? da approfondire e
	// rivedere....probabilmente => linear least square fit del paper di
	// Sbalzarini
	public static double[] linear_fit(final double[] u, final double[] v,
	        final int from, final int to) {
		final double u_bar = Stats.mean(u, from, to);
		final double v_bar = Stats.mean(v, from, to);
		double SigmaUV = 0.0;
		double SigmaU2 = 0.0;
		double SigmaV2 = 0.0;
		for (int i = from; i <= to; ++i) {
			SigmaUV += (u[i] - u_bar) * (v[i] - v_bar);
			SigmaU2 += Stats.pow_2(u[i] - u_bar);
			SigmaV2 += Stats.pow_2(v[i] - v_bar);
		}
		final double m = SigmaUV / SigmaU2;
		final double q = v_bar - (m * u_bar);
		final double r = SigmaUV / StrictMath.sqrt(SigmaU2 * SigmaV2);
		return new double[] { m, q, r };
	}

	public static double[] linear_fit(final double[] u, final double[] v) {
		return Stats.linear_fit(u, v, 0, u.length - 1);
	}

	private double mu(final int nu, final int Delta_n, final int from,
	        final int to) {
		double tot = 0.0;
		for (int i = from; i <= (to - Delta_n); ++i) {
			tot += Stats.pow(this.d[Delta_n][i], nu);
		}
		return tot / ((to + 1) - from - Delta_n);
	}

	public double mu(final int nu, final int Delta_n) {
		return this.mu(nu, Delta_n, 0, this.intervals());
	}

	public double mu(final int nu, final int Delta_n, final int segment) {
		return this.mu(nu, Delta_n, this.from(segment), this.to(segment));
	}

	// VEDI PAPER MOMENTS AND DISPLACEMENTS AND THEIR SPECTRUM IVO SBALZARINI
	private double[][] log_delta_t__log_mu__gamma__D(final int nu,
	        final int from, final int to) {

		final double log_Delta_t = StrictMath.log(this.Delta_t);
		final int M = (to + 1) - from;
		final int max_Delta_n = StrictMath.max(M / 3, 2);
		final double[] log_delta_t = new double[max_Delta_n + 1];
		final double[] log_mu = new double[log_delta_t.length];
		for (int Delta_n = 1; Delta_n <= max_Delta_n; ++Delta_n) {
			log_delta_t[Delta_n] = log_Delta_t + StrictMath.log(Delta_n);
			log_mu[Delta_n] = StrictMath.log(this.mu(nu, Delta_n, from, to));
		}

		final double[] fit = Stats.linear_fit(log_delta_t, log_mu, 1,
		        max_Delta_n);
		final double D = StrictMath.exp(fit[1]) / (2.0 * nu);

		return new double[][] { log_delta_t, log_mu,
		        new double[] { fit[0], fit[1], fit[2], D } };
	}

	/**
	 * Computes and returns 3 arrays. The first array contains the values of
	 * log(delta_t), starting from index Delta_n = 1. The second array contains
	 * the values of log(mu), starting from index Delta_n = 1. The third array
	 * contains gamma (slope), intercept and the correlation of the linear fit
	 * and D as the last element.
	 * 
	 * @param nu
	 * @return
	 */
	public double[][] log_delta_t__log_mu__gamma__D(final int nu) {
		return this.log_delta_t__log_mu__gamma__D(nu, 0, this.intervals());
	}

	/**
	 * Does the same as {@link #log_delta_t__log_mu__gamma__D(int)} but limits
	 * the computation to a single segment.
	 * 
	 * @param nu
	 * @param segment
	 * @return
	 */
	public double[][] log_delta_t__log_mu__gamma__D(final int nu,
	        final int segment) {
		return this.log_delta_t__log_mu__gamma__D(nu, this.from(segment),
		        this.to(segment));
	}

	// TODO
	// nu should be max_moment_order
	// ny should be moment_order
	private double[][] nu__gamma__S_MSS(final int from, final int to) {
		final double[] nu = new double[Stats.MAX_NU + 1];
		final double[] gamma = new double[Stats.MAX_NU + 1];
		for (int ny = 0; ny <= Stats.MAX_NU; ++ny) {
			nu[ny] = ny;
			gamma[ny] = this.log_delta_t__log_mu__gamma__D(ny, from, to)[2][0];
		}
		return new double[][] { nu, gamma, Stats.linear_fit(nu, gamma) };
	}

	/**
	 * Computes and returns 3 arrays. The first array contains the values of nu.
	 * The second array contains the values of gamma. The third array contains
	 * S_MSS, the intercept and the correlation of the linear fit.
	 * 
	 * @param nu
	 * @return
	 */
	public double[][] nu__gamma__S_MSS() {
		return this.nu__gamma__S_MSS(0, this.intervals());
	}

	/**
	 * Does the same as {@link #nu__gamma__S_MSS} but limits the computation to
	 * a single segment.
	 * 
	 * @param nu
	 * @param segment
	 * @return
	 */
	public double[][] nu__gamma__S_MSS(final int segment) {
		return this.nu__gamma__S_MSS(this.from(segment), this.to(segment));
	}

	public double[] instantVelocities() {
		final double[] v = new double[this.x.length];

		v[0] = this.d[1][0] / this.Delta_t;
		v[v.length - 1] = this.d[1][v.length - 2] / this.Delta_t;

		for (int i = 1; i < (v.length - 1); i++) {
			v[i] = (this.d[1][i] + this.d[1][i - 1]) / (2.0 * this.Delta_t);
		}

		return v;
	}

}
