package Program.Helpers;

import java.io.Serializable;

/**
 * A p�lya meghat�r�zos�n�l seg�dkez� oszt�ly.
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
	 *Be�ll�tja az egyik pont x komponens�t
	 *@param x1 - a pont x komponense
	 * @author Bence
	 */	
	public void setX1(float x1){
		this.x1=x1;
	}
	
	/**
	 *Be�ll�tja az egyik pont y komponens�t
	 *@param y1 - a pont y komponense
	 * @author Bence
	 */	
	public void setY1(float y1){
		this.y1=y1;
	}
	
	/**
	 *Be�ll�tja a m�sik pont x komponens�t
	 *@param x2 - a pont x komponense
	 * @author Bence
	 */	
	public void setX2(float x2){
		this.x2=x2;
	}
	
	/**
	 *Be�ll�tja a m�sik pont y komponens�t
	 *@param y2 - a pont y komponense
	 *@author Bence
	 */	
	public void setY2(float y2){
		this.y2=y2;
	}
	
	/**
	 *Lek�rdezi az egyik pont x komponens�t
	 *@author Bence
	 */	
	public float getX1(){
		return this.x1;
	}
	
	/**
	 *Lek�rdezi az egyik pont y komponens�t
	 *@author Bence
	 */	
	public float getY1(){
		return this.y1;
	}
	
	/**
	 *Lek�rdezi a m�sik pont x komponens�t
	 *@author Bence
	 */	
	public float getX2(){
		return this.x2;
	}
	
	/**
	 *Lek�rdezi a m�sik pont y komponens�t
	 *@author Bence
	 */	
	public float getY2(){
		return this.y2;
	}
	
	
	/**
	 *Kisz�molja a szakasz hossz�t
	 * @author Bence
	 */	
	public double length()
	{
		float x=this.x1-this.x2;
		float y=this.y1-this.y2;
		return  Math.sqrt(x*x+y*y);	
	}
	
	/**
	 *Eld�nti, hogy a megadott pont rajta van-e a szakaszon
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
	 *Eld�nti, hogy a k�t szakasz metszi-e egym�st
	 * @param line - a m�sik szakasz, amire vizsg�lja
	 * @author Bence
	 */	
	public Boolean intersect(Line line){
		// a 4 pont vektori�lis elhelyezked�s�nek felv�tele
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
	 * Seg�d met�dus, meghat�rozza, hogy merre az A pont a BC szakaszhoz k�pest,
	 * ha <0 �ramutat� j�r�s�val ellent�tes ir�ny
	 * ha >0 �ramutat� j�r�s�val megegyez� ir�nyba van
	 * ha =0 akkor egy egyenesbe esnek.
	 * @param a - els� pont
	 * @param b - m�sodik pont
	 * @param c - harmadik pont
	 * @author Bence
	 */	
	private float rollDirection(Vector a, Vector b, Vector c){
		c.subtraction(a);
		b.subtraction(a);
		return c.descartesProduct(b);
	}
	
	
}
