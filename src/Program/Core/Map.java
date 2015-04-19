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
 * Olyan oszt�ly, ami a k�s�bbi p�ly�t fogja szimul�lni a skeletonban val�
 * tesztel�sn�l.
 * 
 * @author Rover
 *
 */
public class Map implements Serializable {

	/**
	 * Szerializ�l�shoz kell
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
	private List<PlayerRobot> playerRobots;
	private List<CleanerRobot> cleanerRobots;
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
		playerRobots = new ArrayList<PlayerRobot>();
		track = new ArrayList<Line>();
		SkeletonUtility.printReturn("create Map", this);
	}
	
	/**
	 * Be�ll�tja a tesztp�ly�t (k�r alak� (8sz�g), k�t checkpoint)
	 * amit kiszerializ�l fileba
	 * TODO delete a v�glegesben?
	 * @author Hunor
	 */
	public void createTestMap(String file_name){
		/*A "k�r" megad�sa"*/
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
	 * Felvesz az akad�lyok list�j�ra egy �jat.
	 * 
	 * @param item
	 *            - Akad�ly amit a list�ra kell felvenni.
	 */
	public void addMapItem(MapItem item) {
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
		for (PlayerRobot r : playerRobots) {
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
	public List<PlayerRobot> getRobots() {
		SkeletonUtility.printCall("GetRobots", this);
		SkeletonUtility.printReturn("GetRobots", this);
		return playerRobots;
	}
	
	
	/**
	 * Visszaadja az akad�lyok list�j�t.
	 * 
	 * @return - Lista az akad�lyokr�l.
	 */
	public List<MapItem> getMapItems() {
		
		return mapItems;
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
	 * @param playerRobot
	 *            - Akit vizsg�lni kell az akad�lyokhoz viszony�tva.
	 * @throws IOException 
	 */
	public void validateState(PlayerRobot playerRobot) throws IOException {
		SkeletonUtility.printCall("ValidateState", this);
		/** TODO NEED MORE STABLE LOGIC **/
		playerRobot.getPosition();
		//for (int i = 0; i < mapItems.size(); i++) {

			//MapItem currentItem = mapItems.get(i);
			if(SkeletonUtility.yesOrNoQuestion("Kiest�l a p�ly�r�l?")){
				playerRobot.die();
			}
		
			else if(SkeletonUtility.yesOrNoQuestion("Belel�pett a robot egy ragacsba?")){
				//dummyRagacsForSkeleton.stepIn(playerRobot);
				
			}
			else if(SkeletonUtility.yesOrNoQuestion("Belel�pett a robot egy olajba?")){
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
	 *Eld�nti, hogy egy adott pont a p�ly�n bel�l tal�lhat� vagy sem
	 * @param point a pont amelyre k�v�ncsiak vagyunk
	 *            - Akit vizsg�lni kell az akad�lyokhoz viszony�tva.
	 * @author Bence
	 */
	private Boolean isOnTrack(Point point){
		//egy biztosan k�ls� pont keres�se
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
        //biztosan ne a bal als� sarok legyen a k�ls� pont
        kulso.x-=5;
        kulso.y-=7;
        Line tmpline=new Line(kulso.x, kulso.y, point.x, point.y);
		/* megsz�moljuk, hogy egy k�ls� pont �s az adott pont k�z�tti szakasz
		* h�ny egyenest metsz. Ha p�ros, akkor p�ly�n k�v�li, ha p�ratlan p�ly�n bel�li 
		*/
		int metszesszam=0;
		for( Line i:track){
			
			//Van metsz�s
			if(i.intersect(tmpline))
			{
				//Addig toljuk lefel� a k�ls� pontot, am�g a k�ls� pont �s a vizsg�lt pont
				//k�z�tti szakaszon nem lesz egy p�lya t�r�s pont sem
				Vector v=new Vector(i.getX1(),i.getY1());
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1()-1);
				 v=new Vector(i.getX2(),i.getY2());
				 //Ha a vonal m�sik v�g�re l�gna r�, akkor biztosan bennt van a pont,
				 //�gy nem romlik a p�ly�nmarad�s felt�tele
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
	 *Megadja, hogy az el�z� ugr�sba �thaladt-e az ellen�rz� ponton.
	 *K�zvetlen�l ugr�s ut�n kell megh�vni a helyes m�k�d�shez
	 *@param robot a megadott robotra ellen�rzi az �thalad�s t�ny�t.
	 *@param beforejump -  az ugr�s el�tti poz�ci�
	 * @author Bence
	 */	
	private Boolean isCheckPointChecked(Robot robot,Point beforejump){
		//ugr�s vonal�nak megad�sa
		//TODO jav�tani az ugr�s vonal�ra
		Line ugras= new Line(robot.position.x,robot.position.y,beforejump.x,beforejump.y);
		Boolean metsz=false;
		for(Line i : checkPoints){
		  	if (i.intersect(ugras))
		  		metsz=true;
		}
		return metsz;
	}
	
	/**
	 * Visszadja a takar�t� robotok list�j�t
	 * @return
	 */
	public List<CleanerRobot> getCleanerRobots() {
		return cleanerRobots;
	}
	
	/**
	 * Be�ll�tja a takar�t� robotok list�j�t
	 * @param cleanerRobots
	 */
	public void setCleanerRobots(List<CleanerRobot> cleanerRobots) {
		this.cleanerRobots = cleanerRobots;
	}
	
}
