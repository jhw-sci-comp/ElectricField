package main;

import charge.PointCharge;
import electricfield.ElectricField;
import electricfield.FieldLine;
import vectorspace2d.Vector2D;
import visualization.MainFrame;

public class Main {

	public static void main(String[] args) {
		
		// Example 1:
		//PointCharge q1 = new PointCharge(0.0f, 0.0f, 2.5E-8f);
		//PointCharge q2 = new PointCharge(0.05f, 0.0866f, 1.5E-8f);
		//PointCharge q3 = new PointCharge(0.1f, 0.0f, -2.0E-8f);
		
		// Example 2:
		PointCharge q1 = new PointCharge(-0.1f, 0.0f,   1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f, 0.0f,  -1.0E-6f);
		
		//PointCharge q3 = new PointCharge( 0.0f, 0.1f,  -1.0E-6f);
		//PointCharge q4 = new PointCharge( 0.0f, -0.1f,  1.0E-6f);
		
		/*
		// Example 3:
		PointCharge q1 = new PointCharge(-0.1f,  0.1f,  -1.0E-6f);
		PointCharge q2 = new PointCharge( 0.1f,  0.1f,   1.0E-6f);		
		PointCharge q3 = new PointCharge( 0.1f, -0.1f,  -1.0E-6f);
		PointCharge q4 = new PointCharge(-0.1f, -0.1f,   1.0E-6f);
		*/
		
		ElectricField e = new ElectricField(q1, q2);
		
		
		e.calculateFieldLines();
				
		/*
		for(FieldLine f : e.getFieldLines()) {
			for(Vector2D v : f.getPoints()) {
				System.out.println(v);
			}
		}
		*/
		
				
		e.calculateVectorField();
		
		/*
		 for(Vector2D v : e.getVectorField()) {
				System.out.println("v: " + v);
		 }
		*/
		
		/*
		for(float p : e.getPotentials()) {
			System.out.println("p: " + p);
		}
		*/
		
		
		
		
		
		MainFrame main_frame = new MainFrame(e);
		main_frame.setVisible(true);
		
	}

}
