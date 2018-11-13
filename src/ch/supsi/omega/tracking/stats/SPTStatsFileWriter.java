package ch.supsi.omega.tracking.stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.gui.MainFrame;
import ch.supsi.omega.segmentation.TPoint;
import ch.supsi.omega.segmentation.trajectory.TrajectoriesLoader;
import ch.supsi.omega.segmentation.trajectory.Trajectory;

public class SPTStatsFileWriter {
	@SuppressWarnings("unused")
	private MainFrame parent = null;
	private String directory = "";
	private int framesNumber = 0;
	private FileWriter fstream = null;
	private BufferedWriter out = null;

	public SPTStatsFileWriter(final MainFrame parent, final String directory,
	        final int framesNumber) {
		this.parent = parent;
		this.directory = directory;
		this.framesNumber = framesNumber;
	}

	public void initWriter() {
		try {
			final String fileName = this.directory
			        + System.getProperty("file.separator") + "stats.txt";
			this.fstream = new FileWriter(fileName);
			this.out = new BufferedWriter(this.fstream);
		} catch (final IOException e) {

		}
	}

	public String calculateAndWriteStats() {
		double image_meanSNR = 0;
		double image_minSNR = Double.MAX_VALUE;
		double image_maxSNR = Double.MIN_VALUE;
		double image_minVar = Double.MAX_VALUE;

		// calculation of the AVG MIN SNR of the frame (we will exclude negative
		// values)
		double image_avgMinSNR = 0.0;
		int frameToExclude = 0;

		int image_minSNR_frame = 0;
		int image_maxSNR_frame = 0;
		int image_minVar_frame = 0;

		List<Trajectory> trajectories = null;

		try {
			final TrajectoriesLoader loader = new TrajectoriesLoader(
			        this.directory + System.getProperty("file.separator"),
			        OmegaConstants.TRACKS_FILES_EXTENSION);
			trajectories = loader.loadTrajectories();

			this.out.write(String.format("%s %s %s %s %s %s", "frame",
			        "min_SNR ", "max_SNR", " mean_SNR", "var_SNR ",
			        "no_of_particles"));
			this.out.newLine();

			// for each frame in the image
			for (int i = 1; i <= this.framesNumber; i++) {
				double frame_minSNR = Double.MAX_VALUE;
				double frame_maxSNR = Double.MIN_VALUE;
				double frame_meanSNR = 0;
				int frame_PointNum = 0;
				// list of all SNRs of the frame
				final ArrayList<Double> data = new ArrayList<Double>();

				for (final Trajectory trajectory : trajectories) {
					for (final TPoint point : trajectory.getPoints()) {
						if (point.getFrame() == i) {
							final double SNR = point.getSNR();

							data.add(SNR);
							// add each frame SNR to compute the mean
							frame_meanSNR += SNR;
							frame_PointNum++;

							// check from MIN and MAX on frame
							if (SNR < frame_minSNR) {
								frame_minSNR = SNR;
							}
							if (SNR > frame_maxSNR) {
								frame_maxSNR = SNR;
							}

							break;
						}
					}
				}

				frame_meanSNR /= frame_PointNum;

				// calculate and write statistic for the frame
				final Statistics statistic = new Statistics(data);
				this.out.write(String.format("%5d %f %f %f %f %d", i,
				        frame_minSNR, frame_maxSNR, statistic.getMean(),
				        statistic.getVariance(), data.size()));
				this.out.newLine();

				image_meanSNR += frame_meanSNR;

				// calculation of the AVG MIN
				if (frame_minSNR > 0.0) {
					image_avgMinSNR = image_avgMinSNR + frame_minSNR;
				} else {
					frameToExclude++;
				}

				// check from MIN and MAX on image
				if (frame_minSNR < image_minSNR) {
					image_minSNR = frame_minSNR;
					image_minSNR_frame = i;
				}

				if (frame_maxSNR > image_maxSNR) {
					image_maxSNR = frame_maxSNR;
					image_maxSNR_frame = i;
				}

				if (statistic.getVariance() < image_minVar) {
					image_minVar = statistic.getVariance();
					image_minVar_frame = i;
				}
			}

			image_meanSNR /= this.framesNumber;

			this.out.newLine();
			this.out.write(String.format("Mean SNR:\t\t%f", image_meanSNR));
			this.out.newLine();
			this.out.write(String.format("Minimum SNR:\t\t%f (frame %d)",
			        image_minSNR, image_minSNR_frame));
			this.out.newLine();
			this.out.write(String.format("Maximum SNR:\t\t%f (frame %d)",
			        image_maxSNR, image_maxSNR_frame));
			this.out.newLine();
			this.out.write(String.format(
			        "Minimum SNR variance:\t%f (frame %d)", image_minVar,
			        image_minVar_frame));
			this.out.newLine();
			this.out.write(String.format(
			        "Average minimum SNR:\t%f (negative values excluded)",
			        image_avgMinSNR / (this.framesNumber - frameToExclude)));
		} catch (final Exception e) {

		}

		return String
		        .format("trajectories found: %d\n\nminimum SNR: %f (on frame %d)\nminimum SNR variance: %f (frame %d)",
		                trajectories.size(), image_minSNR, image_minSNR_frame,
		                image_minVar, image_minVar_frame);
	}

	public void closeWriter() {
		if (this.out == null)
			return;
		try {
			this.out.close();
		} catch (final IOException e) {

		}
	}

	private class Statistics {
		ArrayList<Double> data;

		public Statistics(final ArrayList<Double> data) {
			this.data = data;
		}

		double getMean() {
			double sum = 0.0;
			for (final double a : this.data) {
				sum += a;
			}
			return sum / this.data.size();
		}

		double getVariance() {
			final double mean = this.getMean();
			double temp = 0;
			for (final double a : this.data) {
				temp += (mean - a) * (mean - a);
			}
			return temp / this.data.size();
		}
	}

	public static void main(final String[] args) {
		final SPTStatsFileWriter sptStatsFileWriter = new SPTStatsFileWriter(
		        null,
		        "C:\\Users\\galliva\\Desktop\\OMEGA_stats_tests\\Artificial_xyt_8bit",
		        100);
		sptStatsFileWriter.initWriter();
		sptStatsFileWriter.calculateAndWriteStats();
		sptStatsFileWriter.closeWriter();
	}
}
