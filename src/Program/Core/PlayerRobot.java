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
public class PlayerRobot extends Robot implements Serializable  {
	
	
	/**
	 * MapItemCarriedCounter - Map itemek amik m�g a robotn�l vannak.
	 * wantToDrop 
	 * - 0, ha nem szeretne dobni a robot,
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * @author Barna,Rover
	 */
	protected List<Integer> mapItemCarriedCounter;
	private static final long serialVersionUID = -8700911186613988616L;
	private int wantToDrop;

	/**
	 * Konstruktor a j�t�kosok �lltal ir�ny�tott Robothoz
	 * @author Barna
	 */
	public PlayerRobot() {
		super();
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		/**TODO POSITION + List felt�lt�s akad�lyokkal**/
		mapItemCarriedCounter = new ArrayList<Integer>();
		
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a p�ly�r�l ez a f�ggv�ny h�v�dik meg. 
	 */
	public void die() {
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
	public void dropRagacs(Map map) {
		// 3 -szr lehet belel�pni
		int stepInCount = 3;
		int ragacsNumberInList = 0;
		int ragacsLeft = mapItemCarriedCounter.get(ragacsNumberInList);
		
		if(ragacsLeft >0 ) {
			int ragacsNumberInDropping = 1;
			Ragacs ragacs = new Ragacs(stepInCount,getPosition());
			map.AddMapItem(ragacs);
			mapItemCarriedCounter.set(ragacsNumberInList,ragacsLeft-1);
			setWantToDrop(ragacsNumberInDropping);
		} else {
			int noItemToDrop = 0;
			setWantToDrop(noItemToDrop);
			System.out.println("No ragacs left, sorryka");
		}

		
	}
	
	/**
	 * Olajat dob a p�ly�ra
	 * @param map - A p�lya ahova dobja
	 */
	public void dropOlaj(Map map) {
		// 10 k�rig lesz �letben az olaj
		int time = 10; 
		int olajNumberInList = 1;
		int olajLeft = mapItemCarriedCounter.get(olajNumberInList);
		
		if(olajLeft > 0) {
			int olajNumberInDropping = 2;
			Olaj olaj = new Olaj(time,getPosition());;
			map.AddMapItem(olaj);
			mapItemCarriedCounter.set(olajNumberInList, olajLeft-1);
			setWantToDrop(olajNumberInDropping);
		} else {
			int nothingToDrop = 0;
			setWantToDrop(nothingToDrop);
			System.out.println("No olaj left, sorryka");
		}
	}
	
	/**
	 * A robot friss�t� f�ggv�nye
	 */
	public void jump() {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 */
		SkeletonUtility.printReturn("Jump", this);
	}
	
	/**
	 * Visszadja a bitet ami jelzi, hogy a robot szeretne akad�lyt dobni
	 * @return 
	 * - 0, ha a robot nem szeretne dobni
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * DEV:Minden m�s �rt�ket errork�nt elkapunk.
	 */
	public int getWantToDrop() {
		return wantToDrop;
	}
	
	/**
	 * Be�ll�tja a wantToDrop �rt�k�t. 
	 * @param to - Az �rt�k, amire szeretn�nk �ll�tani
	 */
	public void setWantToDrop(int to) {
		wantToDrop = to;
	}
	
	
	
}
