package Program.Helpers;

import java.io.Serializable;

/**
 * A pálya meghatárázosánál segédkezõ osztály.
 * @author Rover
 */
public class Line implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		public float x1;
		public float x2;
		public float y1;
		public float y2;
	public	Line(float x1, float x2, float y1, float y2) {
			this.x1=x1;
			this.x2=x2;
			this.y1=y1;
			this.y2=y2;
		}
	
	/**
	 *Beállítja az egyik pont x komponensét
	 *@param x1 - a pont x komponense
	 * @author Bence
	 */	
	public void setX1(float x1){
		this.x1=x1;
	}
	
	/**
	 *Beállítja az egyik pont y komponensét
	 *@param y1 - a pont y komponense
	 * @author Bence
	 */	
	public void setY1(float y1){
		this.y1=y1;
	}
	
	/**
	 *Beállítja a másik pont x komponensét
	 *@param x2 - a pont x komponense
	 * @author Bence
	 */	
	public void setX2(float x2){
		this.x2=x2;
	}
	
	/**
	 *Beállítja a másik pont y komponensét
	 *@param y2 - a pont y komponense
	 *@author Bence
	 */	
	public void setY2(float y2){
		this.y2=y2;
	}
	
	/**
	 *Lekérdezi az egyik pont x komponensét
	 *@author Bence
	 */	
	public float getX1(){
		return this.x1;
	}
	
	/**
	 *Lekérdezi az egyik pont y komponensét
	 *@author Bence
	 */	
	public float getY1(){
		return this.y1;
	}
	
	/**
	 *Lekérdezi a másik pont x komponensét
	 *@author Bence
	 */	
	public float getX2(){
		return this.x2;
	}
	
	/**
	 *Lekérdezi a másik pont y komponensét
	 *@author Bence
	 */	
	public float getY2(){
		return this.y2;
	}
	
	
	/**
	 *Kiszámolja a szakasz hosszát
	 * @author Bence
	 */	
	public double length()
	{
		float x=this.x1-this.x2;
		float y=this.y1-this.y2;
		return  Math.sqrt(x*x+y*y);	
	}
	
	/**
	 *Eldönti, hogy a megadott pont rajta van-e a szakaszon
	 * @param 
	 * @author Bence
	 */	
	public Boolean isOnLine (Vector vec){
		Vector a= new Vector(this.x1,this.y1);
		Vector b= new Vector(this.x2,this.y2);	
		if(Math.min(this.x1, this.x2)<=vec.getX() &&
     		Math.max(this.x1,this.x2)>=vec.getX() &&
     		Math.min(this.y1, this.y2)<=vec.getY() &&
     	   	Math.max(this.y1,this.y2)>=vec.getY() && 
     	   		rollDirection(a, b, vec)==0)
			  return true;
		
		 return false;
	}
	
	/**
	 *Eldönti, hogy a két szakasz metszi-e egymást
	 * @param line - a másik szakasz, amire vizsgálja
	 * @author Bence
	 */	
	public Boolean intersect(Line line){
		// a 4 pont vektoriális elhelyezkedésének felvétele
		Vector a= new Vector(this.x1,this.y1);
		Vector b= new Vector(this.x2,this.y2);
		Vector c= new Vector(line.x1,line.y1);
		Vector d= new Vector(line.x2,line.y2);
		float e=rollDirection(c, d, a);
		float f=rollDirection(c,d,b);
		float g=rollDirection(a,b,c);
		float h=rollDirection(a,b,d);
		
		if (e*f<0 && g*h<0)
			return true;
		if (line.isOnLine(a))
			 return true;
		if (line.isOnLine(b))
		   return true;
		if (this.isOnLine(c))
		  return true;
		if (this.isOnLine(d))
		  return true;
		return false;
	}
	/**
	 * Segéd metódus, meghatározza, hogy merre az A pont a BC szakaszhoz képest,
	 * ha <0 óramutató járásával ellentétes irány
	 * ha >0 óramutató járásával megegyezõ irányba van
	 * ha =0 akkor egy egyenesbe esnek.
	 * @param a - elsõ pont
	 * @param b - második pont
	 * @param c - harmadik pont
	 * @author Bence
	 */	
	private float rollDirection(Vector a, Vector b, Vector c){
		c.subtraction(a);
		b.subtraction(a);
		return c.descartesProduct(b);
	}
	
	
}
