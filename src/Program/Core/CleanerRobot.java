package Program.Core;

import java.io.Serializable;
import java.util.Random;

import Program.Core.MapItem.CleaningState;
import Program.Helpers.FloatPoint;
import Program.Helpers.Line;
import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;

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
	private int remainingClean = 0;
	// Mit csin�l a robot takar�t, vagy a k�vetkez� oljfolthoz megy.
	CleanerState state;

	private FloatPoint target;

	private static final long serialVersionUID = 2858679422774498028L;
	
	//TODO A normal way to get the active Map, and thus the closest target.

	public CleanerRobot(Map m) {
		target = nextTarget(m, "normal");
		state = CleanerState.moving;
		Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
		sp.setX((float) (sp.getX()/sp.length())); 
		sp.setY((float) (sp.getY()/sp.length())); 
		setCurrentSpeed(sp);
	}

	public CleanerRobot(MapItem trg) {
		setTarget(trg.getPosition());
		Vector speedConst = new Vector(0, 1);
		setCurrentSpeed(speedConst);
		state = CleanerState.moving;
		Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
		sp.setX((float) (sp.getX()/sp.length())); 
		sp.setY((float) (sp.getY()/sp.length())); 
		setCurrentSpeed(sp);
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
	public FloatPoint getTarget() {
		return target;
	}

	/**
	 * Be�ll�t egy c�lt a takar�t� robotnak
	 * 
	 * @param target
	 *            - A c�lpontnak a helye
	 */
	public void setTarget(FloatPoint target) {
		this.target = target;
	}

	/**
	 * F�ggv�ny ami a takar�t� robotot friss�ti
	 */
	public void jump(Map map) {
		int roundItTakesToClean = 2;
		position.add(speed);
		//TODO Position detection based on distance.
		//TODO Moving = speed vektor a target ir�ny�ba.
		boolean isItThere = (position.distance(target) < 1f);
		if (isItThere) {
			speed = new Vector(0f,0f);
			// Akad�lyra �rkez�s
			if (state == CleanerState.moving) {
				state = CleanerState.cleaning;
				remainingClean = roundItTakesToClean;
				MapItem myTarget = findMyTarget(map);
				myTarget.state = CleaningState.beingCleaned;
			} else {
				if (remainingClean <= 0) {
					target = nextTarget(map, "");// V�LTOZ�S! MIUT�N
															// V�GZETT MEGY A
															// K�VETKEZ�H�Z
					Vector sp = new Vector(target.getX()- position.getX(), target.getY()- position.getY());
					sp.setX((float) (sp.getX()/sp.length())); 
					sp.setY((float) (sp.getY()/sp.length())); 
					setCurrentSpeed(sp);
					//Sebess�g be�ll�t�sa, hogy 1 hossz� legyen;
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
					//move();
				} else {
					remainingClean--;
				}
			}
			// Game oszt�ly elpusz�tja a targetet, itt m�r csak hib�t kapunk el
			/*if(PrototypeUtility.allowDebug)System.out
					.println("Error! CleanerRobot standing on shit, and it's still alive");*/
		} else {
			//move();
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
	 * Met�dus ami el�re l�pteti a takar�t� robotot.
	 */
	private void move() {
		FloatPoint currentPosition = getPosition();
		
		//TODO Ez itt hib�san m�k�dik.
		//Pl mivan akkor ha egyenl� az egyik kordin�ta?
		
		
		
/*
		// Ha lejebb van az X tengelyen 
		if (currentPosition.getX() < target.getX()) {
			setPosition(new FloatPoint(currentPosition.getX() + 1, currentPosition.getY()));
			return;
		}
		// Ha X-en feljebb van
		if (currentPosition.getX() > target.getX() ) {
			setPosition(new FloatPoint(currentPosition.getX() - 1, currentPosition.getY() ));
			return;

		}// Ha Y szerint lentebb van
		else if (currentPosition.getY() > target.getY() ) {

			setPosition(new FloatPoint(currentPosition.getX(), currentPosition.getY()-1 ));
			return;

		}// Ha Y szerint fentebb van
		else if (currentPosition.getY() < target.getY()){
			setPosition(new FloatPoint(currentPosition.getX() , currentPosition.getY() + 1));
		}*/
	}
	
	/**
	 * Kijel�li a k�vetkez� c�lpontot,azt a mapitemet ami a legk�zelebb van
	 * 
	 */
	private FloatPoint nextTarget(Map map, String mode) {
		Line line = new Line(this.position.getX(), this.position.getY(), 0, 0);
		FloatPoint hova = new FloatPoint(this.position.getX(), this.position.getY());
		double minlength = 1000000;
		if (mode.equals("abnormal")) {// ez az ir�nyv�ltoztat�shoz kell, nem
										// defini�lt ir�nyba mennek tov�bb
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
	public void collide(Robot robot, Map map, boolean thesame) {
		if (thesame) {
			MapItem myTarget = findMyTarget(map);
			if(myTarget.state == CleaningState.beingCleaned && remainingClean==0) {
				target = nextTarget(map,"abnormal");
			}
		} else {
			this.die(map);
			map.addMapItem(new Olaj(this.position));
		}
	}
	
	/**
	 * Megkeresi az adott c�l objektumot a MapItemek k�z�tt. 
	 * @param map
	 * @return - A c�lnak kijel�lt takar�tand� objektum
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
