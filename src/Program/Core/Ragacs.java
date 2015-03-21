package Program.Core;

import java.awt.Point;
import java.io.Serializable;

/**
 * Dummy oszt�ly amit a Ragacs akad�ly tesztel�s�re haszn�lunk
 * 
 * @author Rover
 *
 */
public class Ragacs extends MapItem{

	/**
	 * A ragacsba val� belel�p�sek sz�m�t �ll�tja be a konstruktor.
	 * @param StepIn
	 */
	Ragacs(int StepIn) {
		SetStepInCounter(StepIn);
	}
	@Override
	public void StepIn(Robot robot) {
		// TODO Ragacs logika
		
		//VAGY +1 ha m�sik ir�nyba akarunk menni
		SetStepInCounter(GetStepInCounter()-1);
	}
}
