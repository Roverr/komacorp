package Program.Skeleton;

import java.awt.Point;

public abstract class dummyMapItem {
	protected Point Position;
	
	public Point GetPosition() {
		return Position;
	}
	
	public abstract void SetPosition(Point place);
	public abstract void StepIn(dummyRobot robot);
}
