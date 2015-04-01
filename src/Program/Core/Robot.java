package Program.Core;

import java.awt.Point;

import Program.Helpers.Line;
import Program.Helpers.Vector;
/**
 * TODO: KOMMENTEZ�S
 * @author Rover
 *
 */
public abstract class Robot {
	
	/**
	 * Alive - Be�ll�that�, hogy �l-e m�g a robot
	 * Distance - Milyen messze jutott a p�ly�n(eredm�ny sz�mol�shoz)
	 * ModSpeed- Vektor amivel a user m�dos�thatja a robot sebess�g�t
	 * Position - A t�rk�pen val� poz�ci�j�t jel�li
	 * Speed ami a robot el�z� sebess�g�t t�rolja, ehhez ad�dik hozz� a modSpeed
	 * @author Barna,Rover
	 */
	protected boolean alive;
	protected int distance;
	protected Vector speed;
	protected Point position;
	
	public Robot() {
		speed = new Vector(0,0);
		position = new Point(0,0);
		alive = true;
		distance = 0;
	}
	
	public abstract void die();
	public abstract void jump();/*{
		//FONTOS: a sebess�g itt m�r az elugr�si sebess�g legyen
		//
		 * Ez abstract met�dus, itt szart se k�ne sz�molni.!!! TODO: FIX PLS
		if (alive){
			countDistance(10);
		}
	};*/
	
	public int getDistance() {
		return distance;
	}
	
	public Vector getSpeed() {
		return speed;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position= position;
	}
	
	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 *Ugr�skor megh�vva a megtett t�vols�got n�veli az ugr�skor megtett t�vols�ggal.
	 *@param time- meddig van a leveg�ben a robot
	 * �rdemes az �rt�k�t nem t�l kicsire megv�lasztani a kvant�l�s maitt(>10)
	 * @author Bence
	 */	
	protected void countDistance(int time){
		distance+=Math.round(speed.length()*time);
	}
}
