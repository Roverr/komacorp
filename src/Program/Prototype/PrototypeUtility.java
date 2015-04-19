package Program.Prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.Game;


/**class PrototypeUtility:
 * A protot�pus modell parancsrendszer�t megval�s�t� oszt�ly.
 * Kezeli a megadott bemenetr�l �rkez� parancsokat, egy l�trehozott Game p�ld�ny m�dos�t�s�val.
 * Kezeli az el�re meg�rt teszteket, kezeli a sz�veg�sszehasonl�t� seg�dprogramot, hogy meggy�z�dj�n
 * a tesztek helyess�g�r�l.
 * 
 * A felhaszn�l�nak jelzi a program �llapot�t minden interakci� lefut�sa ut�n.
 * 
 * @author Karesz
 *
 */
public class PrototypeUtility {

	/**
	 * @attribute classTable - Tartalmazza a parancsokkal l�trehozott oszt�lyokat a nev�k alapj�n
	 */
	private HashMap<String, Object> classTable = new HashMap<String, Object>();
	
	private boolean fromFile = false;
	private String inputFileName;
	private String outputFileName = "latestTest.txt";
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private Game testGame;
	
	public static boolean allowDebug = false;
	
	private String[] readCommand() throws IOException{
		String line = null;
		inputReader.readLine();
		String[] parts = line.split(" ");
		return parts;
	}
	
	
	private void executeCommand(String[] command){
		if(command!= null && command.length >= 1){
		String comm = command[0];
		comm.toLowerCase();
		if(comm.equals("begintest")){
			if (command.length >= 2) {
				String testName = command[1];
				testGame = new Game(100, "", 4);
				
			}
		}else if(comm.equals("loadmap")){
			
		}
		
		
		
		}
	}
	
	
	
}
