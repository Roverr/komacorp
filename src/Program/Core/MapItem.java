package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Skeleton.SkeletonUtility;
/**
 * Egy akad�lynak az abasztrakt oszt�lya. Az akad�lyok tesztel�s�n�l haszn�lt. 
 * @author Rover
 *
 */
public abstract class MapItem implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3066714571792766849L;
	/**
	 * Az a pont a t�rk�pen ahol az akad�ly tal�lhat�
	 */
	protected Point position;
	private int stepInCounter;
	
	/**
	 * Visszaadja az akad�ly poz�ci�j�t.
	 * @return - Position
	 */
	public Point getPosition() {
		SkeletonUtility.printCall("GetPosition", this);
		SkeletonUtility.printReturn("GetPosition", this);
		return position;
	}
	
	/**
	 * Visszadja h�nyszor lehet belel�pni
	 * @return
	 */
	public int getStepInCounter() {
		SkeletonUtility.printCall("GetStepInCounter", this);
		SkeletonUtility.printReturn("GetStepInCounter", this);
		return stepInCounter;
	}
	
	/**
	 * Be�ll�tja h�nyszor lehet belel�pni
	 * @param value
	 */
	public void setStepInCounter(int value) {
		SkeletonUtility.printCall("SetStepInCounter to " + value, this);
		stepInCounter=value;
		SkeletonUtility.printReturn("SetStepInCounter", this);
	}
	
	/**
	 * Be�ll�tja az akad�ly poz�ci�j�t.
	 * @param place - Az a pont ahova helyezz�k az akad�lyt.
	 */
	public void setPosition(Point place) {
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

}
