package Program;

import java.io.IOException;

import Program.Core.MainWindow;
import Program.Prototype.TestRunner;

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
		System.out.println("bbaba�asdf");
		MainWindow window=new MainWindow();
		System.out.println("asdfasf");
		/*TestRunner tr = new TestRunner();
		try {
			tr.handleInputs();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}
