/*
 * World is where the objects reside. Most of the calculations for the 
 * position, speed, and collisions of the particles are done in a thread
 * that runs in this class. It also determines the number and location
 * of gravity particles using an image and associates a random graphic
 * particle with each gravity particle
 */ 

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.awt.Color;

class World implements Runnable{
	private GraphicParticle[] particles;
	private GravityParticle[] g_particles;
	private GravityParticle user_p;
	private boolean isActive;
	private int w,h;
	private double size, maxSpd;
	
	
	public World(int width, int height){
		w = width;
		h = height;
		size = 20.0; 
		maxSpd = 4;
		
		user_p = new GravityParticle(0,0,2,-1.5);
		
		g_particles = setGravityField(new File("PicTest.bmp"));
		System.out.println(g_particles.length);
		
		particles = new GraphicParticle[g_particles.length];
		for (int x = 0; x < particles.length; x++)
			particles[x] = randStart();
		
		
		isActive = false;
		new Thread(this).start();
	}
	
	public void update(){
		for (int i = 0; i < particles.length; i++){
			GraphicParticle p = particles[i];
			GravityParticle g = g_particles[i];
			double[] usrAcc = {0.0, 0.0};
			if(isActive)
				usrAcc = user_p.getAcc(p.getPos());
			double[] spd = p.getSpd();
			double[] acc = g.getAcc(p.getPos());
			
			for (int x = 0; x < 2; x++)
				spd[x] = (spd[x] + acc[x]+ usrAcc[x]) * .99;
			p.setSpd(spd);
		}
		collisionCheck();
		for(GraphicParticle p: particles)
			p.move();
	}
	
	public void wallCheck(){
		for (GraphicParticle p: particles){
			double[] a = p.next();
			
			if((a[0] > (double) w) || (a[0]) < 0){
				p.revXspd();
			}
			if((a[1] > (double) h) || (a[1]) < 0){
				p.revYspd();
			}
		}
	}
	
	public void collisionCheck(){
		for (GraphicParticle p1: particles)
			for (GraphicParticle p2: particles)
				if(p1 != p2)
					if( 
						p1.inside(p2.next()) //|| p2.inside(p1.next())
					){
						double swap[] = p1.getSpd();
						p1.setSpd(p2.getSpd());
						p2.setSpd(swap);
					}
	}
	
	public GravityParticle[] setGravityField(File file){
		ArrayList<GravityParticle> gp_list = 
			new ArrayList<GravityParticle>();
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
			int width = img.getWidth(), height = img.getHeight();
			for( int i = 0; i < width; i+=20 )
				for( int j = 2; j < height; j+=15 )
					if ( img.getRGB(i,j) == -16711423)
						gp_list.add( 
							new GravityParticle (i/1.0, j/1.0 ,0 ,0.7));	
		} catch (IOException e) {
		}
		GravityParticle[] gp_arr = new GravityParticle[gp_list.size()];
		gp_list.toArray(gp_arr);
		
		return gp_arr;
	}
	
	public double myRand(){
		return 2.0 * (double)Math.random() - 1.0;
	}
	
	public GraphicParticle randStart(){
		double x = Math.random() * w;
		double y = Math.random() * h;
		double xspd = myRand() * maxSpd;
		double yspd = myRand() * maxSpd;
		Color col = //Color.black;
		new Color((float)Math.random(),
			(float)Math.random(),(float)Math.random());
		
		return new GraphicParticle(x,y,size,col,xspd,yspd);
	}
	
	public GraphicParticle[] getParticles(){
		return particles;
	}
	
	public GravityParticle[] getGravity(){
		return g_particles;
	}
	
	public GravityParticle getUserParticle(){
		return user_p;
	}
	
	public boolean getStatus(){
		return isActive;
	}
	public void setStatus(boolean stat){
		isActive = stat;
	}
	
	public void run() {
		try {	
			while(true){
				Thread.currentThread().sleep(5);
				update();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
