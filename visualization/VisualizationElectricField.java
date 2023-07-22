package visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import charge.Charge;
import electricfield.ElectricField;
import grid.Grid;
import javafx.util.Pair;

public class VisualizationElectricField extends JPanel {
	
	private int x_shift;
	private int y_shift;
	private int scaling;
	private JPanel layout_panel;
	private ElectricField electric_field;
	
	public VisualizationElectricField(JPanel layout_panel, ElectricField electric_field) {
		 this.layout_panel = layout_panel;
		 this.electric_field = electric_field;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		
		scaling = (int) (800 /  (Grid.MAXWIDTH - Grid.MINWIDTH));
		
		System.out.println("scaling: " + scaling);
		
		this.setSize((int) (0.9 * layout_panel.getWidth()), layout_panel.getHeight());
		x_shift = (int) (this.getWidth() / 2 - (Grid.MAXWIDTH - Grid.MINWIDTH) * scaling / 2);
		y_shift = (int) (this.getHeight() / 2 - (Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling / 2);
		
		this.setBackground(Color.WHITE);
		
		this.drawGrid(g);
		this.drawCharges(g);
	}
	
	
	private ArrayList<Integer> transformCoordinate(float x, float y){
		ArrayList<Integer> transformed_coordinates = new ArrayList<Integer>();		
		
		transformed_coordinates.add(x_shift + (int) (x * scaling));
		transformed_coordinates.add(y_shift + (int) ((Grid.MAXHEIGHT - y) * scaling));
		
		return transformed_coordinates;
	}
	
	
	private int transformScale(float scale) {
		return (int) (scale * scaling);
	}
	
	private void drawGrid(Graphics g) {		
		g.setColor(Color.lightGray);			
		
		int transformed_x_range = transformScale((Grid.MAXWIDTH - Grid.MINWIDTH));
		int transformed_y_range = transformScale((Grid.MAXHEIGHT - Grid.MINHEIGHT));
				
		for(int i = x_shift; i <  x_shift + transformed_x_range; i += 10) {
			for (int j = y_shift; j < y_shift + transformed_y_range; j += 10) {
				g.drawRect(i, j, 10, 10);
			}
		}
		
		g.setColor(Color.darkGray);
		for(int i = x_shift; i <=  x_shift + transformed_x_range; i += 50) {
			g.drawLine(i, y_shift, i, y_shift + transformed_y_range);
		}
		
		for(int j = y_shift; j <= y_shift + transformed_y_range; j += 50) {
			g.drawLine(x_shift, j,  x_shift + transformed_x_range, j);
		}
		
		
		//System.out.println(transformed_range.get(0) + ", " + transformed_range.get(1));
		
			
		// Richtige Darstellung: *********************************************
		
		//System.out.println("x_shift: " + x_shift);
		//System.out.println("y_shift: " + y_shift);
		//System.out.println("scaling: " + scaling);
		//System.out.println((x_shift + (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) * scaling)) + ", " + (y_shift + (int) ((Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling)));
		
		/*
		for(int i = x_shift; i < x_shift + (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) * scaling); i += 10) {
			for (int j = y_shift; j < y_shift + (int) ((Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling); j += 10) {
				g.drawRect(i, j, 10, 10);
			}
		}
		
		g.setColor(Color.darkGray);
		for(int i = x_shift; i <= x_shift + (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) * scaling); i += 100) {
			g.drawLine(i, y_shift, i, y_shift + (int) ((Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling));
		}
		
		for(int j = y_shift; j <= y_shift + (int) ((Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling); j += 100) {
			g.drawLine(x_shift, j, x_shift + (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) * scaling), j);
		}
		*/
			
		
	}
	
	
	private void drawCharges(Graphics g) {
		ArrayList<Integer> transformed_location = new ArrayList<Integer>();		
		
		for(Charge c : electric_field.getCharges()) {
			transformed_location.add(transformCoordinate(c.getLocation().getX(), c.getLocation().getY()).get(0));
			transformed_location.add(transformCoordinate(c.getLocation().getX(), c.getLocation().getY()).get(1));
			
			System.out.println("transformed_location: " + transformed_location.get(0) + ", " + transformed_location.get(1));
			
			if(c.getCharge() >= 0.0f) {
				g.setColor(Color.RED);
				g.fillOval(transformed_location.get(0) - 5, transformed_location.get(1) - 5, 10, 10);
			}
		}
	}
	
	
	
	
	private void drawPotential(Graphics g) {
		
	}
	
	
	
}
