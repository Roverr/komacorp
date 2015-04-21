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
}
