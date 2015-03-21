package Program.Skeleton;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A szkeleton modell m�k�d�s�nek szimul�l�s�ra l�trehozott Robot oszt�ly,
 * ami viselked�s�ben megegyezik a k�s�bbi robot oszt�llyal. 
 * @author Rover
 *
 */
public class dummyRobot implements Serializable  {
	/**
	 * Alive - Be�ll�that�, hogy �l-e m�g a robot
	 * Distance - Milyen messze jutott a p�ly�n(eredm�ny sz�mol�shoz)
	 * MapItemCarriedCounter - Map itemek amik m�g a robotn�l vannak.
	 * ModSpeed- Vektor amivel a user m�dos�thatja a robot sebess�g�t
	 * Position - A t�rk�pen val� poz�ci�j�t jel�li
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
		/**TODO POSITION + List felt�lt�s akad�lyokkal**/
		MapItemCarriedCounter = new ArrayList<Integer>();
		
	}
	
	/**
	 * Ha a robot leesik a p�ly�r�l ez a f�ggv�ny h�v�dik meg. 
	 */
	public void Die() {
		MapItemCarriedCounter.clear();
		Alive = false;
		System.out.println("Felt down!");
		
	}
	
	/**
	 * Ragacsot dob a p�ly�ra ha van, ha nem akkor nem csin�l semmit. 
	 * @param map - P�lya amire ledobja
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
	 * Sebess�g v�ltoztat�st adja vissza.
	 * @return
	 */
	public Vector GetSpeed() {
		return ModSpeed;
		
	}
	
	/**
	 * Azt adja vissza, hogy �l-e m�g a robot
	 * @return - TRUE OR FALSE
	 */
	public boolean isAlive() {
		return Alive;
	}
	
	/**
	 * A robot friss�t� f�ggv�nye
	 */
	public void Jump() {
		/**
		 * TODO 
		 */
	}
	
	/**
	 * A robot sebess�g�t m�dos�tja
	 * @param force - Vector amit haszn�lunk a m�dos�t�s kisz�mol�s�hoz
	 */
	public void ModifySpeed(Vector force) {
		
	}
	
	/**
	 * Robot poz�ci�j�t �ll�tja be
	 * @param place - Point ahova be�ll�tja
	 */
	public void SetPosition(Point place){
		Position=place;
	}
	
}
