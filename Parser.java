package main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Parser {
	private Mouse mouse;
	
	public Parser(Mouse mouse) {
		this.mouse = mouse;
	}

	public ArrayList<Particle> textToParticle(String message, float r, Font f, AffineTransform af, Graphics g) {
		ArrayList<Particle> result = new ArrayList<Particle>();
		Shape s = af.createTransformedShape(f.createGlyphVector(g.getFontMetrics(f).getFontRenderContext(), message).getOutline());
		
		for(float i = (float) s.getBounds2D().getMinX(); i < s.getBounds2D().getMaxX(); i+=2*r) {
			for(float j = (float) s.getBounds2D().getMinY(); j < s.getBounds2D().getMaxY(); j+=2*r) {
				if(s.contains(i, j) == true) {
					result.add(new Particle(i, j, r, this.mouse));
				}
			}
		}
		return result;
	}

}