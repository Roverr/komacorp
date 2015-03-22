package Program.Skeleton;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
<<<<<<< HEAD
import Program.Core.*;
=======

import Program.Core.Game;
import Program.Core.Map;
import Program.Core.Olaj;
import Program.Core.Ragacs;
import Program.Core.Robot;
>>>>>>> 6436fc898b3161162307f88942c11759d9e40c1c
import Program.Helpers.Vector;
/**
 * A szkeleton modell megval�s�t�s��rt felel�s oszt�ly. A bels� business modell a kor�bbiakban megbesz�lt.
 * A SkeletonUtility oszt�ly l�trehozza a tesztel�shez sz�ks�ges k�rnyezetet.
 * Tov�bb� parancsokkal biztos�tja az Use-casek megh�vhat�s�g�t, nyomon k�veti azok lefut�s�t.
 * Tov�bbi inf� a dokument�ci�ban.
 * 
 * @author Sz�kely K�roly
 *
 */
public class SkeletonUtility {
	
	/**
	 * @attribute ident - A sz�veg beh�z�s�t jelzi. Annyi tabul�tor ker�l a sor elej�re, amekkora az ident.
	 * @attribute allowSkeleton - A skeleton csak akkor �rhat ki b�rmit ha ez a v�ltoz� true.
	 * @attribute classTable - tartalmazza az oszt�lyok p�ld�nyainak neveit a p�ld�nyokhoz rendelve.
	 */
	private static int ident;
	private static boolean allowSkeleton;
	private static HashMap<Object, String> classTable = new HashMap<Object, String>();
	
	/**
	 * Az al�bbi v�ltoz�k az elnevez�sekhez sz�ks�gesek. Amikor l�trej�n egy �j objektum 
	 * akkor ezt a sz�mot a neve ut�n �rva megk�l�mb�ztethetj�k a t�bbit�l. 
	 */
	public static int robotCounter = 0;
	public static int mapItemCounter = 0;
	
	private static Game dummyGame;
	private static Map dummyMap;
	private static Robot dummyRobot;
	private static Olaj dummyOlaj;
	private static Ragacs dummyRagacs;
	/**
	 * Klasszikus konstruktor, megalkotja a tesztel�shez sz�ks�ges dummy oszt�lyokat.
	 * Be�ll�tja a kell� statikus v�ltoz�kat.
	 * 
	 */
	public SkeletonUtility(){
		allowSkeleton = true;
		//Create Dummy classes
		dummyGame= new Game(270,"Hal�los Kanyon",3);
		dummyMap = new Map();
		dummyRobot = new Robot();
		dummyOlaj = new Olaj(3);
		dummyRagacs = new Ragacs(3);
		//Set up input listening
		//more..
		
		
	}
	
	/**
	 * Priv�t ki�r� f�ggv�ny, gondoskodik arr�l hogy csak akkor ker�lj�n valami ki�r�sra, ha a Skeleton m�dot enged�lyezt�k.
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
	 * A printSkeleton p�rja, �res stringet dob vissza ha nincs allowSkeleton
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
	 * yesorno question megval�s�t�sa, rekurz�van addig k�rdezget m�g j� v�laszt adsz neki
	 * @author Barna
	 * @throws IOException 
	 */
	public static boolean yesOrNoQuestion(String question) throws IOException{
		boolean isYes = false;
		boolean invalidAnswer = true;
		while(invalidAnswer){
			printSkeleton(question+" yes/no y/n igen/nem");
			String answer=readSkeleton();
			answer.toUpperCase();/**csak a biztons�g kedv��rt ha gy�k�r a user*/
			if("Y".equals(answer) || "YES".equals(answer) || "IGEN".equals(answer)){
				isYes=true;
				invalidAnswer = false;
			}else if("N".equals(answer) || "NO".equals(answer) || "NEM".equals(answer)){
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
	 * A szkeleton Modellben megh�v�skor ki�rja hogy melyik oszt�lyban, milyen met�dust h�v�dott meg.
	 * A ki�r�s t�rdelt jelleg�t az ident v�ltoz� int�zi.
	 * A ki�r�s csak akkor t�rt�nik meg hogyha az allowSkeleton v�ltoz� �rt�ke True.
	 * @param methodname A megh�vott met�dus neve.
	 * @param caller A h�v� oszt�ly. (Legt�bb esetben: this)
	 */
	public static void printCall(String methodname, Object caller){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident++; /*Az ident n�vel�se az�rt sz�ks�ges, hogy a met�dus bels� h�v�sai m�r beljebb legyenek h�zva.*/
		sb.append("called ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}
	

	/**
	 * A szkeleton Modellben visszat�r�skor ki�rja hogy melyik oszt�ly, milyen met�dusa t�r vissza.
	 * A ki�r�s t�rdelt jelleg�t az ident v�ltoz� int�zi.
	 * A ki�r�s csak akkor t�rt�nik meg hogyha az allowSkeleton v�ltoz� �rt�ke True.
	 * @param methodname A visszat�r� met�dus neve.
	 * @param caller A h�v� oszt�ly. (Legt�bb esetben: this)
	 */
	public static void printReturn(String methodname, Object caller){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident--; /*Az ident cs�kkent�se sz�ks�ges, hiszen a bels� h�v�soknak v�ge van.*/
		sb.append("return ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}

	/**
	 * Bels� f�ggv�ny, egy oszt�ly p�ld�nyhoz nevet p�ros�t, ha az szerepel a classTable-ben.
	 * Ha nincs az nyilv�ntart�sban az oszt�ly, �res sztringgel t�r vissza.
	 * @param caller - a sz�ban forg� oszt�ly.
	 * @return visszat�r az oszt�ly nev�vel, vagy egy �res "" stringgel.
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
	 * Ez az imputhandler ami a parancsokat v�rja
	 * Megadott parancsra �s " " el elv�lasztott param�tereket
	 * �tadja a megfelel� f�ggv�nynek, ami egy use-caset reprezent�l
	 *a f�ggv�nyt z�ld komment jelzi, m�g nincsenek meg�rva
	 *nem akartam hib�t beletenni, �rtelemszer�en azokat kell implement�lni
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
				//ez a k�t sor al�bb csak p�lda, hogy mivel kezd�dj�n a 
				//loseGame(int) met�dus
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
				/**Megval�s�tani setDrop() met�dusban
				 * boolean result;
				*  result=yesOrNoQuestion("Out of Item?");
				*  ......
				*  + OIL,oil,Oil,Goo,goo,GOO, javaslom uppercase� tenni
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
	*Ez v�laszt p�ly�t, �s be is t�lti azt, a kor�bban elfogadott j�t�kossz�mmal
	 *@author Bence
	 */
	private void chooseMap(String name){
		
		dummyMap.LoadMap(name,previousnumberofplayers);
	}
	/**
	 * Robot sebess�g�nek megv�ltozat�s�hoz a parancs, float inputokkal. 
	 * @param x - A vektor x komponense
	 * @param y - A vektor y komponense
	 */
	public void setSpeedMod(float x, float y) {
		Vector modifier = new Vector(x,y);
		dummyRobot.ModifySpeed(modifier);
	}
	
	/**
	 *Robot sebess�g�nek megv�ltozat�s�hoz a parancs, Vector inputtal. 
	 * @param modifier - A vetor amivel v�ltoztatni akarunk
	 */
	public void setSpeedMod(Vector modifier) {
		dummyRobot.ModifySpeed(modifier);
	}
	
	/**
	 * Robot poz�ci�j�nak be�ll�t�sa int inputokkal. 
	 * @param x - x koordin�ta
	 * @param y - y koordin�ta
	 */
	public void setPosition(int x, int y) {
		dummyRobot.SetPosition(new Point(x,y));
	}
	
	/**
	 * Robot poz�ci�j�nak be�ll�t�sa int inputokkal. 
	 * @param to - A pont ahova a robotot akarjuk tenni.
	 */
	public void setPosition(Point to) {
		dummyRobot.SetPosition(to);
	}
	/*Ez v�laszt j�t�kossz�mot, �s bet�lti a p�ly�t �jra, csak a j�t�kosok sz�m�t v�ltoztatva.
	 *@author Bence
	 */
	private void chooseNumberOfPlayers(int number){
		
		dummyMap.LoadMap(previousname, number);
	}
	/**
	 * @attribute previousname - Elmenti az aktu�lis p�lya nev�t, alap�rtelmezett a Hal�los Kanyon
	 * @attribute previousnumberofplayers - Elmenti a aktu�lis j�t�kosok sz�m�t, 2 az alap�rtelmezett.
	 */
	private static String previousname="Hal�los Kanyon";
	private static int previousnumberofplayers=2;
}
