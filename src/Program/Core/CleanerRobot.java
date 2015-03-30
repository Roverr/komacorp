package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Helpers.Vector;

/**
 * A takar�t� robotok oszt�lya, amik Robot lesz�rmazottak, de nem lehet a sebess�g�ket
 * m�dos�tani, illetve teljes m�rt�kben user f�ggetlen a vez�rl�s�k.
 * @author Rover
 *
 */
public class CleanerRobot extends Robot implements Serializable {

	private Point target;
	
	private static final long serialVersionUID = 2858679422774498028L;
	
	public CleanerRobot(MapItem target) {
		setTarget(target.getPosition());
		Vector speedConst = new Vector(1,1);
		setCurrentSpeed(speedConst);
	}
	
	/**
	 * Be�ll�tja az aktu�lis gyorsas�g�t a robotnak. A konstruktorban h�v�dik,
	 * illetve ha meghal a robot. 
	 * @param force - A vektor amekkora a sebess�ge. 
	 */
	public void setCurrentSpeed(Vector force) {
		this.speed=force;
	};
	
	
	/**
	 * Meg�li a takar�t� robotot. Akkor h�v�dik ha r�l�pnek. 
	 */
	public void die() {
		Vector zero = new Vector(0,0);
		setCurrentSpeed(zero);
		setAlive(false);
	}

	/**
	 * Visszadja az aktu�lis c�lpont hely�t
	 * @return
	 */
	public Point getTarget() {
		return target;
	}
	
	/**
	 * Be�ll�t egy c�lt a takar�t� robotnak
	 * @param target - A c�lpontnak a helye
	 */
	public void setTarget(Point target) {
		this.target = target;
	}

	/**
	 * F�ggv�ny ami a takar�t� robotot friss�ti
	 */
	public void jump() {
		if(this.getPosition()==this.getTarget()) {
			//Game oszt�ly elpusz�tja a targetet, itt m�r csak hib�t kapunk el
			System.out.println("Error! CleanerRobot standing on shit, and it's still alive");
		} else {
			move();
		}
		
	}
	
	/**
	 * Met�dus ami el�re l�pteti a takar�t� robotot.
	 */
	private void move() {
		Point currentPosition = getPosition();
		/**TODO:
		 * Ide kell majd valami logika, ami ellen�rzi, hogy nem megy-e a falnak a robot
		 * nem zuhan-e a szakad�kba. Az se baj ha ezt a Game oszt�ly kezeli.
		 */
		
		// Ha lejebb van az X �s Y tengelyen is
		if( currentPosition.x < target.x && currentPosition.y < target.y) {
			
			setPosition(new Point(currentPosition.x+1 , currentPosition.y+1));
			
		}//Ha X-en feljebb van, de Y-on lejebb
		else if(currentPosition.x > target.x && currentPosition.y < target.y) {
			
			setPosition(new Point(currentPosition.x-1,currentPosition.y+1));
			
		}//Ha mind a k�t r�szen feljebb van
		else if(currentPosition.x > target.x && currentPosition.y > target.y) {
			
			setPosition(new Point(currentPosition.x-1,currentPosition.y-1));
			
		}//Ha X-en lejebb van, de Y-on feljebb
		else {
			
			setPosition(new Point(currentPosition.x+1,currentPosition.y-1));
			
		}
	}

}
