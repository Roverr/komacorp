package Program.Prototype;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Program.Core.CleanerRobot;
import Program.Core.Game;
import Program.Core.Map;
import Program.Core.MapItem;
import Program.Core.Olaj;
import Program.Core.PlayerRobot;
import Program.Core.Ragacs;
import Program.Core.Robot;
import Program.Helpers.Vector;


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
	private static HashMap<String, Object> classTable = new HashMap<String, Object>();
	
	private boolean fromFile = false;
	private String inputFileName;
	private String outputFileName = "latestTest.txt";
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private PrintWriter outputWriter = new PrintWriter(System.out); 
	private Game testGame;
	
	public static boolean allowDebug = false;
	
	private String[] readCommand() throws IOException{
		String line = null;
		inputReader.readLine();
		String[] parts = line.split(" ");
		return parts;
	}
	
	/**
	 * A következõ függvény feldolgoz egy parancsot. A parancs beazonosítása a szting tömb elsõ elemével történik,
	 * A további elemek a parancs attribútumai. A parancsok a testGame osztállyal, és a classTable
	 * osztálykatalógussal dolgozik.
	 * A hosszú if-else rész végzi a külömbözõ parancsokra fellépõ akciók végrehajtását.
	 * Leellenõrzi ha a parancs formátuma helyes. Helytelen formátum esetén nem hajtja végre a parancsot.
	 * @param command - Széttagolt parancs, a tömb egy eleme egy attribútumot jelent.
	 */
	private void executeCommand(String[] command){
		if(command!= null && command.length >= 1){
		String comm = command[0];
		comm.toLowerCase();
		// A félreértések elkerülése végett kisbetûs szövegként kezeljük a parancsot.
		if(comm.equals("begintest")){
			if (command.length >= 2) {
				String testName = command[1];
				try {
					testGame = new Game(100, "halal_kanyon.txt", 0);
				} catch (MyFileNotFoundException e) {
					// TODO Auto-generated catch block
					String output=e.getMessage();
				}
				
			}
		}else if(comm.equals("loadmap")){//értelmezi a loadmap parancsot
			if (command.length >= 2) {
				String mapName = command[1];
				Map m = getTestMap();
				try {
					m.loadMap(mapName, 0);
				} catch (MyFileNotFoundException e) {
					// TODO Auto-generated catch block
					String output=e.getMessage();
				}
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
					Map m = getTestMap();
					PlayerRobot r = new PlayerRobot();
					addClass(r, name);
					try {
						m.addPlayerRobot(name, x, y);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						String output=e.getMessage();
					}
				}else if(command[1].equals("cleaner")){
					Map m = getTestMap();
					CleanerRobot c = new CleanerRobot();
					c.setPosition(x, y);
					addClass(c, name);
					m.getCleanerRobots().add(c);
				}else if(command[1].equals("olaj")){
					Map m = getTestMap();
					Olaj o = new Olaj(new Point(x,y));
					addClass(o, name);
					m.addMapItem(o);
				}else if(command[1].equals("Ragacs")){
					Map m = getTestMap();
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
		}else if(comm.equals("run")){
			int t = 1;
			if (command.length >= 2) {
				t = Integer.parseInt(command[1]);
			}
			for(int i = 0; i < t ; i++){
				try {
					testGame.run();
				} catch (Exception e) {
					//itt az az exceptionnak a message, hogy EndOfGame!
					// TODO Auto-generated catch block
					e.printStackTrace();
					//ide lehet a fájlkiloggolós logikát megírni :)
					String output = e.getMessage();
				}
			}
		}else if(comm.equals("listolaj")){
			Map m = getTestMap();
			List<Olaj> olajok = new ArrayList<Olaj>();
			for (MapItem item : m.getMapItems()) {
				if(item instanceof Olaj){
					olajok.add((Olaj) item);
				}
			}
			String output = listOlaj(olajok);
			outputWriter.print(output);
		}else if(comm.equals("listragacs")){
			Map m = getTestMap();
			List<Ragacs> ragacsok = new ArrayList<Ragacs>();
			for (MapItem item : m.getMapItems()) {
				if(item instanceof Ragacs){
					ragacsok.add((Ragacs) item);
				}
			}
			String output = listRagacs(ragacsok);
			outputWriter.print(output);
		}else if(comm.equals("listrobots")){
			Map m = getTestMap();
			List<Robot> robotok = new ArrayList<Robot>();
			for (Robot robot : m.getRobots()) {
				robotok.add(robot);
			}
			for (Robot robot : m.getCleanerRobots()) {
				robotok.add(robot);
			}
			String output = listRobots(robotok);
			outputWriter.print(output);
		}else if(comm.equals("listrobotitems")){
			if (command.length >= 2) {
				String name = command[1];
				PlayerRobot r = (PlayerRobot)classTable.get(name);
				List<Integer> items = r.getItemsContained();
				outputWriter.println("Ragacs " + items.get(0));
				outputWriter.println("Olaj " + items.get(1));
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
	
	public static String listOlaj(List<Olaj> list){
		StringBuilder builder = new StringBuilder();
		for (Olaj olaj : list) {
			String name = "";
			
			for (Entry<String, Object> entry : classTable.entrySet()) {
	            if (entry.getValue().equals(olaj)) {
	                name = entry.getKey();
	            }
	        }
			
			builder.append(name + " "+  
					olaj.getPosition().x + " " + olaj.getPosition().y + 
					" Olaj " + olaj.getTimeLeft() + "\n");
		}
		return builder.toString();
	}

	public static String listRagacs(List<Ragacs> list){
		StringBuilder builder = new StringBuilder();
		for (Ragacs ragacs : list) {
			String name = "";
			
			for (Entry<String, Object> entry : classTable.entrySet()) {
	            if (entry.getValue().equals(ragacs)) {
	                name = entry.getKey();
	            }
	        }
			
			builder.append(name + " "+  
					ragacs.getPosition().x + " " + ragacs.getPosition().y + 
					" Olaj " + ragacs.getStepinCounter() + "\n");
		}
		return builder.toString();
	}
	

	public static String listRobots(List<Robot> list){
		StringBuilder builder = new StringBuilder();
		for (Robot robot : list) {
			String name = "";
			
			for (Entry<String, Object> entry : classTable.entrySet()) {
	            if (entry.getValue().equals(robot)) {
	                name = entry.getKey();
	            }
	        }
			
			String robotType = "Robot";
			if(robot instanceof PlayerRobot){
				robotType = "PlayerRobot";
			}else if(robot instanceof CleanerRobot){
				robotType = "CleanerRobot";
			}
			
			builder.append(name + " "+  
					robot.getPosition().x + " " + robot.getPosition().y + 
					" " + robotType +" " + 
					robot.getSpeed().getX() + " " + robot.getSpeed().getY() + "\n");
		}
		return builder.toString();
	}
	
	private static Map getTestMap(){
		return (Map)classTable.get("GameMap");
	}

	
	
	
}
