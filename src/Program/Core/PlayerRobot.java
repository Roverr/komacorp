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
public class PlayerRobot extends Robot implements Serializable  {
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
	 * @author Rover
	 * 
	 * Speed ami a robot elõzõ sebességét tárolja, ehhez adódik hozzá a modSpeed
	 * @author Barna
	 */
	protected List<Integer> mapItemCarriedCounter;

	/**
	 * Konstruktor a dummyRobothoz
	 * 
	 * 
	 * Added speed beállítás,modSpeed beállítása 0-ra
	 * @author Barna
	 */
	public PlayerRobot() {
		super();
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		/**TODO POSITION + List feltöltés akadályokkal**/
		mapItemCarriedCounter = new ArrayList<Integer>();
		
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a pályáról ez a függvény hívódik meg. 
	 */
	public void die() {
		SkeletonUtility.printCall("Die", this);
		mapItemCarriedCounter.clear();
		alive = false;
		//System.out.println("Felt down!");

		SkeletonUtility.printReturn("Die", this);
	}
	
	/**
	 * Ragacsot dob a pályára ha van, ha nem akkor nem csinál semmit. 
	 * @param map - Pálya amire ledobja
	 */
	public void dropRagacs(Map map) {
		SkeletonUtility.printCall("DropRagacs", this);
		int StepInCount = 3;
		Ragacs rag = new Ragacs(StepInCount);
		rag.setPosition(new Point(position.x,position.y));
		map.AddMapItem(rag);
		/**TODO Kikell még venni a robot belsõ listájából*/
		SkeletonUtility.printReturn("DropRagacs", this);
	}
	/**
	 * Olajat dob a pályára
	 * @param map - A pálya ahova dobja
	 */
	public void dropOlaj(Map map) {
		SkeletonUtility.printCall("DropOlaj", this);
		int StepInCount=3;
		Olaj olaj = new Olaj(StepInCount);
		olaj.setPosition(new Point(position.x, position.y));
		map.AddMapItem(olaj);
		/**TODO Kikell még venni a robot belsõ listájából*/
		SkeletonUtility.printReturn("DropOlaj", this);
	}
	
	/**
	 * A robot frissítõ függvénye
	 */
	public void jump() {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 */
		SkeletonUtility.printReturn("Jump", this);
	}
	
}
