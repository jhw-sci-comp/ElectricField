package visualization;


public class Point {

	private int x;
	private int y;
		
	public Point(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public Point(Point p_arg) {
		this.x = p_arg.getX();
		this.y = p_arg.getY();
	}
		
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {		
		this.x = x;
	}
	
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void copy(Point p_arg) {
		this.x = p_arg.getX();
		this.y = p_arg.getY();
	}
	
}
