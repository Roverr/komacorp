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

	/**
	 * A ragacsba való belelépések számát állítja be a konstruktor.
	 * @param StepIn
	 */
	public Ragacs(int StepIn) {
		SkeletonUtility.addClass(this, "Ragacs" + SkeletonUtility.mapItemCounter);
		SkeletonUtility.mapItemCounter++;
		SkeletonUtility.printCall("create Ragacs", this);
		setStepInCounter(StepIn);
		SkeletonUtility.printReturn("create Ragacs", this);
	}
	@Override
	public void stepIn(Robot robot) {
		SkeletonUtility.printCall("StepIn", this);
		// TODO Ragacs logika
		
		//VAGY +1 ha másik irányba akarunk menni
		setStepInCounter(getStepInCounter()-1);
		SkeletonUtility.printReturn("StepIn", this);
	}
}
