package vectorspace2d;


public class Vector2D {
	
	private float x;
	private float y;
	private float direction;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
		this.calculateDirection();
	}
	
	public Vector2D(Vector2D v_arg) {
		this.x = v_arg.getX();
		this.y = v_arg.getY();
		this.calculateDirection();
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {		
		this.x = x;
		this.calculateDirection();
	}
	
	
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		this.y = y;
		this.calculateDirection();
	}
	
	public void copy(Vector2D v_arg) {
		this.x = v_arg.getX();
		this.y = v_arg.getY();
		this.calculateDirection();
	}
	
	public Vector2D add(Vector2D v_arg) {
		return new Vector2D(this.x + v_arg.getX(), this.y + v_arg.getY());
	}
	
	public Vector2D scale(float factor) {
		return new Vector2D(factor * this.x, factor * this.y);
	}
	
	public boolean isInNeighbourhood(Vector2D v_arg, float eps) {
		float dist = VectorSpace2D.calculate2Norm(new Vector2D(v_arg.getX() - this.x, v_arg.getY() - this.y));
		
		if(dist <= eps) {
			return true;
		}
		else {
			return false;
		}		
	}
	
	public void calculateDirection() {
		if(this.x == 0.0f) {
			if(this.y > 0.0f) {
				this.direction = (float) Math.PI / 2.0f;
			}			
			else if(this.y < 0.0f) {
				//this.direction = (float) -Math.PI / 2.0f;
				this.direction = 3.0f * (float) Math.PI / 2.0f;
			}
		}
		else if(this.x > 0.0f) {
			if(this.y > 0.0f) {
				this.direction = (float) Math.atan(this.y / this.x);
			}			
			else if(this.y < 0.0f) {
				//this.direction = (float) Math.atan(this.y / this.x);
				this.direction = 2.0f * (float) Math.PI - (float) Math.abs(Math.atan(this.y / this.x));
			}
			else if(this.y == 0.0f) {
				this.direction = 0.0f;
			}
		}
		else if(this.x < 0.0f) {
			if(this.y > 0.0f) {
				this.direction = (float) (Math.PI -  Math.abs(Math.atan(this.y / this.x)));
			}			
			else if(this.y < 0.0f) {				
				this.direction = (float) (Math.PI +  Math.abs(Math.atan(this.y / this.x)));
			}
			else if(this.y == 0.0f) {
				this.direction = (float) Math.PI;
			}
		}
	}
	
	public float getDirection() {
		return this.direction;
	}
	
	
	public float calculateCrossProductZ(Vector2D v_arg) {
		return (this.x * v_arg.y - this.y * v_arg.x);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + "), direction = " + direction;
	}
	
	

}
