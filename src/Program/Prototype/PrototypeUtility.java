package Program.Prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.Game;


/**class PrototypeUtility:
 * A prototípus modell parancsrendszerét megvalósító osztály.
 * Kezeli a megadott bemenetrõl érkezõ parancsokat, egy létrehozott Game példány módosításával.
 * Kezeli az elõre megírt teszteket, kezeli a szövegösszehasonlító segédprogramot, hogy meggyõzõdjön
 * a tesztek helyességérõl.
 * 
 * A felhasználónak jelzi a program állapotát minden interakció lefutása után.
 * 
 * @author Karesz
 *
 */
public class PrototypeUtility {

	/**
	 * @attribute classTable - Tartalmazza a parancsokkal létrehozott osztályokat a nevük alapján
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
