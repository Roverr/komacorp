package Program.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Helpers.Line;
import Program.Skeleton.SkeletonUtility;

/**
 * Olyan osztály, ami a késõbbi pályát fogja szimulálni a skeletonban való
 * tesztelésnél.
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
	 * Tárol minden páyaelemet CheckPoints - A játékosok menetirányának
	 * ellenörzésére szolgál. MapItems - A pályán lévõ akadályok listája. Robots
	 * - A versenyben lévõ robotok listája. Track - A pálya szélét definiáló
	 * vonalakat tartalmazza.
	 */
	private List<Line> CheckPoints;
	private List<MapItem> MapItems;
	private List<Robot> Robots;
	private List<Line> Track;

	/**
	 * Létrehozásnál létrehozzuk az üres listákat, amik üresek, mert még nincs
	 * pálya betölve, ebbõl kifolyólag nincsenek játékosok se.
	 */
	Map() {
		SkeletonUtility.addClass(this, "dummyMap");
		SkeletonUtility.printCall("create Map", this);
		
		CheckPoints = new ArrayList<Line>();
		MapItems = new ArrayList<MapItem>();
		Robots = new ArrayList<Robot>();
		Track = new ArrayList<Line>();
		SkeletonUtility.printReturn("create Map", this);
	}

	/**
	 * Felvesz az akadályok listájára egy újat.
	 * 
	 * @param item
	 *            - Akadály amit a listára kell felvenni.
	 */
	public void AddMapItem(MapItem item) {
		SkeletonUtility.printCall("AddMapItem", this);
		MapItems.add(item);
		SkeletonUtility.printReturn("AddMapItem", this);
	}

	/**
	 * Visszadja az eredményt
	 * 
	 * @return - Eredmény lista a robotokkal.
	 */
	public List<String> GetResult() {
		SkeletonUtility.printCall("GetResult", this);
		
		for (Robot r : Robots) {
			int dummyint = r.GetDistance();
		}
		
		SkeletonUtility.printReturn("GetResult", this);
		return null;

	}

	/**
	 * Visszaadja a robotok listáját.
	 * 
	 * @return - Lista a robotokkal.
	 */
	public List<Robot> GetRobots() {
		SkeletonUtility.printCall("GetRobots", this);
		SkeletonUtility.printReturn("GetRobots", this);
		return Robots;
	}

	/**
	 * Betölti a mappot egy fileból.
	 * 
	 * @param file
	 *            - Amibõl belehet olvasni a mappot.
	 */
	public void LoadMap(String file) {
		SkeletonUtility.printCall("LoadMap(" + file + ")", this);
		/** TODO MAP OLVASÓ LOGIC TODO **/
		for (int i = 0; i < 2; i++) {
			Robots.add(new Robot());
		}
		
		AddMapItem(new Olaj(3));
		AddMapItem(new Ragacs(3));
		
		SkeletonUtility.printReturn("LoadMap", this);
	}

	/**
	 * Leszed a pályáról egy akadályt
	 * 
	 * @param item
	 *            - Akadály amit lekell szedni.
	 */
	public void RemoveMapItem(MapItem item) {
		SkeletonUtility.printCall("RemoveMapItem", this);
		try {
			MapItems.remove(item);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		SkeletonUtility.printReturn("RemoveMapItem", this);
	}

	/**
	 * Megnézi, hogy az adott robot belépett-e bármelyik akadályba és ha igen,
	 * meghívja a megfelelõ StepIn metódust.
	 * 
	 * @param robot
	 *            - Akit vizsgálni kell az akadályokhoz viszonyítva.
	 */
	public void ValidateState(Robot robot) {
		SkeletonUtility.printCall("ValidateState", this);
		/** TODO NEED MORE STABLE LOGIC **/

		for (int i = 0; i < MapItems.size(); i++) {

			MapItem currentItem = MapItems.get(i);
			
			boolean doStepIn = true; //SkeletonUtility.yesOrNoQuestion("StepIn meghívódik?");
			if (robot.GetPosition() == currentItem.GetPosition()) {
				//TODO Switch, olaj vagy ragacs.
				currentItem.StepIn(robot);
			}
		}
		SkeletonUtility.printReturn("ValidateState", this);
	}
}
