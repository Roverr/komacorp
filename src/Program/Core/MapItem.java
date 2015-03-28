package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Skeleton.SkeletonUtility;
/**
 * Egy akadálynak az abasztrakt osztálya. Az akadályok tesztelésénél használt. 
 * @author Rover
 *
 */
public abstract class MapItem implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3066714571792766849L;
	/**
	 * Az a pont a térképen ahol az akadály található
	 */
	protected Point position;
	private int stepInCounter;
	
	/**
	 * Visszaadja az akadály pozícióját.
	 * @return - Position
	 */
	public Point getPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return position;
	}
	
	/**
	 * Visszadja hányszor lehet belelépni
	 * @return
	 */
	public int getStepInCounter() {
		SkeletonUtility.printCall("GetStepInCounter", this);
		SkeletonUtility.printReturn("GetStepInCounter", this);
		return stepInCounter;
	}
	
	/**
	 * Beállítja hányszor lehet belelépni
	 * @param value
	 */
	public void setStepInCounter(int value) {
		SkeletonUtility.printCall("SetStepInCounter to " + value, this);
		stepInCounter=value;
		SkeletonUtility.printReturn("SetStepInCounter", this);
	}
	
	/**
	 * Beállítja az akadály pozícióját.
	 * @param place - Az a pont ahova helyezzük az akadályt.
	 */
	public void setPosition(Point place) {
		SkeletonUtility.printCall("SetPosition", this);
		position=place;
		SkeletonUtility.printReturn("SetPosition", this);
	}
	
	/**
	 * Ennek a függvénynek kell meghívódnia, ha a robot belelép az akadályba.
	 * Mivel ez máshogy kell a robotot módosítsa, így ez abstract függvény,
	 * itt nem kell megvalósítani.
	 * @param playerRobot - Robot aki rálépett az akadályra.
	 */
	public abstract void stepIn(PlayerRobot playerRobot);

}
