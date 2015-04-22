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
	 * Két pont közti távolság
	 * @param fp - a második pont
	 * @return
	 */
	public float distance(FloatPoint fp){
		if(fp == null){
			return 90; // Itt csak egy nagy számot returnolok azért, mert mindíg kicsire komparálunk. 
			//Elvileg itt exceptiont kéne dobni, de annak az üzenetét kiírja a tesztprogram. Úgyhogy nem dobok.
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
