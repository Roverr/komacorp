package Program.Core;

import java.io.Serializable;
import java.util.Random;

import Program.Core.MapItem.CleaningState;
import Program.Helpers.FloatPoint;
import Program.Helpers.Line;
import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;

/**
 * A takarító robotok osztálya, amik Robot leszármazottak, de nem lehet a
 * sebességüket módosítani, illetve teljes mértékben user független a
 * vezérlésük.
 * 
 * @author Rover
 *
 */
public class CleanerRobot extends Robot implements Serializable {

	enum CleanerState {
		cleaning, moving, waiting // akkor, ha nincs semmi folt a pályán
	}

	/**
	 * Azért felelõs változó, hogy a robot takarításnál mit változtatott.
	 */
	private int remainingClean = 0;
	// Mit csinál a robot takarít, vagy a következõ oljfolthoz megy.
	CleanerState state;

	private FloatPoint target;

	private static final long serialVersionUID = 2858679422774498028L;
	

	public CleanerRobot(Map m) {
		target = nextTarget(m, "normal");
		state = CleanerState.moving;
		Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
		sp.normalize();
		setCurrentSpeed(sp);
	}

	public CleanerRobot(MapItem trg) {
		if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot did jump?");
		setTarget(trg.getPosition());
		state = CleanerState.moving;
		Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
		sp.normalize();
		setCurrentSpeed(sp);
	}

	/**
	 * Beállítja az aktuális gyorsaságát a robotnak. A konstruktorban hívódik,
	 * illetve ha meghal a robot.
	 * 
	 * @param force
	 *            - A vektor amekkora a sebessége.
	 */
	public void setCurrentSpeed(Vector force) {
		this.speed = force;
	};

	/**
	 * Megöli a takarító robotot. Akkor hívódik ha rálépnek.
	 */
	public void die(Map map) {
		Vector zero = new Vector(0, 0);
		setCurrentSpeed(zero);
		setAlive(false);
		Olaj olaj = new Olaj(11, this.position);
		PrototypeUtility.addClass(olaj, "olaj"+Game.olajId);
		Game.olajId++;
		map.addMapItem(olaj); // ;)
	}

	/**
	 * Visszadja az aktuális célpont helyét
	 * 
	 * @return
	 */
	public FloatPoint getTarget() {
		return target;
	}

	/**
	 * Beállít egy célt a takarító robotnak
	 * 
	 * @param target
	 *            - A célpontnak a helye
	 */
	public void setTarget(FloatPoint target) {
		this.target = target;
	}

	/**
	 * Függvény ami a takarító robotot frissíti
	 */
	public void jump(Map map) {
		int roundItTakesToClean = 2;
		position.add(speed);
		boolean isItThere = (position.distance(target) < 1f);
		if (isItThere) {
			speed = new Vector(0f,0f);
			// Akadályra érkezés
			if (state == CleanerState.moving) {
				state = CleanerState.cleaning;
				remainingClean = roundItTakesToClean;
				MapItem myTarget = findMyTarget(map);
				myTarget.state = CleaningState.beingCleaned;
			} else {
				if (remainingClean <= 0) {
					target = nextTarget(map, "");// VÁLTOZÁS! MIUTÁN
															// VÉGZETT MEGY A
															// KÖVETKEZÕHÖZ
					Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
					sp.normalize();
					setCurrentSpeed(sp);
					//Sebesség beállítása, hogy 1 hosszú legyen;
					if (target.equals(position)) {
						state = CleanerState.waiting;
					} else{
						state = CleanerState.moving;
					}
					for (MapItem mI : map.getMapItems()) {
						boolean isItTarget = (mI.getPosition().getX() == getTarget().getX() && mI.getPosition().getY() == getTarget().getY());
						if (isItTarget) {
							map.getMapItems().remove(mI);
							System.out.println("Removed " + getNameFromType(mI));
						}
					}
				} else {
					remainingClean--;
				}
			}
			// Game osztály elpuszítja a targetet, itt már csak hibát kapunk el
			if(PrototypeUtility.allowDebug)System.out
					.println("Error! CleanerRobot standing on shit, and it's still alive");
		} else {
		}

	}
	
	/**
	 * Hacking........ Get name from Class's file name. 
	 * @param mi
	 * @return
	 */
	private String getNameFromType(MapItem mi){
		if(mi.toString().contains("Olaj")){
			return "Olaj";
		} else {
			return "Ragacs";
		}
	}

	
	/**
	 * Kijelöli a következõ célpontot,azt a mapitemet ami a legközelebb van
	 * 
	 */
	private FloatPoint nextTarget(Map map, String mode) {
		Line line = new Line(this.position.getX(), this.position.getY(), 0, 0);
		FloatPoint hova = new FloatPoint(this.position.getX(), this.position.getY());
		double minlength = 1000000;
		if (mode.equals("abnormal")) {// ez az irányváltoztatáshoz kell, nem
										// definiált irányba mennek tovább
			int meret = map.getMapItems().size();
			if(meret > 0) {
			Random random = new Random();
			int index = random.nextInt(meret);
			hova = map.getMapItems().get(index).getPosition();
			} else {
				if(getTarget() != null) {
					hova = getTarget();
				} else {
					hova = new FloatPoint(0,0);
				}
			}
		} else {
			for (MapItem i : map.getMapItems()) {
				line.setX2(i.getPosition().getX());
				line.setY2(i.getPosition().getY());
				// rövidebb az út és olaj van ott
				if (minlength > line.length()){
					minlength = line.length();
					hova = i.getPosition();
				}
			}
		}
		return hova;
	}

	/**
	 * Lekezeli a kisrobot ütközését valami mással ha másik kisrobottal ütköznek
	 * össze, akkor megkergülnek, és véletlenszerûen császkálnak az egyik random
	 * folt felé
	 * 
	 * @author Barna
	 */
	@Override
	public void collide(Robot robot, Map map, boolean thesame) {
		if (thesame) {
			MapItem myTarget = findMyTarget(map);
			if(myTarget!=null){
			if(myTarget.state == CleaningState.beingCleaned && remainingClean==0) {
				target = nextTarget(map,"abnormal");
			}
			} else {
				target = nextTarget(map,"abnormal");
			}
		} else {
			this.die(map);
			map.addMapItem(new Olaj(this.position));
		}
	}
	
	/**
	 * Megkeresi az adott cél objektumot a MapItemek között. 
	 * @param map
	 * @return - A célnak kijelölt takarítandó objektum
	 */
	private MapItem findMyTarget(Map map) {
		for(MapItem mI : map.getMapItems()) {
			boolean isItTarget = (mI.getPosition().getX() == target.getX() && mI.getPosition().getY() == target.getY());
			if(isItTarget) {
				return mI;
			}
		}
		return null;
	}

}
