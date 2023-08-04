package ode;

import java.util.function.Function;

import vectorspace2d.Vector2D;
import vectorspace2d.VectorSpace2D;

public class ODE {
		
	public static Vector2D solveODEStep(Vector2D r, float h, Function<Vector2D, Vector2D> func){
		
		Vector2D r_new = new Vector2D(r.getX(), r.getY());
		
		float normalized_func = 0.0f;
		Vector2D k1 = new Vector2D(0.0f, 0.0f);
		Vector2D k2 = new Vector2D(0.0f, 0.0f);
		Vector2D k3 = new Vector2D(0.0f, 0.0f);
		Vector2D k4 = new Vector2D(0.0f, 0.0f);	
				
		//solution.add(v_start);		
		
			
		normalized_func = VectorSpace2D.calculate2Norm(func.apply(r_new));
		
		k1 = func.apply(r_new).scale(1.0f / normalized_func);
		k2 = func.apply(r_new.add(k1.scale(h / 2.0f))).scale(1.0f / normalized_func);
		k3 = func.apply(r_new.add(k2.scale(h / 2.0f))).scale(1.0f / normalized_func);
		k4 = func.apply(r_new.add(k3.scale(h))).scale(1.0f / normalized_func);
		
		r_new.copy(r_new.add((k1.add(k2.scale(2.0f).add(k3.scale(2.0f).add(k4))).scale(h / 6.0f))));
		

		
		return r_new;
	}

}
