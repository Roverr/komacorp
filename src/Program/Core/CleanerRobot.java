package Program.Core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

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
public class CleanerRobot extends Robot implements Serializable, Drawable {

	enum CleanerState {
		cleaning, //Akkor ha a folton �ll, �s takar�tja
		moving,  //Akkor, ha a legk�zelebbi folthoz megy
		drifting, // Akkor, ha nem a legk�zelebbi folthoz megy, mert �tk�z�tt.
		waiting // akkor, ha nincs semmi folt a p�ly�n
	}

	/**
	 * Az�rt felel�s v�ltoz�, hogy mennyi id� van h�tra az akad�ly takar�t�sb�l.
	 */
	private int remainingClean = 0;
	// Mit csin�l a robot takar�t, vagy a k�vetkez� oljfolthoz megy.
	CleanerState state;
	//A kiv�lasztott Pont amit takar�t.
	private FloatPoint target;
	//A robot k�pe
	BufferedImage cleanerImage;
	
	
	private static final long serialVersionUID = 2858679422774498028L;

	public CleanerRobot(FloatPoint f , Map m) {
		position = f;
		setNewTarget(m, false);
		
		//K�p�nek bet�lt�se
		try {
			cleanerImage = ImageIO.read(new File("assets\\ingame\\CleanerRobot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}

	public CleanerState getState() {
		return this.state;
	}


	public void setState(CleanerState state) {
		this.state = state;
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
	 * Meg�li a takar�t� robotot. Akkor h�v�dik ha r�l�pnek.
	 */
	public void die(Map map) {
		Vector zero = new Vector(0, 0);
		setCurrentSpeed(zero);
		setAlive(false);
		Olaj olaj = new Olaj(11, this.position);
		PrototypeUtility.addClass(olaj, "olaj" + Game.olajId);
		Game.olajId++;
		map.addMapItem(olaj); // ;)
	}

	/**
	 * F�ggv�ny ami a takar�t� robotot friss�ti
	 */
	public void jump(Map map) {
		position.add(speed);
		boolean isItThere = (position.distance(target) < 1f);
		if (target != null && isItThere) {
			speed = new Vector(0f, 0f);
			// Akad�lyra �rkez�s
			if (state == CleanerState.moving || state==CleanerState.drifting) {
				state = CleanerState.cleaning;
				remainingClean = 2;
				if(PrototypeUtility.allowDebug)System.out.println("We finally arrived!");
				MapItem mi = findMyTarget(map);
				if(mi != null){
					mi.setCleaningState(CleaningState.beingCleaned);
				}

			} else if(state == CleanerState.cleaning){
				remainingClean--;
				if (remainingClean <= 0) {
					MapItem mi = findMyTarget(map);
					if(mi != null){
						map.removeMapItem(mi);
					}
					
					
					setNewTarget(map, false);
					
				}
			}
		}else{
			if(state == CleanerState.moving || state == CleanerState.waiting){
				setNewTarget(map, false);
			
			}else if(state==CleanerState.drifting){
				if(PrototypeUtility.allowDebug)System.out.println("This just happened.... !!!");
				setNewTarget(map, true);
			}
		}

	}

	/**
	 * Be�ll�t egy �j targetet a Cleanernek. A mapItemek list�j�b�l csak a szabad (nem �ll rajta cleaner)
	 * - v�lasztja ki
	 * Be�ll�tja az �j sebess�get.
	 * @param map - Honnan v�lasztja ki
	 * @param ignoreClosest - ignor�lja az egyik
	 */
	private void setNewTarget(Map m, boolean ignoreClosest) {
		target = nextTarget(m, false);
		if(PrototypeUtility.allowDebug)System.out.println("Setting a new Target. " + ignoreClosest);

		if(target == null){
			//Ha nem tal�lt j� mapItemet, akkor v�rakozik. K�vetkez� runn�l �jra pr�b�lja.
			state = CleanerState.waiting;
			setCurrentSpeed(new Vector(0f,0f));
		}else{
			if(PrototypeUtility.allowDebug)System.out.println("Found target: " + target.getX() + "," + target.getY());
			if(ignoreClosest){
				//Ha tal�lt mapItemet, akkor mozg�sba lend�l
				state= CleanerState.drifting;
			}else{
				state = CleanerState.moving;
			}
			
			//A sebess�get mindenk�ppen 1 hossz�s�g�ra kell be�ll�tani.
			if(PrototypeUtility.allowDebug)System.out.println("Vec: " 
						+ (target.getX() - position.getX()) + "," + (target.getY()- position.getY()));
			
			Vector sp = new Vector(target.getX() - position.getX(), target.getY()- position.getY());
			sp.normalize();
			setCurrentSpeed(sp);
		}
	}

	/**
	 * Kijel�li a k�vetkez� c�lpontot,azt a mapitemet ami a legk�zelebb van
	 * 
	 */
	private FloatPoint nextTarget(Map map, boolean ignoreClosest) {
		//Ha null-al t�r vissza, az azt jelenti hogy nem tal�lt mapItemet.
		FloatPoint hova = null;
		if(map.getMapItems().size() == 0){
			return null;
		}
		
		List<MapItem> list = map.getMapItems();
		int id = 0;
		//Searching for the first VIABLE mapItem
		while(list.size() > id && (!(list.get(id).getState() == CleaningState.canBeCleaned)) ){
			id++;
		}
		if(id == list.size()){
			return null;
		}
		double minlength = list.get(id).getPosition().distance(position);
		int closestId = id;
		
		//Egyszer�en v�gigfutunk a list�n, mind�g a legk�zelebbi item sorsz�m�t elmentve.
		for (int i = 1; i < list.size(); i++) {
			if(list.get(i).getState() == CleaningState.canBeCleaned){
				double dist = list.get(i).getPosition().distance(position);
				if(dist < minlength){
					minlength = dist;
					closestId = i;	
				}
			}
		}
		
		hova = list.get(closestId).getPosition();
		//Ha �tk�zik �ppen, teh�t ir�nyt v�lt.
		//Ha csak egy ragacs van, akkor m�gis megpr�b�lja azt.
		if (ignoreClosest) {
			if( list.size() <= 1){
				return null;
			}
			int secondClosestId = (closestId == 0)?1:0;
			minlength = list.get(0).getPosition().distance(position);
			//V�gigfutunk a list�n, csak most kihagyjuk a legk�zelebbit.
			for (int i = 1; i < list.size(); i++) {
				if(list.get(i).getState() == CleaningState.canBeCleaned && closestId != i){
					double dist = list.get(i).getPosition().distance(position);
					if(dist < minlength){
						minlength = dist;
						secondClosestId = i;	
					}
				}
			}
			hova = list.get(secondClosestId).getPosition();
			
		}
		return hova;
	}

	/**
	 * Lekezeli a kisrobot �tk�z�s�t valami m�ssal ha m�sik kisrobottal �tk�znek
	 * �ssze, akkor megkerg�lnek, �s v�letlenszer�en cs�szk�lnak az egyik random
	 * folt fel�
	 * 
	 * @author Barna
	 * 
	 */
	@Override
	public void collide(Robot robot, Map map, boolean thesame) {
		if (thesame) {
			setNewTarget(map, true);
		} else {
			this.die(map);
			map.addMapItem(new Olaj(this.position));
		}
	}

	/**
	 * Megkeresi az adott c�l objektumot a MapItemek k�z�tt.
	 * 
	 * @param map
	 * @return - A c�lnak kijel�lt takar�tand� objektum
	 */
	private MapItem findMyTarget(Map map) {
		for (MapItem mI : map.getMapItems()) {
				boolean isItTarget = (mI.position.distance(target) <= 1.02);
				if (isItTarget) {
					return mI;
				}
		}
		return null;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//TODO koordin�takonvert�l�s
		g2.drawImage(cleanerImage, 0, 0, null);
	}

}
