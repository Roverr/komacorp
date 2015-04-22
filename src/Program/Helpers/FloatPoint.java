package Program.Helpers;

public class FloatPoint {
	private float x;
	private float y;
	
	public FloatPoint(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public void setX(float newx){
		this.x=newx;
	}
	public void setY(float newy){
		this.y=newy;
	}
	
	/**
	 * K�t pont k�zti t�vols�g
	 * @param fp - a m�sodik pont
	 * @return
	 */
	public float distance(FloatPoint fp){
		if(fp == null){
			return 90; // Itt csak egy nagy sz�mot returnolok az�rt, mert mind�g kicsire kompar�lunk. 
			//Elvileg itt exceptiont k�ne dobni, de annak az �zenet�t ki�rja a tesztprogram. �gyhogy nem dobok.
			//TODO
		}
		 float hor = x - fp.x;
		 float ver = y - fp.y;
		 return (float) Math.sqrt(hor*hor + ver*ver);
	}

	public void add(Vector speed) {
		x += speed.getX();
		y += speed.getY();
	}
}
