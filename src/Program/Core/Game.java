package Program.Core;

import java.io.IOException;

import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;

public class Game {
		private Map GameMap;
		
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
		public Game(int seconds,String mapname,int numberOfPlayers){
			SkeletonUtility.addClass(this, "dummyGame");
			SkeletonUtility.printCall("Game", this);
			GameMap=new Map();
			GameMap.loadMap(mapname,numberOfPlayers);
			SkeletonUtility.printReturn("Game", this);
		}

		public void startGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (PlayerRobot r : GameMap.getRobots()) {
				r.modifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		
		public void endGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.getResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		

		public void Run() throws IOException{
			SkeletonUtility.printCall("Run", this);
			for (PlayerRobot r : GameMap.getRobots()) {
				GameMap.validateState(r);
				r.jump();
			}
			SkeletonUtility.printReturn("Run", this);
		}
		
		public void userControl(char interact){
			SkeletonUtility.printCall("UserControl", this);
			//...//
			SkeletonUtility.printReturn("UserControl", this);
		}
		
}
