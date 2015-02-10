//Name -
//Date -
//Class -
//Lab  -

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsRunner extends JFrame
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 600;

	public GraphicsRunner()
	{
		super("Particle Simulator");

		setSize(WIDTH,HEIGHT);

		//getContentPane().add(new ShapePanel());

		getContentPane().add(new World(WIDTH,HEIGHT));

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main( String args[] )
	{
		GraphicsRunner run = new GraphicsRunner();
	}
}
