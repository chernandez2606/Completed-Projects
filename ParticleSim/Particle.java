class Particle{
	private double x,y,r,d,r2;
	
	public Particle(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
		this.r2 = Math.pow(r*1.005,2);
		this.d = 2*r;
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
