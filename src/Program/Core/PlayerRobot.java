package Program.Core;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Program.Helpers.Vector;

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
	private int wantToDrop;
	protected Vector modSpeed;

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
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a pályáról ez a függvény hívódik meg. 
	 */
	public void die() {
		SkeletonUtility.printCall("Die", this);
		mapItemCarriedCounter.clear();
		alive = false;
		//System.out.println("Felt down!");

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
		modSpeed.add(force);
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
	public void jump() {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 * mennyi idõ egy ugrás??
		 */
		int time=10;
		this.speed.add(this.modSpeed);
		//Ha életben van, akkor távolságot számolunk
		if (alive){
			countDistance(time);
		}
		this.position.x+=Math.round(time*speed.getX());
		this.position.y+=Math.round(time*speed.getY());
		
		SkeletonUtility.printReturn("Jump", this);}
	public void jump(Map map) {
		if (this.isAlive()){
			SkeletonUtility.printCall("Jump", this);
			int time=10;
			this.speed.add(this.modSpeed);
			/*TODO 
			 * 1) Miért a Point-et használjuk a pozícióra?
			 * Miért nem használjuk a Vector-t, ha már megírtuk? 
			 * Pozíciót csak int pontosságra tárolunk? (Mert a point az int pontosságú, ezért
			 * kell kasztolnom egy csomót, mert a vektorunk meg double)
			 * Nem utolsó sorban hozzá se tudok adni a pozícióhoz vektort (nem ez lett
			 * volna a modSpeed lényege?).
			 * 2) Miért van a doksiban egy move függvény? Nem az ugráskor mozdul el a robot?*/
			
			/*Lekérdezem az aktuális pozíciót*/
			int x = (int) this.getPosition().getX();
			int y = (int) this.getPosition().getY();
			Point oldPosition = new Point(x, y);
			/*Ha dobni akar valamit (1 = ragacs, 2 = olaj), akkor ledobja*/
			if (wantToDrop == 1)
				dropRagacs(map);
			else if (wantToDrop == 2)
				dropOlaj(map);
			
			/*Meghatározom az új pozíciót (hozzáadódik a mostanihoz a sebességvektor)*/
			x += modSpeed.getX();
			y += modSpeed.getY();
			Point newPosition = new Point(x, y);
			
			/*Módosul a pozíció*/
			this.setPosition(newPosition);
			map.isCheckPointChecked(this, oldPosition);
			
			SkeletonUtility.printReturn("Jump", this);
		}

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
	
	
}
