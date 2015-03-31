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
	public void jump(){
		if (alive)
			countDistance();
	}
	
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
	 *Ugráskor meghívva a megtett távolságot növeli a megfelelõ értékkel.
	 * @author Bence
	 */	
	private void countDistance(){
	    const int time=1;
		distance+=Math.sqrt(speed)
	}
}
