package visualization;



import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import electricfield.ElectricField;



public class MainFrame extends JFrame {
	
	private JPanel layout_panel = new JPanel();	
	private VisualizationElectricField visual_electric_field;
		
	
	public MainFrame(ElectricField electric_field) {
		this.setSize(1400, 1000);
		this.setTitle("Electric Field");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);	
		
				
		layout_panel.setSize(this.getContentPane().getSize().width, this.getContentPane().getSize().height);
		
		
		visual_electric_field = new VisualizationElectricField(this, electric_field);
		
				
		this.setLayout(new BorderLayout());	
		this.getContentPane().add(visual_electric_field, BorderLayout.CENTER);		
										
		
	}

}
