package Program.Helpers;

public class Vector {
	private float x;
	private float y;
	public Vector(float x, float y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Ezt használja az Olaj akadály a sebesség felezésére
	 * @return - Visszadja a sebességet elfelezve vectorként
	 */
	public Vector cutIntoHalf(){
		x=x/2;
		y=y/2;
		return this;
	}
	
	/**
	 * X komponens lekérdezésére
	 * @return - vektor X komponense
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Y komponens lekérdezésére
	 * @return - vektor Y komponense
	 */
	public float getY(){
		return y;
	}
	
	/**
	 * X komponens beállítására
	 * @param - vektor X komponense
	 */
	public void setX(float x){
		this.x=x;
	}
	
	/**
	 * Y komponens beállítására
	 * @param - vektor Y komponense
	 */
	public void setY(float y){
		this.y=y;
	}
}
