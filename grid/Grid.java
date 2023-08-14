package grid;

import java.util.ArrayList;
import vectorspace2d.Vector2D;

public class Grid {
	public static final float X_MIN =  -0.2f;  // -0.2 m
	public static final float X_MAX =   0.2f;
	public static final float Y_MIN = -0.2f;
	public static final float Y_MAX =  0.2f;
	public static final float DISTANCE = 0.005f;
	
	private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
	private float width = 0.0f;
	private float height = 0.0f;	
	
	private int rows = (int) ((Grid.X_MAX - Grid.X_MIN) / Grid.DISTANCE);
	private int cols = (int) ((Grid.Y_MAX - Grid.Y_MIN) / Grid.DISTANCE);
		
	public Grid() {
		
		for(int j = 0; j <= this.cols; j++) {
			for(int i = 0; i <= this.rows; i++) {				
				points.add(new Vector2D(Math.round((Grid.X_MIN + i * Grid.DISTANCE) * 1000) / 1000.0f, Math.round((Grid.Y_MIN + j * Grid.DISTANCE) * 1000) / 1000.0f));
			}
		}
		
		this.width = Grid.X_MIN - Grid.X_MIN;
		this.height = Grid.Y_MAX - Grid.Y_MIN;
	}
	
	
	public ArrayList<Vector2D> getPoints() {
		return this.points;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
}
