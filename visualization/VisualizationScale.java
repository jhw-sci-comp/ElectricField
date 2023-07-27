package visualization;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import grid.Grid;

public class VisualizationScale extends JPanel {	
	
	private int x_shift;
	private int y_shift;
	private int scaling;
	private JPanel layout_panel;
	
	public VisualizationScale(JPanel layout_panel) {
		//this.setSize(100, 100);
		this.layout_panel = layout_panel;
		this.setBackground(Color.WHITE);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		this.setSize((int) (0.1 * this.layout_panel.getWidth()), this.layout_panel.getHeight());
		
		//scaling = (int) (200 /  (Grid.MAXWIDTH - Grid.MINWIDTH));
		//x_shift = (int) (this.getWidth() / 2 - (Grid.MAXWIDTH - Grid.MINWIDTH) * scaling / 2);
		//y_shift = (int) (this.getHeight() / 2 - (Grid.MAXHEIGHT - Grid.MINHEIGHT) * scaling / 2);
		
		x_shift = (int) (this.getWidth() / 2 - 200);
		y_shift = (int) (this.getHeight() / 2 - 200);
		
		/*
        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(
                0, 0, Color.white,
                0, h, Color.black);

        g2d.setPaint(gp);
        */
        g2d.fillRect(x_shift, y_shift, 200, 500);
        
		
		
	}

}
