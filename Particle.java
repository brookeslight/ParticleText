package main;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	
	private float tx;
	private float ty;
	private float x;
	private float y;
	private float r;
	private final float attractionConstant = 2.75f; 
	private final float repultionConstant = 5.25f;
	private final float mouseRadius = 128;
	private Mouse mouse;
	private Color color;

	public Particle(float x, float y, float r, Mouse mouse) {
		this.tx = x;
		this.ty = y;
		this.x = x;
		this.y = y;
		this.r = r;
		this.mouse = mouse;
	}
	
	public void tick() {
		//net force
		Vector netForce = new Vector(0, 0);
		this.color = Color.blue;
		//vector towards target
		Vector attraction = new Vector(this.tx - this.x, this.ty - this.y);
		if(attraction.length() != 0) {
			if(attraction.length() > attractionConstant) {
				attraction.setMagnitude(attractionConstant);
			}
			netForce.add(attraction);
		} else {
			this.color = Color.cyan;
		}
		//vector away from mouse
		if(Math.hypot(this.x - this.mouse.x, this.y - this.mouse.y) <= mouseRadius) {
			Vector repultion = new Vector(this.x - this.mouse.x, this.y - this.mouse.y);
			repultion.setMagnitude(repultionConstant);
			netForce.add(repultion);
		}
		this.x += netForce.x;
		this.y += netForce.y;
	}
	
	public void render(Graphics g) {
		g.setColor(this.color);
		g.drawOval((int)(this.x - this.r), (int)(this.y - this.r), (int)(2*this.r), (int)(2*this.r));
	}

}