package ch.supsi.omega.exploration.processing;

import java.io.IOException;

public class TrackingProcessor {
	private static final double Delta_t = 0.02;

	private static final String FILE_SEPARATOR = System
	        .getProperty("file.separator");
	private static final String SEGMENTATION_FILE = "t_segmented.dat";

	private static String trajectoryFileName(final int trajIx) {
		final String s = "0" + Integer.toString(trajIx);
		return "t" + (s.substring(s.length() - 2)) + ".out";
	}

	public static void main(final String[] args) throws IOException {
		final long begin = System.nanoTime();
		final String folderPath = "C:\\OMEGA Project\\VanniCodeTest\\SingleTest1";
		final String segmentsPath = folderPath
		        + TrackingProcessor.FILE_SEPARATOR
		        + TrackingProcessor.SEGMENTATION_FILE;
		final int[][] labels = FileIn.readSegmentation(segmentsPath);
		for (int trajIx = 0; trajIx < labels.length; ++trajIx) {
			final String trajectoryPath = folderPath
			        + TrackingProcessor.FILE_SEPARATOR
			        + TrackingProcessor.FILE_SEPARATOR
			        + TrackingProcessor.trajectoryFileName(trajIx);
			final double[][] trajectory = FileIn.readTrajectory(trajectoryPath);

			final Stats stats = new Stats(trajectory[0], trajectory[1],
			        TrackingProcessor.Delta_t, labels[trajIx]);
			TrackingProcessor.nu__gamma__S_MSS(trajIx, stats);
			for (int segment = 0; segment < stats.labelsLength(); ++segment) {
				TrackingProcessor.nu__gamma__S_MSS(trajIx, stats, segment);
			}
		}
		System.out.println(((System.nanoTime() - begin) / 1000000) + " ms");
	}

	public static void nu__gamma__S_MSS(final int trajIx, final Stats stats) {
		final double[][] nu__gamma__S_MSS = stats.nu__gamma__S_MSS();
		System.out.println("whole trajectory");
		System.out.println("========");
		System.out.println("trajectory,,duration");
		System.out.println(trajIx + "," + stats.duration());
		System.out.println("--------");
		System.out.println("nu,gamma");
		for (int i = 0; i <= Stats.MAX_NU; ++i) {
			System.out.println(nu__gamma__S_MSS[0][i] + ","
			        + nu__gamma__S_MSS[1][i]);
		}
		System.out.println("--------");
		final double[] fit = nu__gamma__S_MSS[2];
		System.out.println("S_MSS,intercept,corr,D2");
		System.out.println(fit[0] + "," + fit[1] + "," + fit[2] + ","
		        + stats.log_delta_t__log_mu__gamma__D(2)[2][1]);
		System.out.println("--------");
		System.out.println();
	}

	public static void nu__gamma__S_MSS(final int trajIx, final Stats stats,
	        final int segment) {
		final double[][] nu__gamma__S_MSS = stats.nu__gamma__S_MSS(segment);
		System.out.println("segment");
		System.out.println("========");
		System.out.println("trajectory,segment,label,duration");
		System.out.println(trajIx + "," + segment + "," + stats.labels(segment)
		        + "," + stats.duration(segment));
		System.out.println("--------");
		System.out.println("nu,gamma");
		for (int i = 0; i <= Stats.MAX_NU; ++i) {
			System.out.println(nu__gamma__S_MSS[0][i] + ","
			        + nu__gamma__S_MSS[1][i]);
		}
		System.out.println("--------");
		final double[] fit = nu__gamma__S_MSS[2];
		System.out.println("S_MSS,intercept,corr,D2");
		System.out.println(fit[0] + "," + fit[1] + "," + fit[2] + ","
		        + stats.log_delta_t__log_mu__gamma__D(2, segment)[2][1]);
		System.out.println("--------");
		System.out.println();
	}

}
