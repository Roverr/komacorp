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
		 * Csin�lok egy construktort amit a Program.java main met�dusa h�v meg
		 * Ez reprezent�lja ha a men�ben be�ll�tottunk mindent �s azt mondjuk h Game
		 * Szerintem kell m�g +1 v�ltoz� h h�ny j�t�kos legyen
		 * Ezzel tesztel�dik a LoadMap met�dus is
		 * 
		 * Lehets�ges hogy sz�t kell v�lasztani a j�t�kossz�mot a loadmapt�l, 
		 * ezt el kell d�nteni
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
		 * A j�t�k elind�t�s��rt felel�s met�dus
		 */
		public void startGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (PlayerRobot r : GameMap.getRobots()) {
				r.modifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		/**
		 * A j�t�k v�get �r�s�n�l h�v�dik meg, illetve ha m�r nincs akt�v j�t�kos a p�ly�n
		 */
		public void endGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.getResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		
		/**
		 * K�r�k szimul�l�s�t v�gzi
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
		 * Meghat�rozza lenyomott billenty� alapj�n, hogy felhaszn�l�
		 * mit szeretne csin�lni, �s megh�vja a megfelel� m�veleteket
		 * */
		public void userControl(char interact){
			/**TODO k�rd�sek
			 * 3) A robotn�l a modifySpeed ugye m�g nincs k�sz? Mert olyasmi r�mlik m�g
			 * megbesz�l�sekr�l, hogy billenty�nyom�s hozz�ad a m�dos�t� sebess�gvektorhoz,
			 * nem fel�l�rja
			 */
			int numberOfRobots = GameMap.getRobots().size();
			
			/**Az els� robotra vonatkoz� billenty�lenyom�sok
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
			
			/**M�sodik robotra vonatkoz� billenty�lenyom�sok
			 * tfgh, hasonl� funkci�val mint els�n�l
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
			
			/**Harmadik robot (ha van), ir�ny�t�sa: ijkl*/
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
