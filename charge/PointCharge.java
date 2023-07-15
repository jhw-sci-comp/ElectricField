package charge;

import vectorspace2d.Vector2D;

public class PointCharge extends Charge {	
	
	public PointCharge(Vector2D location, float charge) {
		super(location, charge);
	}
	
	public PointCharge(float x, float y, float charge) {
		super(new Vector2D(x, y), charge);				
	}	

}
