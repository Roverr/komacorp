package Program.Core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;
import Program.Prototype.MyFileNotFoundException;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

public class Game {
	private Map GameMap;
	private int time;
	private int elapsedTime;

	/**
	 * Csinálok egy construktort amit a Program.java main metódusa hív meg Ez
	 * reprezentálja ha a menüben beállítottunk mindent és azt mondjuk h Game
	 * Szerintem kell még +1 változó h hány játékos legyen Ezzel tesztelõdik a
	 * LoadMap metódus is
	 * 
	 * Lehetséges hogy szét kell választani a játékosszámot a loadmaptól, ezt el
	 * kell dönteni
	 * 
	 * @author Barna
	 * @throws MyFileNotFoundException
	 */
	public Game(int seconds, String mapname, int numberOfPlayers)
			throws MyFileNotFoundException {
		SkeletonUtility.addClass(this, "dummyGame");
		SkeletonUtility.printCall("Game", this);
		GameMap = new Map();
		try {
			GameMap.loadMap(mapname, numberOfPlayers);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyFileNotFoundException();
		}
		this.time = seconds;
		elapsedTime = 0;
		cleanerId = 1;
		olajId = 1;
		ragacsId = 1;
		SkeletonUtility.printReturn("Game", this);
	}

	/**
	 * A játék elindításáért felelõs metódus
	 */
	public void startGame() {
		elapsedTime = 0;
		SkeletonUtility.printCall("StartGame", this);
		for (PlayerRobot r : GameMap.getRobots()) {
			r.modifySpeed(new Vector(0, 0));
		}
		SkeletonUtility.printReturn("StartGame", this);
	}

	/**
	 * A játék véget érésénél hívódik meg, illetve ha már nincs aktív játékos a
	 * pályán
	 */
	public String endResult; // Az eredmény, ez null egészen a játék végéig.

	public void endGame() {
		SkeletonUtility.printCall("EndGame", this);

		StringBuilder sb = new StringBuilder();
		// Egy stringbe fûzzük az eredményeket:
		for (String s : GameMap.getResult()) {
			sb.append(s);
			sb.append("\n");
		}

		endResult = sb.toString();

		SkeletonUtility.printReturn("EndGame", this);
	}	
		public static int cleanerId = 1;	
		public static int ragacsId = 1;	
		public static int olajId = 1;
		/**
		 * Körök szimulálását végzi
		 * @throws Exception 
		 * @throws IOException - Exception amit elkell kapni.
		 */
		public void run() throws Exception{
			if(elapsedTime < time){
				SkeletonUtility.printCall("Run", this);
				//TODO dropOlaj, és dropRagacsot innen meghívni!
				//Ellenõrzi az !összes! robot pozícióját
				GameMap.validateStates();
				
				int aliveCount = 0;
				//Update PlayerRobots (if alive ValidateState, if still alive Jump)
				for (PlayerRobot r : GameMap.getRobots()) {
					if(r.isAlive()){
						r.jump(GameMap);
						if(r.wantToDrop == 1){
							r.dropRagacs(GameMap);
						}else if(r.wantToDrop == 2){
							r.dropOlaj(GameMap);
						}
						aliveCount++;
					}
				}
				
				for (CleanerRobot cl : GameMap.getCleanerRobots()) {
					//if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot before jump: X:" + cl.getPosition().getX()+" Y:"+cl.getPosition().getY());
					cl.jump(GameMap);
					//if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot did jump: X:" + cl.getPosition().getX()+" Y:"+cl.getPosition().getY());
				}
				
				//Update MapItems (Olajok felszáradnak)
				if(!GameMap.getMapItems().isEmpty()) {
					MapItem mi = null;
					for(int i = 0; i < GameMap.getMapItems().size(); i++){
						mi = GameMap.getMapItems().get(i);
						mi.update();
						if(!mi.isAlive()){
							GameMap.removeMapItem(mi);
						}
					}
				}
				
				//Az eltelt idõ nõ.
				elapsedTime++;
				if(PrototypeUtility.allowDebug)System.out.println("time = " + elapsedTime + " < " + time);
				//új cleaner robot felvétele.
				//Mindíg 10 körönként történik, egyszerre 3 cleanerRobot van max a pályán.
				List<CleanerRobot> takker= GameMap.getCleanerRobots();
				if (takker.size()<3 && elapsedTime % 10 == 9){
					boolean ures = true;
					for(CleanerRobot r : takker){
						//TODO Check for PlayerRobots as well.
						if(r.getPosition().distance(new FloatPoint(0f,0f)) < 1f)
						  ures = false;
					}
					if (ures){
						
						CleanerRobot tmp= new CleanerRobot(GameMap);
						//TODO This position should depend on map (Start checkpoint felezõ?)
						tmp.setPosition(0,0);
						tmp.setSpeed(new Vector(1,1));
				    	GameMap.getCleanerRobots().add(tmp);
				    	PrototypeUtility.addClass(tmp, "szolga"+cleanerId);
				    	cleanerId++;
					}
				}

			/**
			 * Ez nem kell most prototípusba.
			 */
//			if(aliveCount <= 1) {
//				endGame();
//			}
			/**
			 *  :(
			 */
			endGame();
			}else{
				if(PrototypeUtility.allowDebug)System.out.println("Game Ended by now.");
				throw new Exception("EndOfGame!");
			}
			SkeletonUtility.printReturn("Run", this);
		}
		



	/**
	 * Meghatározza lenyomott billentyû alapján, hogy felhasználó mit szeretne
	 * csinálni, és meghívja a megfelelõ mûveleteket
	 * */
	public void userControl(char interact) {
		/**
		 * TODO kérdések 3) A robotnál a modifySpeed ugye még nincs kész? Mert
		 * olyasmi rémlik még megbeszélésekrõl, hogy billentyûnyomás hozzáad a
		 * módosító sebességvektorhoz, nem felülírja
		 */
		int numberOfRobots = GameMap.getRobots().size();

		/**
		 * Az elsõ robotra vonatkozó billentyûlenyomások w: fel s: le a: balra
		 * d: jobbra
		 */
		PlayerRobot playerOne = GameMap.getRobots().get(0);
		switch (interact) {
		// Fel
		case 'w':
			playerOne.modifySpeed(new Vector(0, 1));
			break;
		// Le
		case 's':
			playerOne.modifySpeed(new Vector(0, -1));
			break;
		// Balra
		case 'a':
			playerOne.modifySpeed(new Vector(1, 0));
			break;
		// Jobbra
		case 'd':
			playerOne.modifySpeed(new Vector(-1, 0));
			break;
		}

		/**
		 * Második robotra vonatkozó billentyûlenyomások tfgh, hasonló
		 * funkcióval mint elsõnél
		 */
		if (numberOfRobots >= 2) {
			PlayerRobot playerTwo = GameMap.getRobots().get(1);
			switch (interact) {
			// Fel
			case 't':
				playerTwo.modifySpeed(new Vector(0, 1));
				break;
			// Le
			case 'g':
				playerTwo.modifySpeed(new Vector(0, -1));
				break;
			// Balra
			case 'f':
				playerTwo.modifySpeed(new Vector(1, 0));
				break;
			// Jobbra
			case 'h':
				playerTwo.modifySpeed(new Vector(-1, 0));
				break;
			}
		}

		/** Harmadik robot (ha van), irányítása: ijkl */
		if (numberOfRobots == 3) {
			PlayerRobot playerThree = GameMap.getRobots().get(2);
			switch (interact) {
			// Fel
			case 'i':
				playerThree.modifySpeed(new Vector(0, 1));
				break;
			// Le
			case 'k':
				playerThree.modifySpeed(new Vector(0, -1));
				break;
			// Balra
			case 'j':
				playerThree.modifySpeed(new Vector(1, 0));
				break;
			// Jobbra
			case 'l':
				playerThree.modifySpeed(new Vector(-1, 0));
				break;
			}

			SkeletonUtility.printCall("UserControl", this);
			// ...//
			SkeletonUtility.printReturn("UserControl", this);
		}
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setMap(Map m) {
		GameMap = m;
	}

}
