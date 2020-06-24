package main;

public class Vector {
	
	public float x;
	public float y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector a, Vector b) {
		this.x = b.x - a.x;
		this.y = b.y - a.y;
	}
	
	public double length() {
		return Math.hypot(this.x, this.y);
	}
	
	public void normalize() {
		double l = this.length();
		if(l != 0) {
			this.x /= l;
			this.y /= l;
		}
	}
	
	public void setMagnitude(float l) {
		this.normalize();
		this.x *= l;
		this.y *= l;
	}

	public void add(Vector a) {
		this.x += a.x;
		this.y += a.y;
	}

}