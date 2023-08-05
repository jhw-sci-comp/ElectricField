package ode;

import java.util.function.Function;

import vectorspace2d.Vector2D;
import vectorspace2d.VectorSpace2D;

public class ODE {
		
	public static Vector2D solveODEStep(Vector2D r, float h, Function<Vector2D, Vector2D> func){
		
		
		Vector2D r_new = new Vector2D(r.getX(), r.getY());
		
		float normalized_func = 0.0f;
		float eps = 0.0001f;
		float truncation_error = 0.0f; 
		float h_step = h;
		
		Vector2D k1 = new Vector2D(0.0f, 0.0f);
		Vector2D k2 = new Vector2D(0.0f, 0.0f);
		Vector2D k3 = new Vector2D(0.0f, 0.0f);
		Vector2D k4 = new Vector2D(0.0f, 0.0f);	
		Vector2D k5 = new Vector2D(0.0f, 0.0f);
		Vector2D k6 = new Vector2D(0.0f, 0.0f);
				
		//solution.add(v_start);		
		
		do {	
			normalized_func = VectorSpace2D.calculate2Norm(func.apply(r_new));
			
			k1 = func.apply(r_new).scale(1.0f / normalized_func);
			k2 = func.apply(r_new.add(k1.scale(h * 2.0f / 9.0f))).scale(1.0f / normalized_func);
			k3 = func.apply(r_new.add(k1.scale(h / 12.0f).add(k2.scale(h / 4.0f)))).scale(1.0f / normalized_func);
			k4 = func.apply(r_new.add(k1.scale(h * 69.0f / 128.0f).add(k2.scale(-h * 243.0f / 128.0f).add(k3.scale(h * 135.0f / 64.0f))))).scale(1.0f / normalized_func);
			k5 = func.apply(r_new.add(k1.scale(-h * 17.0f / 12.0f).add(k2.scale(h * 27.0f / 4.0f).add(k3.scale(-h * 27.0f / 5.0f).add(k4.scale(h * 16.0f / 15.0f)))))).scale(1.0f / normalized_func);
			k6 = func.apply(r_new.add(k1.scale(h * 65.0f / 432.0f).add(k2.scale(-h * 5.0f / 16.0f).add(k3.scale(h * 13.0f / 16.0f).add(k4.scale(h * 4.0f / 27.0f).add(k5.scale(h * 5.0f / 144.0f))))))).scale(1.0f / normalized_func);
			
			truncation_error = h_step / 300.0f * VectorSpace2D.calculate2Norm(k1.scale(-2.0f).add(k3.scale(9.0f).add(k4.scale(-64.0f).add(k5.scale(-15.0f).add(k6.scale(72.0f))))));
			
			if(Math.abs(truncation_error) > eps) {
				//h_step = h_step * (float) Math.pow(eps, 1.0 / 3) / (float) Math.pow(8.0 * Math.abs(truncation_error), 3);
				h_step = 0.5f * h_step;
			}
					
		}while(Math.abs(truncation_error) > eps);
		
		r_new.copy(r_new.add((k1.scale(47.0f / 450.0f).add(k3.scale(12.0f / 25.0f).add(k4.scale(32.0f / 225.0f).add(k5.scale(1.0f / 30.0f).add(k6.scale(6.0f / 25.0f)))))).scale(h)));
		
		return r_new;
		
		
		
		/*
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
		*/
		
	}

}
