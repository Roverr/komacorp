package Program.Core;

import java.io.IOException;

import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;

public class Game {
		private Map GameMap;
		
		/**
		 * Csin�lok egy construktort amit a Program.java main met�dusa h�v meg
		 * Ez reprezent�lja ha a men�ben be�ll�tottunk mindent �s azt mondjuk h Game
		 * Szerintem kell m�g +1 v�ltoz� h h�ny j�t�kos legyen
		 * Ezzel tesztel�dik a LoadMap met�dus is
		 * 
		 * Lehets�ges hogy sz�t kell v�lasztani a j�t�kossz�mot a loadmapt�l, 
		 * ezt el kell d�nteni
		 * @author Barna
		 */
		public Game(int seconds,String mapname,int numberOfPlayers){
			SkeletonUtility.addClass(this, "dummyGame");
			SkeletonUtility.printCall("Game", this);
			Map GameMap=new Map();
			GameMap.loadMap(mapname,numberOfPlayers);
			SkeletonUtility.printReturn("Game", this);
		}

		public void StartGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (Robot r : GameMap.getRobots()) {
				r.modifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		
		public void EndGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.GetResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		

		public void Run() throws IOException{
			SkeletonUtility.printCall("Run", this);
			for (Robot r : GameMap.getRobots()) {
				GameMap.validateState(r);
				r.jump();
			}
			SkeletonUtility.printReturn("Run", this);
		}
		
		public void UserControl(char interact){
			SkeletonUtility.printCall("UserControl", this);
			//...//
			SkeletonUtility.printReturn("UserControl", this);
		}
		
}
