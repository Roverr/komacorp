package Program.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

/**
 * A szkeleton modell mûkédésének szimulálására létrehozott Robot osztály,
 * ami viselkedésében megegyezik a késõbbi robot osztállyal. 
 * @author Rover
 *
 */
public class PlayerRobot extends Robot implements Serializable  {
	
	
	/**
	 * pilot-legyen gazdája/versenyzõje
	 * MapItemCarriedCounter - Map itemek amik még a robotnál vannak.
	 * wantToDrop 
	 * - 0, ha nem szeretne dobni a robot,
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * modSpeed - A sebesség változtatásnak és irány változtatásnak a vektorát tárolja
	 * @author Barna,Rover
	 */
	protected String pilot;
	protected List<Integer> mapItemCarriedCounter;
	private static final long serialVersionUID = -8700911186613988616L;
	public int wantToDrop;
	protected Vector modSpeed = new Vector(0,0);

	/**
	 * Konstruktor a játékosok álltal irányított Robothoz
	 * @author Barna
	 */
	public PlayerRobot() {
		super();
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		/**TODO POSITION + List feltöltés akadályokkal**/
		mapItemCarriedCounter = new ArrayList<Integer>();
		pilot="AutoPilot";
		mapItemCarriedCounter=new ArrayList<Integer>();
		mapItemCarriedCounter.add(3);
		mapItemCarriedCounter.add(3);
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a pályáról ez a függvény hívódik meg. 
	 */
	public void die(Map map) {
		SkeletonUtility.printCall("Die", this);
		mapItemCarriedCounter.clear();
		alive = false;
		if(PrototypeUtility.allowDebug)System.out.println("Felt down!");

		SkeletonUtility.printReturn("Die", this);
	}
	
	/**
	 * Ragacsot dob a pályára ha van, ha nem akkor nem csinál semmit. 
	 * @param map - Pálya amire ledobja
	 */
	public void dropRagacs(Map map) {
		// 3 -szr lehet belelépni
		int stepInCount = 3;
		int ragacsNumberInList = 0;
		int ragacsLeft = mapItemCarriedCounter.get(ragacsNumberInList);
		//Ha több ragacs van még a tárban, dobunk
		if(ragacsLeft >0 ) {
			int ragacsNumberInDropping = 1;
			Ragacs ragacs = new Ragacs(stepInCount,getPosition());
			map.addMapItem(ragacs);
			mapItemCarriedCounter.set(ragacsNumberInList,ragacsLeft-1);
			setWantToDrop(ragacsNumberInDropping);
		} else {
			//Ha már nincs, akkor jelezzük, hogy nem tudunk dobni. 
			int noItemToDrop = 0;
			setWantToDrop(noItemToDrop);
			System.out.println("No ragacs left, sorryka");
		}

		
	}
	
	/**
	 * Sebességet módosítja (ami a következõ ugrást határozza meg)
	 * A sebességvektorhoz hozzáadja a paraméterként kapott vektort
	 * @param force
	 * @author Hunor
	 */
	public void modifySpeed(Vector force) {
		//modSpeed.add(force)
		//Ezt megváltoztattam, szerintem így helyes
		setModSpeed(force);
	}
	
	public Vector getModSpeed(){
		return modSpeed;
	}
	
	public void setModSpeed(Vector ms){
		modSpeed = ms;
	}
	
	public void setPilot(String pilot){
		this.pilot=pilot;
	}
	
	public String getPilot(){
		return this.pilot;
	}
	
	public List<Integer> getItemsContained(){
		return mapItemCarriedCounter;
	}
	
	/**
	 * Olajat dob a pályára
	 * @param map - A pálya ahova dobja
	 */
	public void dropOlaj(Map map) {
		// 10 körig lesz életben az olaj
		int time = 10; 
		int olajNumberInList = 1;
		int olajLeft = mapItemCarriedCounter.get(olajNumberInList);
		
		if(olajLeft > 0) {
			int olajNumberInDropping = 2;
			Olaj olaj = new Olaj(time,getPosition());;
			map.addMapItem(olaj);
			mapItemCarriedCounter.set(olajNumberInList, olajLeft-1);
			setWantToDrop(olajNumberInDropping);
		} else {
			int nothingToDrop = 0;
			setWantToDrop(nothingToDrop);
			System.out.println("No olaj left, sorryka");
		}
	}
	
	/**
	 * A robot frissítõ függvénye
	 * Elugráskor a pozíciója változik a robotnak, a módosítandó sebességvektor(modSpeed)
	 * függvényében.
	 * @param map - Pálya, amire ledobja (dropRagacs/Olaj kapja majd paraméterként) 
	 * @author Hunor
	 */
	public void jump(Map map) {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 * mennyi idõ egy ugrás??
		 *  : 1 run hívás = 1 ugrás.
		 */
		int time=1;
		this.speed.add(this.modSpeed);
		this.setModSpeed(new Vector(0,0));
		//Ha életben van, akkor távolságot számolunk
		if (alive){
			countDistance(time);
			FloatPoint newPosition=new FloatPoint(this.position.getX()+Math.round(time*speed.getX()),this.position.getY()+Math.round(time*speed.getY()));
			this.setPosition(newPosition);
		}
		
		SkeletonUtility.printReturn("Jump", this);
	}
	
	
	
			
			
			



	
	public int newDistance(FloatPoint newPosition){
		return (int)(Math.sqrt(newPosition.getX()*newPosition.getX()+newPosition.getY()*newPosition.getY()));
	}
	
	/**
	 * Visszadja a bitet ami jelzi, hogy a robot szeretne akadályt dobni
	 * @return 
	 * - 0, ha a robot nem szeretne dobni
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * DEV:Minden más értéket errorként elkapunk.
	 */
	public int getWantToDrop() {
		return wantToDrop;
	}
	
	/**
	 * Beállítja a wantToDrop értékét. 
	 * @param to - Az érték, amire szeretnénk állítani
	 */
	public void setWantToDrop(int to) {
		wantToDrop = to;
	}
	/**Lekezeli a PlayerRobot ütközését valami mással
	 * @author Barna
	 */
	@Override
	public void collide(Robot robot,Map map,boolean thesame) {
		// TODO Auto-generated method stub
		if(thesame){
			if(this.speed.length()<robot.getSpeed().length())
				this.die(map);
			else this.setSpeed(Vector.average(this.speed,robot.getSpeed()));
		} else {
			robot.die(map);
		}
		
	}
	
	
}
