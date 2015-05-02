package Program.Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.Timer;

import Program.Helpers.FloatPoint;
import Program.Helpers.Line;
import Program.Helpers.Vector;
import Program.Prototype.MyFileNotFoundException;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

public class Game {
	private Map GameMap;
	private int time;
	private int elapsedTime;
	private MainWindow mWindow;
	private Timer t;

	/**
	 * Konstruktor játék indításához
	 * @param seconds - Ennyi ideig tart egy menet
	 * @param mapname - A betöltendő pálya neve
	 * @param numberOfPlayer - Játékosok száma
	 * @param mWindow - Referencia a játék képernyőjére
	 * @author Barna, Hunor
	 * @throws MyFileNotFoundException
	 */
	public Game(int seconds, String mapname, int numberOfPlayers, MainWindow mWindow) throws MyFileNotFoundException 
	{
		SkeletonUtility.addClass(this, "dummyGame");
		SkeletonUtility.printCall("Game", this);
		
		/*Pálya betöltése*/
		GameMap = new Map();
		try {
			GameMap.loadMap(mapname, numberOfPlayers);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MyFileNotFoundException();
		}

		/*Incializálás*/
		this.time = seconds;
		elapsedTime = 0;
		cleanerId = 1;
		olajId = 1;
		ragacsId = 1;
		this.mWindow = mWindow;
		SkeletonUtility.printReturn("Game", this);
	}

	/**
	 * A játék elindításáért felelõs metódus
	 */
	public void startGame() {
		elapsedTime = 0;
		SkeletonUtility.printCall("StartGame", this);
		for (PlayerRobot r : GameMap.getRobots()) 
			r.modifySpeed(new Vector(0, 0));
		SkeletonUtility.printReturn("StartGame", this);
		
		/*Elindítja az időzítő motort (külön szálon)
		 * Másodpercenként fog a run függvény meghívódni*/
		final Game rThis = this; //anonim osztály miatt kell
		t = new Timer(50, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					rThis.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.setRepeats(true);
		t.start();
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
		
		t.stop();
		mWindow.showMenu();
	}

	public static int cleanerId = 1;
	public static int ragacsId = 1;
	public static int olajId = 1;

	/**
	 * Körök szimulálását végzi
	 * @throws Exception - Exception amit elkell kapni.
	 */
	public void run() throws Exception {
				/*Ha még nem járt le a játék időkorlátja, léptet*/
				if (elapsedTime < time) {
					System.out.println("Tick " + elapsedTime);
					SkeletonUtility.printCall("Run", this);
					// TODO dropOlaj, és dropRagacsot innen meghívni!
					// Ellenõrzi az !összes! robot pozícióját
					GameMap.validateStates();
		
					int aliveCount = 0;
					// Update PlayerRobots (if alive ValidateState, if still alive Jump)
					for (PlayerRobot r : GameMap.getRobots()) {
						if (r.isAlive()) {
							r.jump(GameMap);
							if (r.wantToDrop == 1) {
								r.dropRagacs(GameMap);
							} else if (r.wantToDrop == 2) {
								r.dropOlaj(GameMap);
							}
							aliveCount++;
						}
					}
		
					for (CleanerRobot cl : GameMap.getCleanerRobots()) {
						// if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot before jump: X:"
						// + cl.getPosition().getX()+" Y:"+cl.getPosition().getY());
						cl.jump(GameMap);
						// if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot did jump: X:"
						// + cl.getPosition().getX()+" Y:"+cl.getPosition().getY());
					}
		
					// Update MapItems (Olajok felszáradnak)
					if (!GameMap.getMapItems().isEmpty()) {
						MapItem mi = null;
						for (int i = 0; i < GameMap.getMapItems().size(); i++) {
							mi = GameMap.getMapItems().get(i);
							mi.update();
							if (!mi.isAlive()) {
								GameMap.removeMapItem(mi);
							}
						}
					}
		
					// Az eltelt idõ nõ.
					elapsedTime++;
					if (PrototypeUtility.allowDebug)
						System.out.println("time = " + elapsedTime + " < " + time);
					// új cleaner robot felvétele.
					// Mindíg 249 körönként történik, egyszerre 3 cleanerRobot van max a
					// pályán.
					List<CleanerRobot> takker = GameMap.getCleanerRobots();
					if (takker.size() < 3 && elapsedTime % 250 == 249) {
						boolean ures = true;
						for (CleanerRobot r : takker) {
							// TODO Check for PlayerRobots as well.
							if (r.getPosition().distance(new FloatPoint(0f, 0f)) < 1f)
								ures = false;
						}
						if (ures) {
		
							CleanerRobot tmp = new CleanerRobot(new FloatPoint(0f, 0f),
									GameMap);
							//Startvonal közepére teszi
							Line start = GameMap.getStartLine();
							tmp.setPosition((start.getX1() + start.getX2()) / 2, 
											(start.getY1() + start.getY2()) / 2);
							tmp.setSpeed(new Vector(1, 1));
							GameMap.getCleanerRobots().add(tmp);
							PrototypeUtility.addClass(tmp, "szolga" + cleanerId);
							cleanerId++;
						}
					}
		
					/*Ha már csak 1 maradt, vége a játéknak*/
					if(aliveCount <= 1) 
						endGame();
				} else {
					//Letelt az idő, vége a játéknak
					endGame();
					if (PrototypeUtility.allowDebug)
						System.out.println("Game Ended by now.");
				}
				SkeletonUtility.printReturn("Run", this);
				
				/*Rendereli a pályát*/
				mWindow.showGame(GameMap);
	}

	/**
	 * Meghatározza lenyomott billentyû alapján, hogy felhasználó mit szeretne
	 * csinálni, és meghívja a megfelelõ mûveleteket
	 * @author Hunor
	 * */
	public void userControl(char interact) {
		int numberOfRobots = GameMap.getRobots().size();

		/**
		 * Az elsõ robotra vonatkozó billentyûlenyomások w: fel s: le a: balra
		 * d: jobbra
		 */
		PlayerRobot playerOne = GameMap.getRobots().get(0);
		switch (interact) {
		// Fel
		case 'w':
			playerOne.modifySpeed(new Vector(0, -1));
			break;
		// Le
		case 's':
			playerOne.modifySpeed(new Vector(0, 1));
			break;
		// Balra
		case 'a':
			playerOne.modifySpeed(new Vector(-1, 0));
			break;
		// Jobbra
		case 'd':
			playerOne.modifySpeed(new Vector(1, 0));
			break;
		//Olajdobás
		case 'q':
			playerOne.dropOlaj(GameMap);
			break;
		//Ragacsdobás
		case 'e':
			playerOne.dropRagacs(GameMap);
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
				playerTwo.modifySpeed(new Vector(0, -1));
				break;
			// Le
			case 'g':
				playerTwo.modifySpeed(new Vector(0, 1));
				break;
			// Balra
			case 'f':
				playerTwo.modifySpeed(new Vector(-1, 0));
				break;
			// Jobbra
			case 'h':
				playerTwo.modifySpeed(new Vector(1, 0));
				break;
			//Olajdobás
			case 'r':
				playerTwo.dropOlaj(GameMap);
				break;
			//Ragacsdobás
			case 'z':
				playerTwo.dropRagacs(GameMap);
				break;
			}
		}

		/** Harmadik robot (ha van), irányítása: ijkl */
		if (numberOfRobots == 3) {
			PlayerRobot playerThree = GameMap.getRobots().get(2);
			switch (interact) {
			// Fel
			case 'i':
				playerThree.modifySpeed(new Vector(0, -1));
				break;
			// Le
			case 'k':
				playerThree.modifySpeed(new Vector(0, 1));
				break;
			// Balra
			case 'j':
				playerThree.modifySpeed(new Vector(-1, 0));
				break;
			// Jobbra
			case 'l':
				playerThree.modifySpeed(new Vector(1, 0));
				break;
			//Olajdobás
			case 'u':
				playerThree.dropOlaj(GameMap);
				break;
			//Ragacsdobás
			case 'o':
				playerThree.dropRagacs(GameMap);
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

	/**
	 * Visszaadja a map-ot a játéknak
	 * @author Hunor
	 * @return Map - A pálya, amin a játék folyik.
	 */
	public Map getMap() {
		return GameMap;
	}

}
