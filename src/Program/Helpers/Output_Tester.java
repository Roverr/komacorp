package Program.Helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * �sszehasonl�tja a kapott kimenetet az elv�rt kimenettel
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
	
	/*Igazat ad vissza, ha az elv�rt kimenet �s kapott kimenet tartalma egyezik*/
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
				/*Pr�b�lunk beolvasni sorokat*/
				try {
			    	   line1 = in1.readLine();
			       	   line2 = in2.readLine();
			       	   linecount++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			    /*Ha az egyik file v�g�re �rt�nk, v�ge a hasonl�tgat�snak*/
			    if (line1 == null || line2 == null){
			      	 EOF = true;
			    }
			    /*Ha az egyik file-nak a v�g�re �rt�nk, de a m�siknak nem, akkor nem j�*/
			    if (((line1 == null) && (line2 != null)) || ((line1 == null) && (line2 != null))){
			       	match = false;
			    }
			    /*Ha elt�r a k�t sor*/
			    else if (line1!=null && line2!=null && !line1.equals(line2)){
			    	System.out.println("Ki�rt�kel�s: A " + linecount +". sorban elt�r�s van.");
			      	match = false;
			    }
			  }
			in1.close();in2.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		/*Visszat�r�nk azzal, hogy egyezik-e vagy sem*/
		
		return match;
	}
}
