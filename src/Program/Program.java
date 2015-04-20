package Program;

import java.io.IOException;

import Program.Core.Game;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

/**
 * Az egész projekt belépési pontja. Ez készíti el a menüket, és játék indításakor a Game osztályt.
 * 
 * 
 * @author Karesz
 *
 */

public class Program {

	/** main függvény, itt kreálódjon meg a menü majd a grafikus verzióhoz. 
	 *  Egyelõre egy dummy Game kreálódjon.
	 * 
	 * @param args - Standard bemenet. 
	 * @author Karesz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new Game(180 seconds, map:"Halálos kanyon");
		/**letrehozok egy jatekot 180 másodperc hosszú, mapnév,játékosszám
		 * @author Barna
		 */
		//Game skeletonGame=new Game(180,"Halalos kanyon",3);
		//SkeletonUtility su = new SkeletonUtility();
		PrototypeUtility pu= new PrototypeUtility();
		pu.runTest("egymaradt");
		
	}

}
