package charge;

import vectorspace2d.Vector2D;


/*
 * Class for point charges which inherits the charge value and location from parent class Charge.
 * */



public class PointCharge extends Charge {		
	
	public PointCharge(Vector2D location, float charge) {
		super(location, charge);
	}
	
	
	public PointCharge(float x, float y, float charge) {
		super(new Vector2D(x, y), charge);				
	}

}
