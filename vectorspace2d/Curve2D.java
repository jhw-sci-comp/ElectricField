package vectorspace2d;

import java.util.ArrayList;
import java.util.function.Function;


public class Curve2D {
	
	ArrayList<Vector2D> curvePoints = new ArrayList<Vector2D>();	
	private Function<Float, Vector2D> func;
	
	public Curve2D(Function<Float, Vector2D> func) {
		this.func = func;
	}
	
	public Vector2D calculateCurvePoint(float t) {
		Vector2D curve_point = this.func.apply(t);
		return curve_point;
	}

}
