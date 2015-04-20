package Program.Prototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestRunner {
	
	private static String menu =    "1 - Teszt �r�sa (Standard bemenetr�l �rkez� teszt lefuttat�sa)\n" +
									"2 - Egy teszt futtat�sa (v�lassz egy tesztet a 'tesztek' mapp�b�l)\n"+
									"3 - �sszes teszt lefuttat�sa (a 'tesztek' mapp�b�l)\n" + 
									"4 - Kil�p�s";
	
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public TestRunner(){
		
	}
	
	public void handleInputs() throws IOException{
		PrototypeUtility pr = new PrototypeUtility();
		boolean quit = false;
		boolean awaitTestNumber = false;
		/*
		 * Ameddig kil�p�st nem parancsolunk a programnak, v�rja az utas�t�sokat.
		 */
		while(!quit){
			/*	Ha almen�ben vagyunk, akkor azt kezelj�k le.
			 *  Ha nem, akkor v�rjuk a k�vetkez� tesztet.
			 * 	a readLine seg�ts�g�vel beolvasunk egy sz�mot, ami a ki�rt men�pontok k�z�l v�laszt.
			 * 
			 */
			if(!awaitTestNumber){
				//Nem vagyunk az almen�ben, teh�t ki�rjuk a f�men�t,
				System.out.println(menu);
				// majd beolvassuk a felhaszn�l� v�lasz�t.
				String line = input.readLine();
				int choice = Integer.parseInt(line);
				if(choice == 1){
					pr.runTest("");
					//�res param�ter� runTest a standard bemenetr�l v�rja a tesztesetet.
				}else if(choice == 2){
					//A tesztesetek ki�r�s�hoz bel�p�nk az almen�be:
					awaitTestNumber = true;
				}else if(choice == 3){
					//Az �sszes teszt lefuttat�sa, haszn�lja a ki�rt�kel�programot.
					ArrayList<String> tests = listTests();
					for (String string : tests) {
						pr.runTest(string);
						//TODO ki�rt�kel�s
					}
				}else if(choice == 4){
					quit = true;
				}
			}else{
				//Az almen�ben vagyunk, am�g nem kapunk egy �rtelmes v�laszt addig ott is maradunk.
				while(awaitTestNumber){
					ArrayList<String> tests = listTests();
					// A men� ki�r�sa:
					for (int i = 0; i < tests.size(); i++) {
						System.out.println( (i + 1) + " - " + tests.get(i));
					}
					System.out.println((tests.size() +1) + " - Kil�p�s az almen�b�l");
					
					//V�lasz beolvas�sa
					String line = input.readLine();
					int choice = Integer.parseInt(line);
					//A felhaszn�l�i k�nyelem miatt 1-t�l kezdj�k a sz�moz�st,
					//Ez�rt n�hol az indexel�st m�dos�tgatni kell.
					if((choice > 0) && (choice - 1 <= tests.size())){
						//Teh�t valid teszt sz�mot adtunk meg
						pr.runTest(tests.get(choice-1));
						//TODO ki�rt�kel�s
						awaitTestNumber = false; //kil�p�s az almen�b�l
					}else if(choice - 1 == tests.size()){
						//Az utols� men�pont a kil�p�s az almen�b�l
						awaitTestNumber = false; //kil�p�s az almen�b�l
					}
				}
			}
		}
	}
	
	private ArrayList<String> listTests(){
		//A tesztesetek a k�vetkez� mapp�ban helyezkednek el, innen kell �ket kilist�zni:
		File testDir = new File(System.getProperty("user.dir") + "\\tesztek\\input");
		//kilist�z�s:
		ArrayList<String> names = new ArrayList<String>();
		for (File f : testDir.listFiles()) {
			String name =  f.getName();
			//A tesztn�vhez a kiterjeszt�st t�r�lj�k a file v�g�r�l.
			if(name.endsWith(".txt")){
				name = name.substring(0, name.length()-4);
			}
			names.add(name);
		}
		//Visszat�r�nk a tesztek list�j�val
		return names;
	}
	

}
