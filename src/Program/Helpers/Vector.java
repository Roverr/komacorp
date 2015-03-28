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
}
