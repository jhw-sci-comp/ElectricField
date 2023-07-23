package visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
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
		Polygon triangle = new Polygon();
		float direction;
		
		System.out.println("vector field size: " + electric_field.getVectorField().size());
		
		for(int i = 0; i <= this.electric_field.getGrid().getRows(); i += 5) {
			for(int j = 0; j <= this.electric_field.getGrid().getCols(); j += 5) {				
				transformed_location.add(transformCoordinate(this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX(), this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY()).get(0));
				transformed_location.add(transformCoordinate(this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX(), this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY()).get(1));
								
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
				
				
				//direction = 3.0f/4.0f * (float) Math.PI;
				//System.out.println("(" + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getX() + ", " + this.electric_field.getGrid().getPoints().get(i * (this.electric_field.getGrid().getCols() + 1) + j).getY() + "), " + "direction: " + direction);
				
				//direction = (float) (Math.toRadians(45));				
				
				at.rotate(-direction, transformed_location.get(0), transformed_location.get(1));
				g2.setTransform(at);
				
				g2.fillPolygon(x_points, y_points, 3);
				
				at.setToIdentity();
									
				transformed_location.clear();				
			}
			
		}
		
		/*
		for(Vector2D v : electric_field.getVectorField()) {
			//System.out.println("v: " + v);
			
			transformed_location.add(transformCoordinate(v.getX(), v.getY()).get(0));
			transformed_location.add(transformCoordinate(v.getX(), v.getY()).get(1));
			
			//System.out.println("x_shift: " + this.x_shift);
			//System.out.println("transformed field vector: " + transformed_location.get(0) + ", " + transformed_location.get(1));
			
			g.setColor(Color.BLUE);
			g.fillOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
			
			transformed_location.clear();
		}
		*/
	}
	
	
	private void drawPotential(Graphics g) {
		
	}
	
	
	
}
