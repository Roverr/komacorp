package Program.Core;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Program.Helpers.Line;
import Program.Helpers.Vector;
import Program.Prototype.MyFileNotFoundException;
import Program.Prototype.PrototypeUtility;
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
		PrototypeUtility.addClass(this, "GameMap");
		SkeletonUtility.addClass(this, "dummyMap");
		SkeletonUtility.printCall("create Map", this);

		checkPoints = new ArrayList<Line>();
		mapItems = new ArrayList<MapItem>();
		playerRobots = new ArrayList<PlayerRobot>();
		cleanerRobots = new ArrayList<CleanerRobot>();
		track = new ArrayList<Line>();
		SkeletonUtility.printReturn("create Map", this);
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
	 * Ez az�rt kell, hogy a men�ben kiv�lasztott robotokat be�ll�tsuk
	 * 
	 * @param robots
	 * @author Barna
	 * @throws Exception
	 */
	public void setPlayerRobots(List<PlayerRobot> robots) throws Exception {
		for (PlayerRobot r : robots) {
			this.addPlayerRobot(r.getName(), r.getPosition().x,
					r.getPosition().y);
		}
	}

	/**
	 * Ez meg az�rt, hogy a Protot�pusb�l el�rj�k a robotokat, �s tudjunk
	 * addolni a testhez
	 * 
	 * @throws Exception
	 */
	public void addPlayerRobot(String name, int x, int y) throws Exception {
		if (playerRobots.size() <= 3) {
			PlayerRobot robot = new PlayerRobot();
			PrototypeUtility.addClass(robot, name);
			robot.setName(name);
			robot.setPosition(x, y);
			this.playerRobots.add(robot);
		} else
			throw new Exception("Too Many Robots");
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
	 * Lehet 0 robottal is?!?!?!?!, ekkor csak a p�lya megn�z�se a c�l de mivel
	 * �llapotot nem t�rolunk, teh�t meg kell k�rdezni a felhaszn�l�t�l h h�ny
	 * robottal akarjuk megcsin�lni
	 * 
	 * @author Barna
	 * @param numberOfPlayers
	 * @throws MyFileNotFoundException
	 * @throws FileNotFoundException
	 */
	// Beolvassa a map le�r�s�t fileb�l
	// A file fel�p�t�se: Soronk�nt egy sz�m
	public void loadMap(String file, int numberOfPlayers)
			throws MyFileNotFoundException {
		SkeletonUtility.printCall("LoadMap(" + file + ")", this);
		/* Inicializ�l�s */
		track = new ArrayList<Line>();
		checkPoints = new ArrayList<Line>();

		File f = new File(System.getProperty("user.dir") + "\\" + file + ".txt");
		BufferedReader reader = null;
		// Ebben fognak t�rol�dni a sz�mok
		ArrayList<Integer> input = new ArrayList<Integer>();
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e2) {
			throw new MyFileNotFoundException();
		}
		String text = null;
		/* Beolvasunk mindent az input list�ba */
		try {
			while ((text = reader.readLine()) != null)
				input.add(Integer.parseInt(text));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* A bemenet feldolgoz�sa */
		// A file els� sz�ma a vonalak sz�ma, a m�sodik a checkpointok sz�ma
		Integer number_of_lines = input.get(0);
		Integer number_of_checkpoints = input.get(1);

		/* A vonalakat olvassa be */
		for (int i = 2; i < (number_of_lines * 4) + 2; i += 4) {
			int x1 = input.get(i);
			int y1 = input.get(i + 1);
			int x2 = input.get(i + 2);
			int y2 = input.get(i + 3);
			track.add(new Line(x1, y1, x2, y2));
		}

		/* A checkpointokat olvassa be */
		for (int i = (number_of_lines * 4) + 2; i < (number_of_checkpoints + number_of_lines) * 4; i += 4) {
			int x1 = input.get(i);
			int y1 = input.get(i + 1);
			int x2 = input.get(i + 2);
			int y2 = input.get(i + 3);
			checkPoints.add(new Line(x1, y1, x2, y2));
		}
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
	 * V�gign�zi az �sszes
	 * PlayerRobot-PlayerRobot,PlayerRobot-CleanerRobot,PlayerRobot-MapItem
	 * objektumokat, �tk�z�st, belel�p�st detekt�l CleanerRobot-CleanerRobot
	 * �tk�z�st is CleanerRobot-Mapitem �tk�z�s k�l�nleges eset, nem itt
	 * kezelj�k MapItem-Mapitem, nem kell kezelni
	 * 
	 * @param playerRobot
	 *            - Akit vizsg�lni kell az akad�lyokhoz viszony�tva.
	 * @throws IOException
	 */
	public void validateStates() throws IOException {
		SkeletonUtility.printCall("ValidateState", this);
		for (PlayerRobot probot : playerRobots) {

			if (!this.isOnTrack(probot.getPosition()))
				probot.die(this);
			for (PlayerRobot probotcompare : playerRobots) {
				if (!probot.equals(probotcompare)
						&& probot.getPosition().equals(
								probotcompare.getPosition())) {
					probot.collide(probotcompare, this, true);
					probotcompare.collide(probot, this, true);
				}
			}
			for (CleanerRobot crobot : cleanerRobots) {
				if (probot.getPosition().equals(crobot.getPosition())) {
					probot.collide(crobot, this, false);
					crobot.collide(probot, this, false);
				}
			}
			for (MapItem currentItem : mapItems) {
				if (currentItem.getPosition().equals(probot.getPosition())) {
					currentItem.stepIn(probot);
				}
			}

		}

		for (CleanerRobot crobot : cleanerRobots) {
			for (CleanerRobot crobotcompare : cleanerRobots) {
				if (!crobot.equals(crobotcompare)
						&& crobot.getPosition().equals(
								crobotcompare.getPosition())) {
					crobot.collide(crobotcompare, this, true);
					crobotcompare.collide(crobot, this, true);
				}
			}
		}
		SkeletonUtility.printReturn("ValidateState", this);
	}

	/**
	 * Eld�nti, hogy egy adott pont a p�ly�n bel�l tal�lhat� vagy sem
	 * 
	 * @param point
	 *            a pont amelyre k�v�ncsiak vagyunk - Akit vizsg�lni kell az
	 *            akad�lyokhoz viszony�tva.
	 * @author Bence
	 */
	private Boolean isOnTrack(Point point) {
		// egy biztosan k�ls� pont keres�se
		Point kulso = new Point(0, 0);
		for (Line i : track) {
			if (i.x1 < kulso.x)
				kulso.x = i.x1;
			if (i.x2 < kulso.x)
				kulso.x = i.x1;
			if (i.y1 < kulso.y)
				kulso.y = i.y1;
			if (i.y2 < kulso.y)
				kulso.y = i.y1;
		}
		// biztosan ne a bal als� sarok legyen a k�ls� pont
		kulso.x -= 5;
		kulso.y -= 7;
		Line tmpline = new Line(kulso.x, kulso.y, point.x, point.y);
		/*
		 * megsz�moljuk, hogy egy k�ls� pont �s az adott pont k�z�tti szakasz
		 * h�ny egyenest metsz. Ha p�ros, akkor p�ly�n k�v�li, ha p�ratlan
		 * p�ly�n bel�li
		 */
		int metszesszam = 0;
		for (Line i : track) {

			// Van metsz�s
			if (i.intersect(tmpline)) {
				// Addig toljuk lefel� a k�ls� pontot, am�g a k�ls� pont �s a
				// vizsg�lt pont
				// k�z�tti szakaszon nem lesz egy p�lya t�r�s pont sem
				Vector v = new Vector(i.getX1(), i.getY1());
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1() - 1);
				v = new Vector(i.getX2(), i.getY2());
				// Ha a vonal m�sik v�g�re l�gna r�, akkor biztosan bennt van a
				// pont,
				// �gy nem romlik a p�ly�nmarad�s felt�tele
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1() - 1);
				if (i.intersect(tmpline))
					metszesszam++;
			}
		}
		if (metszesszam % 2 == 0)
			return false;
		else
			return true;
	}

	/**
	 * Megadja, hogy az el�z� ugr�sba �thaladt-e az ellen�rz� ponton.
	 * K�zvetlen�l ugr�s ut�n kell megh�vni a helyes m�k�d�shez
	 *
	 * @param robot
	 *            a megadott robotra ellen�rzi az �thalad�s t�ny�t.
	 * @param beforejump
	 *            - az ugr�s el�tti poz�ci�
	 * @author Bence
	 */
	public Boolean isCheckPointChecked(Robot robot, Point beforejump) {
		// ugr�s vonal�nak megad�sa
		// TODO jav�tani az ugr�s vonal�ra
		Line ugras = new Line(robot.position.x, robot.position.y, beforejump.x,
				beforejump.y);
		Boolean metsz = false;
		for (Line i : checkPoints) {
			if (i.intersect(ugras))
				metsz = true;
		}
		return metsz;
	}

	/**
	 * Visszadja a takar�t� robotok list�j�t
	 * 
	 * @return
	 */
	public List<CleanerRobot> getCleanerRobots() {
		return cleanerRobots;
	}

	/**
	 * Be�ll�tja a takar�t� robotok list�j�t
	 * 
	 * @param cleanerRobots
	 */
	public void setCleanerRobots(List<CleanerRobot> cleanerRobots) {
		this.cleanerRobots = cleanerRobots;
	}

}
