package org.usfirst.frc.team1533.scorpius;

public class Vector2 {
	/**
     * 2D Mathematical Vector
     */
	double x = 0, y = 0;

	public Vector2 (double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double GetAngle() {
		return Math.atan2(y, x);
	}

	public double GetMagnitude() {
		return Math.hypot(x, y);
	}

	public void Scale (double scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public Vector2 Transpose (Vector2 scaler) {
		return new Vector2(x * scaler.x, y * scaler.y);
	}

	public void Add(Vector2 v) {
		x += v.x;
		y += v.y;
	}
	
	public void Subtract(Vector2 v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void MakePerpendicular() {
		double temp = x;
		x = y;
		y = -temp;
	}
	
	public String ToString () {
		return "("+x+","+y+")";
	}
}
