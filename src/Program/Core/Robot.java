package Program.Core;

import java.awt.Point;

import Program.Helpers.Line;
import Program.Helpers.Vector;
/**
 * TODO: KOMMENTEZÉS
 * @author Rover
 *
 */
public abstract class Robot {
	
	/**
	 * Alive - Beállítható, hogy él-e még a robot
	 * Distance - Milyen messze jutott a pályán(eredmény számoláshoz)
	 * ModSpeed- Vektor amivel a user módosíthatja a robot sebességét
	 * Position - A térképen való pozícióját jelöli
	 * Speed ami a robot elõzõ sebességét tárolja, ehhez adódik hozzá a modSpeed
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
		//FONTOS: a sebesség itt már az elugrási sebesség legyen
		//
		 * Ez abstract metódus, itt szart se kéne számolni.!!! TODO: FIX PLS
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
	 *Ugráskor meghívva a megtett távolságot növeli az ugráskor megtett távolsággal.
	 *@param time- meddig van a levegõben a robot
	 * érdemes az értékét nem túl kicsire megválasztani a kvantálás maitt(>10)
	 * @author Bence
	 */	
	protected void countDistance(int time){
		distance+=Math.round(speed.length()*time);
	}
}
