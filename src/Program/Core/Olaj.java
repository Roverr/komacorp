package Program.Core;

import java.awt.Point;
import java.util.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * Dummy oszt�ly amit az Olaj akad�ly tesztel�s�re haszn�lunk
 * 
 * @author Rover
 *
 */
public class Olaj extends MapItem {

	/**
	 * Az olajba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	public Olaj(int StepIn) {
		SkeletonUtility.addClass(this, "Olaj" + SkeletonUtility.mapItemCounter);
		SkeletonUtility.mapItemCounter++;
		SkeletonUtility.printCall("create Olaj", this);
		setStepInCounter(StepIn);
		SkeletonUtility.printReturn("create Olaj", this);
	}
	/**
	 * Az�rt felel�s, hogy a belel�pett roboton negat�v effektet fejtsen ki.
	 * 
	 * @param robot- Robot ami belel�pett
	 *            
	 */
	public void stepIn(Robot robot) {
		SkeletonUtility.printCall("StepIn", this);
		/**
		 * Vissza�ll�tja az eredetire a sebess�get // TODO Megcsin�lni �rtelmes Logiccal
		 */
		robot.modifySpeed(robot.getSpeed());
		// VAGY +1 ha m�sik ir�nyba akarunk menni
		setStepInCounter(getStepInCounter() - 1);
		SkeletonUtility.printReturn("StepIn", this);
	}

}
