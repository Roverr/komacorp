package Program.Core;


import Program.Core.MapItem.CleaningState;
import Program.Helpers.FloatPoint;


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
	public Ragacs(int stepIn,FloatPoint position) {
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
		stepInCounter = stepInCounter-1;
		playerRobot.setSpeed(playerRobot.getSpeed().cutIntoHalf());
	}
	public void update(){
		//Mivel k�r�nk�nt nem kell friss�teni, ide semmi nem ker�l.
		return;
	}

	@Override
	public boolean isAlive() {
		return (stepInCounter > 0);
	}
	
	
}
