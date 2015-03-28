package Program.Core;

import java.awt.Point;
import java.util.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * Olaj osztály , ami akadályként dobható a pályára.
 * @author Rover
 */
public class Olaj extends MapItem {

	private int timeLeft;
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivitási idejét állítja be a konstruktor.
	 * @param timing - Az idõ ameddig az olaj aktív
	 */
	public Olaj(int timing,Point position) {
		/*SkeletonUtility.addClass(this, "Olaj" + SkeletonUtility.mapItemCounter);
		SkeletonUtility.mapItemCounter++;
		SkeletonUtility.printCall("create Olaj", this);
		SkeletonUtility.printReturn("create Olaj", this);*/
		this.timeLeft = timing;
		this.position = position;
	}
	
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * @param playerRobot- Robot ami belelépet        
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
