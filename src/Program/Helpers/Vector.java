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
	
	/**
	 * A vektorhoz egy mások vektor hozzáadása
	 * @param - a másik vektor, amivel növeljük az aktuálisat
	 * @author Bence
	 */
	public Vector add(Vector vec){
		this.x+=vec.x;
		this.y+=vec.y;
		return this;
	}
	
	/**
	 * A vektorból egy mások vektor kivonása
	 * @param - a másik vektor, amit kivonunk az aktuálisból
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
	 * Két vektor keresztszorzata
	 * @param
	 * @author Bence
	 */
	public float descartesProduct(Vector vec){
		return this.x*vec.y-this.y*vec.x;
	}
	
	/**
	 * Normalizálja a vektort, a hossza egységnyi lesz, iránya marad 
	 */
	public void normalize(){
		//Meghatározza a vektor hosszát
		float length = (float) length();
		//Normalizálja a vektor koordinátáit
		x /= length;
		y /= length;
	}
	
	/**
	 * Két vektor vektorátlaga
	 * @author Barna
	 */
	public static Vector average(Vector vec1,Vector vec2){
		return new Vector((vec1.getX()+vec2.getX())/2, (vec1.getY()+vec2.getY())/2);
	}
	
}
