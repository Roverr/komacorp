package Program.Core;

import java.awt.Point;

import Program.Helpers.Vector;

/**
 * Olaj osztály , ami akadályként dobható a pályára.
 * @author Rover
 */
public class Olaj extends MapItem {
	
	/**
	 * timeLeft - Az olaj aktivitási ideje, amíg a pályán marad
	 */
	private int timeLeft;
	
	/**
	 * Szerializáláshoz szükséges
	 */
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivitási idejét állítja be a konstruktor.
	 * @param timing - Az idõ ameddig az olaj aktív
	 */
	public Olaj(int timing,Point position) {
		this.setTimeLeft(timing);
		this.position = position;
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Publikus konstruktor ami az olaj pozíciójával képes beállítani
	 * @param position
	 */
	public Olaj(Point position){
		this(20,position);
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * @param playerRobot- Robot ami belelépet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		Vector zero = new Vector(0,0);
		playerRobot.modifySpeed(zero);
	}
	
	/**
	 * Visszadja mennyi idõ van még hátra
	 * @return
	 */
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Beállítja mennyi idõ van még hátra
	 * @param timeLeft
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

}
