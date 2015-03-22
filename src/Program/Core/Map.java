package Program.Core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Helpers.Line;
import Program.Skeleton.SkeletonUtility;

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
	private List<Line> checkPoints;
	private List<MapItem> mapItems;
	private Ragacs dummyRagacsForSkeleton=new Ragacs(2);
	private Olaj dummyOlajForSkeleton=new Olaj(2);
	private List<Robot> robots;
	private List<Line> track;

	/**
	 * L�trehoz�sn�l l�trehozzuk az �res list�kat, amik �resek, mert m�g nincs
	 * p�lya bet�lve, ebb�l kifoly�lag nincsenek j�t�kosok se.
	 */
	public Map() {
		SkeletonUtility.addClass(this, "dummyMap");
		SkeletonUtility.printCall("create Map", this);
		
		checkPoints = new ArrayList<Line>();
		mapItems = new ArrayList<MapItem>();
		robots = new ArrayList<Robot>();
		track = new ArrayList<Line>();
		SkeletonUtility.printReturn("create Map", this);
	}

	/**
	 * Felvesz az akad�lyok list�j�ra egy �jat.
	 * 
	 * @param item
	 *            - Akad�ly amit a list�ra kell felvenni.
	 */
	public void AddMapItem(MapItem item) {
		SkeletonUtility.printCall("AddMapItem", this);
		mapItems.add(item);
		SkeletonUtility.printReturn("AddMapItem", this);
	}

	/**
	 * Visszadja az eredm�nyt
	 * 
	 * @return - Eredm�ny lista a robotokkal.
	 */
	public List<String> getResult() {
		SkeletonUtility.printCall("GetResult", this);
		List<String> temp = new ArrayList<String>();
		for (Robot r : robots) {
			int dummyint = r.getDistance();
			temp.add(r.toString());
		}
		
		SkeletonUtility.printReturn("GetResult", this);
		return temp;

	}

	/**
	 * Visszaadja a robotok list�j�t.
	 * 
	 * @return - Lista a robotokkal.
	 */
	public List<Robot> getRobots() {
		SkeletonUtility.printCall("GetRobots", this);
		SkeletonUtility.printReturn("GetRobots", this);
		return robots;
	}

	/**
	 * Bet�lti a mappot egy fileb�l.
	 * 
	 * @param file
	 *            - Amib�l belehet olvasni a mappot.
	 */
	/**
	 * Szerintem ez sz�ks�ges m�g a LoadMaphoz, ne legyenek rajta f�l�s robotok
	 * Lehet 0 robottal is?!?!?!?!, ekkor csak a p�lya megn�z�se a c�l
	 * de mivel �llapotot nem t�rolunk, teh�t meg kell k�rdezni a felhaszn�l�t�l h 
	 * h�ny robottal akarjuk megcsin�lni
	 * @author Barna
	 * @param numberOfPlayers
	 */
	public void loadMap(String file,int numberOfPlayers) {
		SkeletonUtility.printCall("LoadMap(" + file + ")", this);
		/** TODO MAP OLVAS� LOGIC TODO **/
		for (int i = 0; i < numberOfPlayers; i++) {
			robots.add(new Robot());
		}
		
		AddMapItem(new Olaj(3));
		AddMapItem(new Ragacs(3));
		
		SkeletonUtility.printReturn("LoadMap", this);
	}

	/**
	 * Leszed a p�ly�r�l egy akad�lyt
	 * 
	 * @param item
	 *            - Akad�ly amit lekell szedni.
	 */
	public void removeMapItem(MapItem item) {
		SkeletonUtility.printCall("RemoveMapItem", this);
		try {
			mapItems.remove(item);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		SkeletonUtility.printReturn("RemoveMapItem", this);
	}

	/**
	 * Megn�zi, hogy az adott robot bel�pett-e b�rmelyik akad�lyba �s ha igen,
	 * megh�vja a megfelel� StepIn met�dust.
	 * 
	 * @param robot
	 *            - Akit vizsg�lni kell az akad�lyokhoz viszony�tva.
	 * @throws IOException 
	 */
	public void validateState(Robot robot) throws IOException {
		SkeletonUtility.printCall("ValidateState", this);
		/** TODO NEED MORE STABLE LOGIC **/
		robot.getPosition();
		//for (int i = 0; i < mapItems.size(); i++) {

			//MapItem currentItem = mapItems.get(i);
			if(SkeletonUtility.yesOrNoQuestion("Kiest�l a p�ly�r�l?")){
				robot.die();
			}
		
			if(SkeletonUtility.yesOrNoQuestion("Belel�pett a robot egy ragacsba?")){
				dummyRagacsForSkeleton.stepIn(robot);
				
			}
			if(SkeletonUtility.yesOrNoQuestion("Belel�pett a robot egy olajba?")){
				dummyOlajForSkeleton.stepIn(robot);
			}
			//if (robot.getPosition() == currentItem.GetPosition()) {
				//TODO Switch, olaj vagy ragacs.
				//currentItem.StepIn(robot);
			//}
		//}
		SkeletonUtility.printReturn("ValidateState", this);
	}
}
