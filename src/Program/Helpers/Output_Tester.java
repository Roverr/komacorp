package Program.Helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Összehasonlítja a kapott kimenetet az elvárt kimenettel
 * @author Hunor
 *
 */
public class Output_Tester {
	String recievedOutputFileName;
	String expectedOutputFileName;
	
	public Output_Tester(String recievedOutputFileName, String expectedOutputFileName){
		this.recievedOutputFileName = recievedOutputFileName;
		this.expectedOutputFileName = expectedOutputFileName;
	}
	
	/*Igazat ad vissza, ha az elvárt kimenet és kapott kimenet tartalma egyezik*/
	public boolean compare() throws IOException{
		boolean match = true;
		try {
			FileInputStream f1 = new FileInputStream(recievedOutputFileName);
			FileInputStream f2 =  new FileInputStream(expectedOutputFileName);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(f1));
			BufferedReader in2 = new BufferedReader(new InputStreamReader(f2));
			
			String line1 = null, line2 = null;
			boolean EOF = false;
			int linecount = 0;
			
			while (!EOF) {  
				/*Próbálunk beolvasni sorokat*/
				try {
			    	   line1 = in1.readLine();
			       	   line2 = in2.readLine();
			       	   linecount++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			    /*Ha az egyik file végére értünk, vége a hasonlítgatásnak*/
			    if (line1 == null || line2 == null){
			      	 EOF = true;
			    }
			    /*Ha az egyik file-nak a végére értünk, de a másiknak nem, akkor nem jó*/
			    if (((line1 == null) && (line2 != null)) || ((line1 == null) && (line2 != null))){
			       	match = false;
			    }
			    /*Ha eltér a két sor*/
			    else if (line1!=null && line2!=null && !line1.equals(line2)){
			    	System.out.println("Kiértékelés: A " + linecount +". sorban eltérés van.");
			      	match = false;
			    }
			  }
			in1.close();in2.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		/*Visszatérünk azzal, hogy egyezik-e vagy sem*/
		
		return match;
	}
}
