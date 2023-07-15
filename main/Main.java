package main;

import charge.PointCharge;
import electricfield.ElectricField;
import vectorspace2d.Vector2D;

public class Main {

	public static void main(String[] args) {
		//PointCharge q1 = new PointCharge(0.0f, 0.0f, 2.5E-8f);
		//PointCharge q2 = new PointCharge(0.05f, 0.0866f, 1.5E-8f);
		//PointCharge q3 = new PointCharge(0.1f, 0.0f, -2.0E-8f);
		PointCharge q1 = new PointCharge(-0.01f, 0.0f,  1.0E-6f);
		PointCharge q2 = new PointCharge( 0.01f, 0.0f, -1.0E-6f);
		
		ElectricField e = new ElectricField(q1, q2);		
		Vector2D E = e.calculateFieldVector(new Vector2D(0.05f, 0.0289f));	
		System.out.println(E);
		System.out.println("E = " + (Math.sqrt(Math.pow(E.getX(), 2) + Math.pow(E.getY(), 2))));
		
		e.calculateFieldLines();
		
		
		for(Vector2D v : e.getFieldLines()) {
			System.out.println(v);
		}				
		
	}

}
