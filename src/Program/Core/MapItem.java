package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Helpers.FloatPoint;
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
	protected FloatPoint position;
	
	/**
	 * Visszaadja az akadály pozícióját.
	 * @return - Position
	 */
	public FloatPoint getPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return position;
	}
	
	/**
	 * Beállítja az akadály pozícióját.
	 * @param place - Az a pont ahova helyezzük az akadályt.
	 */
	public void setPosition(FloatPoint place) {
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
