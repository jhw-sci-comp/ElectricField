package electricfield;

import java.util.ArrayList;

import charge.Charge;
import grid.Grid;
import ode.ODE;
//import grid.Grid;
import vectorspace2d.Vector2D;
import vectorspace2d.VectorSpace2D;


public class ElectricField {
	private static final float EPS_0 = 8.854E-12f;
	private  float eps_r = 1.0f;
	//private Grid grid = new Grid();
	private ArrayList<Charge> charges = new ArrayList<Charge>();
	private ArrayList<Vector2D> field_lines = new ArrayList<Vector2D>();
	
	public ElectricField(Charge... charges) {
		for(Charge c : charges) {
			this.charges.add(c);
		}
		
	}	
	
	
	
	public Vector2D calculateFieldVector(Vector2D p) {
		float E_x = 0.0f;
		float E_y = 0.0f;
		float factor;
		float dx, dy;
		
		for(int i = 0; i < charges.size(); i++) {
			dx = p.getX() - charges.get(i).getLocation().getX();
			dy = p.getY() - charges.get(i).getLocation().getY();
			factor = charges.get(i).getCharge() / ((float) (Math.pow(VectorSpace2D.calculate2Norm(new Vector2D(dx, dy)), 3)));
			E_x += factor * dx;
			E_y += factor * dy;
		}
		
		factor = 1 / (4.0f * (float) Math.PI * EPS_0 * eps_r);
		E_x = factor * E_x;
		E_y = factor * E_y;
		
		return new Vector2D(E_x, E_y);
	}	
	
	
	/*TODO: 
	 * - loop over all charges
	 * - loop over 8 directions from charge
	 * - how to deal with negative charges? (maybe invert direction an follow the line?)*/
	public void calculateFieldLines() {		
		Vector2D v_start = new Vector2D(charges.get(0).getLocation().getX(), charges.get(0).getLocation().getY());
		Vector2D r_new = new Vector2D(v_start);
		Vector2D r_temp = new Vector2D(v_start);
		boolean valid_step = true;
		float step_size = 0.001f;
		
		field_lines.add(new Vector2D(v_start));
		
		r_new.copy(ODE.solveODEStep(r_new.add(new Vector2D(0.0f, step_size)), step_size,  (r) -> this.calculateFieldVector(r)));

		
		
		field_lines.add(new Vector2D(r_new));
		
		
		while(valid_step) {
			
			r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
			
			//System.out.println("r_new: " + r_new.hashCode());
			
			if(r_new.getX() >= Grid.MINWIDTH && r_new.getX() <= Grid.MAXWIDTH && r_new.getY() >= Grid.MINHEIGHT && r_new.getY() <= Grid.MAXHEIGHT) {				
				field_lines.add(new Vector2D(r_new));
				
				if(r_new.getX() == Grid.MINWIDTH || r_new.getX() == Grid.MAXWIDTH || r_new.getY() == Grid.MINHEIGHT || r_new.getY() == Grid.MAXHEIGHT) {
					valid_step = false;
				}
			}
			else if(r_new.getX() < Grid.MINWIDTH) {
				r_new.setX(Grid.MINWIDTH);
				field_lines.add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getX() > Grid.MAXWIDTH) {
				r_new.setX(Grid.MAXWIDTH);
				field_lines.add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getY() < Grid.MINHEIGHT) {
				r_new.setY(Grid.MINHEIGHT);
				field_lines.add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getY() > Grid.MAXHEIGHT) {
				r_new.setY(Grid.MAXHEIGHT);
				field_lines.add(new Vector2D(r_new));
				valid_step = false;
			}
			
			for(Charge c : charges) {
				r_temp.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
				r_temp = r_temp.add(r_new.scale(-1.0f));
				
				//System.out.println("r_new: " + r_new);
				//System.out.println("r_temp: " + r_temp);
				
				if(r_new.isInNeighbourhood(c.getLocation(), VectorSpace2D.calculate2Norm(r_temp))) {
					field_lines.add(new Vector2D(c.getLocation()));
					valid_step = false;
				}
			}
			
		}
		
		
		
		//field_lines.addAll(ODE.solveODE(v_start.add(new Vector2D(0.0f, -0.1f)), 0.1f,  (r) -> this.calculateFieldVector(r)));
		//field_lines.addAll(ODE.solveODE(v_start.add(new Vector2D(-0.1f, 0.0f)), 0.1f,  (r) -> this.calculateFieldVector(r)));
		//field_lines.addAll(ODE.solveODE(v_start.add(new Vector2D(0.1f, 0.0f)), 0.1f,  (r) -> this.calculateFieldVector(r)));
		
		
	}
	
	public ArrayList<Vector2D> getFieldLines() {
		return field_lines;
	}
	
}
