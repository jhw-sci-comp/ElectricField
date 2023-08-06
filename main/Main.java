package main;

import charge.PointCharge;
import electricfield.ElectricField;
import electricfield.FieldLine;
import grid.GridBoundaryException;
import vectorspace2d.Vector2D;
import visualization.MainFrame;

public class Main {

	public static void main(String[] args) {
		
		
		// Example 1:
		PointCharge q1 = new PointCharge(-0.1f, 0.0f,   1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, 0.0f,  -1.0E-6f);
		
		ElectricField e = new ElectricField(q1, q2);
		
		
		
		/*
		// Example 2:
		PointCharge q1 = new PointCharge(-0.1f, 0.0f,   1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, 0.0f,  -1.0E-6f);		
		PointCharge q3 = new PointCharge( 0.0f, 0.1f,  -1.0E-6f);
		PointCharge q4 = new PointCharge( 0.0f, -0.1f,  1.0E-6f);
		
		ElectricField e = new ElectricField(q1, q2, q3, q4);
		*/
		
		
		/*
		// Example 3:
		PointCharge q1 = new PointCharge(-0.1f,  0.1f,  -1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f,  0.1f,   1.0E-6f);		
		PointCharge q3 = new PointCharge( 0.1f, -0.1f,  -1.0E-6f);
		PointCharge q4 = new PointCharge(-0.1f, -0.1f,   1.0E-6f);
				
		ElectricField e = new ElectricField(q1, q2, q3, q4);
		*/
		
		
		/*
		// Example 4:
		PointCharge q1 = new PointCharge(-0.1f,  -0.1f, 1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f,   0.1f, 1.0E-6f);		
				
		ElectricField e = new ElectricField(q1, q2);
		*/
		
		
		/*
		// Example 5:
		PointCharge q1 = new PointCharge(-0.1f,  -0.1f, -1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f,   0.1f, -1.0E-6f);		
				
		ElectricField e = new ElectricField(q1, q2);
		*/
		
		
		/*
		// Example 6:
		PointCharge q1 = new PointCharge(-0.1f, 0.0f,   2.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, 0.0f,  -1.0E-6f);
				
		ElectricField e = new ElectricField(q1, q2);
		*/
		
		
		/*
		// Example 7:
		PointCharge q1 = new PointCharge(-0.1f, -0.1f,  1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, -0.1f,  1.0E-6f);
		PointCharge q3 = new PointCharge( 0.1f,  0.1f,  1.0E-6f);
		PointCharge q4 = new PointCharge(-0.1f,  0.1f,   1.0E-6f);
		PointCharge q5 = new PointCharge( 0.0f,  0.0f,   1.0E-6f);
		
		ElectricField e = new ElectricField(q1, q2, q3, q4, q5);
		*/
		
		/*
		// Example 8:
		PointCharge q1 = new PointCharge(-0.1f, 0.1f,   1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, -0.1f,  -1.0E-6f);
		
		ElectricField e = new ElectricField(q1, q2);
		*/
		
		
		e.calculateFieldLines();
		
		/*
		for(FieldLine f : e.getFieldLines()) {
			System.out.println(f.getPoints());
		}
		*/
		
				
		e.calculateVectorField();
		
				
		MainFrame main_frame = new MainFrame(e);
		main_frame.setVisible(true);
		
		
		
		
	}

}
