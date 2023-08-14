package charge;

import vectorspace2d.Vector2D;


/*
 * Superclass for charges providing the value and location as attributes.*
 */

public abstract class Charge {
	public static final float MINCHARGE = -3E-6f;  //minimum charge: -3 * 10^{-6} C
	public static final float MAXCHARGE =  3E-6f;  //minimum charge:  3 * 10^{-6} C
	
	protected float value;			// value of charge
	protected Vector2D location;	// location of charge
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + Float.floatToIntBits(value);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "charge = " + value + ", location = " + location;
	}	
	
}
