package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Skeleton.SkeletonUtility;

/**
 * Dummy osztály amit a Ragacs akadály tesztelésére használunk
 * 
 * @author Rover
 *
 */
public class Ragacs extends MapItem{

	private int stepInCounter;
	private static final long serialVersionUID = 6191556765398850843L;
	/**
	 * A ragacsba való belelépések számát állítja be a konstruktor.
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
	@Override
	public void stepIn(PlayerRobot playerRobot) {
		//SkeletonUtility.printCall("StepIn", this);
		// TODO Ragacs logika
		
		//VAGY +1 ha másik irányba akarunk menni
		setStepinCounter(getStepinCounter()-1);
		//SkeletonUtility.printReturn("StepIn", this);
	}
}
