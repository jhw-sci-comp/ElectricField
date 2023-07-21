package visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grid.Grid;

public class VisualizationElectricField extends JPanel {
	
	private int x_shift;
	private int y_shift;
	private int scaling;
	private JPanel layout_panel;
	
	public VisualizationElectricField(JPanel layout_panel) {
		 this.layout_panel = layout_panel;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		
		scaling = (int) (800 /  (Grid.MAXWIDTH - Grid.MINWIDTH));
		
		this.setSize((int) (0.9 * layout_panel.getWidth()), layout_panel.getHeight());
		x_shift = (int) (this.getWidth() / 2 - (Grid.MAXWIDTH - Grid.MINWIDTH) * scaling / 2);
		y_shift = (int) (this.getHeight() / 2 - (Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling / 2);
			
		//System.out.println("visualization panel width: " + this.getWidth());
		//System.out.println("visualization panel height: " + this.getHeight());
		//System.out.println("x_shift: " + x_shift);
		//System.out.println("y_shift: " + y_shift);
		
		this.setBackground(Color.WHITE);
		
		this.drawGrid(g);
	}
	
	private void drawGrid(Graphics g) {		
		g.setColor(Color.lightGray);
				
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
		
	}
	
	
	private void drawCharges(Graphics g) {
		
	}
	
	
	private void drawPotential(Graphics g) {
		
	}
	
	
	
}
