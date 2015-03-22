package Program.Skeleton;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.*;


import Program.Helpers.Vector;
/**
 * A szkeleton modell megvalósításáért felelõs osztály. A belsõ business modell a korábbiakban megbeszélt.
 * A SkeletonUtility osztály létrehozza a teszteléshez szükséges környezetet.
 * Továbbá parancsokkal biztosítja az Use-casek meghívhatóságát, nyomon követi azok lefutását.
 * További infó a dokumentációban.
 * 
 * @author Székely Károly
 *
 */
public class SkeletonUtility {
	
	/**
	 * @attribute ident - A szöveg behúzását jelzi. Annyi tabulátor kerül a sor elejére, amekkora az ident.
	 * @attribute allowSkeleton - A skeleton csak akkor írhat ki bármit ha ez a változó true.
	 * @attribute classTable - tartalmazza az osztályok példányainak neveit a példányokhoz rendelve.
	 */
	private static int ident;
	private static boolean allowSkeleton;
	private static HashMap<Object, String> classTable = new HashMap<Object, String>();
	
	/**
	 * Az alábbi változók az elnevezésekhez szükségesek. Amikor létrejön egy új objektum 
	 * akkor ezt a számot a neve után írva megkülömböztethetjük a többitõl. 
	 */
	public static int robotCounter = 0;
	public static int mapItemCounter = 0;
	
	private static Game dummyGame;
	private static Map dummyMap;
	private static Robot dummyRobot;
	private static Olaj dummyOlaj;
	private static Ragacs dummyRagacs;
	
	private static BufferedReader brKeyboard;
	/**
	 * Klasszikus konstruktor, megalkotja a teszteléshez szükséges dummy osztályokat.
	 * Beállítja a kellõ statikus változókat.
	 * 
	 */
	public SkeletonUtility(){
		//Create Dummy classes
		dummyGame= new Game(270,"Halálos Kanyon",3);
		dummyMap = new Map();
		dummyMap.loadMap("Dummyk lankája", 3);
		dummyRobot = new Robot();
		dummyOlaj = new Olaj(3);
		dummyRagacs = new Ragacs(3);
		
		//Kiírások engedélyezése:
		allowSkeleton = true;
		
		//Input reader inicializálás:
		brKeyboard = new BufferedReader(new InputStreamReader(System.in));
		
		
		
	}
	
	/**
	 * Privát kiíró függvény, gondoskodik arról hogy csak akkor kerüljön valami kiírásra, ha a Skeleton módot engedélyeztük.
	 * @param s
	 */
	static boolean notificationsent = false;
	private static void printSkeleton(String s){
		if(allowSkeleton){
			System.out.println(s);
		}else{
			if(!notificationsent){
				System.out.println("allowSkeleton is not activated, you cant use skeleton methods");
				notificationsent = true;
			}
		}
	}
	/**
	 * A printSkeleton párja, üres stringet dob vissza ha nincs allowSkeleton
	 * @author Barna
	 * @throws IOException 
	 */
	private static String readSkeleton() throws IOException{
		if(allowSkeleton){
			String line = brKeyboard.readLine();
			return line;
		}else{
			if(!notificationsent){
				System.out.println("allowSkeleton is not activated, you cant use skeleton methods");
				notificationsent = true;
		}
			return "";
		}
		
	}
	/**
	 * yesorno question megvalósítása, rekurzívan addig kérdezget míg jó választ adsz neki
	 * @author Barna
	 * @throws IOException 
	 */
	public static boolean yesOrNoQuestion(String question) throws IOException{
		boolean isYes = false;
		boolean invalidAnswer = true;
		while(invalidAnswer){
			printSkeleton(question+" yes/no y/n igen/nem");
			String answer=readSkeleton();
			if("Y".equals(answer) || "YES".equals(answer) || "IGEN".equals(answer)
					||"y".equals(answer) || "yes".equals(answer) || "igen".equals(answer)){
				isYes=true;
				invalidAnswer = false;
			}else if("N".equals(answer) || "NO".equals(answer) || "NEM".equals(answer)
					|| "n".equals(answer) || "no".equals(answer) || "nem".equals(answer)){
				isYes=false;
				invalidAnswer = false;
			}else{ 
				printSkeleton("Invalid answer");
				invalidAnswer = true;
			}
		}
		return isYes;
	}

	/**
	 * A szkeleton Modellben meghíváskor kiírja hogy melyik osztályban, milyen metódust hívódott meg.
	 * A kiírás tördelt jellegét az ident változó intézi.
	 * A kiírás csak akkor történik meg hogyha az allowSkeleton változó értéke True.
	 * @param methodname A meghívott metódus neve.
	 * @param caller A hívó osztály. (Legtöbb esetben: this)
	 */
	public static void printCall(String methodname, Object caller){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident++; /*Az ident növelése azért szükséges, hogy a metódus belsõ hívásai már beljebb legyenek húzva.*/
		sb.append("called ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}
	

	/**
	 * A szkeleton Modellben visszatéréskor kiírja hogy melyik osztály, milyen metódusa tér vissza.
	 * A kiírás tördelt jellegét az ident változó intézi.
	 * A kiírás csak akkor történik meg hogyha az allowSkeleton változó értéke True.
	 * @param methodname A visszatérõ metódus neve.
	 * @param caller A hívó osztály. (Legtöbb esetben: this)
	 */
	public static void printReturn(String methodname, Object caller){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident--; /*Az ident csökkentése szükséges, hiszen a belsõ hívásoknak vége van.*/
		sb.append("return ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}

	/**
	 * Belsõ függvény, egy osztály példányhoz nevet párosít, ha az szerepel a classTable-ben.
	 * Ha nincs az nyilvántartásban az osztály, üres sztringgel tér vissza.
	 * @param caller - a szóban forgó osztály.
	 * @return visszatér az osztály nevével, vagy egy üres "" stringgel.
	 */
	private static String matchNameToObject(Object caller) {
		String s = classTable.get(caller);
		if(s != null){
			return  "- " + s;
		}
		return "";
	}

	public static void addClass(Object obj, String name) {
		classTable.put(obj, name);
		
	}
	
	/**
	 * Ez az imputhandler ami a parancsokat várja
	 * Megadott parancsra és " " el elválasztott paramétereket
	 * átadja a megfelelõ függvénynek, ami egy use-caset reprezentál
	 *a függvényt zöld komment jelzi, még nincsenek megírva
	 *nem akartam hibát beletenni, értelemszerûen azokat kell implementálni
	 *@author Barna
	 * @throws IOException 
	 */
	public void inputHandler() throws IOException{
		boolean quit = false;
		while(!quit){
			printSkeleton("Please enter a valid command:");
			String line = readSkeleton();
			String[] parts=line.split(" ");
			String command=parts[0];
			
			boolean wrongParameters = false;

			if(command.equals("LoadMap")){
				if(parts.length >= 2){
					String name=parts[1];
					chooseMap(name);
				}else{
					wrongParameters = true;
				}
			}else if(command.equals("SetPlayerCount")){
				int number=Integer.parseInt(parts[1]);
				chooseNumberOfPlayers(number);
				
			}else if(command.equals("WinGame")){
				int playernumber=Integer.parseInt(parts[1]);
				winGame(playernumber);
				
			}else if(command.equals("Exit")){
				exitGame();
				
			}else if(command.equals("LoseGame")){
				if(parts.length >= 2){
				
					int playernumber=Integer.parseInt(parts[1]);
				
				loseGame(playernumber);
				}else{
					wrongParameters = true;
				}
			}else if(command.equals("SetSpeedMod")){
				float x,y;
				if(parts.length >= 3){
					x=Float.parseFloat(parts[1]);
					y=Float.parseFloat(parts[2]);
					Vector newvector=new Vector(x,y);
					setSpeedModification(newvector);
				}else{
					wrongParameters = true;
				}
			}else if(command.equals("SetPos")){
				int x,y;
				if(parts.length >= 3){
					x=Integer.parseInt(parts[1]);
					y=Integer.parseInt(parts[2]);
					Point newpoint=new Point(x,y);
					setPosition(newpoint);
				}else{
					wrongParameters = true;
				}
			}else if(command.equals("SetDrop")){
				String item=parts[1];
				/**Megvalósítani setDrop() metódusban
				 * boolean result;
				*  result=yesOrNoQuestion("Out of Item?");
				*  ......
				*  + OIL,oil,Oil,Goo,goo,GOO, javaslom uppercaseé tenni
				*/
				String question = "Out of item?";
				boolean outOfItem;
				try{
					outOfItem = !SkeletonUtility.yesOrNoQuestion(question);
					if(outOfItem) {
						//Ha nem fogyott még ki a dobnivalóból. 
						
						
					}
				}catch(Exception e) {
					SkeletonUtility.printSkeleton(e.getMessage());
				}
				setDropItem(item);
				
			}else if(command.equals("ValidateState")){
				if(parts.length >= 2){
					int playernumber=Integer.parseInt(parts[1]);
					boolean beforeAllow = allowSkeleton;
					allowSkeleton = false;
					if(dummyMap.getRobots().size() > playernumber){
						Robot rob = dummyMap.getRobots().get(playernumber);
						allowSkeleton = beforeAllow;
						dummyMap.validateState(rob);		
					}else{
						wrongParameters = true;
					}
					allowSkeleton = beforeAllow;
				}else{
					wrongParameters = true;
				}
				
			}else if(command.equals("ModSpeed")){
				float x,y;
				if(parts.length >= 3){
					x=Float.parseFloat(parts[1]);
					y=Float.parseFloat(parts[2]);
					Vector newvector=new Vector(x,y);
					/**
					 * javaslat ezzel kezdeni:
					 * result1,result2:yesorno(drop oil?,drop goo?)
					 */
					//modifySpeed(newvector);
				}else{
					wrongParameters = true;
				}
			}else if(command.equals("Help")){
				printSkeleton("UseCase & Elágazások    Parancs:    paraméter\n"+
					"Choose Map    LoadMap    string név\n"+
					"Choose Number of Players    SetPlayerCount    int 1..3\n"+
					"Win Game    WinGame    int whichplayer\n"+
					"Exit Game       Exit\n"+
					"Lose Game       LoseGame    int whichplayer\n"+
					"	Only One Left?           y/n\n"+
					"Set Speed Modification (In Air)    SetSpeedMod    vector float,float\n"+
					"Set Position (In Air)    SetPos       int int\n"+
					"Set Drop Item (In Air)    SetDrop       string olaj/ragacs\n"+
					"	Out of Item?           y/n\n"+
					"Validate State (On Fall)        ValidateState    int whichplayer\n"+
					"	Out Of Map?           y/n\n"+
					"	StepIn Olaj?           y/n\n"+
					"	StepIn Ragacs?           y/n\n"+
					"Modify Speed (On Launch)        ModSpeed    vector float,float\n"+
					"	Drop Olaj?           y/n\n"+
					"	Drop Ragacs?           y/n\n"+
					"Quit (Kilép a Skeletonból)\n"
						+ "\n"
						+ "\n");
			}else if(command.equals("Quit")){
				quit = true;
			}else{
				printSkeleton("Wrong command."); 
				printSkeleton("Type \"Help\" to see the commands."); 
			}
			if(wrongParameters){
				printSkeleton("Something Went wrong with the parameters :S .");
			}
		}
					
		printSkeleton("Skeleton is now Quitting!");

		brKeyboard.close();
	}
	/**
	*Ez választ pályát, és be is tölti azt, a korábban elfogadott játékosszámmal
	 *@author Bence
	 */
	private static void chooseMap(String name){
		
		dummyMap.loadMap(name,previousnumberofplayers);
	}
	/**
	 * Robot sebességének megváltozatásához a parancs, float inputokkal. 
	 * @param x - A vektor x komponense
	 * @param y - A vektor y komponense
	 */
	public void setSpeedModification(float x, float y) {
		Vector modifier = new Vector(x,y);
		dummyRobot.modifySpeed(modifier);
	}
	
	/**
	 *Robot sebességének megváltozatásához a parancs, Vector inputtal. 
	 * @param modifier - A vetor amivel változtatni akarunk
	 */
	public void setSpeedModification(Vector modifier) {
		dummyRobot.modifySpeed(modifier);
	}
	
	/**
	 * Robot pozíciójának beállítása int inputokkal. 
	 * @param x - x koordináta
	 * @param y - y koordináta
	 */
	public void setPosition(int x, int y) {
		dummyRobot.setPosition(new Point(x,y));
	}
	
	/**
	 * Robot pozíciójának beállítása int inputokkal. 
	 * @param to - A pont ahova a robotot akarjuk tenni.
	 */
	public void setPosition(Point to) {
		dummyRobot.setPosition(to);
	}
	
	/**
	 * Ha a robot ragacsot vagy olajat dob el akkor hívódik meg
	 * @param what - A string, ami kiválasztja, hogy mit dobjon a robot. 
	 * String, mert az jobban olvasható kódot szül. 
	 */
	public void setDropItem(String what) {
		what = what.toLowerCase();
		if(what=="ragacs") {
			dummyRobot.dropRagacs(dummyMap);
		} else if(what=="olaj") {
			dummyRobot.dropOlaj(dummyMap);
		} else {
			//Hibakezelés
			SkeletonUtility.printSkeleton("I can't throw it! It's not Ragacs or Olaj!");
			//throw new OutOfShitError();
		}
	}
	/*Ez választ játékosszámot, és betölti a pályát újra, csak a játékosok számát változtatva.
	 *@author Bence
	 */
	private void chooseNumberOfPlayers(int number){
		
		dummyMap.loadMap(previousname, number);
	}
	/**
	 * @attribute previousname - Elmenti az aktuális pálya nevét, alapértelmezett a Halálos Kanyon
	 * @attribute previousnumberofplayers - Elmenti a aktuális játékosok számát, 2 az alapértelmezett.
	 */
	private static String previousname="Halálos Kanyon";
	private static int previousnumberofplayers=2;
	/**
	 * A megadott játékos megnyeri a játékot
	 * @param player
	 * @author Barna
	 */
	private void winGame(int player){
		dummyGame.EndGame();
		printSkeleton(player+" megnyerte a játékot!");
	}
	/**
	 * A megadott játékos elveszti a játékot
	 * @param player
	 * @author Barna
	 */
	private void loseGame(int player){
		dummyGame.EndGame();
		printSkeleton(player+" elvesztette a játékot!");
	}
	/**
	 * 
	 * @author Barna
	 * @throws IOException 
	 */
	private void exitGame() throws IOException{
		if(yesOrNoQuestion("Biztos hogy meg akarod szakítani a meccset? Ha másoknak még van esélye nyerni, elveszed tõlük a lehetõséget."))
		dummyGame.EndGame();
		else printSkeleton("Helyes, egy igazi BME-s a végsõkig küzd!");
	}
	
}
