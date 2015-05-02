package Program.Core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Program.Helpers.FloatPoint;
import Program.Prototype.PrototypeUtility;

/**
 * Ragacs osztály, ami akadályként dobható a pályára
 * 
 * @author Rover
 *
 */
public class Ragacs extends MapItem {

	/**
	 * Azért felelõs, hogy hányszor lehet a ragacsba lépni
	 */
	private int stepInCounter;
	
	/**
	 * Robot grafikus képe
	 */
	BufferedImage ragacsImage;

	/**
	 * Szerializáláshoz kell.
	 */
	private static final long serialVersionUID = 6191556765398850843L;

	/**
	 * A ragacsba való belelépések számát állítja be a konstruktor.
	 * 
	 * @param StepIn
	 */
	public Ragacs(int stepIn, FloatPoint position) {
		setPosition(position);
		setStepinCounter(stepIn);
		state = CleaningState.canBeCleaned;
		
		//Ragacs képének betöltése
		try {
			ragacsImage = ImageIO.read(new File("assets\\ingame\\Ragacs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Ragacs(FloatPoint position) {
		this(3, position);
		state = CleaningState.canBeCleaned;
		
		//Ragacs képének betöltése
		try {
			ragacsImage = ImageIO.read(new File("assets\\ingame\\Ragacs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Beállítja azt, hogy hányszor lehet belelépni a Ragacsba
	 * 
	 * @param to
	 *            - Egész szám a Ragacs maximális belelépésének lehetõségérõl.
	 */
	public void setStepinCounter(int to) {
		stepInCounter = to;
	}

	/**
	 * Visszadja azt, hogy hányszor lehet még belelépni a Ragacsba
	 * 
	 * @return
	 */
	public int getStepinCounter() {
		return stepInCounter;
	}

	/**
	 * Beállítja a negatív effektust, ha egy robot belelép, illetve csökkenti a
	 * belelépés számát.
	 * 
	 * @param playerRobot
	 *            - A robot, ami belelépett
	 */
	public void stepIn(PlayerRobot playerRobot) {
		if (PrototypeUtility.allowDebug)
			System.out.println("Ragacs - stepin +" + playerRobot.name);
		stepInCounter = stepInCounter - 1;
		playerRobot.setSpeed(playerRobot.getSpeed().cutIntoHalf());
	}

	public void update() {
		// Mivel körönként nem kell frissíteni, ide semmi nem kerül.
		return;
	}

	@Override
	public boolean isAlive() {
		return (stepInCounter > 0);
	}
	
	@Override
	/**
	 * Kirajzolja a képernyőre a ragacsot
	 * @author Hunor
	 */
	public void draw(Graphics g) {
		/*Ragacs kirajzolása*/
		Graphics2D g2 = (Graphics2D) g;
		int x = (int) position.getX();
		int y = (int) position.getY();
		x = x - (ragacsImage.getWidth() / 2); //Eltolás, hogy a középponton legyen a közepe
		y = y - (ragacsImage.getHeight() / 2);
		g2.drawImage(ragacsImage, x, y, null);
	}

}
