package Program.Skeleton;

import java.awt.Point;
/**
 * Egy akad�lynak az abasztrakt oszt�lya. Az akad�lyok tesztel�s�n�l haszn�lt. 
 * @author Rover
 *
 */
public abstract class dummyMapItem {
	protected Point Position;
	
	public Point GetPosition() {
		return Position;
	}
	
	public void SetPosition(Point place) {
		Position=place;
	}
	public abstract void StepIn(dummyRobot robot);
}
