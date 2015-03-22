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
	/**
	 * Klasszikus konstruktor, megalkotja a teszteléshez szükséges dummy osztályokat.
	 * Beállítja a kellõ statikus változókat.
	 * 
	 */
	public SkeletonUtility(){
		allowSkeleton = true;
		//Create Dummy classes
		//Set up input listening
		//more..
		
		
	}
	
	/**
	 * Privát kiíró függvény, gondoskodik arról hogy csak akkor kerüljön valami kiírásra, ha a Skeleton módot engedélyeztük.
	 * @param s
	 */
	private static void printSkeleton(String s){
		if(allowSkeleton){
			System.out.println(s);
		}else{
			System.out.println("allowSkeleton is not activated, you cant use skeleton methods");
		}
	}
	/**
	 * A printSkeleton párja, üres stringet dob vissza ha nincs allowSkeleton
	 * @author Barna
	 * @throws IOException 
	 */
	private static String readSkeleton() throws IOException{
		if(allowSkeleton){
			BufferedReader brKeyboard = new BufferedReader(new InputStreamReader(System.in));
			String line = brKeyboard.readLine();
			brKeyboard.close();
			return line;
		}else{
			System.out.println("allowSkeleton is not activated, you cant use skeleton methods");
			return "";
		}
		
	}
	/**
	 * yesorno question megvalósítása, rekurzívan addig kérdezget míg jó választ adsz neki
	 * @author Barna
	 * @throws IOException 
	 */
	public static boolean yesOrNoQuestion(String question) throws IOException{
		boolean isYes;
		printSkeleton(question+" yes/no y/n igen/nem");
		String answer=readSkeleton();
		answer.toUpperCase();/**csak a biztonság kedvéért ha gyökér a user*/
		if("Y".equals(answer) || "YES".equals(answer) || "IGEN".equals(answer)){
			isYes=true;
		}else if("N".equals(answer) || "NO".equals(answer) || "NEM".equals(answer))
			isYes=false;
		else{ printSkeleton("Invalid answer");
			isYes=yesOrNoQuestion(question);
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
		sb.append("from ");
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
		ident--; /*Az ident csökkentése szükséges, hiszen a belsõ hívásoknak vége van.*/
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		sb.append("return ");
		sb.append(methodname + " ");
		sb.append("from ");
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
			return s;
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
	public static void inputHandler() throws IOException{
			String line = readSkeleton();
			String[] parts=line.split(" ");
			String command=parts[0];
			

			if(command.equals("LoadMap")){
				String name=parts[1];
				chooseMap(name);
				
			}else if(command.equals("SetPlayerCount")){
				int number=Integer.parseInt(parts[1]);
				//chooseNumberOfPlayers(number);
				
			}else if(command.equals("WinGame")){
				int playernumber=Integer.parseInt(parts[1]);
				//winGame(playernumber);
				
			}else if(command.equals("Exit")){
				//exitGame();
				
			}else if(command.equals("LoseGame")){
				int playernumber=Integer.parseInt(parts[1]);
				//ez a két sor alább csak példa, hogy mivel kezdõdjön a 
				//loseGame(int) metódus
				/**boolean result;
				*  result=yesOrNoQuestion("Only One Left?");
				*/
				//loseGame(playernumber);
				
			}else if(command.equals("SetSpeedMod")){
				float x,y;
				x=Float.parseFloat(parts[1]);
				y=Float.parseFloat(parts[2]);
				Vector newvector=new Vector(x,y);
				//setSpeedModification(newvector);
				
			}else if(command.equals("SetPos")){
				int x,y;
				x=Integer.parseInt(parts[1]);
				y=Integer.parseInt(parts[2]);
				Point newpoint=new Point(x,y);
				//setPosition(newpoint);
				
			}else if(command.equals("SetDrop")){
				String item=parts[1];
				/**Megvalósítani setDrop() metódusban
				 * boolean result;
				*  result=yesOrNoQuestion("Out of Item?");
				*  ......
				*  + OIL,oil,Oil,Goo,goo,GOO, javaslom uppercaseé tenni
				*/
				//setDropItem(item);
				
			}else if(command.equals("ValidateState")){
				int playernumber=Integer.parseInt(parts[1]);
				/**
				 * javaslat ezzel kezdeni:
				 * result1,result2,result3:yesorno(out?,oil?,goo?)
				 */
				//validateState(playernumber);
				
			}else if(command.equals("ModSpeed")){
				float x,y;
				x=Float.parseFloat(parts[1]);
				y=Float.parseFloat(parts[2]);
				Vector newvector=new Vector(x,y);
				/**
				 * javaslat ezzel kezdeni:
				 * result1,result2:yesorno(drop oil?,drop goo?)
				 */
				//modifySpeed(newvector);
				
			}else printSkeleton("Wrong command"); 
			
					
		
	}
	/**
	*Ez vÃ¡laszt pÃ¡lyÃ¡t, Ã©s be is tÃ¶lti azt.
	 *@author Bence
	 */
	private void chooseMap(String name){
		Map map;
		
		map.LoadMap(name, 1);
	}
	private void chooseNumberOfPlayers(int number){
		Map map;
		
		map.LoadMap("", number);
	}
}
