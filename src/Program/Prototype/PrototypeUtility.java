package Program.Prototype;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import Program.Helpers.FloatPoint;
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
	private static HashMap<Object, String> nameTable = new HashMap<Object, String>();
	
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private PrintWriter outputWriter = new PrintWriter(System.out);
	
	//A tesztosztály, amin a módosításokat végezzük. minden beginTest alkalmával új készül.
	private Game testGame;
	
	public static boolean allowDebug = true;
	
	private String[] readCommand() throws IOException{
		String line = null;
		String[] parts = null;
		line = inputReader.readLine();
		System.out.println("readcommand:"+line);
		if(line != null){
			parts = line.split(" ");	
		}
		return parts;
	}
	
	public void runTest(String testName){
		if(!testName.equals("")){
			try {
				inputReader = new BufferedReader(
					new FileReader(System.getProperty("user.dir") +  "\\tesztek\\input\\" + testName + ".txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1.getMessage().toString());
			}
		}
		System.out.println("Running test...." + testName);
		
		boolean endOfTest = false;
		while(!endOfTest){
			try {
				String[] command = readCommand();
				if(command != null){
					executeCommand(command);
				}else{
					endOfTest = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			inputReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputReader=new BufferedReader(new InputStreamReader(System.in));
		outputWriter.close();
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
		if(comm.equals("beginTest")){
			if (command.length >= 2) {
				System.out.println("begintest");
				try {
					testGame = new Game(100, "Tesztmap", 2);
					testGame.startGame();
					if(allowDebug)System.out.println("created game osztály");
				} catch (MyFileNotFoundException e) {
					// TODO Auto-generated catch block
					outputWriter.println(e.getMessage());
					System.out.println(e.getMessage());
				}
				
			}
		}else if(comm.equals("loadMap")){//értelmezi a loadmap parancsot
			if(allowDebug)System.out.println("loadMap.");
			if (command.length >= 2) {
				String mapName = command[1];
				Map m =new Map();// getTestMap();
				try {
					m.loadMap(mapName, 0);
					testGame.setMap(m);
				} catch (MyFileNotFoundException e) {
					// TODO Auto-generated catch block
					outputWriter.println("No such map exists!");
					System.out.println(e.getMessage());
				}
			}
		}else if(comm.equals("setGameLength")){
			if (command.length >= 2) {
				int time = Integer.parseInt(command[1]);
				testGame.setTime(time);
			}
		}else if(comm.equals("add")){
			if (command.length >= 5) {
				String name = command[2];
				float x = Float.parseFloat(command[3]);
				float y = Float.parseFloat(command[4]);
				command[1].toLowerCase();
				if(command[1].equals("robot")){
					Map m = getTestMap();
					try {
						m.addPlayerRobot(name, x, y);
					} catch (Exception e) {
						e.getMessage();
					}
				}else if(command[1].equals("cleaner")){
					Map m = getTestMap();
					CleanerRobot c = new CleanerRobot(m);
					c.setPosition(x, y);
					addClass(c, name);
					m.getCleanerRobots().add(c);
				}else if(command[1].equals("olaj")){
					Map m = getTestMap();
					Olaj o = new Olaj(new FloatPoint(x,y));
					addClass(o, name);
					m.addMapItem(o);
				}else if(command[1].equals("ragacs")){
					Map m = getTestMap();
					Ragacs o = new Ragacs(3, new FloatPoint(x,y));
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
				}else if(attr.equals("modSpeed") && command.length >= 5){
					PlayerRobot r = (PlayerRobot) obj;
					float x = Float.parseFloat(command[3]);
					float y = Float.parseFloat(command[4]);
					r.setModSpeed(new Vector(x,y));
				}else if(attr.equals("dropOlaj") && obj instanceof PlayerRobot && command.length >= 4){
					if(allowDebug)System.out.println("Want Drop the Olaj got this far.");
					PlayerRobot r = (PlayerRobot) obj;
					boolean drop = yesOrNo(command[3]);
					if(drop){
						r.setWantToDrop(2);
						if(allowDebug)System.out.println("Want Drop the Olaj set.");
					}else{
						r.setWantToDrop(0);
					}
				}else if(attr.equals("dropRagacs") && obj instanceof PlayerRobot && command.length >= 4){
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
					//e.printStackTrace();
					outputWriter.println(e.getMessage());
				}
			}
		}else if(comm.equals("listOlaj")){
			if(allowDebug)System.out.println(" Fuck. My. Life.");
			Map m = getTestMap();
			List<Olaj> olajok = new ArrayList<Olaj>();
			for (MapItem item : m.getMapItems()) {
					if(allowDebug)System.out.println(" (Not) Added an olaj ot the output list.");
				if(item instanceof Olaj){
					olajok.add((Olaj) item);
				}
			}
			String output = listOlaj(olajok);
			if(output!= null){
				outputWriter.print(output);
			}
		}else if(comm.equals("listRagacs")){
			Map m = getTestMap();
			List<Ragacs> ragacsok = new ArrayList<Ragacs>();
			for (MapItem item : m.getMapItems()) {
				if(item instanceof Ragacs){
					ragacsok.add((Ragacs) item);
				}
			}
			String output = listRagacs(ragacsok);
			if(output!= null){
				outputWriter.print(output);
			}
		}else if(comm.equals("listRobots")){
			Map m = getTestMap();
			List<Robot> robotok = new ArrayList<Robot>();
			for (Robot robot : m.getRobots()) {
				robotok.add(robot);
			}
			for (Robot robot : m.getCleanerRobots()) {
				robotok.add(robot);
			}
			String output = listRobots(robotok);
			if(output!= null){
				outputWriter.print(output);
			}
			System.out.println(output);
		}else if(comm.equals("listRobotItems")){
			if (command.length >= 2) {
				String name = command[1];
				PlayerRobot r = (PlayerRobot)classTable.get(name);
				List<Integer> items = r.getItemsContained();
				outputWriter.println("Olaj " + items.get(1));
				outputWriter.println("Ragacs " + items.get(0));
			}
				
		}else if(comm.equals("result")){
			String res = testGame.endResult;
			System.out.println(res);
			if(res != null){
				outputWriter.print(res);
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
		nameTable.put(obj, name);
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
			String name = nameTable.get(olaj);
			
			builder.append(name + " "+  
					olaj.getPosition().getX() + " " + olaj.getPosition().getY() + 
					" Olaj " + olaj.getTimeLeft() + "\n");
		}
		return builder.toString();
	}

	public static String listRagacs(List<Ragacs> list){
		StringBuilder builder = new StringBuilder();
		for (Ragacs ragacs : list) {
			String name = nameTable.get(ragacs);
			
			builder.append(name + " "+  
					ragacs.getPosition().getX() + " " + ragacs.getPosition().getY() + 
					" Olaj " + ragacs.getStepinCounter() + "\n");
		}
		return builder.toString();
	}
	

	public static String listRobots(List<Robot> list){
		StringBuilder builder = new StringBuilder();
		for (Robot robot : list) {
			String name = nameTable.get(robot);
			
			String robotType = "Robot";
			if(robot instanceof PlayerRobot){
				robotType = "PlayerRobot";
			}else if(robot instanceof CleanerRobot){
				robotType = "CleanerRobot";
			}
			builder.append(name + " "+  
					robot.getPosition().getX() + " " + robot.getPosition().getY() + 
					" " + robotType +" " + 
					robot.getSpeed().getX() + " " + robot.getSpeed().getY() + "\n");
			
		}
		return builder.toString();
	}
	
	private static Map getTestMap(){
		return (Map)classTable.get("GameMap");
	}

	public void setOutput(String outputString) {
		if(!outputString.equals("")){
			try {
				outputWriter = new PrintWriter(
					new FileOutputStream(System.getProperty("user.dir") +  "\\" + outputString + ".txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1.getMessage().toString());
			}
		}
		
	}

	
	
	
}
