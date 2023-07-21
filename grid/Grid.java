package grid;

import java.util.ArrayList;

import vectorspace2d.Vector2D;

public class Grid {
	public static final float MINWIDTH =  -0.2f;  // -0.2 m
	public static final float MAXWIDTH =   0.2f;
	public static final float MINHEIGHT = -0.2f;
	public static final float MAXHEIGHT =  0.2f;
	public static final float DISTANCE = 0.001f;
	public ArrayList<Vector2D> points = new ArrayList<Vector2D>();	
	
	private float grid_width;
	private float grid_height;
	
	
	public Grid(float grid_width, float grid_height) {
		this.grid_width = grid_width;
		this.grid_height = grid_height;
	}
	
}
