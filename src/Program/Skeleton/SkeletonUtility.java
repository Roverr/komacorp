package Program.Skeleton;

import java.util.HashMap;
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
		}
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
}
