package Program.Core;

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
			Map GameMap=new Map();
			GameMap.LoadMap(mapname,numberOfPlayers);
			SkeletonUtility.printReturn("Game", this);
		}

		public void StartGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (Robot r : GameMap.GetRobots()) {
				r.ModifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		
		public void EndGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.GetResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		

		public void Run(){
			SkeletonUtility.printCall("Run", this);
			for (Robot r : GameMap.GetRobots()) {
				GameMap.ValidateState(r);
				r.Jump();
			}
			SkeletonUtility.printReturn("Run", this);
		}
		
		public void UserControl(char interact){
			SkeletonUtility.printCall("UserControl", this);
			//...//
			SkeletonUtility.printReturn("UserControl", this);
		}
		
}
