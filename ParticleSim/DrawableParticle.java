import java.awt.Color;
import java.awt.Graphics;

class DrawableParticle extends Particle{
	Color color;
	
	public DrawableParticle(double x, double y, double r, Color c){
		super(x,y,r);
		setCol(c);
	}
	
	public void setCol(Color c){
		color = c;
	}
	
	/*
	 * The draw method draws the shape on the screen.
	 */
	public void draw(Graphics window) {
		window.setColor(color);
		window.fillOval((int)(getX()-getR()+.5), (int)(getY()-getR()+.5), 
			(int)(getR()+.5), (int)(getR()+.5));
	}

	/*
	 * This draw method will be used to erase the shape.
	 */
	public void draw(Graphics window, Color col) {
		window.setColor(col);
		window.fillOval((int)(getR()+getX()+.5), (int)(getR()+getY()+.5), 
			(int)(getD()+.5), (int)(getD()+.5));
	}
}
