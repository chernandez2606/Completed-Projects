/*
 * GravityParticle is a particle that attracts the other particles
 * This particle can be drawn onto a canvas for reference but cannot be
 * moved by other particles.
 */

class GravityParticle extends DrawableParticle{
	private double acc;
	
	public GravityParticle(double x, double y, double r){
		super(x,y,r);
		acc = 0;
	}
	
	public GravityParticle(double x, double y, double r, double a){
		this(x,y,r);
		setAcc(a);
	}
	
	public void setAcc(double a){
		acc = a;
	}
	public double getAcc(){
		return acc;
	}
	
	public double[] getAcc(double ... x){
		double out[] = {0.0, 0.0};
		if (this.inside(x))
			return out;
		
		//these two calculations are used instead of sin and cos
		//since arctan gives limited values
		out[0]=-acc*(x[0]-this.getX())/getZ(x);
		out[1]=-acc*(x[1]-this.getY())/getZ(x);
		
		return out;
	}
	
	public double getZ(double ... x){
		double a = x[0], b = x[1];
		a=this.getX()-a;
		b=this.getY()-b;
		b=Math.sqrt(a*a+b*b);
		return b;
	}
}
