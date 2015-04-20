package Program;

import java.io.IOException;

import Program.Core.Game;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

/**
 * Az eg�sz projekt bel�p�si pontja. Ez k�sz�ti el a men�ket, �s j�t�k ind�t�sakor a Game oszt�lyt.
 * 
 * 
 * @author Karesz
 *
 */

public class Program {

	/** main f�ggv�ny, itt kre�l�djon meg a men� majd a grafikus verzi�hoz. 
	 *  Egyel�re egy dummy Game kre�l�djon.
	 * 
	 * @param args - Standard bemenet. 
	 * @author Karesz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new Game(180 seconds, map:"Hal�los kanyon");
		/**letrehozok egy jatekot 180 m�sodperc hossz�, mapn�v,j�t�kossz�m
		 * @author Barna
		 */
		//Game skeletonGame=new Game(180,"Halalos kanyon",3);
		//SkeletonUtility su = new SkeletonUtility();
		PrototypeUtility pu= new PrototypeUtility();
		pu.runTest("egymaradt");
		
	}

}
