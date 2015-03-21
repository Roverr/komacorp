package Program.Skeleton;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A szkeleton modell mûkédésének szimulálására létrehozott Robot osztály,
 * ami viselkedésében megegyezik a késõbbi robot osztállyal. 
 * @author Rover
 *
 */
public class dummyRobot implements Serializable  {
	/**
	 * Alive - Beállítható, hogy él-e még a robot
	 * Distance - Milyen messze jutott a pályán(eredmény számoláshoz)
	 * MapItemCarriedCounter - Map itemek amik még a robotnál vannak.
	 * ModSpeed- Vektor amivel a user módosíthatja a robot sebességét
	 * Position - A térképen való pozícióját jelöli
	 */
	protected boolean Alive;
	protected int Distance;
	protected List<Integer> MapItemCarriedCounter;
	protected Vector ModSpeed;
	protected Point Position;

	/**
	 * Konstruktor a dummyRobothoz
	 */
	dummyRobot() {
		Alive = true;
		Distance = 0;
		/**TODO POSITION + List feltöltés akadályokkal**/
		MapItemCarriedCounter = new ArrayList<Integer>();
		
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
	 * Ragacsot dob a pályára ha van, ha nem akkor nem csinál semmit. 
	 * @param map - Pálya amire ledobja
	 */
	public void DropRagacs(dummyMap map) { 
		int StepInCount = 3;
		map.AddMapItem(new dummyRagacs(StepInCount));
	}
	
	public void DropOlaj(dummyMap map) {
		
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
