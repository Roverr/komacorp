package Program.Skeleton;

import java.awt.Point;

/**
 * Dummy osztály amit a Ragacs akadály tesztelésére használunk
 * 
 * @author Rover
 *
 */
public class dummyRagacs extends dummyMapItem {

	/**
	 * A ragacsba való belelépések számát állítja be a konstruktor.
	 * @param StepIn
	 */
	dummyRagacs(int StepIn) {
		SetStepInCounter(StepIn);
	}
	@Override
	public void StepIn(dummyRobot robot) {
		// TODO Ragacs logika
		
		//VAGY +1 ha másik irányba akarunk menni
		SetStepInCounter(GetStepInCounter()-1);
	}
}
