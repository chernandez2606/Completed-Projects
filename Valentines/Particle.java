class Particle{
	private double x,y,r,d,r2;
	
	public Particle(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
		this.d = 2*r;
		//~ this.r2 = Math.pow(d,2);
		this.r2 = Math.pow(r*1.005,2);
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
		return !( Math.pow(x-a[0],2) + Math.pow(y-a[1],2) > (double) r2);
	}
	
}
