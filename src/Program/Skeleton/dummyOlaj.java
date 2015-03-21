package Program.Skeleton;

import java.awt.Point;
import java.util.Vector;

/**
 * Dummy osztály amit az Olaj akadály tesztelésére használunk
 * 
 * @author Rover
 *
 */
public class dummyOlaj extends dummyMapItem {

	/**
	 * Az olajba való belelépések számát állítja be a konstruktor.
	 * @param StepIn
	 */
	dummyOlaj(int StepIn) {
		SetStepInCounter(StepIn);
	}
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * 
	 * @param robot- Robot ami belelépett
	 *            
	 */
	public void StepIn(dummyRobot robot) {
		/**
		 * Visszaállítja az eredetire a sebességet // TODO Megcsinálni értelmes Logiccal
		 */
		robot.ModifySpeed(robot.GetSpeed());
		// VAGY +1 ha másik irányba akarunk menni
		SetStepInCounter(GetStepInCounter() - 1);
	}

}
