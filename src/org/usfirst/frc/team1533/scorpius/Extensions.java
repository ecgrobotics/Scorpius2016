package org.usfirst.frc.team1533.scorpius;

//All utilities go here
public class Extensions {
	
	public interface Callback {
		public void Execute();
	}
	
	public enum Scaling {
		Linear,
		Quadratic,
		Cubic
	}
	
	public static double Lerp (double a, double b, double t) {
    	return (b - a)*t + a;
    }	
	
	public static double Scale (double num, Scaling scaling) {
		double ret = 0;
		double sign = Math.signum(num);
		switch (scaling) {
		case Linear:
			ret = num;
		case Quadratic:
			ret = num*num*sign;
		case Cubic:
			ret = num*num*num;
		}
		return ret;
	}
}
