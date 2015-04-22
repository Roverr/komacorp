package Program.Core;

import java.io.Serializable;

import Program.Core.MapItem.CleaningState;
import Program.Helpers.FloatPoint;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;
/**
 * Egy akad�lynak az abasztrakt oszt�lya. Az akad�lyok tesztel�s�n�l haszn�lt. 
 * @author Rover
 *
 */
public abstract class MapItem implements Serializable  {
	enum CleaningState {
		beingCleaned,
		canBeCleaned
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3066714571792766849L;
	/**
	 * Az a pont a t�rk�pen ahol az akad�ly tal�lhat�
	 */
	protected FloatPoint position;
	
	public CleaningState state = CleaningState.canBeCleaned;
	
	/**
	 * Visszaadja az akad�ly poz�ci�j�t.
	 * @return - Position
	 */
	public FloatPoint getPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return position;
	}
	
	/**
	 * Be�ll�tja az akad�ly poz�ci�j�t.
	 * @param place - Az a pont ahova helyezz�k az akad�lyt.
	 */
	public void setPosition(FloatPoint place) {
		SkeletonUtility.printCall("SetPosition", this);
		position=place;
		SkeletonUtility.printReturn("SetPosition", this);
	}
	
	/**
	 * Ennek a f�ggv�nynek kell megh�v�dnia, ha a robot belel�p az akad�lyba.
	 * Mivel ez m�shogy kell a robotot m�dos�tsa, �gy ez abstract f�ggv�ny,
	 * itt nem kell megval�s�tani.
	 * @param playerRobot - Robot aki r�l�pett az akad�lyra.
	 */
	public abstract void stepIn(PlayerRobot playerRobot);
	/**
	 * Az update f�ggv�ny megh�v�dik minden run alkalm�val. Ez teszi lehet�v� az Olaj �reg�t�s�t.
	 */
	public abstract void update();
	
	public CleaningState getState(){
		return this.state;
	}

	public abstract boolean isAlive();

	public void setCleaningState(CleaningState st) {
		state = st;

		if(PrototypeUtility.allowDebug)System.out.println("Einstand! - MapItem clamied by a Cleaner.");
	}

}
