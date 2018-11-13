package ch.supsi.omegaanalysis.errorestimation;

import java.util.Scanner;

public class Main
{

	public static double[]	SNR	= { 1.291059, 1.990510, 2.846111, 3.494379, 4.556798, 6.516668, 8.832892, 11.632132, 15.067460, 19.326731, 24.642859, 31.306549 };
	public static double[]	L		= { 10, 20, 30, 40, 50, 80, 110, 140, 170, 200, 250, 300, 350, 400, 450, 500, 600, 700, 800, 1000 };
	public static double[]	SMSS	= { 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.7, 0.8, 0.9, 1.0 };
	public static double[]	D		= { 0.00001, 0.03, 0.1, 0.25, 0.5, 1, 5, 10, 25, 50 };

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.println("ResultAggregator v0.1");

		double L    = 1;
		double SMSS = -1;
		double D    = -1;

//		double SNR = in.nextDouble();
//		double L = in.nextDouble();
//		double SMSS = in.nextDouble();
//		double D = in.nextDouble();

		in.close();

		Aggregator aggregator = new Aggregator("/Users/galliva/Desktop/2012_07_16_risultati_completi/D", L, SMSS, D);

		try
		{
			aggregator.aggregate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
