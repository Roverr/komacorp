package Program.Skeleton;

import java.util.HashMap;
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
		}
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
}
