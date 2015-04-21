package Program.Helpers;

//TODO Mindenhol replacelni a java.awt.Point-ot ezzel.

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
	
	/**
	 * A vektorhoz egy m�sok vektor hozz�ad�sa
	 * @param - a m�sik vektor, amivel n�velj�k az aktu�lisat
	 * @author Bence
	 */
	public Vector add(Vector vec){
		this.x+=vec.x;
		this.y+=vec.y;
		return this;
	}
	
	/**
	 * A vektorb�l egy m�sok vektor kivon�sa
	 * @param - a m�sik vektor, amit kivonunk az aktu�lisb�l
	 * @author Bence
	 */
	public Vector subtraction(Vector vec){
		this.x-=vec.x;
		this.y-=vec.y;
		return this;
	}
	
	/**
	 * A vektor hossza
	 * @author Bence
	 */
	public double length(){
		return  Math.sqrt(this.x*this.x+this.y*this.y);		
	}
	
	/**
	 * K�t vektor keresztszorzata
	 * @param
	 * @author Bence
	 */
	public float descartesProduct(Vector vec){
		return this.x*vec.y-this.y*vec.x;
	}
	
	/**
	 * Normaliz�lja a vektort, a hossza egys�gnyi lesz, ir�nya marad 
	 */
	public void normalize(){
		//Meghat�rozza a vektor hossz�t
		float length = (float) length();
		//Normaliz�lja a vektor koordin�t�it
		x /= length;
		y /= length;
	}
	
	/**
	 * K�t vektor vektor�tlaga
	 * @author Barna
	 */
	public static Vector average(Vector vec1,Vector vec2){
		return new Vector((vec1.getX()+vec2.getX())/2, (vec1.getY()+vec2.getY())/2);
	}
	
}
