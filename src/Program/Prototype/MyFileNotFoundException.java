package Program.Prototype;

import java.io.FileNotFoundException;

public class MyFileNotFoundException extends FileNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613879104454986042L;
	public MyFileNotFoundException(){
		super("No such map exists");
	}
	public MyFileNotFoundException(String msg){
		super(msg);
	}
}
