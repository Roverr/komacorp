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
	protected Point Position;
	private int StepInCounter;
	
	/**
	 * Visszaadja az akadály pozícióját.
	 * @return - Position
	 */
	public Point GetPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return Position;
	}
	
	/**
	 * Visszadja hányszor lehet belelépni
	 * @return
	 */
	public int GetStepInCounter() {
		SkeletonUtility.printCall("GetStepInCounter", this);
		SkeletonUtility.printReturn("GetStepInCounter", this);
		return StepInCounter;
	}
	
	/**
	 * Beállítja hányszor lehet belelépni
	 * @param value
	 */
	public void SetStepInCounter(int value) {
		SkeletonUtility.printCall("SetStepInCounter to " + value, this);
		StepInCounter=value;
		SkeletonUtility.printReturn("SetStepInCounter", this);
	}
	
	/**
	 * Beállítja az akadály pozícióját.
	 * @param place - Az a pont ahova helyezzük az akadályt.
	 */
	public void SetPosition(Point place) {
		SkeletonUtility.printCall("SetPosition", this);
		Position=place;
		SkeletonUtility.printReturn("SetPosition", this);
	}
	
	/**
	 * Ennek a függvénynek kell meghívódnia, ha a robot belelép az akadályba.
	 * Mivel ez máshogy kell a robotot módosítsa, így ez abstract függvény,
	 * itt nem kell megvalósítani.
	 * @param robot - Robot aki rálépett az akadályra.
	 */
	public abstract void StepIn(Robot robot);

}
