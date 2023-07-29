package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.binding.StringFormatter;

import charge.Charge;
import electricfield.ElectricField;
import grid.Grid;
import vectorspace2d.Vector2D;


public class VisualizationElectricField extends JPanel {
	
	private int x_shift;
	private int y_shift;
	private int scaling;	
	private ElectricField electric_field;
	
	private JFrame main_frame;
	
	private float min_potential;     // minimum potential
	private float max_potential;     // maximum potential
	
	
	
	//public VisualizationElectricField(JPanel layout_panel, ElectricField electric_field) {
	public VisualizationElectricField(JFrame main_window, ElectricField electric_field) {		 		 
		 this.electric_field = electric_field;
		 
		 this.main_frame = main_window;
		 
		 this.min_potential = this.electric_field.getMinPotential();
		 this.max_potential = this.electric_field.getMaxPotential();
		 
		 
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		
		scaling = (int) (800 /  (Grid.X_MAX - Grid.X_MIN));
		
		//System.out.println("scaling: " + scaling);		
		
		this.setSize(this.main_frame.getWidth(), this.main_frame.getHeight());
		x_shift = (int) (this.getWidth() / 2 - (Grid.X_MAX - Grid.X_MIN) * scaling / 2);
		y_shift = (int) (this.getHeight() / 2.2 - (Grid.Y_MAX - Grid.Y_MIN) * scaling / 2);
		
		this.setBackground(Color.WHITE);
		
		this.drawPotentials(g);
		this.drawGrid(g);		
		this.drawElectricFieldVectors(g);
		this.drawCharges(g);		
		this.drawScale(g);
	}
	
	
	private ArrayList<Integer> transformCoordinate(float x, float y){
		ArrayList<Integer> transformed_coordinates = new ArrayList<Integer>();		
		
		transformed_coordinates.add(x_shift + (int) ((Math.abs(Grid.X_MIN) + x) * scaling));
		transformed_coordinates.add(y_shift + (int) ((Grid.Y_MAX - y) * scaling));
		
		//System.out.println("transformeCoordinate(): " + transformed_coordinates.get(0));
		
		return transformed_coordinates;
	}
	
	
	private int transformScale(float range) {
		return (int) (range * scaling);
	}
	
	private void drawGrid(Graphics g) {		
		//g.setColor(Color.lightGray);
		Graphics2D g2 = (Graphics2D) g;
		Font font = g2.getFont().deriveFont( 18.0f );
		float string_value;
				
		int transformed_x_range = transformScale((Grid.X_MAX - Grid.X_MIN));
		int transformed_y_range = transformScale((Grid.Y_MAX - Grid.Y_MIN));
		
		g2.setColor(new Color(80, 80, 80));
		g2.setStroke(new BasicStroke(0.5f));		
				
		for(int i = x_shift; i <  x_shift + transformed_x_range; i += 10) {
			for (int j = y_shift; j < y_shift + transformed_y_range; j += 10) {
				g2.drawRect(i, j, 10, 10);
			}
		}
		
		
	    g2.setFont(font);
	    g2.setStroke(new BasicStroke(1.6f));
		//g2.setColor(Color.darkGray);
	    g2.setColor(new Color(80, 80, 80));
		
		for(int i = x_shift; i <=  x_shift + transformed_x_range; i += 50) {			
			if((i - x_shift) % 100 == 0) {
				g2.drawLine(i, y_shift, i, y_shift + transformed_y_range + 10);
				
				string_value = Math.round((Grid.X_MIN + ((float) (i - x_shift)) / scaling) * 100) / 100.0f;
				if(string_value < 0.0f) {
					g2.drawString(Float.toString(string_value), i - 15, y_shift + transformed_y_range + 30);
				}
				else {
					g2.drawString(Float.toString(string_value), i - 10, y_shift + transformed_y_range + 30);
				}
			}
			else {
				g2.drawLine(i, y_shift, i, y_shift + transformed_y_range);
			}
		}
		
		
		for(int j = y_shift; j <= y_shift + transformed_y_range; j += 50) {
			if((j - y_shift) % 100 == 0) {
				g2.drawLine(x_shift - 5, j,  x_shift + transformed_x_range, j);
				
				string_value = Math.round((Grid.Y_MAX - ((float) (j - y_shift)) / scaling) * 100) / 100.0f;
				if(string_value <= 0.0f) {
					g2.drawString(Float.toString(string_value), x_shift - 55, j + 5);
					
				}
				else {
					g2.drawString(Float.toString(string_value), x_shift - 50, j + 5);
				}
			}
			else {
				g2.drawLine(x_shift, j,  x_shift + transformed_x_range, j);
			}
		}
		
		g2.drawString("x [m]", x_shift + transformScale(0.5f * (Grid.X_MAX - Grid.X_MIN)), y_shift + transformed_y_range + 55);
		g2.drawString("y [m]", x_shift  - 120, y_shift + transformScale(0.5f * (Grid.Y_MAX - Grid.Y_MIN)));
		
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
				g.setColor(new Color(0, 0 ,150));
				g.fillOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
			}			
			else {
				g.setColor(Color.BLACK);
				g.drawOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
				g.setColor(new Color(150, 0 ,0));
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
		
		//System.out.println("vector field size: " + electric_field.getVectorField().size());
		
		for(int i = 0; i <= this.electric_field.getGrid().getRows(); i += 5) {
			for(int j = 0; j <= this.electric_field.getGrid().getCols(); j += 5) {
				point_x = this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX();
				point_y = this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY();
				transformed_location.add(transformCoordinate(point_x, point_y).get(0));
				transformed_location.add(transformCoordinate(point_x, point_y).get(1));
								
				g2.setColor(Color.DARK_GRAY);
				//g2.setColor(new Color(90, 90, 90));
				
				x_points = new int[3];
				x_points[0] = transformed_location.get(0) - 6;
				x_points[1] = transformed_location.get(0) + 16;
				x_points[2] = transformed_location.get(0) - 6;
				y_points = new int[3];
				y_points[0] = transformed_location.get(1) - 6;
				y_points[1] = transformed_location.get(1);
				y_points[2] = transformed_location.get(1) + 6;
				
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
		int y_range = this.transformScale(Grid.X_MAX - Grid.X_MIN);
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
		
		Object min_potential_obj, max_potential_obj;
		
		float min_scale;         // minimum scale value
		float max_scale;         // maximum scale value
		
		float potential_fraction;		
		float scale_subinterval;		
		
		
		if(Math.max(Math.abs(min_potential), Math.abs(max_potential)) % 6.0f != 0.0f) {
			min_scale = -((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 6.0f) + 1.0f) * 6.0f;
			max_scale =  ((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 6.0f) + 1.0f) * 6.0f;
		}
		else {
			min_scale = -((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 6.0f)) * 6.0f;
			max_scale =  ((int) (Math.max(Math.abs(min_potential), Math.abs(max_potential)) / 6.0f)) * 6.0f;
		}
		
		scale_subinterval = (max_scale - min_scale) / 6.0f;
				
		
		if(potential >= min_scale && potential < (min_scale + 2.0f * scale_subinterval)) {			
			potential_fraction = ((potential - min_scale) / scale_subinterval/2.0f);
						
			color_values.add((int) (55 + potential_fraction * 200));			
			color_values.add(0);
			color_values.add(0);			
		}
		else if(potential >= min_scale + 2.0f * scale_subinterval && potential < (min_scale + 2.9f * scale_subinterval)) {			
			potential_fraction = ((potential - (min_scale + 2.0f * scale_subinterval)) / scale_subinterval/0.9f);
			
			color_values.add(255);
			color_values.add((int) (potential_fraction * 255));
			color_values.add(0);
		}		
		else if(potential >= min_scale + 2.9f * scale_subinterval && potential < (min_scale + 3.1f * scale_subinterval)) {			
			potential_fraction = ((potential - (min_scale + 2.9f * scale_subinterval)) / (0.2f * scale_subinterval));
			
			color_values.add(255 - (int) (potential_fraction * 255));
			color_values.add(255);
			color_values.add((int) (potential_fraction * 255));
		}
		else if(potential >= min_scale + 3.1f * scale_subinterval && potential < (min_scale + 4.0f * scale_subinterval)) {			
			potential_fraction = ((potential - (min_scale + 3.1f * scale_subinterval)) / scale_subinterval/0.9f);
			
			color_values.add(0);
			color_values.add(255 - (int) (potential_fraction * 255));
			color_values.add(255);
		}
		else if(potential >= min_scale + 4.0f * scale_subinterval && potential <= max_scale) {			
			potential_fraction = ((potential - (min_scale + 4.0f * scale_subinterval)) / scale_subinterval/2.0f);
						
			color_values.add(0);
			color_values.add(0);
			color_values.add(255 - (int) (potential_fraction * 200));
		}
		else {
			color_values.add(255);
			color_values.add(255);
			color_values.add(255);
		}
		
				
		return color_values;
	}
	
	private void drawScale(Graphics g) {
		ArrayList<Integer> color_1_values = new ArrayList<Integer>();
		ArrayList<Integer> color_2_values = new ArrayList<Integer>();
		Graphics2D g2d = (Graphics2D)g;
		GradientPaint gp;         
       	
		float min_scale;         // minimum scale value
		float max_scale;         // maximum scale value
				
		float scale_subinterval;
				
		int y_pos_1, y_pos_2;
		
		int scale_tick_range;
		
		Font font = g2d.getFont().deriveFont( 18.0f );		
		
		
		if(Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) % 6.0f != 0.0f) {
			min_scale = -((int) (Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) / 6.0f) + 1.0f) * 6.0f;
			max_scale =  ((int) (Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) / 6.0f) + 1.0f) * 6.0f;
		}
		else {
			min_scale = -((int) (Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) / 6.0f)) * 6.0f;
			max_scale =  ((int) (Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) / 6.0f)) * 6.0f;
		}
		
		scale_subinterval = (max_scale - min_scale) / 6.0f;
        
		//System.out.println("max_scale: " + max_scale);
		//System.out.println("max_potential: " + max_potential);
        //System.out.println("color 1: " + (int)(((max_potential - (min_scale + 4.0f * scale_subinterval)) / scale_subinterval/2.0f) * 250));
		//System.out.println("y: " + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval)) / (max_scale - min_scale) * 800));
		color_1_values.add(0);
		color_1_values.add(0);
		color_1_values.add(55);
		
		color_2_values.add(0);
		color_2_values.add(0);
		color_2_values.add(255);
		y_pos_1 = y_shift;
		y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval)) / (max_scale - min_scale) * 800);
		gp = new GradientPaint(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, new Color(color_1_values.get(0), color_1_values.get(1), color_1_values.get(2)), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_2, new Color(color_2_values.get(0), color_2_values.get(1), color_2_values.get(2)));
        g2d.setPaint(gp);        
        g2d.fillRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_shift, 50, y_pos_2 - y_shift);
        
        color_1_values.clear();
        color_2_values.clear();
        
         
        color_1_values.add(0);
		color_1_values.add(0);
		color_1_values.add(255);	
		
		color_2_values.add(0);
		color_2_values.add(255);
		color_2_values.add(255);
		y_pos_1 = y_pos_2;
		//y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval)) / (max_scale - min_scale) * 800) + (int) (((min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval)) / (max_scale - min_scale) * 800);
		y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval) + (min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval)) / (max_scale - min_scale) * 800);
		gp = new GradientPaint(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, new Color(color_1_values.get(0), color_1_values.get(1), color_1_values.get(2)), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_2, new Color(color_2_values.get(0), color_2_values.get(1), color_2_values.get(2)));
        g2d.setPaint(gp);        
        g2d.fillRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, 50, y_pos_2 - y_pos_1);
        
        color_1_values.clear();
        color_2_values.clear();
        
        //System.out.println("color 1: " + ((((min_scale + 3.1f * scale_subinterval) - (min_scale + 2.9f * scale_subinterval)) / (0.2f * scale_subinterval)) * 255));
        color_1_values.add(0);
		color_1_values.add(255);
		color_1_values.add(255);	
		
		color_2_values.add(255);
		color_2_values.add(255);
		color_2_values.add(0);	
		y_pos_1 = y_pos_2;
		//y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval)) / (max_scale - min_scale) * 800) + (int) (((min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval)) / (max_scale - min_scale) * 800) + (int) (((min_scale + 3.1f * scale_subinterval) - (min_scale + 2.9f * scale_subinterval)) / (max_scale - min_scale) * 800);
		y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval) + (min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval) + (min_scale + 3.1f * scale_subinterval) - (min_scale + 2.9f * scale_subinterval)) / (max_scale - min_scale) * 800);
		gp = new GradientPaint(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, new Color(color_1_values.get(0), color_1_values.get(1), color_1_values.get(2)), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_2, new Color(color_2_values.get(0), color_2_values.get(1), color_2_values.get(2)));
        g2d.setPaint(gp);        
        g2d.fillRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, 50, y_pos_2 - y_pos_1);
        
        color_1_values.clear();
        color_2_values.clear();
        
              
        color_1_values.add(255);
		color_1_values.add(255);
		color_1_values.add(0);	
		
		color_2_values.add(255);
		color_2_values.add(0);
		color_2_values.add(0);	
		
		y_pos_1 = y_pos_2;		
		y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval) + (min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval) + (min_scale + 3.1f * scale_subinterval) - (min_scale + 2.9f * scale_subinterval) + (min_scale + 2.9f * scale_subinterval) - (min_scale + 2.0f * scale_subinterval)) / (max_scale - min_scale) * 800);
		gp = new GradientPaint(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, new Color(color_1_values.get(0), color_1_values.get(1), color_1_values.get(2)), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_2, new Color(color_2_values.get(0), color_2_values.get(1), color_2_values.get(2)));
        g2d.setPaint(gp);        
        g2d.fillRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, 50, y_pos_2 - y_pos_1);
        
        color_1_values.clear();
        color_2_values.clear();
                
        
        color_1_values.add(255);
		color_1_values.add(0);
		color_1_values.add(0);	
		
		color_2_values.add(55);
		color_2_values.add(0);
		color_2_values.add(0);	
		
		y_pos_1 = y_pos_2;		
		y_pos_2 = y_shift + (int) ((max_scale - (min_scale + 4.0f * scale_subinterval) + (min_scale + 4.0f * scale_subinterval) - (min_scale + 3.1f * scale_subinterval) + (min_scale + 3.1f * scale_subinterval) - (min_scale + 2.9f * scale_subinterval) + (min_scale + 2.9f * scale_subinterval) - (min_scale + 2.0f * scale_subinterval) + (min_scale + 2.0f * scale_subinterval) - min_scale) / (max_scale - min_scale) * 800);
		gp = new GradientPaint(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, new Color(color_1_values.get(0), color_1_values.get(1), color_1_values.get(2)), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_2, new Color(color_2_values.get(0), color_2_values.get(1), color_2_values.get(2)));
        g2d.setPaint(gp);        
        g2d.fillRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_pos_1, 50, y_pos_2 - y_pos_1);
        
        color_1_values.clear();
        color_2_values.clear();
        
        
       g2d.setColor(Color.darkGray);
       g2d.drawRect(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 100, y_shift, 50, 800);
       
       //System.out.println("max potential: " + this.max_potential);
       
       if(Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) % 5.0f != 0.0f) {
    	   scale_tick_range = (int) ((Math.max(Math.abs(this.min_potential), Math.abs(this.max_potential)) / 5.0f) - 1);
       }
       else {
    	   scale_tick_range = (int) (Math.abs(this.max_potential) / 5.0f);
       }
       
       for(int i = 1; i <= 5; i++) {
    	   g2d.drawLine(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 150, y_shift + i * 400 / 5 + 5 , x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 160, y_shift + i * 400 / 5 + 5);
    	   g2d.drawString(Float.toString(5 * scale_tick_range - i * scale_tick_range), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 170, y_shift + i * 400 / 5 + 10);
       }
       
       for(int i = 1; i < 5; i++) {
    	   g2d.drawLine(x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 150, y_shift + 400 + i * 400 / 5 + 5 , x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 160, y_shift + 400 + i * 400 / 5 + 5);
    	   g2d.drawString(Float.toString(- i * scale_tick_range), x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 170, y_shift + 400 + i * 400 / 5 + 10);
       }
       
       g2d.drawString("potential [V]", x_shift + (int) ((Grid.X_MAX - Grid.X_MIN) * this.scaling) + 90, y_shift + 855);
              
       
       //g2d.drawString(Float.toString(min_potential), x_shift + (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) * this.scaling) + 170, y_shift + 800);
	
	}
	
	
	
}
