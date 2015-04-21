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
	 * Ez azért kell, hogy a menüben kiválasztott robotokat beállítsuk
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
	 * Ez meg azért, hogy a Prototípusból elérjük a robotokat, és tudjunk
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
	 * Visszadja az eredményt
	 * 
	 * @return - Eredmény lista a robotokkal.
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
	 * Lehet 0 robottal is?!?!?!?!, ekkor csak a pálya megnézése a cél de mivel
	 * állapotot nem tárolunk, tehát meg kell kérdezni a felhasználótól h hány
	 * robottal akarjuk megcsinálni
	 * 
	 * @author Barna
	 * @param numberOfPlayers
	 * @throws MyFileNotFoundException
	 * @throws FileNotFoundException
	 */
	// Beolvassa a map leírását fileból
	// A file felépítése: Soronként egy szám
	public void loadMap(String file, int numberOfPlayers)
			throws MyFileNotFoundException {
		SkeletonUtility.printCall("LoadMap(" + file + ")", this);
		/* Inicializálás */
		track = new ArrayList<Line>();
		checkPoints = new ArrayList<Line>();

		File f = new File(System.getProperty("user.dir") + "\\" + file + ".txt");
		BufferedReader reader = null;
		// Ebben fognak tárolódni a számok
		ArrayList<Integer> input = new ArrayList<Integer>();
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e2) {
			throw new MyFileNotFoundException();
		}
		String text = null;
		/* Beolvasunk mindent az input listába */
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

		/* A bemenet feldolgozása */
		// A file elsõ száma a vonalak száma, a második a checkpointok száma
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
	 * Végignézi az összes
	 * PlayerRobot-PlayerRobot,PlayerRobot-CleanerRobot,PlayerRobot-MapItem
	 * objektumokat, ütközést, belelépést detektál CleanerRobot-CleanerRobot
	 * ütközést is CleanerRobot-Mapitem ütközés különleges eset, nem itt
	 * kezeljük MapItem-Mapitem, nem kell kezelni
	 * 
	 * @param playerRobot
	 *            - Akit vizsgálni kell az akadályokhoz viszonyítva.
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
	 * Eldönti, hogy egy adott pont a pályán belül található vagy sem
	 * 
	 * @param point
	 *            a pont amelyre kíváncsiak vagyunk - Akit vizsgálni kell az
	 *            akadályokhoz viszonyítva.
	 * @author Bence
	 */
	private Boolean isOnTrack(Point point) {
		// egy biztosan külsõ pont keresése
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
		// biztosan ne a bal alsó sarok legyen a külsõ pont
		kulso.x -= 5;
		kulso.y -= 7;
		Line tmpline = new Line(kulso.x, kulso.y, point.x, point.y);
		/*
		 * megszámoljuk, hogy egy külsõ pont és az adott pont közötti szakasz
		 * hány egyenest metsz. Ha páros, akkor pályán kívüli, ha páratlan
		 * pályán belüli
		 */
		int metszesszam = 0;
		for (Line i : track) {

			// Van metszés
			if (i.intersect(tmpline)) {
				// Addig toljuk lefelé a külsõ pontot, amíg a külsõ pont és a
				// vizsgált pont
				// közötti szakaszon nem lesz egy pálya törés pont sem
				Vector v = new Vector(i.getX1(), i.getY1());
				while (tmpline.isOnLine(v))
					tmpline.setY1(tmpline.getY1() - 1);
				v = new Vector(i.getX2(), i.getY2());
				// Ha a vonal másik végére lógna rá, akkor biztosan bennt van a
				// pont,
				// így nem romlik a pályánmaradás feltétele
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
	 * Megadja, hogy az elõzõ ugrásba áthaladt-e az ellenõrzõ ponton.
	 * Közvetlenül ugrás után kell meghívni a helyes mûködéshez
	 *
	 * @param robot
	 *            a megadott robotra ellenõrzi az áthaladás tényét.
	 * @param beforejump
	 *            - az ugrás elõtti pozíció
	 * @author Bence
	 */
	public Boolean isCheckPointChecked(Robot robot, Point beforejump) {
		// ugrás vonalának megadása
		// TODO javítani az ugrás vonalára
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
	 * Visszadja a takarító robotok listáját
	 * 
	 * @return
	 */
	public List<CleanerRobot> getCleanerRobots() {
		return cleanerRobots;
	}

	/**
	 * Beállítja a takarító robotok listáját
	 * 
	 * @param cleanerRobots
	 */
	public void setCleanerRobots(List<CleanerRobot> cleanerRobots) {
		this.cleanerRobots = cleanerRobots;
	}

}
