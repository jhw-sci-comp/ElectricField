package visualization;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import electricfield.ElectricField;
import vectorspace2d.Vector2D;


public class MainFrame extends JFrame {
	
	private JPanel layout_panel = new JPanel();	
	private VisualizationElectricField visual_electric_field;
	private VisualizationScale visual_scale = new VisualizationScale();
	
	
	public MainFrame(ElectricField electric_field) {
		this.setSize(1400, 1000);
		this.setTitle("Electric Field");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);	
		
		//this.setVisible(true);		
		
		//System.out.println("size: " + this.getContentPane().getSize());
		
		layout_panel.setSize(this.getContentPane().getSize().width, this.getContentPane().getSize().height);
		
		visual_electric_field = new VisualizationElectricField(layout_panel, electric_field);
				
		this.setLayout(new BorderLayout());		
		layout_panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.fill = GridBagConstraints.BOTH;		
		gbc.weightx = 0.9;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;	
		layout_panel.add(visual_electric_field, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		layout_panel.add(visual_scale, gbc);
		
		
		
		this.getContentPane().add(layout_panel, BorderLayout.CENTER);
			
		
		//System.out.println("layout_panel width: " + layout_panel.getWidth());
		//System.out.println("layout_panel height: " + layout_panel.getHeight());								
		
	}

}
