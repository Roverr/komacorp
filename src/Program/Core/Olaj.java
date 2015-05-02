package Program.Core;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;

/**
 * Olaj osztály , ami akadályként dobható a pályára.
 * @author Rover
 */
public class Olaj extends MapItem {
	
	/**
	 * timeLeft - Az olaj aktivitási ideje, amíg a pályán marad
	 */
	private int timeLeft;
	
	/**
	 * Ragacs grafikus képe
	 */
	BufferedImage olajImage;
	
	/**
	 * Szerializáláshoz szükséges
	 */
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivitási idejét állítja be a konstruktor.
	 * @param timing - Az idõ ameddig az olaj aktív
	 */
	public Olaj(int timing,FloatPoint position) {
		this.setTimeLeft(timing);
		this.position = position;
		state = CleaningState.canBeCleaned;
		
		//Olaj képének betöltése
		try {
			olajImage = ImageIO.read(new File("assets\\ingame\\Olaj.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Publikus konstruktor ami az olaj pozíciójával képes beállítani
	 * @param position
	 */
	public Olaj(FloatPoint position){
		this(10,position);
		state = CleaningState.canBeCleaned;
		
		//Olaj képének betöltése
		try {
			olajImage = ImageIO.read(new File("assets\\ingame\\Olaj.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Azért felelõs, hogy a belelépett roboton negatív effektet fejtsen ki.
	 * @param playerRobot- Robot ami belelépet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		if(PrototypeUtility.allowDebug)System.out.println("Olaj - stepin +" + playerRobot.name);
		Vector zero = new Vector(0,0);
		playerRobot.setModSpeed(zero);
	}
	
	/**
	 * Visszadja mennyi idõ van még hátra
	 * @return
	 */
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Beállítja mennyi idõ van még hátra
	 * @param timeLeft
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	/**
	 * Öregíti az olajat ha megvan hívódva.
	 */
	public void update(){
		timeLeft = timeLeft-1;
	}

	@Override
	public boolean isAlive() {
		return (timeLeft > 0);
	}

	@Override
	/**
	 * Kirajzolja a képernyőre az olajat
	 * @author Hunor
	 */
	public void draw(Graphics g) {
		/*Olajfolt kirajzolása*/
		Graphics2D g2 = (Graphics2D) g;
		int x = (int) position.getX();
		int y = (int) position.getY();
		x = x - (olajImage.getWidth() / 2); //Eltolás, hogy a középponton legyen a közepe
		y = y - (olajImage.getHeight() / 2);
		g2.drawImage(olajImage, x, y, null);
	}

}
