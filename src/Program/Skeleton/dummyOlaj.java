package Program.Skeleton;

import java.awt.Point;
import java.util.Vector;

/**
 * Dummy oszt�ly amit az Olaj akad�ly tesztel�s�re haszn�lunk
 * 
 * @author Rover
 *
 */
public class dummyOlaj extends dummyMapItem {

	/**
	 * Az olajba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	dummyOlaj(int StepIn) {
		SetStepInCounter(StepIn);
	}
	/**
	 * Az�rt felel�s, hogy a belel�pett roboton negat�v effektet fejtsen ki.
	 * 
	 * @param robot- Robot ami belel�pett
	 *            
	 */
	public void StepIn(dummyRobot robot) {
		/**
		 * Vissza�ll�tja az eredetire a sebess�get // TODO Megcsin�lni �rtelmes Logiccal
		 */
		robot.ModifySpeed(robot.GetSpeed());
		// VAGY +1 ha m�sik ir�nyba akarunk menni
		SetStepInCounter(GetStepInCounter() - 1);
	}

}
