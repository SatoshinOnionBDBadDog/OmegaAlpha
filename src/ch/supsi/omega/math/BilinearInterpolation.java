package ch.supsi.omega.math;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.supsi.omega.common.OmegaConstants;
import ch.supsi.omega.common.StringHelper;

public class BilinearInterpolation
{	
	public static enum SurfaceType
	{
		SMSS, D
	};
		
	public static double interpolate(double SNR, int L, SurfaceType sizeToInterpolate) throws Exception
	{
		if(SNR < 1.291059 || SNR > 31.306549)
			throw new IllegalArgumentException(OmegaConstants.ERROR_INTERPOLATION_CALCULATION_SNR);
		
		ArrayList<SurfacePoint> knownPoints    = getKnownPoints(sizeToInterpolate);
		
		if(knownPoints == null)
			throw new Exception(OmegaConstants.ERROR_INTERPOLATION_CALCULATION);
		
		ArrayList<SurfacePoint> closest4Points = getClosest4Points(knownPoints, SNR, L);
		
		if(closest4Points == null || closest4Points.size() != 4)
			throw new Exception(OmegaConstants.ERROR_INTERPOLATION_CALCULATION);
		
		return doInterpolation(SNR, L, closest4Points);
	}
	
	public static ArrayList<SurfacePoint> getKnownPoints(SurfaceType sizeToInterpolate)
	{
		ArrayList<SurfacePoint> knownPoints = new ArrayList<SurfacePoint>();
		
		DataInputStream in 		= null;
		BufferedReader br  		= null;
		
		try
		{
			InputStream fstream = null;
			
			if(sizeToInterpolate.equals(SurfaceType.SMSS))
				fstream	= BilinearInterpolation.class.getResourceAsStream(OmegaConstants.SMSS_INTERPOLATION_FILE);
			else if(sizeToInterpolate.equals(SurfaceType.D))
				fstream	= BilinearInterpolation.class.getResourceAsStream(OmegaConstants.D_INTERPOLATION_FILE);
			else
				throw new IllegalArgumentException();
			
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;

			while ((strLine = br.readLine()) != null)
			{
				String[] splitted = StringHelper.splitString(strLine, ";");
				
				if(splitted.length != 3)
					continue;
				
				double SNR = Double.valueOf(splitted[0]);
				Double L   = Double.valueOf(splitted[1]);
				double val = Double.valueOf(splitted[2]);
				
				SurfacePoint surfacePoint = new SurfacePoint(SNR, L.intValue(), val);
				
				knownPoints.add(surfacePoint);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				br.close();
				in.close();
			}
			catch (Exception e)
			{
			}
		}

		return knownPoints;
	}
	
	public static ArrayList<SurfacePoint> getClosest4Points(ArrayList<SurfacePoint> knownPoints, double SNR, int L)
	{
		ArrayList<SurfacePoint> closest4Points = new ArrayList<SurfacePoint>(4);
		
		SurfacePoint q11 = null;
		SurfacePoint q12 = null;
		SurfacePoint q21 = null;
		SurfacePoint q22 = null;
		
		double minDistanceQ11 = Double.MAX_VALUE;
		double minDistanceQ12 = Double.MAX_VALUE;
		double minDistanceQ21 = Double.MAX_VALUE;
		double minDistanceQ22 = Double.MAX_VALUE;

		for (SurfacePoint knownPoint : knownPoints)
		{
			double currentSNRDistance = knownPoint.SNR - SNR;
			int currentLDistance      = knownPoint.L - L;
	
			double currentDistance    = StrictMath.sqrt(currentSNRDistance*currentSNRDistance + currentLDistance*currentLDistance);
			
			if(currentSNRDistance < 0 && currentLDistance < 0)
			{
				if (currentDistance < minDistanceQ11)
				{
					minDistanceQ11 = currentDistance;
					q11 = knownPoint;
				}
			}
			else if(currentSNRDistance < 0 && currentLDistance >= 0)
			{
				if (currentDistance < minDistanceQ12)
				{
					minDistanceQ12 = currentDistance;
					q12 = knownPoint;
				}
			}
			else if(currentSNRDistance >= 0 && currentLDistance < 0)
			{
				if (currentDistance < minDistanceQ21)
				{
					minDistanceQ21 = currentDistance;
					q21 = knownPoint;
				}
			}
			else if(currentSNRDistance >= 0 && currentLDistance >= 0)
			{
				if (currentDistance < minDistanceQ22)
				{
					minDistanceQ22 = currentDistance;
					q22 = knownPoint;
				}
			}
		}
		
		closest4Points.add(q11);
		closest4Points.add(q12);
		closest4Points.add(q21);
		closest4Points.add(q22);
		
		return closest4Points;
	}

	public static double doInterpolation(double SNR, int L, ArrayList<SurfacePoint> closest4Points)
	{
		SurfacePoint q11 = closest4Points.get(0);
		SurfacePoint q12 = closest4Points.get(1);
		SurfacePoint q21 = closest4Points.get(2);
		SurfacePoint q22 = closest4Points.get(3);
		
		double result =
				(1/((q21.SNR-q11.SNR)*(q12.L-q11.L))) * 
				(
						q11.value * (q21.SNR-SNR) * (q12.L-L) + 
						q21.value * (SNR-q11.SNR) * (q12.L-L) +
						q12.value * (q21.SNR-SNR) * (L-q11.L) +
						q22.value * (SNR-q11.SNR) * (L-q11.L)
				)
		;
		
		return result;
	}
}
