package Program.Core;

import java.awt.Point;

import Program.Helpers.Vector;
/**
 * TODO: KOMMENTEZÉS
 * @author Rover
 *
 */
public abstract class Robot {
	protected boolean alive;
	protected int distance;
	protected Vector speed;
	protected Vector modSpeed;
	protected Point position;
	
	public Robot() {
		speed = new Vector(0,0);
		position = new Point(0,0);
		alive = true;
		distance = 0;
	}
	
	public abstract void die();
	
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
	public Vector getModSpeed(){
		return modSpeed;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void modifySpeed(Vector force) {
		modSpeed = force;
	}
	
}
