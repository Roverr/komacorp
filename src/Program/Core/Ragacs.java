package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Skeleton.SkeletonUtility;

/**
 * Ragacs oszt�ly, ami akad�lyk�nt dobhat� a p�ly�ra
 * @author Rover
 *
 */
public class Ragacs extends MapItem{

	private int stepInCounter;
	private static final long serialVersionUID = 6191556765398850843L;
	/**
	 * A ragacsba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	public Ragacs(int stepIn,Point position) {
		setPosition(position);
		setStepinCounter(stepIn);
		System.out.println("Test, im ready to work, i am Robot");
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
	}
}
