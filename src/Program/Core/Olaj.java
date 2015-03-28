package Program.Core;

import java.awt.Point;
import java.util.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * Olaj oszt�ly , ami akad�lyk�nt dobhat� a p�ly�ra.
 * @author Rover
 */
public class Olaj extends MapItem {

	private int timeLeft;
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivit�si idej�t �ll�tja be a konstruktor.
	 * @param timing - Az id� ameddig az olaj akt�v
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
	 * Az�rt felel�s, hogy a belel�pett roboton negat�v effektet fejtsen ki.
	 * @param playerRobot- Robot ami belel�pet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		SkeletonUtility.printCall("StepIn", this);
		/**
		 * Vissza�ll�tja az eredetire a sebess�get // TODO Megcsin�lni �rtelmes Logiccal
		 */
		playerRobot.modifySpeed(playerRobot.getSpeed());
		SkeletonUtility.printReturn("StepIn", this);
	}

}
