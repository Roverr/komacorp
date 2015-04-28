package Program;

import java.io.IOException;

import Program.Core.MainWindow;
import Program.Prototype.TestRunner;

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
		System.out.println("bbabaóasdf");
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
