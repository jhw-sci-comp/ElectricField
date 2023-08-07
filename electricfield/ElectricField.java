package electricfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import charge.Charge;
import charge.ChargeLocationGridBoundaryException;
import charge.ChargeValueException;
import charge.ChargesIdenticalLocationException;
import charge.PointCharge;
import grid.Grid;
import grid.GridException;
import ode.ODE;
import vectorspace2d.Vector2D;
import vectorspace2d.VectorSpace2D;




/*
 * Class for representing an electric field mainly represented by field vectors, field lines and electric potentials
 * */



public class ElectricField {
	private static final float EPS_0 = 8.854E-12f;								// electric field constant (vacuum permittivity)
	private float eps_r = 1.0f;													// relative permittivity
	private ArrayList<Charge> charges = new ArrayList<Charge>();				// 
	private ArrayList<FieldLine> field_lines = new ArrayList<FieldLine>();
	private ArrayList<Vector2D> vector_field = new ArrayList<Vector2D>();
	private ArrayList<Float> potentials = new ArrayList<Float>();
	private Grid grid = new Grid();
	
	private float min_potential = 0.0f;
	private float max_potential = 0.0f;
	
	private ArrayList<Vector2D> zero_vectors = new ArrayList<Vector2D>();
	
	
	public ElectricField(Charge... charges) throws GridException, ChargeValueException {		
		
		for(Charge c : charges) {
			if(c.getLocation().getX() < Grid.X_MIN || c.getLocation().getX() > Grid.X_MAX || c.getLocation().getY() < Grid.Y_MIN || c.getLocation().getY() > Grid.Y_MAX) {
				throw new ChargeLocationGridBoundaryException();
			}
			
			if(c.getCharge() < Charge.MINCHARGE || c.getCharge() > Charge.MAXCHARGE) {
				throw new ChargeValueException();
			}			
			
			this.charges.add(c);
		}
		
		
		for(int i = 0; i < this.charges.size(); i++) {
			for(int j = i + 1; j < this.charges.size(); j++) {
				if(this.charges.get(i).getLocation().equals(this.charges.get(j).getLocation())) {
					throw new ChargesIdenticalLocationException();
				}
			}
		}
		
		
				
		this.calculateVectorField();
		this.calculateFieldLines();
				
	}	
	
	
	
	public Vector2D calculateFieldVector(Vector2D p) {
		float E_x = 0.0f;
		float E_y = 0.0f;
		float factor = 0.0f;
		float dx, dy;
		boolean is_charge_in_p = false;
							
		for(int i = 0; i < charges.size(); i++) {
			dx = p.getX() - charges.get(i).getLocation().getX();
			dy = p.getY() - charges.get(i).getLocation().getY();			
			
			if(dx == 0.0f && dy == 0.0f) {				
				is_charge_in_p = true;
				E_x = 0.0f;
				E_y = 0.0f;
			}
			
			if(!is_charge_in_p) {
				factor = charges.get(i).getCharge() / ((float) (Math.pow(VectorSpace2D.calculate2Norm(new Vector2D(dx, dy)), 3)));
								
				E_x += factor * dx;
				E_y += factor * dy;
			}
			
		}
		
		factor = 1.0f / (4.0f * (float) Math.PI * EPS_0 * eps_r);		
		E_x = factor * E_x;
		E_y = factor * E_y;		
		
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
				
				if(Math.abs(this.calculateFieldVector(grid.getPoints().get(i * (grid.getCols() + 1) + j)).getX()) < 1.0E-8f && Math.abs(this.calculateFieldVector(new Vector2D(grid.getPoints().get(i * (grid.getCols() + 1) + j))).getY()) < 1.0E-8f) {
					
					this.zero_vectors.add(new Vector2D(grid.getPoints().get(i * (grid.getCols() + 1) + j)));
					
				}
				
				if((i % 5 == 0) && (j % 5 == 0)) {					
					this.vector_field.add(new Vector2D(this.calculateFieldVector(grid.getPoints().get(i * (grid.getCols() + 1) + j))));					
				}
			}
		}
		
		for(Charge c : this.charges) {
			this.zero_vectors.removeIf(v -> (v.getX() == c.getLocation().getX()) && (v.getY() == c.getLocation().getY()));
		}
						
		
		this.calculateMinPotential();
		this.calculateMaxPotential();
	}
	
	
	public void calculateFieldLines() {		
		Vector2D v_start = new Vector2D(0.0f, 0.0f);
		Vector2D r_new = new Vector2D(0.0f, 0.0f);
		Vector2D r_temp = new Vector2D(0.0f, 0.0f);
		FieldLine field_line_temp = new FieldLine();
		boolean valid_step = true;
		float step_size = 0.001f;
		float angle = (float) (Math.PI / 6.0);
				
		Vector2D r_old = new Vector2D(0.0f, 0.0f);
		Vector2D dr_old = new Vector2D(0.0f, 0.0f);
		Vector2D dr_new = new Vector2D(0.0f, 0.0f);	
		
		Vector2D point_temp = new Vector2D(0.0f, 0.0f);
		Vector2D point_temp_2 = new Vector2D(0.0f, 0.0f);		
		
		HashMap<Charge, ArrayList<Vector2D>> charges_start_end_points = new HashMap<Charge, ArrayList<Vector2D>>();
						
		
		for(Charge charge : this.charges) {
			if(charge instanceof PointCharge) {
				charges_start_end_points.put(charge, new ArrayList<Vector2D>());
								
				for(int k = 0; k < 12; k++) {
					point_temp.setX(charge.getLocation().getX() + step_size * (float) Math.cos(k * angle));
					point_temp.setY(charge.getLocation().getY() + step_size * (float) Math.sin(k * angle));
										
					if(charge.getCharge() >= 0.0f) {
						point_temp_2.copy(this.calculateFieldVector(point_temp));
					}
					else {
						point_temp_2.copy(this.calculateFieldVector(point_temp).scale(-1.0f));
					}
										
					point_temp.copy(point_temp_2.scale(step_size / VectorSpace2D.calculate2Norm(point_temp_2)));
					
					point_temp_2.copy(point_temp.add(charge.getLocation()));
										
					charges_start_end_points.get(charge).add(new Vector2D(point_temp_2));					
					
				}
			}
		}
				
		
		for(Charge charge : this.charges) {				
			
			for(int i = 0; i < 12; i++) {					
				
				field_line_temp.addPoint(charge.getLocation());
				
				v_start.copy(charges_start_end_points.get(charge).get(i));
				
				r_new.copy(charges_start_end_points.get(charge).get(i));
				
				r_temp.copy(charges_start_end_points.get(charge).get(i));	
				
				field_line_temp.addPoint(new Vector2D(v_start));
				
				
				
				if(charge.getCharge() >= 0.0f) {					
					r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r)));
				}
				else {
					r_new.copy(ODE.solveODEStep(r_new, step_size,  (r) -> this.calculateFieldVector(r).scale(-1.0f)));
				}			
				
				
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
	
						if(r_new.getX() == Grid.X_MIN && charge.getLocation().getX() != Grid.X_MIN || r_new.getX() == Grid.X_MAX && charge.getLocation().getX() != Grid.X_MAX || r_new.getY() == Grid.Y_MIN && charge.getLocation().getY() != Grid.Y_MIN || r_new.getY() == Grid.Y_MAX && charge.getLocation().getY() != Grid.Y_MAX) {
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
												
					
						
						if(!c.equals(charge) && r_new.isInNeighbourhood(c.getLocation(), 0.001f)) {						
							

							field_line_temp.getPoints().add(new Vector2D(c.getLocation()));
							valid_step = false;								
							
						}
						
					}	
										
					
					for(Vector2D v : this.zero_vectors) {
						if(VectorSpace2D.calculate2Norm(r_new.add(v.scale(-1.0f))) < 0.0001f) {
							valid_step = false;
							field_line_temp.getPoints().clear();
						}
					}
					
					
														
					if(Math.asin(Math.abs((dr_new.scale(-1.0f)).calculateCrossProductZ(dr_old)) / (VectorSpace2D.calculate2Norm(dr_new) * VectorSpace2D.calculate2Norm(dr_old))) > (float) Math.PI / 2.0 || VectorSpace2D.calculateDotProduct(dr_old, dr_new) < 0.0f) {
						
						valid_step = false;
						field_line_temp.getPoints().clear();
					}					
					
				}	
							
				
				if(!field_line_temp.getPoints().isEmpty()) {
					field_lines.add(new FieldLine(field_line_temp));
				}
				
				
							
				
				field_line_temp.getPoints().clear();
				valid_step = true;				
				
			}//end for i
			
			
			
		}// end if iteration over charges				
		
		
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
