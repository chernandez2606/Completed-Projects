

//Name -
//Date -
//Class -
//Lab  -


import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Canvas;
//import javax.swing.JPanel;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;

public class GraphicWorld extends JPanel implements Runnable {
	private GraphicParticle[] particles;
	private GravityParticle[] g_particles;
	private UserGravParticle user_p;
	private World my_world;
	//~ private int count = 0;

	public GraphicWorld(int width, int height) {
		setBackground(Color.WHITE);
		setVisible(true);
		my_world = new World(width,height);

		//~ g_particles = my_world.getGravity();
		particles = my_world.getParticles();
		user_p = my_world.getUserParticle();
		
		this.addMouseListener(new Mousy());
		
		new Thread(this).start();
	}
	
	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		//~ if ( (count++) == 5000){ //draw with path
			window.setColor(Color.white);
			window.fillRect(0, 0, getWidth(), getHeight());
			//~ count = 0;		}
		
		for(GraphicParticle p: particles)
			p.draw(window);
			
		//~ for(GravityParticle g: g_particles)
			//~ g.draw(window);
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

	class Mousy implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			//statusLabel.setText("Mouse Clicked: ("+e.getX()+", "+e.getY() +")");
			user_p.setStatus(false);
			user_p.setX(e.getX());
			user_p.setY(e.getY());
		}

		public void mousePressed(MouseEvent e) {
			user_p.setStatus(true);
			user_p.setX(e.getX());
			user_p.setY(e.getY());
		}
		
		public void mouseDragged(MouseEvent e){
			user_p.setStatus(true);
			user_p.setX(e.getX());
			user_p.setY(e.getY());
		}

		public void mouseReleased(MouseEvent e) {
			user_p.setStatus(false);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}

