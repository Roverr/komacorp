package Program.Skeleton;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A szkeleton modell mûkédésének szimulálására létrehozott Robot osztály,
 * ami viselkedésében megegyezik a késõbbi robot osztállyal. 
 * @author Rover
 *
 */
public class dummyRobot {
	/**
	 * Alive - Beállítható, hogy él-e még a robot
	 * Distance - Milyen messze jutott a pályán(eredmény számoláshoz)
	 * MapItemCarriedCounter - Map itemek amik még a robotnál vannak.
	 * ModSpeed- Vektor amivel a user módosíthatja a robot sebességét
	 * Position - A térképen való pozícióját jelöli
	 */
	protected boolean Alive;
	protected int Distance;
	protected List<dummyMapItem> MapItemCarriedCounter;
	protected Vector ModSpeed;
	protected Point Position;

	/**
	 * Konstruktor a dummyRobothoz
	 */
	dummyRobot() {
		Alive = true;
		Distance = 0;
		/**TODO POSITION + List feltöltés akadályokkal**/
		MapItemCarriedCounter = new ArrayList<dummyMapItem>();
		
	}
	
	/**
	 * Ha a robot leesik a pályáról ez a függvény hívódik meg. 
	 */
	public void Die() {
		MapItemCarriedCounter.clear();
		Alive = false;
		System.out.println("Felt down!");
		
	}
	
	/**
	 * Akadályt dob a pályára
	 * @param item - Az akadály amit hordoz a robot
	 * @param map - A pálya ami átveszi az akadályt
	 */
	public void DropMapItem(dummyMapItem item, dummyMap map) {
		if(MapItemCarriedCounter.contains(item)) {
			map.AddMapItem(item);
			MapItemCarriedCounter.remove(item);
		} else {
			System.out.println("Cant drop this dummyMapItem, i don't have it!");
		}
		
	}
	
	/**
	 * Distance-t adja vissza
	 * @return
	 */
	public int GetDistance() {
		return Distance;
	}
	
	/**
	 * Position-t adja vissza
	 * @return
	 */
	public Point GetPosition() {
		return Position;
		
	}
	
	/**
	 * Sebesség változtatást adja vissza.
	 * @return
	 */
	public Vector GetSpeed() {
		return ModSpeed;
		
	}
	
	/**
	 * Azt adja vissza, hogy él-e még a robot
	 * @return - TRUE OR FALSE
	 */
	public boolean isAlive() {
		return Alive;
	}
	
	/**
	 * A robot frissítõ függvénye
	 */
	public void Jump() {
		/**
		 * TODO 
		 */
	}
	
	/**
	 * A robot sebességét módosítja
	 * @param force - Vector amit használunk a módosítás kiszámolásához
	 */
	public void ModifySpeed(Vector force) {
		
	}
	
	/**
	 * Robot pozícióját állítja be
	 * @param place - Point ahova beállítja
	 */
	public void SetPosition(Point place){
		Position=place;
	}
	
}
