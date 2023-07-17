package vectorspace2d;


public class VectorSpace2D {	
	
	public static float calculate2Norm(Vector2D p) {
		return (float) Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
	}
	
}
