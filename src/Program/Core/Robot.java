package Program.Core;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Program.Helpers.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * A szkeleton modell mûkédésének szimulálására létrehozott Robot osztály,
 * ami viselkedésében megegyezik a késõbbi robot osztállyal. 
 * @author Rover
 *
 */
public class Robot implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8700911186613988616L;
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
	Robot() {
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		
		Alive = true;
		Distance = 0;
		/**TODO POSITION + List feltöltés akadályokkal**/
		MapItemCarriedCounter = new ArrayList<Integer>();
		
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a pályáról ez a függvény hívódik meg. 
	 */
	public void Die() {
		SkeletonUtility.printCall("Die", this);
		MapItemCarriedCounter.clear();
		Alive = false;
		//System.out.println("Felt down!");

		SkeletonUtility.printReturn("Die", this);
	}
	
	/**
	 * Ragacsot dob a pályára ha van, ha nem akkor nem csinál semmit. 
	 * @param map - Pálya amire ledobja
	 */
	public void DropRagacs(Map map) {
		SkeletonUtility.printCall("DropRagacs", this);
		int StepInCount = 3;
		Ragacs rag = new Ragacs(StepInCount);
		rag.SetPosition(new Point(Position.x,Position.y));
		map.AddMapItem(rag);
		/**TODO Kikell még venni a robot belsõ listájából*/
		SkeletonUtility.printReturn("DropRagacs", this);
	}
	/**
	 * Olajat dob a pályára
	 * @param map - A pálya ahova dobja
	 */
	public void DropOlaj(Map map) {
		SkeletonUtility.printCall("DropOlaj", this);
		int StepInCount=3;
		Olaj olaj = new Olaj(StepInCount);
		olaj.SetPosition(new Point(Position.x, Position.y));
		map.AddMapItem(olaj);
		/**TODO Kikell még venni a robot belsõ listájából*/
		SkeletonUtility.printReturn("DropOlaj", this);
	}
	
	/**
	 * Distance-t adja vissza
	 * @return
	 */
	public int GetDistance() {
		SkeletonUtility.printCall("GetDistance", this);
		SkeletonUtility.printReturn("GetDistance", this);
		return Distance;
	}
	
	/**
	 * Position-t adja vissza
	 * @return
	 */
	public Point GetPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return Position;
		
	}
	
	/**
	 * Sebesség változtatást adja vissza.
	 * @return
	 */
	public Vector GetSpeed() {
		SkeletonUtility.printCall("GetSpeed", this);

		SkeletonUtility.printReturn("GetSpeed", this);
		return ModSpeed;
		
	}
	
	/**
	 * Azt adja vissza, hogy él-e még a robot
	 * @return - TRUE OR FALSE
	 */
	public boolean isAlive() {
		SkeletonUtility.printCall("isAlive", this);
		SkeletonUtility.printReturn("isAlive", this);
		return Alive;
	}
	
	/**
	 * A robot frissítõ függvénye
	 */
	public void Jump() {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 */
		SkeletonUtility.printReturn("Jump", this);
	}
	
	/**
	 * A robot sebességét módosítja
	 * @param force - Vector amit használunk a módosítás kiszámolásához
	 */
	public void ModifySpeed(Vector force) {
		SkeletonUtility.printCall("ModifySpeed", this);

		SkeletonUtility.printReturn("ModifySpeed", this);
	}
	
	/**
	 * Robot pozícióját állítja be
	 * @param place - Point ahova beállítja
	 */
	public void SetPosition(Point place){
		SkeletonUtility.printCall("SetPosition", this);
		Position=place;
		SkeletonUtility.printReturn("SetPosition", this);
	}
	
}
