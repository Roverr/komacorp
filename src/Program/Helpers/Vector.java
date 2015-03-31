package Program.Helpers;

public class Vector {
	private float x;
	private float y;
	public Vector(float x, float y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Ezt haszn�lja az Olaj akad�ly a sebess�g felez�s�re
	 * @return - Visszadja a sebess�get elfelezve vectork�nt
	 */
	public Vector cutIntoHalf(){
		x=x/2;
		y=y/2;
		return this;
	}
	
	/**
	 * X komponens lek�rdez�s�re
	 * @return - vektor X komponense
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Y komponens lek�rdez�s�re
	 * @return - vektor Y komponense
	 */
	public float getY(){
		return y;
	}
	
	/**
	 * X komponens be�ll�t�s�ra
	 * @param - vektor X komponense
	 */
	public void setX(float x){
		this.x=x;
	}
	
	/**
	 * Y komponens be�ll�t�s�ra
	 * @param - vektor Y komponense
	 */
	public void setY(float y){
		this.y=y;
	}
}
