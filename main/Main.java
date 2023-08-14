package main;

import charge.ChargeLocationGridBoundaryException;
import charge.ChargeValueException;
import charge.ChargesIdenticalLocationException;
import charge.PointCharge;
import electricfield.ElectricField;
import grid.GridException;
import visualization.MainFrame;

public class Main {

	public static void main(String[] args) {
		
		try {
		
			
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
			
			/*
			// Example 9:
			PointCharge q1 = new PointCharge(-0.1f,  0.0f, 1.0E-6f);
			PointCharge q2 = new PointCharge( 0.1f,   0.0f, 1.0E-6f);		
					
			ElectricField e = new ElectricField(q1, q2);
			*/
					
			MainFrame main_frame = new MainFrame(e);
			main_frame.setVisible(true);
		
		}
		catch(ChargeLocationGridBoundaryException clgbe) {
			System.out.println("ChargeLocationGridBoundaryException occured");
		}
		catch(ChargesIdenticalLocationException cile) {
			System.out.println("ChargesIdenticalLocationException occured");
		}
		catch(ChargeValueException cve) {
			System.out.println("ChargeValueException occured");
		}
		catch(GridException gbe) {
			System.out.println("GridException occured");
		}
		catch(Exception e) {
			System.out.println("Program terminated with an exception.");
		}		
		
	}

}
