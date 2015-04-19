package Program.Core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import Program.Helpers.Line;
import Program.Helpers.Vector;
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
	 * Szerializáláshoz kell
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
	private List<CleanerRobot> cleanerRobots;
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
	 * Beállítja a tesztpályát (kör alakú (8szög), két checkpoint)
	 * amit kiszerializál fileba
	 * TODO delete a véglegesben?
	 * @author Hunor
	 */
	public void createTestMap(String file_name){
		/*A "kör" megadása"*/
		track.add(new Line(100, 50, 100, 70));
		track.add(new Line(100, 70, 80, 90));
		track.add(new Line(80, 90, 60, 90));
		track.add(new Line(60, 90, 40, 70));
		track.add(new Line(40, 70, 40, 50));
		track.add(new Line(40, 50, 60, 30));
		track.add(new Line(60, 30, 80, 30));
		track.add(new Line(80, 30, 100, 50));
		track.add(new Line(80, 50, 80, 70));
		track.add(new Line(80, 70, 60, 70));
		track.add(new Line(60, 70, 60, 50));
		track.add(new Line(60, 50, 80, 50));
	
		/*Checkpointok*/
		checkPoints.add(new Line(80, 50, 100, 50));
		checkPoints.add(new Line(60, 70, 60, 90));
		
		try {
			FileOutputStream outFile = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(outFile);
			out.writeObject(this);
			out.close();
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Felvesz az akadályok listájára egy újat.
	 * 
	 * @param item
	 *            - Akadály amit a listára kell felvenni.
	 */
	public void addMapItem(MapItem item) {
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
	 * Visszaadja az akadályok listáját.
	 * 
	 * @return - Lista az akadályokról.
	 */
	public List<MapItem> getMapItems() {
		
		return mapItems;
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
		//Beolvassa a mapot
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			try {
				Map temp = (Map)in.readObject();
				this.track = temp.track;
				this.checkPoints = temp.checkPoints;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
			
			//Van metszés
			if(i.intersect(tmpline))
			{
				//Addig toljuk lefelé a külsõ pontot, amíg a külsõ pont és a vizsgált pont
				//közötti szakaszon nem lesz egy pálya törés pont sem
				Vector v=new Vector(i.getX1(),i.getY1());
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1()-1);
				 v=new Vector(i.getX2(),i.getY2());
				 //Ha a vonal másik végére lógna rá, akkor biztosan bennt van a pont,
				 //így nem romlik a pályánmaradás feltétele
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1()-1);
				if (i.intersect(tmpline))
					metszesszam++;
			}
		}
		if (metszesszam%2==0)
			return false;
		else
			return true;
	}

	
	/**
	 *Megadja, hogy az elõzõ ugrásba áthaladt-e az ellenõrzõ ponton.
	 *Közvetlenül ugrás után kell meghívni a helyes mûködéshez
	 *@param robot a megadott robotra ellenõrzi az áthaladás tényét.
	 *@param beforejump -  az ugrás elõtti pozíció
	 * @author Bence
	 */	
	private Boolean isCheckPointChecked(Robot robot,Point beforejump){
		//ugrás vonalának megadása
		//TODO javítani az ugrás vonalára
		Line ugras= new Line(robot.position.x,robot.position.y,beforejump.x,beforejump.y);
		Boolean metsz=false;
		for(Line i : checkPoints){
		  	if (i.intersect(ugras))
		  		metsz=true;
		}
		return metsz;
	}
	
	/**
	 * Visszadja a takarító robotok listáját
	 * @return
	 */
	public List<CleanerRobot> getCleanerRobots() {
		return cleanerRobots;
	}
	
	/**
	 * Beállítja a takarító robotok listáját
	 * @param cleanerRobots
	 */
	public void setCleanerRobots(List<CleanerRobot> cleanerRobots) {
		this.cleanerRobots = cleanerRobots;
	}
	
}
