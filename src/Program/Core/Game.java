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
	 * Csin�lok egy construktort amit a Program.java main met�dusa h�v meg Ez
	 * reprezent�lja ha a men�ben be�ll�tottunk mindent �s azt mondjuk h Game
	 * Szerintem kell m�g +1 v�ltoz� h h�ny j�t�kos legyen Ezzel tesztel�dik a
	 * LoadMap met�dus is
	 * 
	 * Lehets�ges hogy sz�t kell v�lasztani a j�t�kossz�mot a loadmapt�l, ezt el
	 * kell d�nteni
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
	 * A j�t�k elind�t�s��rt felel�s met�dus
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
	 * A j�t�k v�get �r�s�n�l h�v�dik meg, illetve ha m�r nincs akt�v j�t�kos a
	 * p�ly�n
	 */
	public String endResult; // Az eredm�ny, ez null eg�szen a j�t�k v�g�ig.

	public void endGame() {
		SkeletonUtility.printCall("EndGame", this);

		StringBuilder sb = new StringBuilder();
		// Egy stringbe f�zz�k az eredm�nyeket:
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
		 * K�r�k szimul�l�s�t v�gzi
		 * @throws Exception 
		 * @throws IOException - Exception amit elkell kapni.
		 */
		public void run() throws Exception{
			if(elapsedTime < time){
				SkeletonUtility.printCall("Run", this);
				//TODO dropOlaj, �s dropRagacsot innen megh�vni!
				//Ellen�rzi az !�sszes! robot poz�ci�j�t
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
				
				//Update MapItems (Olajok felsz�radnak)
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
				
				//Az eltelt id� n�.
				elapsedTime++;
				if(PrototypeUtility.allowDebug)System.out.println("time = " + elapsedTime + " < " + time);
				//�j cleaner robot felv�tele.
				//Mind�g 10 k�r�nk�nt t�rt�nik, egyszerre 3 cleanerRobot van max a p�ly�n.
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
						//TODO This position should depend on map (Start checkpoint felez�?)
						tmp.setPosition(0,0);
						tmp.setSpeed(new Vector(1,1));
				    	GameMap.getCleanerRobots().add(tmp);
				    	PrototypeUtility.addClass(tmp, "szolga"+cleanerId);
				    	cleanerId++;
					}
				}

			/**
			 * Ez nem kell most protot�pusba.
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
	 * Meghat�rozza lenyomott billenty� alapj�n, hogy felhaszn�l� mit szeretne
	 * csin�lni, �s megh�vja a megfelel� m�veleteket
	 * */
	public void userControl(char interact) {
		/**
		 * TODO k�rd�sek 3) A robotn�l a modifySpeed ugye m�g nincs k�sz? Mert
		 * olyasmi r�mlik m�g megbesz�l�sekr�l, hogy billenty�nyom�s hozz�ad a
		 * m�dos�t� sebess�gvektorhoz, nem fel�l�rja
		 */
		int numberOfRobots = GameMap.getRobots().size();

		/**
		 * Az els� robotra vonatkoz� billenty�lenyom�sok w: fel s: le a: balra
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
		 * M�sodik robotra vonatkoz� billenty�lenyom�sok tfgh, hasonl�
		 * funkci�val mint els�n�l
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

		/** Harmadik robot (ha van), ir�ny�t�sa: ijkl */
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
