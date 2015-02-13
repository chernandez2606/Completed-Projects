class UserGravParticle extends GravityParticle{
	boolean active;
	
	public UserGravParticle(double x, double y, double acc){
		super(x,y,2,-acc);
		active = false;
	}
	
	public void setStatus(boolean stat){
		active = stat;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public double[] getAcc(double ... x){
		double a[] = {0.0, 0.0};
		if(!active)
			return a;
		return super.getAcc(x);
	}
	
}
