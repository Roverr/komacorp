package Program.Core;

import java.awt.Point;

import Program.Core.MapItem.CleaningState;

/**
 * Ragacs osztály, ami akadályként dobható a pályára
 * @author Rover
 *
 */
public class Ragacs extends MapItem{

	/**
	 * Azért felelõs, hogy hányszor lehet a ragacsba lépni
	 */
	private int stepInCounter;
	
	/**
	 * Szerializáláshoz kell. 
	 */
	private static final long serialVersionUID = 6191556765398850843L;
	
	/**
	 * A ragacsba való belelépések számát állítja be a konstruktor.
	 * @param StepIn
	 */
	public Ragacs(int stepIn,Point position) {
		setPosition(position);
		setStepinCounter(stepIn);
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Beállítja azt, hogy hányszor lehet belelépni a Ragacsba
	 * @param to - Egész szám a Ragacs maximális belelépésének lehetõségérõl. 
	 */
	public void setStepinCounter(int to) {
		stepInCounter = to;
	}
	
	/**
	 * Visszadja azt, hogy hányszor lehet még belelépni a Ragacsba
	 * @return
	 */
	public int getStepinCounter() {
		return stepInCounter;
	}
	
	/**
	 * Beállítja a negatív effektust, ha egy robot belelép, illetve csökkenti a belelépés számát.
	 * @param playerRobot - A robot, ami belelépett
	 */
	public void stepIn(PlayerRobot playerRobot) {
		setStepinCounter(getStepinCounter()-1);
		playerRobot.modifySpeed(playerRobot.getModSpeed().cutIntoHalf());
		if(stepInCounter == 0){
			//TODO RemoveMapItem
		}
	}
}
