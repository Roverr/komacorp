package Program.Skeleton;

import java.awt.Point;

/**
 * Dummy oszt�ly amit a Ragacs akad�ly tesztel�s�re haszn�lunk
 * 
 * @author Rover
 *
 */
public class dummyRagacs extends dummyMapItem {

	/**
	 * A ragacsba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	dummyRagacs(int StepIn) {
		SetStepInCounter(StepIn);
	}
	@Override
	public void StepIn(dummyRobot robot) {
		// TODO Ragacs logika
		
		//VAGY +1 ha m�sik ir�nyba akarunk menni
		SetStepInCounter(GetStepInCounter()-1);
	}
}
