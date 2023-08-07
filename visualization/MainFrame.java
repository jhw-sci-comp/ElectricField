package visualization;



import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import electricfield.ElectricField;



public class MainFrame extends JFrame {
	
	private JPanel layout_panel = new JPanel();	
	private VisualizationElectricField visual_electric_field;
	//private VisualizationScale visual_scale;
	
	
	public MainFrame(ElectricField electric_field) {
		this.setSize(1400, 1000);
		this.setTitle("Electric Field");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);	
		
		//this.setVisible(true);		
		
		//System.out.println("size: " + this.getContentPane().getSize());
		
		layout_panel.setSize(this.getContentPane().getSize().width, this.getContentPane().getSize().height);
		
		//visual_electric_field = new VisualizationElectricField(layout_panel, electric_field);
		visual_electric_field = new VisualizationElectricField(this, electric_field);
		//visual_scale = new VisualizationScale(layout_panel);
				
		this.setLayout(new BorderLayout());	
		this.getContentPane().add(visual_electric_field, BorderLayout.CENTER);
		
		/*
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
		*/
		
		//System.out.println("layout_panel width: " + layout_panel.getWidth());
		//System.out.println("layout_panel height: " + layout_panel.getHeight());								
		
	}

}
