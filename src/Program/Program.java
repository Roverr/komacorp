package Program;

import Program.Core.MainWindow;

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
		final MainWindow window = new MainWindow();
		/*TestRunner tr = new TestRunner();
		try {
			tr.handleInputs();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}
