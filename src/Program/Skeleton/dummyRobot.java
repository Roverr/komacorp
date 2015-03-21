package Program.Skeleton;

import java.awt.Point;
import java.util.List;
import java.util.Vector;

/**
 * A szkeleton modell m�k�d�s�nek szimul�l�s�ra l�trehozott Robot oszt�ly,
 * ami viselked�s�ben megegyezik a k�s�bbi robot oszt�llyal. 
 * @author Sz�kely K�roly
 *
 */
public class dummyRobot {
	protected boolean Alive;
	protected int Distance;
	protected List<Integer> MapItemCarriedCounter;
	protected Vector ModSpeed;
	protected Point Position;
	
	public void Die() {
		
	}
	
	public void DropMapItem(dummyMapItem item, dummyMap map) {
		
	}
	
	public int GetDistance() {
		return Distance;
	}
	
	public Point GetPosition() {
		return Position;
		
	}
	
	public Vector GetSpeed() {
		return ModSpeed;
		
	}
	
	public boolean isAlive() {
		return Alive;
	}
	
	public void Jump() {
		
	}
	
	public void ModifySpeed(Vector force) {
		
	}
	
	public void SetPosition(Point place){
		
	}
	
}
