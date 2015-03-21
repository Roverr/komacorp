package Program.Core;

import java.awt.Point;
import java.io.Serializable;

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
	Ragacs(int StepIn) {
		SetStepInCounter(StepIn);
	}
	@Override
	public void StepIn(Robot robot) {
		// TODO Ragacs logika
		
		//VAGY +1 ha másik irányba akarunk menni
		SetStepInCounter(GetStepInCounter()-1);
	}
}
