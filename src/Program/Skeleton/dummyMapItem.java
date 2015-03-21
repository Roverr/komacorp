package Program.Skeleton;

import java.awt.Point;
/**
 * Egy akadálynak az abasztrakt osztálya. Az akadályok tesztelésénél használt. 
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
