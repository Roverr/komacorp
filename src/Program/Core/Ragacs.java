package Program.Core;

import java.awt.Point;

import Program.Core.MapItem.CleaningState;

/**
 * Ragacs oszt�ly, ami akad�lyk�nt dobhat� a p�ly�ra
 * @author Rover
 *
 */
public class Ragacs extends MapItem{

	/**
	 * Az�rt felel�s, hogy h�nyszor lehet a ragacsba l�pni
	 */
	private int stepInCounter;
	
	/**
	 * Szerializ�l�shoz kell. 
	 */
	private static final long serialVersionUID = 6191556765398850843L;
	
	/**
	 * A ragacsba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	public Ragacs(int stepIn,Point position) {
		setPosition(position);
		setStepinCounter(stepIn);
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Be�ll�tja azt, hogy h�nyszor lehet belel�pni a Ragacsba
	 * @param to - Eg�sz sz�m a Ragacs maxim�lis belel�p�s�nek lehet�s�g�r�l. 
	 */
	public void setStepinCounter(int to) {
		stepInCounter = to;
	}
	
	/**
	 * Visszadja azt, hogy h�nyszor lehet m�g belel�pni a Ragacsba
	 * @return
	 */
	public int getStepinCounter() {
		return stepInCounter;
	}
	
	/**
	 * Be�ll�tja a negat�v effektust, ha egy robot belel�p, illetve cs�kkenti a belel�p�s sz�m�t.
	 * @param playerRobot - A robot, ami belel�pett
	 */
	public void stepIn(PlayerRobot playerRobot) {
		setStepinCounter(getStepinCounter()-1);
		playerRobot.modifySpeed(playerRobot.getModSpeed().cutIntoHalf());
		if(stepInCounter == 0){
			//TODO RemoveMapItem
		}
	}
}
