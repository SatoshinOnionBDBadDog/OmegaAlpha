package ch.supsi.omega.math.interpolation4d;

public class HyperPoint
{
		public double SNR;	
		public double L;
		public double SMSS;
		public double D;
		public double value;
		
		public HyperPoint(double sNR, double l, double sMSS, double d, double value)
		{
			super();
			this.SNR = sNR;
			this.L = l;
			this.SMSS = sMSS;
			this.D = d;
			this.value = value;
		}
}
