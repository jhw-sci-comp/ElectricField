package charge;

import vectorspace2d.Vector2D;

public abstract class Charge {
	public static final float MINCHARGE = -3E-6f;  //minimum charge -3 micro Coulomb 
	public static final float MAXCHARGE =  3E-6f;  //minimum charge -3 micro Coulomb
	
	protected float value;
	protected Vector2D location;
	
	public Charge(Vector2D location, float value) {
		this.location = location;
		this.value = value; 
	}
		
	public float getCharge() {
		return value;
	}
	
	public Vector2D getLocation() {
		return this.location;
	}
}
