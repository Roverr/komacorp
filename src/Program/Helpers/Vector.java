package Program.Helpers;

public class Vector {
	private float x;
	private float y;
	public Vector(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public Vector cutIntoHalf(){
		x=x/2;
		y=y/2;
		return this;
	}
}
