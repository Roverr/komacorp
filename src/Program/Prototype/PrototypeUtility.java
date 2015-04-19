package Program.Prototype;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.CleanerRobot;
import Program.Core.Game;
import Program.Core.Map;
import Program.Core.Olaj;
import Program.Core.PlayerRobot;
import Program.Core.Ragacs;
import Program.Core.Robot;
import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;


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
	private static HashMap<String, Object> classTable = new HashMap<String, Object>();
	
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
				testGame = new Game(100, testName, 0);
				
			}
		}else if(comm.equals("loadmap")){
			if (command.length >= 2) {
				String mapName = command[1];
				Map m = (Map)classTable.get("GameMap");
				m.loadMap(mapName, 0);
			}
		}else if(comm.equals("setgamelength")){
			if (command.length >= 2) {
				int time = Integer.parseInt(command[1]);
				testGame.setTime(time);
			}
		}else if(comm.equals("add")){
			if (command.length >= 5) {
				String name = command[2];
				int x = Integer.parseInt(command[3]);
				int y = Integer.parseInt(command[4]);
				command[1].toLowerCase();
				if(command[1].equals("robot")){
					Map m = (Map)classTable.get("GameMap");
					PlayerRobot r = new PlayerRobot();
					r.setPosition(x, y);
					addClass(r, name);
					m.getRobots().add(r);
				}else if(command[1].equals("cleaner")){
					Map m = (Map)classTable.get("GameMap");
					CleanerRobot c = new CleanerRobot();
					c.setPosition(x, y);
					addClass(c, name);
					m.getCleanerRobots().add(c);
				}else if(command[1].equals("olaj")){
					Map m = (Map)classTable.get("GameMap");
					Olaj o = new Olaj(new Point(x,y));
					addClass(o, name);
					m.addMapItem(o);
				}else if(command[1].equals("Ragacs")){
					Map m = (Map)classTable.get("GameMap");
					Ragacs o = new Ragacs(3, new Point(x,y));
					addClass(o, name);
					m.addMapItem(o);
				}
			}
		}else if(comm.equals("set")){
			if (command.length >= 3) {
				String name = command[1];
				String attr = command[2];
				Object obj = classTable.get(name);
				if(attr.equals("speed") && obj instanceof Robot && command.length >= 5){
					Robot r = (Robot) obj;
					float x = Float.parseFloat(command[3]);
					float y = Float.parseFloat(command[4]);
					r.setSpeed(new Vector(x,y));
				}else if(attr.equals("modSpeed") && obj instanceof PlayerRobot && command.length >= 5){
					PlayerRobot r = (PlayerRobot) obj;
					float x = Float.parseFloat(command[3]);
					float y = Float.parseFloat(command[4]);
					r.setModSpeed(new Vector(x,y));
				}else if(attr.equals("dropolaj") && obj instanceof PlayerRobot && command.length >= 4){
					PlayerRobot r = (PlayerRobot) obj;
					boolean drop = yesOrNo(command[3]);
					if(drop){
						r.setWantToDrop(2);
					}else{
						r.setWantToDrop(0);
					}
				}else if(attr.equals("dropragacs") && obj instanceof PlayerRobot && command.length >= 4){
					PlayerRobot r = (PlayerRobot) obj;
					boolean drop = yesOrNo(command[3]);
					if(drop){
						r.setWantToDrop(1);
					}else{
						r.setWantToDrop(0);
					}
				}
			}
		}
		
		
		
		}
	}
	
	private static int classTableNameCntr = 0;
	public static void addClass(Object obj, String name){
		if(name.equals("")){
			name = "TestClass_" + classTableNameCntr;
			classTableNameCntr++;
		}
		classTable.put(name, obj);
	}
	

	public static boolean yesOrNo(String value){
		value.toLowerCase();
		if(value.equals("yes") || value.equals("y") ){
			return true;
		}return false;
	}

	/*
	 * Gondolom �n, ide j�nnek a teszteset kiv�laszt�shoz a beolvas�s inputr�l,
	 *  amit azt�n �t tudunk adni annak a logik�nak ami kiolvassa az el�re megadott file-okb�l 
	 *  a commandokat, �s �tadja az executecommandnak
	 *  M�g az a gondom, hogy a val�s logik�t tartalmaz� f�ggv�nyek honnan tudj�k hogy hova kell ki�rniuk azt hogy
	 *  mit csin�lnak
	 * @author Barna
	 */

	
	
	
}
