package visualization;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class VisualizationScale extends JPanel {	
		
	public VisualizationScale() {
		//this.setSize(100, 100);
		this.setBackground(Color.blue);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0,0, 100, 100);
		
		//g.drawRect(130, 30,100, 80);
	}

}
