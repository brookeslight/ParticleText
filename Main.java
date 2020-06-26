package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7604959185792845431L;
	private boolean running;
	private Thread thread;
	private Mouse mouse;
	private Parser parser;
	private ArrayList<Particle> particles;

	public static void main(String[] args) {
		new Main().start();
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.thread = new Thread(this);
		this.thread.start();
		this.running = true;
	}
	
	public synchronized void stop() {
		this.running = false;
		//clean up
	}
	
	private void init() {
		this.mouse = new Mouse();
		JFrame frame = new JFrame("2D Game");
		frame.setSize(960, 800);
		this.addMouseMotionListener(this.mouse);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.requestFocus();
		//
		this.parser = new Parser(this.mouse);
		AffineTransform af = new AffineTransform();
		af.translate(128, 128);
		af.rotate(0.785398);
		af.scale(2, 2);
		this.particles = this.parser.textToParticle("Hey Man", 3f, new Font("Georgia", Font.PLAIN, 80), af, frame.getGraphics()); //r size between 2-4 is best
	}
	
	@Override
	public void run() {
		this.init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(this.running == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				this.tick();
				updates++;
				delta--;
			}
			this.render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		System.exit(0);
	}
	
	private void tick() {
		for(Particle p: this.particles) {
			p.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform af = g2d.getTransform();
		//start draw
			//bg
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//particle
		for(Particle p: this.particles) {
			p.render(g);
		}
		//end draw
		g2d.setTransform(af);
		g.dispose();
		bs.show();
	}

}