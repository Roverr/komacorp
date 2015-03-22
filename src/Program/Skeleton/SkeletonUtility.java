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
	/**
	 * Klasszikus konstruktor, megalkotja a tesztel�shez sz�ks�ges dummy oszt�lyokat.
	 * Be�ll�tja a kell� statikus v�ltoz�kat.
	 * 
	 */
	public SkeletonUtility(){
		allowSkeleton = true;
		//Create Dummy classes
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
		boolean isYes;
		printSkeleton(question+" yes/no y/n igen/nem");
		String answer=readSkeleton();
		answer.toUpperCase();/**csak a biztons�g kedv��rt ha gy�k�r a user*/
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
		sb.append("from ");
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
		ident--; /*Az ident cs�kkent�se sz�ks�ges, hiszen a bels� h�v�soknak v�ge van.*/
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
	 * Bels� f�ggv�ny, egy oszt�ly p�ld�nyhoz nevet p�ros�t, ha az szerepel a classTable-ben.
	 * Ha nincs az nyilv�ntart�sban az oszt�ly, �res sztringgel t�r vissza.
	 * @param caller - a sz�ban forg� oszt�ly.
	 * @return visszat�r az oszt�ly nev�vel, vagy egy �res "" stringgel.
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
	*Ez választ pályát, és be is tölti azt.
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
