package Program.Helpers;
/**
 * A p�lya meghat�r�zos�n�l seg�dkez� oszt�ly.
 * @author Rover
 */
public class Line {
		public int x1;
		public int x2;
		public int y1;
		public int y2;
	public	Line(int x1, int x2, int y1, int y2) {
			this.x1=x1;
			this.x2=x2;
			this.y1=y1;
			this.y2=y2;
		}
	
	
	/**
	 *Kisz�molja a szakasz hossz�t
	 * @author Bence
	 */	
	public double lenght()
	{
		int x=this.x1-this.x2;
		int y=this.y1-this.y2;
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
     	   		forgasirany(a, b, vec)==0)
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
		float e=forgasirany(c, d, a);
		float f=forgasirany(c,d,b);
		float g=forgasirany(a,b,c);
		float h=forgasirany(a,b,d);
		
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
	private float forgasirany(Vector a, Vector b, Vector c){
		c.subtraction(a);
		b.subtraction(a);
		return c.descartesProduct(b);
	}
	
}
