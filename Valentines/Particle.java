/*
 * Particle is an object that stores its position and radius
 * It also checks to see if a location is inside of the particle 
 */

class Particle{
	private double x,y,r,d,r2,step,pi2;
	
	public Particle(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
		this.d = 2*r;
		//this.r2 = Math.pow(d,2);
		this.r2 = Math.pow(r,1.5);
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getR(){
		return r;
	}
	public double getD(){
		return d;
	}
	
	//Gives the position as an array
	public double[] getPos(){
		double[] a = {x, y};
		return a;
	}
	
	public void setX(double n){
		x = n;
	}
	public void setY(double n){
		y = n;
	}
	public void setR(double n){
		r = n;
	}
	
	public boolean inside(double ... a){
		return !( Math.pow(x-a[0],2) + Math.pow(y-a[1],2) > r2);
	}
}
