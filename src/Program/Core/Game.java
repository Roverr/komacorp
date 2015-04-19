package Program.Core;


import java.io.IOException;
import java.util.List;

import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

public class Game {
		private Map GameMap;
		private int time;
		private int elapsedTime;
		
		/**
		 * Csinálok egy construktort amit a Program.java main metódusa hív meg
		 * Ez reprezentálja ha a menüben beállítottunk mindent és azt mondjuk h Game
		 * Szerintem kell még +1 változó h hány játékos legyen
		 * Ezzel tesztelõdik a LoadMap metódus is
		 * 
		 * Lehetséges hogy szét kell választani a játékosszámot a loadmaptól, 
		 * ezt el kell dönteni
		 * @author Barna
		 */
		public Game(int seconds, String mapname, int numberOfPlayers){
			SkeletonUtility.addClass(this, "dummyGame");
			SkeletonUtility.printCall("Game", this);
			GameMap=new Map();
			GameMap.loadMap(mapname,numberOfPlayers);
			this.time=seconds;
			SkeletonUtility.printReturn("Game", this);
		}
		
		/**
		 * A játék elindításáért felelõs metódus
		 */
		public void startGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (PlayerRobot r : GameMap.getRobots()) {
				r.modifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		/**
		 * A játék véget érésénél hívódik meg, illetve ha már nincs aktív játékos a pályán
		 */
		public void endGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.getResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		
		/**
		 * Körök szimulálását végzi
		 * @throws Exception 
		 * @throws IOException - Exception amit elkell kapni.
		 */
		public void run() throws Exception{
			if(elapsedTime<=time){
				SkeletonUtility.printCall("Run", this);
				if(!GameMap.getMapItems().isEmpty()) {
					
				}
				
				for (PlayerRobot r : GameMap.getRobots()) {
					try {
						GameMap.validateState(r);
						r.jump(GameMap);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				SkeletonUtility.printReturn("Run", this);
				elapsedTime++;
			}else throw new Exception("EndOfGame!");
		}
		
		/**
		 * Meghatározza lenyomott billentyû alapján, hogy felhasználó
		 * mit szeretne csinálni, és meghívja a megfelelõ mûveleteket
		 * */
		public void userControl(char interact){
			/**TODO kérdések
			 * 3) A robotnál a modifySpeed ugye még nincs kész? Mert olyasmi rémlik még
			 * megbeszélésekrõl, hogy billentyûnyomás hozzáad a módosító sebességvektorhoz,
			 * nem felülírja
			 */
			int numberOfRobots = GameMap.getRobots().size();
			
			/**Az elsõ robotra vonatkozó billentyûlenyomások
			 * w: fel
			 * s: le
			 * a: balra
			 * d: jobbra*/
			PlayerRobot playerOne = GameMap.getRobots().get(0);
			switch(interact){
				//Fel
				case 'w': 
					playerOne.modifySpeed(new Vector(0, 1));
					break;
				//Le
				case 's': 
					playerOne.modifySpeed(new Vector(0, -1));
					break;
				//Balra
				case 'a': 
					playerOne.modifySpeed(new Vector(1, 0));
					break;
				//Jobbra
				case 'd': 
					playerOne.modifySpeed(new Vector(-1, 0));
					break;
			}
			
			/**Második robotra vonatkozó billentyûlenyomások
			 * tfgh, hasonló funkcióval mint elsõnél
			 */
			if (numberOfRobots >= 2){
				PlayerRobot playerTwo = GameMap.getRobots().get(1);
				switch(interact){
					//Fel
					case 't': 
						playerTwo.modifySpeed(new Vector(0, 1));
						break;
					//Le
					case 'g': 
						playerTwo.modifySpeed(new Vector(0, -1));
						break;
					//Balra
					case 'f': 
						playerTwo.modifySpeed(new Vector(1, 0));
						break;
					//Jobbra
					case 'h': 
						playerTwo.modifySpeed(new Vector(-1, 0));
						break;
				}
			}
			
			/**Harmadik robot (ha van), irányítása: ijkl*/
			if (numberOfRobots == 3){
				PlayerRobot playerThree = GameMap.getRobots().get(2);
				switch(interact){
					//Fel
					case 'i': 
						playerThree.modifySpeed(new Vector(0, 1));
						break;
						//Le
					case 'k': 
						playerThree.modifySpeed(new Vector(0, -1));
						break;
						//Balra
					case 'j': 
						playerThree.modifySpeed(new Vector(1, 0));
						break;
						//Jobbra
					case 'l': 
						playerThree.modifySpeed(new Vector(-1, 0));
						break;
				}
				
				SkeletonUtility.printCall("UserControl", this);
				//...//
				SkeletonUtility.printReturn("UserControl", this);
			}
		}
		
		
		public void setTime(int time){
			this.time = time;
		}
		
}
