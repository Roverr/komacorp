package Program.Core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

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
	private List<Line> checkPoints;
	private List<MapItem> mapItems;
	private List<PlayerRobot> playerRobots;
	private List<Line> track;

	/**
	 * Létrehozásnál létrehozzuk az üres listákat, amik üresek, mert még nincs
	 * pálya betölve, ebbõl kifolyólag nincsenek játékosok se.
	 */
	public Map() {
		SkeletonUtility.addClass(this, "dummyMap");
		SkeletonUtility.printCall("create Map", this);
		
		checkPoints = new ArrayList<Line>();
		mapItems = new ArrayList<MapItem>();
		playerRobots = new ArrayList<PlayerRobot>();
		track = new ArrayList<Line>();
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
		mapItems.add(item);
		SkeletonUtility.printReturn("AddMapItem", this);
	}

	/**
	 * Visszadja az eredményt
	 * 
	 * @return - Eredmény lista a robotokkal.
	 */
	public List<String> getResult() {
		SkeletonUtility.printCall("GetResult", this);
		List<String> temp = new ArrayList<String>();
		for (PlayerRobot r : playerRobots) {
			int dummyint = r.getDistance();
			temp.add(r.toString());
		}
		
		SkeletonUtility.printReturn("GetResult", this);
		return temp;

	}

	/**
	 * Visszaadja a robotok listáját.
	 * 
	 * @return - Lista a robotokkal.
	 */
	public List<PlayerRobot> getRobots() {
		SkeletonUtility.printCall("GetRobots", this);
		SkeletonUtility.printReturn("GetRobots", this);
		return playerRobots;
	}

	/**
	 * Betölti a mappot egy fileból.
	 * 
	 * @param file
	 *            - Amibõl belehet olvasni a mappot.
	 */
	/**
	 * Szerintem ez szükséges még a LoadMaphoz, ne legyenek rajta fölös robotok
	 * Lehet 0 robottal is?!?!?!?!, ekkor csak a pálya megnézése a cél
	 * de mivel állapotot nem tárolunk, tehát meg kell kérdezni a felhasználótól h 
	 * hány robottal akarjuk megcsinálni
	 * @author Barna
	 * @param numberOfPlayers
	 */
	public void loadMap(String file,int numberOfPlayers) {
		SkeletonUtility.printCall("LoadMap(" + file + ")", this);
		/** TODO MAP OLVASÓ LOGIC TODO **/
		for (int i = 0; i < numberOfPlayers; i++) {
			playerRobots.add(new PlayerRobot());
		}
		
		//AddMapItem(new Olaj(3));
		//AddMapItem(new Ragacs(3));
		
		SkeletonUtility.printReturn("LoadMap", this);
	}

	/**
	 * Leszed a pályáról egy akadályt
	 * 
	 * @param item
	 *            - Akadály amit lekell szedni.
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
	 * Megnézi, hogy az adott robot belépett-e bármelyik akadályba és ha igen,
	 * meghívja a megfelelõ StepIn metódust.
	 * 
	 * @param playerRobot
	 *            - Akit vizsgálni kell az akadályokhoz viszonyítva.
	 * @throws IOException 
	 */
	public void validateState(PlayerRobot playerRobot) throws IOException {
		SkeletonUtility.printCall("ValidateState", this);
		/** TODO NEED MORE STABLE LOGIC **/
		playerRobot.getPosition();
		//for (int i = 0; i < mapItems.size(); i++) {

			//MapItem currentItem = mapItems.get(i);
			if(SkeletonUtility.yesOrNoQuestion("Kiestél a pályáról?")){
				playerRobot.die();
			}
		
			else if(SkeletonUtility.yesOrNoQuestion("Belelépett a robot egy ragacsba?")){
				//dummyRagacsForSkeleton.stepIn(playerRobot);
				
			}
			else if(SkeletonUtility.yesOrNoQuestion("Belelépett a robot egy olajba?")){
				//dummyOlajForSkeleton.stepIn(playerRobot);
			}
			//if (robot.getPosition() == currentItem.GetPosition()) {
				//TODO Switch, olaj vagy ragacs.
				//currentItem.StepIn(robot);
			//}
		//}
		SkeletonUtility.printReturn("ValidateState", this);
	}
	/**
	 *Eldönti, hogy egy adott pont a pályán belül található vagy sem
	 * @param point a pont amelyre kíváncsiak vagyunk
	 *            - Akit vizsgálni kell az akadályokhoz viszonyítva.
	 * @author Bence
	 */
	private Boolean isOnTrack(Point point){
		//egy biztosan külsõ pont keresése
		Point kulso=new Point(0,0);
        for( Line i:track){
			if (i.x1<kulso.x)
				kulso.x=i.x1;
			if (i.x2<kulso.x)
				kulso.x=i.x1;
			if (i.y1<kulso.y)
				kulso.y=i.y1;
			if (i.y2<kulso.y)
				kulso.y=i.y1;
		}
        //biztosan ne a bal alsó sarok legyen a külsõ pont
        kulso.x-=5;
        kulso.y-=7;
        Line tmpline=new Line(kulso.x, kulso.y, point.x, point.y);
		/* megszámoljuk, hogy egy külsõ pont és az adott pont közötti szakasz
		* hány egyenest metsz. Ha páros, akkor pályán kívüli, ha páratlan pályán belüli 
		*/
		int metszesszam=0;
		for( Line i:track){
			
			metszes(i,tmpline);
		}
		if (metszesszam%2==0)
			return false;
		else
			return true;
	}
	/**
	 *Eldönti, hogy a két szakasz metszi-e egymást
	 * @param l1 az egyik szakasz
	 * @param l2 a mások szakasz
	 * @author Bence
	 */	
	private Boolean metszes(Line l1,Line l2){
		return false;
	}
	
	/**
	 *Megadja, hogy az elõzõ ugrásba áthaladt-e az ellenõrzõ ponton
	 *@param robot a megadott robotra ellenõrzi az áthaladás tényét.
	 * @author Bence
	 */	
	private Boolean IsCheckPointChecked(Robot robot){
		Line ugras= new Line(robot.position.x,robot.position.y,0,0);
		Boolean metsz=false;
		for(Line i : checkPoints){
		  	if (metszes(i,ugras))
		  		metsz=true;
		}
		return metsz;
	}
	
}
