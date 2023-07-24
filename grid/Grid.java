package grid;

import java.util.ArrayList;

import vectorspace2d.Vector2D;

public class Grid {
	public static final float MINWIDTH =  -0.2f;  // -0.2 m
	public static final float MAXWIDTH =   0.2f;
	public static final float MINHEIGHT = -0.2f;
	public static final float MAXHEIGHT =  0.2f;
	public static final float DISTANCE = 0.005f;
	
	private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
	
	//TODO: rename attributes because it is confusing
	private int rows = (int) ((Grid.MAXWIDTH - Grid.MINWIDTH) / Grid.DISTANCE);
	private int cols = (int) ((Grid.MAXHEIGHT - Grid.MINHEIGHT) / Grid.DISTANCE);
		
	public Grid() {
		
		for(int j = 0; j <= this.cols; j++) {
			for(int i = 0; i <= this.rows; i++) {
				//System.out.println(Math.round((Grid.MINWIDTH + i * Grid.DISTANCE) * 1000) / 1000.0f + ", " + Math.round((Grid.MINHEIGHT + j * Grid.DISTANCE) * 1000) / 1000.0f);
				points.add(new Vector2D(Math.round((Grid.MINWIDTH + i * Grid.DISTANCE) * 1000) / 1000.0f, Math.round((Grid.MINHEIGHT + j * Grid.DISTANCE) * 1000) / 1000.0f));
			}
		}		
		
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
	
}
