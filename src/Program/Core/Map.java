package Program.Core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Program;
import Program.Helpers.FloatPoint;
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
			this.addPlayerRobot(r.getName(), r.getPosition().getX(), r
					.getPosition().getY());
		}
	}

	/**
	 * Ez meg azért, hogy a Prototípusból elérjük a robotokat, és tudjunk
	 * addolni a testhez
	 * 
	 * @throws Exception
	 */
	public void addPlayerRobot(String name, float x, float y) throws Exception {
		if (playerRobots.size() < 3) {
			PlayerRobot robot = new PlayerRobot();
			PrototypeUtility.addClass(robot, name);
			robot.setName(name);
			robot.setPosition(x, y);
			robot.setCheckpointNumber(checkPoints.size());
			this.playerRobots.add(robot);
		} else
			throw new Exception("Too Many Robots!");
	}

	/**
	 * Visszadja az eredményt
	 * 
	 * @return - Eredmény lista a robotokkal.
	 */
	public ArrayList<String> getResult() {
		//SkeletonUtility.printCall("GetResult", this);
		ArrayList<String> temp = new ArrayList<String>();
		for (PlayerRobot r : playerRobots) {
			String kiesett = "él";
			if (!r.isAlive()) {
				kiesett = "KIESETT";
			}
			temp.add(r.name + ":" + r.distance +":"+ kiesett);
		}
		//SkeletonUtility.printReturn("GetResult", this);
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
	 * Beolvassa a map leírását fileból
	 * @param file - Amibõl belehet olvasni a mappot.
	 * @author Barna, Hunor
	 * @param numberOfPlayers
	 * @throws MyFileNotFoundException
	 */
	public void loadMap(String file, int numberOfPlayers, ArrayList<String> robotNames) throws MyFileNotFoundException {
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
			track.add(new Line(x1, x2, y1, y2));
		}

		/* A checkpointokat olvassa be */
		for (int i = (number_of_lines * 4) + 2; i < (number_of_checkpoints + number_of_lines) * 4; i += 4) {
			int x1 = input.get(i);
			int y1 = input.get(i + 1);
			int x2 = input.get(i + 2);
			int y2 = input.get(i + 3);
			checkPoints.add(new Line(x1, x2, y1, y2));
		}
		
		/*Lerakja a robotokat a startvonalra*/
			/*A nulladik eleme a checkpointoknak a startvonal */
			Line start = checkPoints.get(0);
			//Első játékos pályára tétele (ha van (mivel a menü nem kötelez a létrehozására))
			if (numberOfPlayers >= 1)
				try {
					//Startvonal balszélére helyezi
					addPlayerRobot(robotNames.get(0), start.getX1(), start.getY1());
				} catch (Exception e) {
					e.printStackTrace();
				}
			//Második játékos pályára tétele (ha van)
			if (numberOfPlayers >= 2)
				try {
					//Startvonal közepére helyezi
					addPlayerRobot(robotNames.get(1), 
									(start.getX1() + start.getX2())/2,
									(start.getY1() + start.getY2()) / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			//Harmadik játékos pályára tétele (ha van)
			if (numberOfPlayers == 3)
				try {
					//Startvonal jobbszélére helyezi
					addPlayerRobot(robotNames.get(2), start.getX2(), start.getY2());
				} catch (Exception e) {
					e.printStackTrace();
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
	 * Debugoláshoz a páyla ívét kirajzolja
	@author Hunor
	 */
	public void drawDebug(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(new Color(255, 0, 0));
		for (Line l : track)
			g2.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
		
		for (Line l : checkPoints)
			g2.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
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
	public void validateStates() {
		SkeletonUtility.printCall("ValidateStates", this);
		// TODO Checkpointon keresztülmenés (PlayerRobotban változtat valamit).
		for (PlayerRobot probot : playerRobots) {

			if (!this.isOnTrack(probot.getPosition()))
				probot.die(this);
            this.isCheckPointChecked(probot);
			
			for (PlayerRobot probotcompare : playerRobots) {
				if (!probot.equals(probotcompare)
						&& probotcompare.getPosition().distance(
								probot.getPosition()) < 1f) {
					probot.collide(probotcompare, this, true);
					
				}
			}
			for (CleanerRobot crobot : cleanerRobots) {
				if (crobot.getPosition().distance(probot.getPosition()) < 1f) {
					probot.collide(crobot, this, false);
					
				}
			}
			for (MapItem currentItem : mapItems) {
				if (currentItem.getPosition().distance(probot.getPosition()) < 1f) {
					currentItem.stepIn(probot);
				}
			}

		}

		for (CleanerRobot crobot : cleanerRobots) {
			for (CleanerRobot crobotcompare : cleanerRobots) {
				if (!crobot.equals(crobotcompare)
						&& crobot.getPosition().distance(
								crobotcompare.getPosition()) < 1f) {
					crobot.collide(crobotcompare, this, true);
				}
			}
			for (PlayerRobot probot : playerRobots) {
				if (probot.getPosition().distance(crobot.getPosition()) < 1f) {
					crobot.collide(probot, this, false);
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
	private Boolean isOnTrack(FloatPoint point) {
		// egy biztosan külsõ pont keresése
		FloatPoint kulso = new FloatPoint(0, 0);
		for (Line i : track) {
			if (i.x1 < kulso.getX())
				kulso.setX(i.x1);
			if (i.x2 < kulso.getX())
				kulso.setX(i.x1);
			if (i.y1 < kulso.getY())
				kulso.setY(i.y1);
			if (i.y2 < kulso.getY())
				kulso.setY(i.y1);
		}
		// biztosan ne a bal alsó sarok legyen a külsõ pont
		kulso.setX(kulso.getX()-5); 
		kulso.setY(kulso.getY()-7);
		Line tmpline = new Line(kulso.getX(), point.getX(),  kulso.getY(), point.getY());
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
	public Boolean isCheckPointChecked(PlayerRobot robot) {
		// ugrás vonalának megadása
		FloatPoint beforejump=new FloatPoint(robot.getPosition().getX()-robot.getSpeed().getX(),robot.getPosition().getY()-robot.getSpeed().getY());
		Line ugras = new Line(robot.position.getX(),beforejump.getX(), robot.position.getY(),beforejump.getY());
		Boolean metsz = false;
		for (int i=0;i<checkPoints.size();i++) {
			if (checkPoints.get(i).intersect(ugras))
			{	
				metsz = true;
				robot.CheckCheckpoint(i);
			}
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
	
	/**
	 * Visszaadja a startvonalat
	 * @author Hunor
	 */
	public Line getStartLine(){
		return checkPoints.get(0);
	}

}
