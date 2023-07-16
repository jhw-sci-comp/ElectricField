package electricfield;

import java.util.ArrayList;

import vectorspace2d.Vector2D;

public class FieldLine {
	private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
	
	public ArrayList<Vector2D> getPoints(){
		return this.points;
	}
	
	public void addPoint(Vector2D p) {
		this.points.add(p);
	}

}
