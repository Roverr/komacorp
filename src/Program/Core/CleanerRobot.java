package Program.Core;

import java.awt.Point;
import java.io.Serializable;

import Program.Helpers.Vector;

/**
 * A takarító robotok osztálya, amik Robot leszármazottak, de nem lehet a sebességüket
 * módosítani, illetve teljes mértékben user független a vezérlésük.
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
	 * Beállítja az aktuális gyorsaságát a robotnak. A konstruktorban hívódik,
	 * illetve ha meghal a robot. 
	 * @param force - A vektor amekkora a sebessége. 
	 */
	public void setCurrentSpeed(Vector force) {
		this.speed=force;
	};
	
	
	/**
	 * Megöli a takarító robotot. Akkor hívódik ha rálépnek. 
	 */
	public void die() {
		Vector zero = new Vector(0,0);
		setCurrentSpeed(zero);
		setAlive(false);
	}

	/**
	 * Visszadja az aktuális célpont helyét
	 * @return
	 */
	public Point getTarget() {
		return target;
	}
	
	/**
	 * Beállít egy célt a takarító robotnak
	 * @param target - A célpontnak a helye
	 */
	public void setTarget(Point target) {
		this.target = target;
	}

	/**
	 * Függvény ami a takarító robotot frissíti
	 */
	public void jump() {
		if(this.getPosition()==this.getTarget()) {
			//Game osztály elpuszítja a targetet, itt már csak hibát kapunk el
			System.out.println("Error! CleanerRobot standing on shit, and it's still alive");
		} else {
			move();
		}
		
	}
	
	/**
	 * Metódus ami elõre lépteti a takarító robotot.
	 */
	private void move() {
		Point currentPosition = getPosition();
		/**TODO:
		 * Ide kell majd valami logika, ami ellenõrzi, hogy nem megy-e a falnak a robot
		 * nem zuhan-e a szakadékba. Az se baj ha ezt a Game osztály kezeli.
		 */
		
		// Ha lejebb van az X és Y tengelyen is
		if( currentPosition.x < target.x && currentPosition.y < target.y) {
			
			setPosition(new Point(currentPosition.x+1 , currentPosition.y+1));
			
		}//Ha X-en feljebb van, de Y-on lejebb
		else if(currentPosition.x > target.x && currentPosition.y < target.y) {
			
			setPosition(new Point(currentPosition.x-1,currentPosition.y+1));
			
		}//Ha mind a két részen feljebb van
		else if(currentPosition.x > target.x && currentPosition.y > target.y) {
			
			setPosition(new Point(currentPosition.x-1,currentPosition.y-1));
			
		}//Ha X-en lejebb van, de Y-on feljebb
		else {
			
			setPosition(new Point(currentPosition.x+1,currentPosition.y-1));
			
		}
	}

}
