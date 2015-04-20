package Program.Core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

import Program.Helpers.Line;
import Program.Helpers.Vector;

/**
 * A takar�t� robotok oszt�lya, amik Robot lesz�rmazottak, de nem lehet a
 * sebess�g�ket m�dos�tani, illetve teljes m�rt�kben user f�ggetlen a
 * vez�rl�s�k.
 * 
 * @author Rover
 *
 */
public class CleanerRobot extends Robot implements Serializable {

	enum CleanerState {
		cleaning, moving, waiting // akkor, ha nincs semmi folt a p�ly�n
	}

	/**
	 * Az�rt felel�s v�ltoz�, hogy a robot takar�t�sn�l mit v�ltoztatott.
	 */
	private int remainingClean;
	// Mit csin�l a robot takar�t, vagy a k�vetkez� oljfolthoz megy.
	CleanerState state;

	private Point target;

	private static final long serialVersionUID = 2858679422774498028L;

	public CleanerRobot() {
		Vector speedConst = new Vector(0, 1);
		setCurrentSpeed(speedConst);
	}

	public CleanerRobot(MapItem target) {
		setTarget(target.getPosition());
		Vector speedConst = new Vector(0, 1);
		setCurrentSpeed(speedConst);
	}

	/**
	 * Be�ll�tja az aktu�lis gyorsas�g�t a robotnak. A konstruktorban h�v�dik,
	 * illetve ha meghal a robot.
	 * 
	 * @param force
	 *            - A vektor amekkora a sebess�ge.
	 */
	public void setCurrentSpeed(Vector force) {
		this.speed = force;
	};

	/**
	 * Meg�li a takar�t� robotot. Akkor h�v�dik ha r�l�pnek.
	 */
	public void die(Map map) {
		Vector zero = new Vector(0, 0);
		setCurrentSpeed(zero);
		setAlive(false);
		Olaj olaj = new Olaj(this.position);
		// TODO:hozz�adni map-hez
		map.addMapItem(olaj); // ;)
	}

	/**
	 * Visszadja az aktu�lis c�lpont hely�t
	 * 
	 * @return
	 */
	public Point getTarget() {
		return target;
	}

	/**
	 * Be�ll�t egy c�lt a takar�t� robotnak
	 * 
	 * @param target
	 *            - A c�lpontnak a helye
	 */
	public void setTarget(Point target) {
		this.target = target;
	}

	/**
	 * F�ggv�ny ami a takar�t� robotot friss�ti
	 */
	public void jump(Map map) {
		int roundItTakesToClean = 2;
		if (this.getPosition() == this.getTarget()) {
			// Akad�lyra �rkez�s
			if (state == CleanerState.moving) {
				state = CleanerState.cleaning;
				remainingClean = roundItTakesToClean;
			} else {
				if (remainingClean <= 0) {
					this.target = this.nextTarget(map, "");// V�LTOZ�S! MIUT�N
															// V�GZETT MEGY A
															// K�VETKEZ�H�Z
					if (this.target.equals(this.position)) {
						this.state = CleanerState.waiting;
					} else
						state = CleanerState.moving;
					for (MapItem mI : map.getMapItems()) {
						if (mI.getPosition() == getTarget()) {
							map.getMapItems().remove(mI);
						}
					}
					move();
				} else {
					remainingClean--;
				}
			}
			// Game oszt�ly elpusz�tja a targetet, itt m�r csak hib�t kapunk el
			System.out
					.println("Error! CleanerRobot standing on shit, and it's still alive");
		} else {
			move();
		}

	}

	/**
	 * Met�dus ami el�re l�pteti a takar�t� robotot.
	 */
	private void move() {
		Point currentPosition = getPosition();
		/**
		 * TODO: Ide kell majd valami logika, ami ellen�rzi, hogy nem megy-e a
		 * falnak a robot nem zuhan-e a szakad�kba. Az se baj ha ezt a Game
		 * oszt�ly kezeli.
		 */

		// Ha lejebb van az X �s Y tengelyen is
		if (currentPosition.x < target.x && currentPosition.y < target.y) {
			setPosition(new Point(currentPosition.x + 1, currentPosition.y + 1));
		}
		// Ha X-en feljebb van, de Y-on lejebb
		if (currentPosition.x > target.x && currentPosition.y < target.y) {
			setPosition(new Point(currentPosition.x - 1, currentPosition.y + 1));

		}// Ha mind a k�t r�szen feljebb van
		else if (currentPosition.x > target.x && currentPosition.y > target.y) {

			setPosition(new Point(currentPosition.x - 1, currentPosition.y - 1));

		}// Ha X-en lejebb van, de Y-on feljebb
		else {

			setPosition(new Point(currentPosition.x + 1, currentPosition.y - 1));

		}
	}

	/**
	 * Kijel�li a k�vetkez� c�lpontot,azt a mapitemet ami a legk�zelebb van
	 * 
	 */
	private Point nextTarget(Map map, String mode) {
		Line line = new Line(this.position.x, this.position.y, 0, 0);
		Point hova = new Point(this.position.x, this.position.y);
		double minlength = 1000000;
		if (mode.equals("abnormal")) {// ez az ir�nyv�ltoztat�shoz kell, nem
										// defini�lt ir�nyba mennek tov�bb
			int meret = map.getMapItems().size();
			Random random = new Random();
			int index = random.nextInt(meret);
			hova = map.getMapItems().get(index).getPosition();
		} else {
			for (MapItem i : map.getMapItems()) {
				line.setX2(i.getPosition().x);
				line.setY2(i.getPosition().y);
				// r�videbb az �t �s olaj van ott
				if (minlength > line.length()){
					minlength = line.length();
					hova = i.getPosition();
				}
			}
		}
		return hova;
	}

	/**
	 * Lekezeli a kisrobot �tk�z�s�t valami m�ssal ha m�sik kisrobottal �tk�znek
	 * �ssze, akkor megkerg�lnek, �s v�letlenszer�en cs�szk�lnak az egyik random
	 * folt fel�
	 * 
	 * @author Barna
	 */
	@Override
	public void collide(Robot robot, Map map) {
		if (robot.getClass().equals(CleanerRobot.class)) {
			this.nextTarget(map, "abnormal");
		} else
			this.die(map);

	}

}
