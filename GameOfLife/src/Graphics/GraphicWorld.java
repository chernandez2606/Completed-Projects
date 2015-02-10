package Graphics;

//Name -
//Date -
//Class -
//Lab  -

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import javax.swing.JPanel;

import Things.Cell;
import Things.World;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GraphicWorld extends JPanel implements Runnable {
	private World world;
	private Cell[][] table;
	private int size;
	private GraphicsRunner grphics;
	int brand = 0;
	
	// use an array of Molecule
	// Molecule[] molecules;

	public GraphicWorld(GraphicsRunner gr, int s, double chance) {
		setBackground(Color.black);
		setVisible(true);
		grphics = gr;
		size = s;
		world = new World(gr.getWidth()/size,gr.getHeight()/size, chance);
		table = world.getWorld();
		new Thread(this).start();
	}
	
	public GraphicWorld(GraphicsRunner gr, int s, World wrld) {
		brand=mode();
		setBackground(Color.black);
		setVisible(true);
		grphics = gr;
		size = s;
		world = wrld;
		table = world.getWorld();
		new Thread(this).start();
	}

	public void update(Graphics window) {
		paint(window);
	}
	
	public int mode(){
		Scanner scan = new Scanner(System.in);
		System.out.println("What mode do you want?");
		System.out.println("0 is normal");
		System.out.println("1 is pretty mode");
		System.out.println("2 is seizuriffic");
		return scan.nextInt();
		
	}

	public void paint(Graphics window) {
		if (brand != 2) window.setColor(Color.black);
		else window.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
		window.fillRect(0, 0, grphics.getWidth(),grphics.getHeight());
		if(brand == 0) window.setColor(Color.white);
		for(int x = 0; x < world.getW(); x++){
			for(int y = 0; y < world.getH(); y++){
				if(brand != 0) window.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
				if(table[x][y].isAlive())
					window.fillRect(x*size, y*size, size, size);
			}
		}
		
	}
	

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(0);
				repaint();
			}
		} catch (Exception e) {
		}
	}
}
