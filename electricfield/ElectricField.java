package electricfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import charge.Charge;
import charge.PointCharge;
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
	
	private float min_potential = 0.0f;
	private float max_potential = 0.0f;
	
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
		
		this.calculateMinPotential();
		this.calculateMaxPotential();
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
		float angle = (float) (Math.PI / 8.0);
				
		Vector2D r_old = new Vector2D(0.0f, 0.0f);
		Vector2D dr_old = new Vector2D(0.0f, 0.0f);
		Vector2D dr_new = new Vector2D(0.0f, 0.0f);	
		
		Vector2D endpoint_fieldline = new Vector2D(0.0f, 0.0f);
		
		
		HashMap<Charge, ArrayList<Float>> charges_field_line_angles = new HashMap<Charge, ArrayList<Float>>();
		HashMap<Charge, ArrayList<Integer>> charges_field_line_count = new HashMap<Charge, ArrayList<Integer>>();
		
		
		for(Charge charge : this.charges) {
			if(charge instanceof PointCharge) {
				charges_field_line_angles.put(charge, new ArrayList<Float>());
				
				for(int k = 0; k < 16; k++) {
					//charges_field_line_angles.get(charge).add(true);
				}
			}
		}
		
		
		for(Charge charge : this.charges) {
			if(charge instanceof PointCharge) {
				charges_field_line_count.put(charge, new ArrayList<Integer>());
				
				for(int k = 1; k <= 4; k++) {
					charges_field_line_count.get(charge).add(4);
				}
			}
		}
		
		//System.out.println(charges_field_line_count);
		
		
		//TODO: introduce attribute that keeps track of the field lines that have already been calculated
		
		for(Charge charge : this.charges) {		
			
			
			
			//System.out.println(charge);
			
			for(int i = 0; i < 16; i++) {		
				
				//System.out.println(i + ": " + (int)(i/4) + ", line count: " + charges_field_line_count.get(charge).get((int) (i / 4)));
							
				
				//System.out.println(i + ": " + charges_field_line_count);
				
				v_start.setX(charge.getLocation().getX());
				v_start.setY(charge.getLocation().getY());
				
				r_new.setX(charge.getLocation().getX());
				r_new.setY(charge.getLocation().getY());
				
				r_temp.setX(charge.getLocation().getX());
				r_temp.setY(charge.getLocation().getY());		
				
				field_line_temp.addPoint(new Vector2D(v_start));
				
				
				//System.out.println("angle: " + i);			
				
				
				
				if(charge.getCharge() >= 0.0f) {
					r_new.copy(ODE.solveODEStep(r_new.add(new Vector2D(step_size * (float) Math.cos(i * angle), step_size * (float) Math.sin(i * angle))), step_size,  (r) -> this.calculateFieldVector(r)));
				}
				else {
					r_new.copy(ODE.solveODEStep(r_new.add(new Vector2D(step_size * (float) Math.cos(i * angle), step_size * (float) Math.sin(i * angle))), step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
				}
				
				//System.out.println("r_new: " + r_new);
				
				dr_new.copy(r_new.add(charge.getLocation().scale(-1.0f)));
						
				
				field_line_temp.addPoint(new Vector2D(r_new));	
				
				
				while(valid_step) {
					
										
					
					r_old.copy(r_new);
					dr_old.copy(dr_new);
								
					
					if(charge.getCharge() >= 0.0f) {
						r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
					}
					else {
						r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
					}
										
					
					
					dr_new.copy(r_new);					
					dr_new.copy(r_new.add(r_old.scale(-1)));
					dr_new.copy(dr_new.scale(1.0f / VectorSpace2D.calculate2Norm(dr_new)));
													
					
					if(r_new.getX() >= Grid.X_MIN && r_new.getX() <= Grid.X_MAX && r_new.getY() >= Grid.Y_MIN && r_new.getY() <= Grid.Y_MAX) {				
						field_line_temp.getPoints().add(new Vector2D(r_new));
						
	//TODO: Careful: special case when charge is on border will not work with this!!!!!!!!!!!!!!!!!!!
						if(r_new.getX() == Grid.X_MIN || r_new.getX() == Grid.X_MAX || r_new.getY() == Grid.Y_MIN || r_new.getY() == Grid.Y_MAX) {
							valid_step = false;
						}
					}
					else if(r_new.getX() < Grid.X_MIN) {
						r_new.setX(Grid.X_MIN);
						field_line_temp.getPoints().add(new Vector2D(r_new));
						valid_step = false;
					}			
					else if(r_new.getX() > Grid.X_MAX) {
						r_new.setX(Grid.X_MAX);
						field_line_temp.getPoints().add(new Vector2D(r_new));
						valid_step = false;
					}			
					else if(r_new.getY() < Grid.Y_MIN) {
						r_new.setY(Grid.Y_MIN);
						field_line_temp.getPoints().add(new Vector2D(r_new));
						valid_step = false;
					}			
					else if(r_new.getY() > Grid.Y_MAX) {
						r_new.setY(Grid.Y_MAX);
						field_line_temp.getPoints().add(new Vector2D(r_new));
						valid_step = false;
					}
										
					
					for(Charge c : charges) {
						
						if(charge.getCharge() >= 0.0f) {
							r_temp.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
						}
						else {
							r_temp.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
						}
						
						r_temp = r_temp.add(r_new.scale(-1.0f));
						
						//System.out.println("r_new: " + r_new);
						//System.out.println("r_temp: " + r_temp);
						
						if(r_new.isInNeighbourhood(c.getLocation(), VectorSpace2D.calculate2Norm(r_temp)) && !c.equals(charge)) {					

							field_line_temp.getPoints().add(new Vector2D(c.getLocation()));
							valid_step = false;			
							
						}
					}
					
										
					
					// if the current step points in the opposite direction as the previous step discard field line
					//if(dr_new.add(dr_old).scale(-1).getX() == 0.0f && dr_new.add(dr_old).scale(-1).getY() == 0.0f) {
					if(Math.abs(dr_new.getDirection() - dr_old.getDirection()) > (float) Math.PI / 2.0) {
						
						valid_step = false;
						field_line_temp.getPoints().clear();
					}
					
				}	
				
				
				//System.out.println(charges_field_line_angles);
				
				
				if(!field_line_temp.getPoints().isEmpty()) {
					field_lines.add(new FieldLine(field_line_temp));
				}
				
				field_line_temp.getPoints().clear();
				valid_step = true;
			
				
				
			}//end for i
			
			
			
		}
		
		//System.out.println("field lines: " + field_lines.size());
		
		
	}
	
	private void calculateMinPotential() {
		Object min_potential_obj;
		
		min_potential_obj = Collections.min(this.potentials);
		min_potential = Float.parseFloat(min_potential_obj.toString());		
	}
	
	private void calculateMaxPotential() {
		Object max_potential_obj;
		
		max_potential_obj = Collections.max(this.potentials);
		max_potential = Float.parseFloat(max_potential_obj.toString());
	}
	
	public float getMinPotential() {
		return this.min_potential;
	}
	
	public float getMaxPotential() {
		return this.max_potential;
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
