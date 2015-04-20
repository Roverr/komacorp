package Program.Prototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Program.Helpers.Output_Tester;

/**
 * Az alábbi osztály a tesztelési felületet biztosítja a felhasználók számára.
 * Egy egyszerû menübõl a felhasználó tesztelési módokat választhat. Kiléphet a programból.
 * A .txt kiterjesztésû teszt fájlokat a tesztek\input mappába másolva a saját tesztek 
 * is lefuttathatóak. Ha az expected mappában létezik a teszthez illõ elvárt kimenet, 
 * akkor ezzel összehasonlítja a teszt során.
 * @author Karesz
 *
 */

public class TestRunner {
	
	private static String menu =    "1 - Teszt írása (Standard bemenetrõl érkezõ teszt lefuttatása)\n" +
									"2 - Egy teszt futtatása (válassz egy tesztet a 'tesztek' mappából)\n"+
									"3 - Összes teszt lefuttatása (a 'tesztek' mappából)\n" + 
									"4 - Kilépés";
	
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public TestRunner(){
		
	}
	
	/*
	 * Ez a függvény végzi a felhasználóval a kommunikációt. Végrehajtja az utasításokat
	 */
	public void handleInputs() throws IOException{
		PrototypeUtility pr = new PrototypeUtility();
		boolean quit = false;
		boolean awaitTestNumber = false;
		/*
		 * Ameddig kilépést nem parancsolunk a programnak, várja az utasításokat.
		 */
		while(!quit){
			/*	Ha almenüben vagyunk, akkor azt kezeljük le.
			 *  Ha nem, akkor várjuk a következõ tesztet.
			 * 	a readLine segítségével beolvasunk egy számot, ami a kiírt menüpontok közül választ.
			 * 
			 */
			if(!awaitTestNumber){
				//Nem vagyunk az almenüben, tehát kiírjuk a fõmenüt,
				System.out.println("");
				System.out.println(menu);
				// majd beolvassuk a felhasználó válaszát.
				String line = input.readLine();
				int choice = -1;
				try {
					choice = Integer.parseInt(line);
				} catch (NumberFormatException e) {
					choice = -1;
					System.out.println("\n Adj meg egy valid számot!");
				}
				if(choice == 1){
					pr.runTest("");
					//Üres paraméterû runTest a standard bemenetrõl várja a tesztesetet.
				}else if(choice == 2){
					//A tesztesetek kiírásához belépünk az almenübe:
					awaitTestNumber = true;
				}else if(choice == 3){
					//Az összes teszt lefuttatása, használja a kiértékelõprogramot.
					ArrayList<String> tests = listTests();
					for (String string : tests) {
						pr.setOutput("testLog");
						pr.runTest(string);
						kiertekel(string);
					}
				}else if(choice == 4){
					quit = true;
				}
			}else{
				//Az almenüben vagyunk, amíg nem kapunk egy értelmes választ addig ott is maradunk.
				while(awaitTestNumber){
					System.out.println("");
					ArrayList<String> tests = listTests();
					// A menü kiírása:
					for (int i = 0; i < tests.size(); i++) {
						System.out.println( (i + 1) + " - " + tests.get(i));
					}
					System.out.println((tests.size() +1) + " - Kilépés az almenüböl");
					
					//Válasz beolvasása
					String line = input.readLine();
					int choice = -1;
					try {
						choice = Integer.parseInt(line);
					} catch (NumberFormatException e) {
						choice = -1;
						System.out.println("\n Adj meg egy valid számot!");
					}
					
					//A felhasználói kényelem miatt 1-tõl kezdjük a számozást,
					//Ezért néhol az indexelést módosítgatni kell.
					if((choice > 0) && (choice - 1 <= tests.size())){
						//Tehát valid teszt számot adtunk meg
						pr.setOutput("testLog");
						pr.runTest(tests.get(choice-1));
						kiertekel(tests.get(choice-1));
						awaitTestNumber = false; //kilépés az almenübõl
					}else if(choice - 1 == tests.size()){
						//Az utolsó menüpont a kilépés az almenübõl
						awaitTestNumber = false; //kilépés az almenübõl
					}
				}
			}
		}
	}
	
	private void kiertekel(String string) {
		String offset = System.getProperty("user.dir");
		Output_Tester ot = new Output_Tester(offset + "\\testLog.txt", offset+"\\tesztek\\expected\\" + string + "Expected.txt");
		try {
			printResult(ot.compare());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printResult(boolean valid) {
		if(valid){
			System.out.println("Gratulálunk a teszt sikeres volt.");
		}else{
			System.out.println("A teszt helyenként eltér.");
		}
		
	}

	private ArrayList<String> listTests(){
		//A tesztesetek a következõ mappában helyezkednek el, innen kell õket kilistázni:
		File testDir = new File(System.getProperty("user.dir") + "\\tesztek\\input");
		//kilistázás:
		ArrayList<String> names = new ArrayList<String>();
		for (File f : testDir.listFiles()) {
			String name =  f.getName();
			//A tesztnévhez a kiterjesztést töröljük a file végérõl.
			if(name.endsWith(".txt")){
				name = name.substring(0, name.length()-4);
			}
			names.add(name);
		}
		//Visszatérünk a tesztek listájával
		return names;
	}
	

}
