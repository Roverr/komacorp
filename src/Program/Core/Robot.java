package Program.Core;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Program.Helpers.Vector;

import Program.Skeleton.SkeletonUtility;

/**
 * A szkeleton modell m�k�d�s�nek szimul�l�s�ra l�trehozott Robot oszt�ly,
 * ami viselked�s�ben megegyezik a k�s�bbi robot oszt�llyal. 
 * @author Rover
 *
 */
public class Robot implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8700911186613988616L;
	/**
	 * Alive - Be�ll�that�, hogy �l-e m�g a robot
	 * Distance - Milyen messze jutott a p�ly�n(eredm�ny sz�mol�shoz)
	 * MapItemCarriedCounter - Map itemek amik m�g a robotn�l vannak.
	 * ModSpeed- Vektor amivel a user m�dos�thatja a robot sebess�g�t
	 * Position - A t�rk�pen val� poz�ci�j�t jel�li
	 * @author Rover
	 * 
	 * Speed ami a robot el�z� sebess�g�t t�rolja, ehhez ad�dik hozz� a modSpeed
	 * @author Barna
	 */
	protected boolean alive;
	protected int distance;
	protected List<Integer> mapItemCarriedCounter;
	protected Vector speed;
	protected Vector modSpeed;
	protected Point position;

	/**
	 * Konstruktor a dummyRobothoz
	 * 
	 * 
	 * Added speed be�ll�t�s,modSpeed be�ll�t�sa 0-ra
	 * @author Barna
	 */
	public Robot() {
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		speed=new Vector(0, 0);
		modSpeed=new Vector(0,0);
		alive = true;
		distance = 0;
		/**TODO POSITION + List felt�lt�s akad�lyokkal**/
		mapItemCarriedCounter = new ArrayList<Integer>();
		
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a p�ly�r�l ez a f�ggv�ny h�v�dik meg. 
	 */
	public void Die() {
		SkeletonUtility.printCall("Die", this);
		mapItemCarriedCounter.clear();
		alive = false;
		//System.out.println("Felt down!");

		SkeletonUtility.printReturn("Die", this);
	}
	
	/**
	 * Ragacsot dob a p�ly�ra ha van, ha nem akkor nem csin�l semmit. 
	 * @param map - P�lya amire ledobja
	 */
	public void DropRagacs(Map map) {
		SkeletonUtility.printCall("DropRagacs", this);
		int StepInCount = 3;
		Ragacs rag = new Ragacs(StepInCount);
		rag.SetPosition(new Point(position.x,position.y));
		map.AddMapItem(rag);
		/**TODO Kikell m�g venni a robot bels� list�j�b�l*/
		SkeletonUtility.printReturn("DropRagacs", this);
	}
	/**
	 * Olajat dob a p�ly�ra
	 * @param map - A p�lya ahova dobja
	 */
	public void DropOlaj(Map map) {
		SkeletonUtility.printCall("DropOlaj", this);
		int StepInCount=3;
		Olaj olaj = new Olaj(StepInCount);
		olaj.SetPosition(new Point(position.x, position.y));
		map.AddMapItem(olaj);
		/**TODO Kikell m�g venni a robot bels� list�j�b�l*/
		SkeletonUtility.printReturn("DropOlaj", this);
	}
	
	/**
	 * Distance-t adja vissza
	 * @return
	 */
	public int GetDistance() {
		SkeletonUtility.printCall("GetDistance", this);
		SkeletonUtility.printReturn("GetDistance", this);
		return distance;
	}
	
	/**
	 * Position-t adja vissza
	 * @return
	 */
	public Point GetPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return position;
		
	}
	
	/**
	 * Sebess�g v�ltoztat�st adja vissza.
	 * @return
	 */
	public Vector GetSpeed() {
		SkeletonUtility.printCall("GetSpeed", this);

		SkeletonUtility.printReturn("GetSpeed", this);
		return modSpeed;
		
	}
	
	/**
	 * Azt adja vissza, hogy �l-e m�g a robot
	 * @return - TRUE OR FALSE
	 */
	public boolean isAlive() {
		SkeletonUtility.printCall("isAlive", this);
		SkeletonUtility.printReturn("isAlive", this);
		return alive;
	}
	
	/**
	 * A robot friss�t� f�ggv�nye
	 */
	public void Jump() {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 */
		SkeletonUtility.printReturn("Jump", this);
	}
	
	/**
	 * A robot sebess�g�t m�dos�tja
	 * @param force - Vector amit haszn�lunk a m�dos�t�s kisz�mol�s�hoz
	 */
	public void ModifySpeed(Vector force) {
		SkeletonUtility.printCall("ModifySpeed", this);

		SkeletonUtility.printReturn("ModifySpeed", this);
	}
	
	/**
	 * Robot poz�ci�j�t �ll�tja be
	 * @param place - Point ahova be�ll�tja
	 */
	public void SetPosition(Point place){
		SkeletonUtility.printCall("SetPosition", this);
		position=place;
		SkeletonUtility.printReturn("SetPosition", this);
	}
	
}
