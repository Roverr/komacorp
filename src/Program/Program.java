package Program;

import Program.Core.MainWindow;

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
		final MainWindow window = new MainWindow();
		/*TestRunner tr = new TestRunner();
		try {
			tr.handleInputs();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}
