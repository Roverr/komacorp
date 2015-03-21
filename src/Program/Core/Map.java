package Program.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Graphics.Line;

/**
 * Olyan oszt�ly, ami a k�s�bbi p�ly�t fogja szimul�lni a skeletonban val�
 * tesztel�sn�l.
 * 
 * @author Rover
 *
 */
public class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5351272359744547434L;
	/**
	 * T�rol minden p�yaelemet CheckPoints - A j�t�kosok menetir�ny�nak
	 * ellen�rz�s�re szolg�l. MapItems - A p�ly�n l�v� akad�lyok list�ja. Robots
	 * - A versenyben l�v� robotok list�ja. Track - A p�lya sz�l�t defini�l�
	 * vonalakat tartalmazza.
	 */
	private List<Line> CheckPoints;
	private List<MapItem> MapItems;
	private List<Robot> Robots;
	private List<Line> Track;

	/**
	 * L�trehoz�sn�l l�trehozzuk az �res list�kat, amik �resek, mert m�g nincs
	 * p�lya bet�lve, ebb�l kifoly�lag nincsenek j�t�kosok se.
	 */
	Map() {
		CheckPoints = new ArrayList<Line>();
		MapItems = new ArrayList<MapItem>();
		Robots = new ArrayList<Robot>();
		Track = new ArrayList<Line>();
	}

	/**
	 * Felvesz az akad�lyok list�j�ra egy �jat.
	 * 
	 * @param item
	 *            - Akad�ly amit a list�ra kell felvenni.
	 */
	public void AddMapItem(MapItem item) {
		MapItems.add(item);
	}

	/**
	 * Visszadja az eredm�nyt
	 * 
	 * @return - Eredm�ny lista a robotokkal.
	 */
	public List<String> GetResult() {
		/* !!!! TODO !!!! */
		return null;

	}

	/**
	 * Visszaadja a robotok list�j�t.
	 * 
	 * @return - Lista a robotokkal.
	 */
	public List<Robot> GetRobots() {
		return Robots;
	}

	/**
	 * Bet�lti a mappot egy fileb�l.
	 * 
	 * @param file
	 *            - Amib�l belehet olvasni a mappot.
	 */
	public void LoadMap(String file) {
		/** TODO MAP OLVAS� LOGIC TODO **/
	}

	/**
	 * Leszed a p�ly�r�l egy akad�lyt
	 * 
	 * @param item
	 *            - Akad�ly amit lekell szedni.
	 */
	public void RemoveMapItem(MapItem item) {
		try {
			MapItems.remove(item);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Megn�zi, hogy az adott robot bel�pett-e b�rmelyik akad�lyba �s ha igen,
	 * megh�vja a megfelel� StepIn met�dust.
	 * 
	 * @param robot
	 *            - Akit vizsg�lni kell az akad�lyokhoz viszony�tva.
	 */
	public void ValidateState(Robot robot) {
		/** TODO NEED MORE STABLE LOGIC **/

		for (int i = 0; i < MapItems.size(); i++) {

			MapItem currentItem = MapItems.get(i);

			if (robot.GetPosition() == currentItem.GetPosition()) {
				// Ha StepIncounter el�r valamenynit, utols� bel�p�sre kivessz�k
				// a mapb�l
				if (currentItem.GetStepInCounter() - 1 == 0) {
					currentItem.StepIn(robot);
					MapItems.remove(currentItem);
				} else {
					currentItem.StepIn(robot);
				}
			}
		}
	}
}
