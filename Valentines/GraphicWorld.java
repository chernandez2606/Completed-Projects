/*
 * Author - Carlo Hernandez
 * Graphic World draws all the needed Particles onto the canvas and
 * updates them as needed. Graphic World also alows users to give input
 * to the program to create and move a negative gravity particle to push
 * particles around their mice
 */

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class GraphicWorld extends JPanel implements Runnable {
	private GraphicParticle[] particles;
	private GravityParticle[] g_particles;
	private GravityParticle user_p;
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
	
	/*
	 * Paints particles onto the canvas based on their location.
	 */
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
	
	/*
	 *	Mouse Listener to detect the position and click status of the 
	 * 	mouse. Sets the position of the user particle to mouse position
	 * 	and activates the gravity field around it.
	 */
	class Mousy implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			my_world.setStatus(false);
		}

		public void mousePressed(MouseEvent e) {
			my_world.setStatus(true);
			user_p.setX(e.getX());
			user_p.setY(e.getY());
		}
		
		public void mouseDragged(MouseEvent e){
			my_world.setStatus(true);
			user_p.setX(e.getX());
			user_p.setY(e.getY());
		}

		public void mouseReleased(MouseEvent e) {
			my_world.setStatus(false);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}

