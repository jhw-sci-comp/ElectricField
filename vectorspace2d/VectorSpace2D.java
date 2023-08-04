package vectorspace2d;


public class VectorSpace2D {	
	
	public static float calculate2Norm(Vector2D p) {
		return (float) Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
	}
	
	
	public static float calculateDotProduct(Vector2D v1_arg, Vector2D v2_arg) {
		return (v1_arg.getX() * v2_arg.getX() + v1_arg.getY() * v2_arg.getY());
	}
	
}
