package Program.Skeleton;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.*;


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
	
	private static BufferedReader brKeyboard;
	/**
	 * Klasszikus konstruktor, megalkotja a tesztel�shez sz�ks�ges dummy oszt�lyokat.
	 * Be�ll�tja a kell� statikus v�ltoz�kat.
	 * 
	 */
	public SkeletonUtility(){
		//Create Dummy classes
		dummyGame= new Game(270,"Hal�los Kanyon",3);
		dummyMap = new Map();
		dummyMap.loadMap("Dummyk lank�ja", 3);
		dummyRobot = new Robot();
		dummyOlaj = new Olaj(3);
		dummyRagacs = new Ragacs(3);
		
		//Ki�r�sok enged�lyez�se:
		allowSkeleton = true;
		
		//Input reader inicializ�l�s:
		brKeyboard = new BufferedReader(new InputStreamReader(System.in));
		
		
		
	}
	
	/**
	 * Priv�t ki�r� f�ggv�ny, gondoskodik arr�l hogy csak akkor ker�lj�n valami ki�r�sra, ha a Skeleton m�dot enged�lyezt�k.
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
	 * A printSkeleton p�rja, �res stringet dob vissza ha nincs allowSkeleton
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
				/**Megval�s�tani setDrop() met�dusban
				 * boolean result;
				*  result=yesOrNoQuestion("Out of Item?");
				*  ......
				*  + OIL,oil,Oil,Goo,goo,GOO, javaslom uppercase� tenni
				*/
				String question = "Out of item?";
				boolean outOfItem;
				try{
					outOfItem = !SkeletonUtility.yesOrNoQuestion(question);
					if(outOfItem) {
						//Ha nem fogyott m�g ki a dobnival�b�l. 
						
						
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
				printSkeleton("UseCase & El�gaz�sok    Parancs:    param�ter\n"+
					"Choose Map    LoadMap    string n�v\n"+
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
					"Quit (Kil�p a Skeletonb�l)\n"
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
	*Ez v�laszt p�ly�t, �s be is t�lti azt, a kor�bban elfogadott j�t�kossz�mmal
	 *@author Bence
	 */
	private static void chooseMap(String name){
		
		dummyMap.loadMap(name,previousnumberofplayers);
	}
	/**
	 * Robot sebess�g�nek megv�ltozat�s�hoz a parancs, float inputokkal. 
	 * @param x - A vektor x komponense
	 * @param y - A vektor y komponense
	 */
	public void setSpeedModification(float x, float y) {
		Vector modifier = new Vector(x,y);
		dummyRobot.modifySpeed(modifier);
	}
	
	/**
	 *Robot sebess�g�nek megv�ltozat�s�hoz a parancs, Vector inputtal. 
	 * @param modifier - A vetor amivel v�ltoztatni akarunk
	 */
	public void setSpeedModification(Vector modifier) {
		dummyRobot.modifySpeed(modifier);
	}
	
	/**
	 * Robot poz�ci�j�nak be�ll�t�sa int inputokkal. 
	 * @param x - x koordin�ta
	 * @param y - y koordin�ta
	 */
	public void setPosition(int x, int y) {
		dummyRobot.setPosition(new Point(x,y));
	}
	
	/**
	 * Robot poz�ci�j�nak be�ll�t�sa int inputokkal. 
	 * @param to - A pont ahova a robotot akarjuk tenni.
	 */
	public void setPosition(Point to) {
		dummyRobot.setPosition(to);
	}
	
	/**
	 * Ha a robot ragacsot vagy olajat dob el akkor h�v�dik meg
	 * @param what - A string, ami kiv�lasztja, hogy mit dobjon a robot. 
	 * String, mert az jobban olvashat� k�dot sz�l. 
	 */
	public void setDropItem(String what) {
		what = what.toLowerCase();
		if(what=="ragacs") {
			dummyRobot.dropRagacs(dummyMap);
		} else if(what=="olaj") {
			dummyRobot.dropOlaj(dummyMap);
		} else {
			//Hibakezel�s
			SkeletonUtility.printSkeleton("I can't throw it! It's not Ragacs or Olaj!");
			//throw new OutOfShitError();
		}
	}
	/*Ez v�laszt j�t�kossz�mot, �s bet�lti a p�ly�t �jra, csak a j�t�kosok sz�m�t v�ltoztatva.
	 *@author Bence
	 */
	private void chooseNumberOfPlayers(int number){
		
		dummyMap.loadMap(previousname, number);
	}
	/**
	 * @attribute previousname - Elmenti az aktu�lis p�lya nev�t, alap�rtelmezett a Hal�los Kanyon
	 * @attribute previousnumberofplayers - Elmenti a aktu�lis j�t�kosok sz�m�t, 2 az alap�rtelmezett.
	 */
	private static String previousname="Hal�los Kanyon";
	private static int previousnumberofplayers=2;
	/**
	 * A megadott j�t�kos megnyeri a j�t�kot
	 * @param player
	 * @author Barna
	 */
	private void winGame(int player){
		dummyGame.EndGame();
		printSkeleton(player+" megnyerte a j�t�kot!");
	}
	/**
	 * A megadott j�t�kos elveszti a j�t�kot
	 * @param player
	 * @author Barna
	 */
	private void loseGame(int player){
		dummyGame.EndGame();
		printSkeleton(player+" elvesztette a j�t�kot!");
	}
	/**
	 * 
	 * @author Barna
	 * @throws IOException 
	 */
	private void exitGame() throws IOException{
		if(yesOrNoQuestion("Biztos hogy meg akarod szak�tani a meccset? Ha m�soknak m�g van es�lye nyerni, elveszed t�l�k a lehet�s�get."))
		dummyGame.EndGame();
		else printSkeleton("Helyes, egy igazi BME-s a v�gs�kig k�zd!");
	}
	
}
