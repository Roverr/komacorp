package Program.Skeleton;

import java.awt.Point;
/**
 * Egy akad�lynak az abasztrakt oszt�lya. Az akad�lyok tesztel�s�n�l haszn�lt. 
 * @author Rover
 *
 */
public abstract class dummyMapItem {
	/**
	 * Az a pont a t�rk�pen ahol az akad�ly tal�lhat�
	 */
	protected Point Position;
	
	/**
	 * Visszaadja az akad�ly poz�ci�j�t.
	 * @return - Position
	 */
	public Point GetPosition() {
		return Position;
	}
	
	/**
	 * Be�ll�tja az akad�ly poz�ci�j�t.
	 * @param place - Az a pont ahova helyezz�k az akad�lyt.
	 */
	public void SetPosition(Point place) {
		Position=place;
	}
	
	/**
	 * Ennek a f�ggv�nynek kell megh�v�dnia, ha a robot belel�p az akad�lyba.
	 * Mivel ez m�shogy kell a robotot m�dos�tsa, �gy ez abstract f�ggv�ny,
	 * itt nem kell megval�s�tani.
	 * @param robot - Robot aki r�l�pett az akad�lyra.
	 */
	public abstract void StepIn(dummyRobot robot);
}
