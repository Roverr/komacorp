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
}
