package Program.Core;

import java.awt.Point;
import java.util.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * Dummy osztály amit az Olaj akadály tesztelésére használunk
 * 
 * @author Rover
 *
 */
public class Olaj extends MapItem {

	/**
	 * Az olajba való belelépések számát állítja be a konstruktor.
	 * @param StepIn
	 */
	public Olaj(int StepIn) {
		SkeletonUtility.addClass(this, "Olaj" + SkeletonUtility.mapItemCounter);
		SkeletonUtility.mapItemCounter++;
		SkeletonUtility.printCall("create Olaj", this);
		SetStepInCounter(StepIn);
		SkeletonUtility.printReturn("create Olaj", this);
	}
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * 
	 * @param robot- Robot ami belelépett
	 *            
	 */
	public void StepIn(Robot robot) {
		SkeletonUtility.printCall("StepIn", this);
		/**
		 * Visszaállítja az eredetire a sebességet // TODO Megcsinálni értelmes Logiccal
		 */
		robot.ModifySpeed(robot.GetSpeed());
		// VAGY +1 ha másik irányba akarunk menni
		SetStepInCounter(GetStepInCounter() - 1);
		SkeletonUtility.printReturn("StepIn", this);
	}

}
