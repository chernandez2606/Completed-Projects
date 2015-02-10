

//Name -
//Date -
//Class -
//Lab  -

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Canvas;
import javax.swing.JPanel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class World extends JPanel implements Runnable {
	private GraphicParticle[] particles;
	private GraphicParticle myP;
	private int h, w;
	private double size = 20.0, maxSpd = 5;
	// use an array of Molecule
	// Molecule[] molecules;

	public World(int width, int height) {
		h = height;
		w = width;
		setBackground(Color.WHITE);
		setVisible(true);

		// refer sh to a new Particle
		particles = new GraphicParticle[100];
		for (int x = 0; x < particles.length; x++){
			particles[x] = randStart();
		}
			
		//myP = new GraphicParticle(2.0,2.0,20,Color.red,1.0,100.0);
		//myP = randStart();
		
		new Thread(this).start();
	}
	
	public double myRand(){
		return 2.0 * (double)Math.random() - 1.0;
	}
	
	public GraphicParticle randStart(){
		double x = Math.random() * h;
		double y = Math.random() * w;
		double xspd = myRand() * maxSpd;
		double yspd = myRand() * maxSpd;
		Color col = //Color.black;
		new Color((float)Math.random(),
			(float)Math.random(),(float)Math.random());
		
		return new GraphicParticle(x,y,size,col,xspd,yspd);
	}

	public void update(Graphics window) {
		//check();
		
		//for (GraphicParticle p: particles)
		//	p.move();
		//myP.move();
		paint(window);
	}

	public void paint(Graphics window) {
		window.setColor(Color.white);
		window.fillRect(0, 0, getWidth(), getHeight());
		check();
		
		for (GraphicParticle p: particles){
			double[] spd = p.getSpd();
			p.setSpd(spd[0],spd[1]+.05);
			p.move();
			p.draw(window);
		}
		//myP.move();
		//myP.draw(window);
	}
	
	public void check(){
		
		for (GraphicParticle p: particles){
			double[] a = p.next();
			
			if((a[0] > (double) getWidth()) || (a[0]) < 0){
				p.revXspd();
				p.setX((double) p.getX()%getWidth());
			}
			if((a[1] > (double) getHeight()) || (a[1]) < 0){
				p.revYspd();
				p.setY((double) p.getY()%getHeight());
			}
		}
		
		
		for (GraphicParticle p1: particles)
			for (GraphicParticle p2: particles)
				if(p1 != p2)
					if( p1.inside(p2.next()) || p2.inside(p1.next())){
						double swap[] = p1.getSpd();
						p1.setSpd(p2.getSpd());
						p2.setSpd(swap);
					}
	}

	public void run() {
		try {	
			while(true){
				Thread.currentThread().sleep(10);
				repaint();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
