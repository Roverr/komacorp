package Program.Core;

import java.awt.Point;

import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;

/**
 * Olaj osztály , ami akadályként dobható a pályára.
 * @author Rover
 */
public class Olaj extends MapItem {
	
	/**
	 * timeLeft - Az olaj aktivitási ideje, amíg a pályán marad
	 */
	private int timeLeft;
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivitási idejét állítja be a konstruktor.
	 * @param timing - Az idõ ameddig az olaj aktív
	 */
	public Olaj(int timing,Point position) {
		this.timeLeft = timing;
		this.position = position;
	}
	
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * @param playerRobot- Robot ami belelépet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		Vector zero = new Vector(0,0);
		playerRobot.modifySpeed(zero);
	}

}
