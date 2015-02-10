package Graphics;
//Name -
//Date -
//Class -
//Lab  -

import javax.swing.JFrame;

import Things.World;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GraphicsRunner extends JFrame
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 800;

	public GraphicsRunner()
	{
		super("Game of No Life");

		setSize(WIDTH,HEIGHT);
		int size = 2;
		
		World world = new World(WIDTH/size,HEIGHT/size);
		world.insertPattern(0, 0, new File("points.txt"));
		world.insertPattern(0, -351, new File("points.txt"));
//		world.insertPattern(WIDTH/size/2, HEIGHT/size/2, new File("f-pentomino"));
		world.start();
//		new GraphicWorld(this,2,.1);
		getContentPane().add(new GraphicWorld(this,size,world));

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main( String args[] )
	{
		GraphicsRunner run = new GraphicsRunner();
	}
}