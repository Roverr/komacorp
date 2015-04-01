package Program.Core;

import java.awt.Point;

import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;

/**
 * Olaj oszt�ly , ami akad�lyk�nt dobhat� a p�ly�ra.
 * @author Rover
 */
public class Olaj extends MapItem {
	
	/**
	 * timeLeft - Az olaj aktivit�si ideje, am�g a p�ly�n marad
	 */
	private int timeLeft;
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivit�si idej�t �ll�tja be a konstruktor.
	 * @param timing - Az id� ameddig az olaj akt�v
	 */
	public Olaj(int timing,Point position) {
		this.setTimeLeft(timing);
		this.position = position;
	}
	
	public Olaj(Point position){
		this(20,position);
	}
	
	/**
	 * Az�rt felel�s, hogy a belel�pett roboton negat�v effektet fejtsen ki.
	 * @param playerRobot- Robot ami belel�pet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		Vector zero = new Vector(0,0);
		playerRobot.modifySpeed(zero);
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

}
