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
	private float eps_r = 1.0f;	
	private ArrayList<Charge> charges = new ArrayList<Charge>();
	private ArrayList<FieldLine> field_lines = new ArrayList<FieldLine>();
	private ArrayList<Vector2D> vector_field = new ArrayList<Vector2D>();
	private ArrayList<Float> potentials = new ArrayList<Float>();
	private Grid grid = new Grid();
	
	public ElectricField(Charge... charges) {
		for(Charge c : charges) {
			this.charges.add(c);
		}
				
	}	
	
	
	
	public Vector2D calculateFieldVector(Vector2D p) {
		float E_x = 0.0f;
		float E_y = 0.0f;
		float factor = 0.0f;
		float dx, dy;
		boolean is_charge_in_p = false;
		
		//System.out.println("p: " + p);
		
		for(int i = 0; i < charges.size(); i++) {
			dx = p.getX() - charges.get(i).getLocation().getX();
			dy = p.getY() - charges.get(i).getLocation().getY();
			
			//System.out.println("dx: " + dx);
			//System.out.println("dy: " + dy);
			//System.out.println("norm: " + VectorSpace2D.calculate2Norm(new Vector2D(dx, dy)));
			
			//System.out.println(i + ": " + (float) (Math.pow(VectorSpace2D.calculate2Norm(new Vector2D(dx, dy)), 3)));
			
			
			
			if(dx == 0.0f && dy == 0.0f) {
				//System.out.println(charges.get(i));
				is_charge_in_p = true;
				E_x = 0.0f;
				E_y = 0.0f;
			}
			
			if(!is_charge_in_p) {
				factor = charges.get(i).getCharge() / ((float) (Math.pow(VectorSpace2D.calculate2Norm(new Vector2D(dx, dy)), 3)));
				//System.out.println("factor: " + factor);
				
				E_x += factor * dx;
				E_y += factor * dy;
			}
			
		}
		
		factor = 1.0f / (4.0f * (float) Math.PI * EPS_0 * eps_r);		
		E_x = factor * E_x;
		E_y = factor * E_y;
		
		//System.out.println("E_x: " + E_x);
		
		return new Vector2D(E_x, E_y);
	}	
	
	
	public float calculateElectricPotential(Vector2D p) {
		float potential = 0.0f;
		float dx, dy;
		boolean is_charge_in_p = false;
		
		for(int i = 0; i < charges.size(); i++) {
			dx = p.getX() - charges.get(i).getLocation().getX();
			dy = p.getY() - charges.get(i).getLocation().getY();
			
			if(dx == 0.0f && dy == 0.0f) {
				//System.out.println(charges.get(i));
				is_charge_in_p = true;
				potential = 0.0f;
			}
			
			if(!is_charge_in_p) {
				potential += charges.get(i).getCharge() / ((float) (VectorSpace2D.calculate2Norm(new Vector2D(dx, dy))));
			}
		}
				
		return 1.0f / (4.0f * (float) Math.PI * EPS_0 * eps_r) * potential;
	}
	
	
	public void calculateVectorField() {		
		for(int i = 0; i <= grid.getRows(); i++) {
			for(int j = 0; j <= grid.getCols(); j++) {				
				this.potentials.add(this.calculateElectricPotential(grid.getPoints().get(i * (grid.getCols() + 1) + j)));				
				
				if((i % 5 == 0) && (j % 5 == 0)) {					
					this.vector_field.add(new Vector2D(this.calculateFieldVector(grid.getPoints().get(i * (grid.getCols() + 1) + j))));					
					//System.out.println("(" + grid.getPoints().get(i * (grid.getCols() + 1) + j).getX() + ", " + grid.getPoints().get(i * (grid.getCols() + 1) + j).getY() + "), " + this.calculateFieldVector(grid.getPoints().get(i * (grid.getCols() + 1) + j)));
				}
			}
		}
	}

	
	
	/*TODO: 
	 * - loop over all charges
	 * - loop over 8 directions from charge */
	public void calculateFieldLines() {		
		Vector2D v_start = new Vector2D(0.0f, 0.0f);
		Vector2D r_new = new Vector2D(0.0f, 0.0f);
		Vector2D r_temp = new Vector2D(0.0f, 0.0f);
		FieldLine field_line_temp = new FieldLine();
		boolean valid_step = true;
		float step_size = 0.001f;
		
				
		v_start.setX(charges.get(0).getLocation().getX());
		v_start.setY(charges.get(0).getLocation().getY());
		
		r_new.setX(charges.get(0).getLocation().getX());
		r_new.setY(charges.get(0).getLocation().getY());
		
		r_temp.setX(charges.get(0).getLocation().getX());
		r_temp.setY(charges.get(0).getLocation().getY());		
		
		field_line_temp.addPoint(new Vector2D(v_start));
		
		
		if(charges.get(0).getCharge() >= 0.0f) {
			r_new.copy(ODE.solveODEStep(r_new.add(new Vector2D(0.0f, step_size)), step_size,  (r) -> this.calculateFieldVector(r)));
		}
		else {
			r_new.copy(ODE.solveODEStep(r_new.add(new Vector2D(0.0f, step_size)), step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
		}
				
		
		field_line_temp.addPoint(new Vector2D(r_new));	
		
		
		while(valid_step) {
			
			if(charges.get(0).getCharge() >= 0.0f) {
				r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
			}
			else {
				r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
			}
			
			//System.out.println("r_new: " + r_new.hashCode());
			
			if(r_new.getX() >= Grid.MINWIDTH && r_new.getX() <= Grid.MAXWIDTH && r_new.getY() >= Grid.MINHEIGHT && r_new.getY() <= Grid.MAXHEIGHT) {				
				field_line_temp.getPoints().add(new Vector2D(r_new));
				
				if(r_new.getX() == Grid.MINWIDTH || r_new.getX() == Grid.MAXWIDTH || r_new.getY() == Grid.MINHEIGHT || r_new.getY() == Grid.MAXHEIGHT) {
					valid_step = false;
				}
			}
			else if(r_new.getX() < Grid.MINWIDTH) {
				r_new.setX(Grid.MINWIDTH);
				field_line_temp.getPoints().add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getX() > Grid.MAXWIDTH) {
				r_new.setX(Grid.MAXWIDTH);
				field_line_temp.getPoints().add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getY() < Grid.MINHEIGHT) {
				r_new.setY(Grid.MINHEIGHT);
				field_line_temp.getPoints().add(new Vector2D(r_new));
				valid_step = false;
			}			
			else if(r_new.getY() > Grid.MAXHEIGHT) {
				r_new.setY(Grid.MAXHEIGHT);
				field_line_temp.getPoints().add(new Vector2D(r_new));
				valid_step = false;
			}
			
			for(Charge c : charges) {
				
				if(charges.get(0).getCharge() >= 0.0f) {
					r_temp.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
				}
				else {
					r_temp.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
				}
				
				r_temp = r_temp.add(r_new.scale(-1.0f));
				
				//System.out.println("r_new: " + r_new);
				//System.out.println("r_temp: " + r_temp);
				
				if(r_new.isInNeighbourhood(c.getLocation(), VectorSpace2D.calculate2Norm(r_temp))) {
					field_line_temp.getPoints().add(new Vector2D(c.getLocation()));
					valid_step = false;
				}
			}			
			
		}		
		
		
		field_lines.add(new FieldLine(field_line_temp));
		field_line_temp.getPoints().clear();
		
	}
	
	public ArrayList<FieldLine> getFieldLines() {
		return field_lines;
	}
	
	public ArrayList<Float> getPotentials() {
		return this.potentials;
	}
	
	public ArrayList<Vector2D> getVectorField() {
		return this.vector_field;
	}
	
	public ArrayList<Charge> getCharges() {
		return this.charges;
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
}
