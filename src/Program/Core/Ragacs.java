package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Skeleton.SkeletonUtility;

/**
 * Dummy oszt�ly amit a Ragacs akad�ly tesztel�s�re haszn�lunk
 * 
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
		/*SkeletonUtility.addClass(this, "Ragacs" + SkeletonUtility.mapItemCounter);
		SkeletonUtility.mapItemCounter++;
		SkeletonUtility.printCall("create Ragacs", this);
		setStepInCounter(StepIn);
		SkeletonUtility.printReturn("create Ragacs", this);*/
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
	@Override
	public void stepIn(PlayerRobot playerRobot) {
		//SkeletonUtility.printCall("StepIn", this);
		// TODO Ragacs logika
		
		//VAGY +1 ha m�sik ir�nyba akarunk menni
		setStepinCounter(getStepinCounter()-1);
		//SkeletonUtility.printReturn("StepIn", this);
	}
}
