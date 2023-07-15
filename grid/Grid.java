package grid;

import java.util.ArrayList;

public class Grid {
	public static final float MINWIDTH =  -0.02f;
	public static final float MAXWIDTH =   0.02f;
	public static final float MINHEIGHT = -0.02f;
	public static final float MAXHEIGHT =  0.02f;
	public static final float DISTANCE = 0.001f;
	public ArrayList<Point> points = new ArrayList<Point>();
	
	public Grid() {
		int m = (int) ((MAXWIDTH - MINWIDTH) / DISTANCE);
		int n = (int) ((MAXHEIGHT - MINHEIGHT) / DISTANCE);
		
		for(int i = 0; i <= m; i++) {
			for(int j = 0; j <= n; j++) {
				points.add(new Point(MINWIDTH + i * DISTANCE, MINHEIGHT + j * DISTANCE));
			}
		}
	}	
	
}
