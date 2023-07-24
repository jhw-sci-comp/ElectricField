package visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import charge.Charge;
import electricfield.ElectricField;
import grid.Grid;
import vectorspace2d.Vector2D;


public class VisualizationElectricField extends JPanel {
	
	private int x_shift;
	private int y_shift;
	private int scaling;
	private JPanel layout_panel;
	private ElectricField electric_field;
	
	public VisualizationElectricField(JPanel layout_panel, ElectricField electric_field) {
		 this.layout_panel = layout_panel;		 
		 this.electric_field = electric_field;		 

		 /*
		 for(Vector2D v : electric_field.getVectorField()) {
				System.out.println("v: " + v);
		 }
		 */
		 
		 /*
		 for(float p : this.electric_field.getPotentials()) {
				System.out.println("p: " + p);
		 }
		 */
		 
		 
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		
		scaling = (int) (800 /  (Grid.MAXWIDTH - Grid.MINWIDTH));
		
		//System.out.println("scaling: " + scaling);
		
		this.setSize((int) (0.9 * layout_panel.getWidth()), layout_panel.getHeight());
		x_shift = (int) (this.getWidth() / 2 - (Grid.MAXWIDTH - Grid.MINWIDTH) * scaling / 2);
		y_shift = (int) (this.getHeight() / 2 - (Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling / 2);
		
		this.setBackground(Color.WHITE);
		
		this.drawPotentials(g);
		this.drawGrid(g);		
		this.drawElectricFieldVectors(g);
		this.drawCharges(g);
	}
	
	
	private ArrayList<Integer> transformCoordinate(float x, float y){
		ArrayList<Integer> transformed_coordinates = new ArrayList<Integer>();		
		
		transformed_coordinates.add(x_shift + (int) ((Math.abs(Grid.MINWIDTH) + x) * scaling));
		transformed_coordinates.add(y_shift + (int) ((Grid.MAXHEIGHT - y) * scaling));
		
		//System.out.println("transformeCoordinate(): " + transformed_coordinates.get(0));
		
		return transformed_coordinates;
	}
	
	
	private int transformScale(float scale) {
		return (int) (scale * scaling);
	}
	
	private void drawGrid(Graphics g) {		
		g.setColor(Color.lightGray);
		
		Font font = g.getFont().deriveFont( 15.0f );
		float string_value;
				
		int transformed_x_range = transformScale((Grid.MAXWIDTH - Grid.MINWIDTH));
		int transformed_y_range = transformScale((Grid.MAXHEIGHT - Grid.MINHEIGHT));
				
		for(int i = x_shift; i <  x_shift + transformed_x_range; i += 10) {
			for (int j = y_shift; j < y_shift + transformed_y_range; j += 10) {
				g.drawRect(i, j, 10, 10);
			}
		}
		
		
	    g.setFont(font);
	    		
		g.setColor(Color.darkGray);
		
		for(int i = x_shift; i <=  x_shift + transformed_x_range; i += 50) {			
			if((i - x_shift) % 100 == 0) {
				g.drawLine(i, y_shift, i, y_shift + transformed_y_range + 10);
				
				string_value = Math.round((Grid.MINWIDTH + ((float) (i - x_shift)) / scaling) * 100) / 100.0f;
				if(string_value < 0.0f) {
					g.drawString(Float.toString(string_value), i - 15, y_shift + transformed_y_range + 25);
				}
				else {
					g.drawString(Float.toString(string_value), i - 10, y_shift + transformed_y_range + 25);
				}
			}
			else {
				g.drawLine(i, y_shift, i, y_shift + transformed_y_range);
			}
		}
		
		
		for(int j = y_shift; j <= y_shift + transformed_y_range; j += 50) {
			if((j - y_shift) % 100 == 0) {
				g.drawLine(x_shift - 5, j,  x_shift + transformed_x_range, j);
				
				string_value = Math.round((Grid.MAXHEIGHT - ((float) (j - y_shift)) / scaling) * 100) / 100.0f;
				if(string_value <= 0.0f) {
					g.drawString(Float.toString(string_value), x_shift - 40, j + 5);
					
				}
				else {
					g.drawString(Float.toString(string_value), x_shift - 40, j + 5);
				}
			}
			else {
				g.drawLine(x_shift, j,  x_shift + transformed_x_range, j);
			}
		}			
		
	}
	
	
	private void drawCharges(Graphics g) {
		ArrayList<Integer> transformed_location = new ArrayList<Integer>();		
		
		for(Charge c : electric_field.getCharges()) {
			transformed_location.add(transformCoordinate(c.getLocation().getX(), c.getLocation().getY()).get(0));
			transformed_location.add(transformCoordinate(c.getLocation().getX(), c.getLocation().getY()).get(1));
			
			//System.out.println("transformed_location: " + transformed_location.get(0) + ", " + transformed_location.get(1));
			
			if(c.getCharge() >= 0.0f) {
				g.setColor(Color.BLACK);
				g.drawOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
				g.setColor(Color.BLUE);
				g.fillOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
			}			
			else {
				g.setColor(Color.BLACK);
				g.drawOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
				g.setColor(Color.RED);
				g.fillOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
			}
			
			transformed_location.clear();
		}
	}
	
	
	private void drawElectricFieldVectors(Graphics g) {
		ArrayList<Integer> transformed_location = new ArrayList<Integer>();
		Graphics2D g2 = (Graphics2D) g.create();
		int[] x_points, y_points;
		
		AffineTransform at = new AffineTransform();		
		float direction;
		float point_x, point_y, field_vector_x, field_vector_y;
		
		System.out.println("vector field size: " + electric_field.getVectorField().size());
		
		for(int i = 0; i <= this.electric_field.getGrid().getRows(); i += 5) {
			for(int j = 0; j <= this.electric_field.getGrid().getCols(); j += 5) {
				point_x = this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX();
				point_y = this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY();
				transformed_location.add(transformCoordinate(point_x, point_y).get(0));
				transformed_location.add(transformCoordinate(point_x, point_y).get(1));
								
				g2.setColor(Color.DARK_GRAY);
				
				x_points = new int[3];
				x_points[0] = transformed_location.get(0) - 5;
				x_points[1] = transformed_location.get(0) + 15;
				x_points[2] = transformed_location.get(0) - 5;
				y_points = new int[3];
				y_points[0] = transformed_location.get(1) - 5;
				y_points[1] = transformed_location.get(1);
				y_points[2] = transformed_location.get(1) + 5;
				
				//System.out.println(this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j) + ", direction: " + this.electric_field.getVectorField().get((this.electric_field.getGrid().getCols() + 1) + j).getDirection());
				//System.out.println("(" + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX() + ", " + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY() + "), " + this.electric_field.getVectorField().get(i/5  + j/5));
			    //System.out.println("index: " + i * (this.electric_field.getGrid().getCols() + 1) + j);
				
				
				//System.out.println("(" + i + ", " + j + ") " + "index: " + (i * (electric_field.getGrid().getCols() / 5 + 1) + j)/5);
				direction = electric_field.getVectorField().get((i * (electric_field.getGrid().getCols() / 5 + 1) + j) / 5).getDirection();
				field_vector_x = electric_field.getVectorField().get((i * (electric_field.getGrid().getCols() / 5 + 1) + j) / 5).getX();
				field_vector_y = electric_field.getVectorField().get((i * (electric_field.getGrid().getCols() / 5 + 1) + j) / 5).getY();
				
				
				//direction = 3.0f/4.0f * (float) Math.PI;
				//System.out.println("(" + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX() + ", " + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY() + "), " + "direction: " + direction);
				
				//direction = (float) (Math.toRadians(45));				
				
				if(field_vector_x != 0.0f || field_vector_y != 0.0f) {
					at.rotate(-direction, transformed_location.get(0), transformed_location.get(1));
					g2.setTransform(at);
					
					g2.fillPolygon(x_points, y_points, 3);
				}
				
				at.setToIdentity();
									
				transformed_location.clear();				
			}
			
		}		
		
	}
	
	
	private void drawPotentials(Graphics g) {		
		int y_range = this.transformScale(Grid.MAXWIDTH - Grid.MINWIDTH);
		ArrayList<Integer> color_values = new ArrayList<Integer>();
		float p_00, p_10, p_11, p_01;
		
		
		for(int i = 0; i < electric_field.getGrid().getRows(); i ++) {
			for(int j = 0; j < electric_field.getGrid().getCols(); j ++) {
				//System.out.println(this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j));
				p_00 = this.electric_field.getPotentials().get(i * (this.electric_field.getGrid().getCols() + 1) + j);
				p_10 = this.electric_field.getPotentials().get(i * (this.electric_field.getGrid().getCols() + 1) + (j + 1));
				p_11 = this.electric_field.getPotentials().get((i + 1) * (this.electric_field.getGrid().getCols() + 1) + (j + 1));
				p_01 = this.electric_field.getPotentials().get((i + 1) * (this.electric_field.getGrid().getCols() + 1) + j);
				
				color_values = this.getPotentialColor((p_00 + p_10 + p_11 + p_01) / 4.0f);
				
				g.setColor(new Color(color_values.get(0), color_values.get(1), color_values.get(2)));
				//g.setColor(Color.ORANGE);
				//g.fillRect(x_shift + i * 10, y_shift + y_range  - (j + 1) * 10, 10, 10);	
				g.fillRect(x_shift + j * 10, y_shift + y_range  - (i + 1) * 10, 10, 10);
				
				color_values.clear();
			}
		}
	}
	
	private ArrayList<Integer> getPotentialColor(float potential) {
		ArrayList<Integer> color_values = new ArrayList<Integer>();		
		float charge_subinterval;
		float charge_sub_subinterval;
		int factor;
		Object min_potential_obj, max_potential_obj;
		float min_potential;     // minimum potential
		float max_potential;     // maximum potential
		float min_scale;         // minimum scale value
		float max_scale;         // maximum scale value
		
		
		min_potential_obj = Collections.min(this.electric_field.getPotentials());
		min_potential = Float.parseFloat(min_potential_obj.toString());		
		max_potential_obj = Collections.max(this.electric_field.getPotentials());
		max_potential = Float.parseFloat(max_potential_obj.toString());
		
		
		if(Math.max(Math.abs(min_potential), Math.abs(max_potential)) % 30.0f != 0.0f) {
			min_scale = -((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 30.0f) + 1.0f) * 30.0f;
			max_scale =  ((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 30.0f) + 1.0f) * 30.0f;
		}
		else {
			min_scale = -((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 30.0f)) * 30.0f;
			max_scale =  ((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 30.0f)) * 30.0f;
		}
		
		charge_subinterval = (max_scale - min_scale) / 6.0f;
		charge_sub_subinterval = charge_subinterval / 5.0f;
		
		//System.out.println("min_scale: " + min_scale + ", min potential: " + min_potential);
		//System.out.println("max_scale: " + max_scale + ", max potential: " + max_potential);
		//System.out.println((max_scale - min_scale));
		
				
		if(potential >= min_scale && potential < (min_scale + charge_subinterval)) {
			factor = (int) Math.floor((potential - min_scale) / charge_sub_subinterval);
			color_values.add(150 + factor * 15);
			color_values.add(0);
			color_values.add(0);			
		}
		else if(potential >= min_scale + charge_subinterval && potential < (min_scale + 2.0f * charge_subinterval)) {
			factor = (int) Math.floor((potential - (min_scale + charge_subinterval)) / charge_sub_subinterval);
			color_values.add(255);
			color_values.add(factor * 51);
			color_values.add(0);
		}
		else if(potential >= min_scale + 2.0f * charge_subinterval && potential < (min_scale + 3.0f * charge_subinterval)) {
			factor = (int) Math.floor((potential - (min_scale + 2.0f * charge_subinterval)) / charge_sub_subinterval);
			color_values.add(255 - factor * 51);
			color_values.add(255);
			color_values.add(0);
		}
		else if(potential >= min_scale + 3.0f * charge_subinterval && potential < (min_scale + 4.0f * charge_subinterval)) {
			factor = (int) Math.floor((potential - (min_scale + 3.0f * charge_subinterval)) / charge_sub_subinterval);
			color_values.add(0);
			color_values.add(255);
			color_values.add(factor * 51);
		}
		else if(potential >= min_scale + 4.0f * charge_subinterval && potential < (min_scale + 5.0f * charge_subinterval)) {
			factor = (int) Math.floor((potential - (min_scale + 4.0f * charge_subinterval)) / charge_sub_subinterval);
			color_values.add(0);
			color_values.add(255 - factor * 51);
			color_values.add(255);
		}
		else if(potential >= min_scale + 5.0f * charge_subinterval && potential <= max_scale) {
			factor = (int) Math.floor((potential - (min_scale + 5.0f * charge_subinterval)) / charge_sub_subinterval);
			color_values.add(0);
			color_values.add(0);
			color_values.add(255 - factor * 15);
		}
		
		
		return color_values;
	}
	
	
	
}
