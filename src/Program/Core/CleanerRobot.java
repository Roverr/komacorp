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
 * A takarító robotok osztálya, amik Robot leszármazottak, de nem lehet a
 * sebességüket módosítani, illetve teljes mértékben user független a
 * vezérlésük.
 * 
 * @author Rover
 *
 */
public class CleanerRobot extends Robot implements Serializable, Drawable {

	enum CleanerState {
		cleaning, //Akkor ha a folton áll, és takarítja
		moving,  //Akkor, ha a legközelebbi folthoz megy
		drifting, // Akkor, ha nem a legközelebbi folthoz megy, mert ütközött.
		waiting // akkor, ha nincs semmi folt a pályán
	}

	/**
	 * Azért felelõs változó, hogy mennyi idõ van hátra az akadály takarításból.
	 */
	private int remainingClean = 0;
	// Mit csinál a robot takarít, vagy a következõ oljfolthoz megy.
	CleanerState state;
	//A kiválasztott Pont amit takarít.
	private FloatPoint target;
	//A robot képe
	BufferedImage cleanerImage;
	
	
	private static final long serialVersionUID = 2858679422774498028L;

	public CleanerRobot(FloatPoint f , Map m) {
		position = f;
		setNewTarget(m, false);
		
		//Képének betöltése
		try {
			cleanerImage = ImageIO.read(new File("assets\\ingame\\CleanerRobot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}

	public CleanerState getState() {
		return this.state;
	}


	public void setState(CleanerState state) {
		this.state = state;
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
	 * Megöli a takarító robotot. Akkor hívódik ha rálépnek.
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
	 * Függvény ami a takarító robotot frissíti
	 */
	public void jump(Map map) {
		position.add(speed);
		boolean isItThere = (position.distance(target) < 1f);
		if (target != null && isItThere) {
			speed = new Vector(0f, 0f);
			// Akadályra érkezés
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
	 * Beállít egy új targetet a Cleanernek. A mapItemek listájából csak a szabad (nem áll rajta cleaner)
	 * - választja ki
	 * Beállítja az új sebességet.
	 * @param map - Honnan választja ki
	 * @param ignoreClosest - ignorálja az egyik
	 */
	private void setNewTarget(Map m, boolean ignoreClosest) {
		target = nextTarget(m, false);
		if(PrototypeUtility.allowDebug)System.out.println("Setting a new Target. " + ignoreClosest);

		if(target == null){
			//Ha nem talált jó mapItemet, akkor várakozik. Következõ runnál újra próbálja.
			state = CleanerState.waiting;
			setCurrentSpeed(new Vector(0f,0f));
		}else{
			if(PrototypeUtility.allowDebug)System.out.println("Found target: " + target.getX() + "," + target.getY());
			if(ignoreClosest){
				//Ha talált mapItemet, akkor mozgásba lendül
				state= CleanerState.drifting;
			}else{
				state = CleanerState.moving;
			}
			
			//A sebességet mindenképpen 1 hosszúságúra kell beállítani.
			if(PrototypeUtility.allowDebug)System.out.println("Vec: " 
						+ (target.getX() - position.getX()) + "," + (target.getY()- position.getY()));
			
			Vector sp = new Vector(target.getX() - position.getX(), target.getY()- position.getY());
			sp.normalize();
			setCurrentSpeed(sp);
		}
	}

	/**
	 * Kijelöli a következõ célpontot,azt a mapitemet ami a legközelebb van
	 * 
	 */
	private FloatPoint nextTarget(Map map, boolean ignoreClosest) {
		//Ha null-al tér vissza, az azt jelenti hogy nem talált mapItemet.
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
		
		//Egyszerûen végigfutunk a listán, mindíg a legközelebbi item sorszámát elmentve.
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
		//Ha ütközik éppen, tehát irányt vált.
		//Ha csak egy ragacs van, akkor mégis megpróbálja azt.
		if (ignoreClosest) {
			if( list.size() <= 1){
				return null;
			}
			int secondClosestId = (closestId == 0)?1:0;
			minlength = list.get(0).getPosition().distance(position);
			//Végigfutunk a listán, csak most kihagyjuk a legközelebbit.
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
	 * Lekezeli a kisrobot ütközését valami mással ha másik kisrobottal ütköznek
	 * össze, akkor megkergülnek, és véletlenszerûen császkálnak az egyik random
	 * folt felé
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
	 * Megkeresi az adott cél objektumot a MapItemek között.
	 * 
	 * @param map
	 * @return - A célnak kijelölt takarítandó objektum
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
		//TODO koordinátakonvertálás
		g2.drawImage(cleanerImage, 0, 0, null);
	}

}
