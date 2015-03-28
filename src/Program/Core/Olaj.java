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
		SkeletonUtility.printReturn("create Olaj", this);
	}
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * 
	 * @param robot- Robot ami belelépett
	 *            
	 */
	public void stepIn(PlayerRobot playerRobot) {
		SkeletonUtility.printCall("StepIn", this);
		/**
		 * Visszaállítja az eredetire a sebességet // TODO Megcsinálni értelmes Logiccal
		 */
		playerRobot.modifySpeed(playerRobot.getSpeed());
		SkeletonUtility.printReturn("StepIn", this);
	}

}
