package Program.Skeleton;

import java.util.ArrayList;
import java.util.List;

import Program.Graphics.Line;

/**
 * Olyan osztály, ami a késõbbi pályát fogja szimulálni a skeletonban való
 * tesztelésnél.
 * 
 * @author Rover
 *
 */
public class dummyMap {
	/**
	 * Tárol minden páyaelemet CheckPoints - A játékosok menetirányának
	 * ellenörzésére szolgál. MapItems - A pályán lévõ akadályok listája. Robots
	 * - A versenyben lévõ robotok listája. Track - A pálya szélét definiáló
	 * vonalakat tartalmazza.
	 */
	private List<Line> CheckPoints;
	private List<dummyMapItem> MapItems;
	private List<dummyRobot> Robots;
	private List<Line> Track;

	/**
	 * Létrehozásnál létrehozzuk az üres listákat, amik üresek, mert még nincs
	 * pálya betölve, ebbõl kifolyólag nincsenek játékosok se.
	 */
	dummyMap() {
		CheckPoints = new ArrayList<Line>();
		MapItems = new ArrayList<dummyMapItem>();
		Robots = new ArrayList<dummyRobot>();
		Track = new ArrayList<Line>();
	}

	/**
	 * Felvesz az akadályok listájára egy újat.
	 * 
	 * @param item
	 *            - Akadály amit a listára kell felvenni.
	 */
	public void AddMapItem(dummyMapItem item) {
		MapItems.add(item);
	}

	/**
	 * Visszadja az eredményt
	 * 
	 * @return - Eredmény lista a robotokkal.
	 */
	public List<String> GetResult() {
		/* !!!! TODO !!!! */
		return null;

	}

	/**
	 * Visszaadja a robotok listáját.
	 * 
	 * @return - Lista a robotokkal.
	 */
	public List<dummyRobot> GetRobots() {
		return Robots;
	}

	/**
	 * Betölti a mappot egy fileból.
	 * 
	 * @param file
	 *            - Amibõl belehet olvasni a mappot.
	 */
	public void LoadMap(String file) {
		/** TODO MAP OLVASÓ LOGIC TODO **/
	}

	/**
	 * Leszed a pályáról egy akadályt
	 * 
	 * @param item
	 *            - Akadály amit lekell szedni.
	 */
	public void RemoveMapItem(dummyMapItem item) {
		try {
			MapItems.remove(item);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Megnézi, hogy az adott robot belépett-e bármelyik akadályba és ha igen,
	 * meghívja a megfelelõ StepIn metódust.
	 * 
	 * @param robot
	 *            - Akit vizsgálni kell az akadályokhoz viszonyítva.
	 */
	public void ValidateState(dummyRobot robot) {
		/** TODO NEED MORE STABLE LOGIC **/

		for (int i = 0; i < MapItems.size(); i++) {

			dummyMapItem currentItem = MapItems.get(i);

			if (robot.GetPosition() == currentItem.GetPosition()) {
				// Ha StepIncounter elér valamenynit, utolsó belépésre kivesszük
				// a mapból
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
