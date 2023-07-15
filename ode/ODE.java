package ode;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import vectorspace2d.Vector2D;
import vectorspace2d.VectorSpace2D;

public class ODE {
	
	public static ArrayList<Vector2D> solveODE(Vector2D v_start, float h_start, BiFunction<Float, Vector2D, Vector2D> func){
		ArrayList<Vector2D> solution = new ArrayList<Vector2D>();
		float t_new = v_start.getX();
		float h = h_start;
		//Vector2D r_new = new Vector2D(0.0f, 0.0f);
		Vector2D r_new = new Vector2D(v_start.getX(), v_start.getY());
		boolean valid_step = true;
		
		int i = 0;
		
		solution.add(v_start);
		
		//r_new.copy(r_new.add(new Vector2D(0.0f, 0.1f)));
		//r_new.copy(func.apply(t_new, r_new));
		
		
		//System.out.println("r_new:" + r_new);
		
		while(i < 30) {
			//System.out.println("func: " + func.apply(1.0f, r_new));
			
			r_new = r_new.add(func.apply(t_new, r_new).scale(h));
			//t_new = t_new + h * func.apply(t_new, r_new).getX();
			t_new = t_new + h;
			
			
			//System.out.println("t_new:" + t_new);
			
			solution.add(r_new);
			
			i++;
		}
		
		return solution;
	}
	
	public static Vector2D solveODEStep(Vector2D r, float h, Function<Vector2D, Vector2D> func){
		//Vector2D solution = new Vector2D(0.0f, 0.0f);
		//float h = h_start;
		Vector2D r_new = new Vector2D(r.getX(), r.getY());
		//boolean valid_step = true;
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
		
		r_new = r_new.add((k1.add(k2.scale(2.0f).add(k3.scale(2.0f).add(k4))).scale(h / 6.0f)));			
		
		//solution.add(r_new);
		
		//System.out.println("ODE r_new: " + r_new.hashCode());

		
		return r_new;
	}

}
