package Program.Core;

import java.awt.Point;
import java.io.Serializable;
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
		return Position;
	}
	
	/**
	 * Visszadja hányszor lehet belelépni
	 * @return
	 */
	public int GetStepInCounter() {
		return StepInCounter;
	}
	
	/**
	 * Beállítja hányszor lehet belelépni
	 * @param value
	 */
	public void SetStepInCounter(int value) {
		StepInCounter=value;
	}
	
	/**
	 * Beállítja az akadály pozícióját.
	 * @param place - Az a pont ahova helyezzük az akadályt.
	 */
	public void SetPosition(Point place) {
		Position=place;
	}
	
	/**
	 * Ennek a függvénynek kell meghívódnia, ha a robot belelép az akadályba.
	 * Mivel ez máshogy kell a robotot módosítsa, így ez abstract függvény,
	 * itt nem kell megvalósítani.
	 * @param robot - Robot aki rálépett az akadályra.
	 */
	public abstract void StepIn(Robot robot);

}
