package vectorspace2d;

public class Vector2D {
	
	private float x;
	private float y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D v_arg) {
		this.x = v_arg.getX();
		this.y = v_arg.getY();
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void copy(Vector2D v_arg) {
		this.x = v_arg.getX();
		this.y = v_arg.getY();
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
		return "Vector2D = (" + x + ", " + y + ")";
	}
	
	

}
