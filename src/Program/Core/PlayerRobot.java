package Program.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;
import Program.Prototype.PrototypeUtility;
import Program.Skeleton.SkeletonUtility;

/**
 * A szkeleton modell m�k�d�s�nek szimul�l�s�ra l�trehozott Robot oszt�ly,
 * ami viselked�s�ben megegyezik a k�s�bbi robot oszt�llyal. 
 * @author Rover
 *
 */
public class PlayerRobot extends Robot implements Serializable  {
	
	
	/**
	 * pilot-legyen gazd�ja/versenyz�je
	 * MapItemCarriedCounter - Map itemek amik m�g a robotn�l vannak.
	 * wantToDrop 
	 * - 0, ha nem szeretne dobni a robot,
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * modSpeed - A sebess�g v�ltoztat�snak �s ir�ny v�ltoztat�snak a vektor�t t�rolja
	 * @author Barna,Rover
	 */
	protected String pilot;
	protected List<Integer> mapItemCarriedCounter;
	private static final long serialVersionUID = -8700911186613988616L;
	public int wantToDrop;
	protected Vector modSpeed = new Vector(0,0);

	/**
	 * Konstruktor a j�t�kosok �lltal ir�ny�tott Robothoz
	 * @author Barna
	 */
	public PlayerRobot() {
		super();
		//SKELETON PART
		SkeletonUtility.addClass(this, "Robot" + SkeletonUtility.robotCounter);
		SkeletonUtility.robotCounter++;
		SkeletonUtility.printCall("create Robot", this);
		//SKELETON PART
		/**TODO POSITION + List felt�lt�s akad�lyokkal**/
		mapItemCarriedCounter = new ArrayList<Integer>();
		pilot="AutoPilot";
		mapItemCarriedCounter=new ArrayList<Integer>();
		mapItemCarriedCounter.add(3);
		mapItemCarriedCounter.add(3);
		SkeletonUtility.printReturn("create Robot", this);
	}
	
	/**
	 * Ha a robot leesik a p�ly�r�l ez a f�ggv�ny h�v�dik meg. 
	 */
	public void die(Map map) {
		SkeletonUtility.printCall("Die", this);
		mapItemCarriedCounter.clear();
		alive = false;
		if(PrototypeUtility.allowDebug)System.out.println("Felt down!");

		SkeletonUtility.printReturn("Die", this);
	}
	
	/**
	 * Ragacsot dob a p�ly�ra ha van, ha nem akkor nem csin�l semmit. 
	 * @param map - P�lya amire ledobja
	 */
	public void dropRagacs(Map map) {
		// 3 -szr lehet belel�pni
		int stepInCount = 3;
		int ragacsNumberInList = 0;
		int ragacsLeft = mapItemCarriedCounter.get(ragacsNumberInList);
		//Ha t�bb ragacs van m�g a t�rban, dobunk
		if(ragacsLeft >0 ) {
			int ragacsNumberInDropping = 1;
			Ragacs ragacs = new Ragacs(stepInCount,getPosition());
			map.addMapItem(ragacs);
			mapItemCarriedCounter.set(ragacsNumberInList,ragacsLeft-1);
			setWantToDrop(ragacsNumberInDropping);
		} else {
			//Ha m�r nincs, akkor jelezz�k, hogy nem tudunk dobni. 
			int noItemToDrop = 0;
			setWantToDrop(noItemToDrop);
			System.out.println("No ragacs left, sorryka");
		}

		
	}
	
	/**
	 * Sebess�get m�dos�tja (ami a k�vetkez� ugr�st hat�rozza meg)
	 * A sebess�gvektorhoz hozz�adja a param�terk�nt kapott vektort
	 * @param force
	 * @author Hunor
	 */
	public void modifySpeed(Vector force) {
		//modSpeed.add(force)
		//Ezt megv�ltoztattam, szerintem �gy helyes
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
	 * Olajat dob a p�ly�ra
	 * @param map - A p�lya ahova dobja
	 */
	public void dropOlaj(Map map) {
		// 10 k�rig lesz �letben az olaj
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
	 * A robot friss�t� f�ggv�nye
	 * Elugr�skor a poz�ci�ja v�ltozik a robotnak, a m�dos�tand� sebess�gvektor(modSpeed)
	 * f�ggv�ny�ben.
	 * @param map - P�lya, amire ledobja (dropRagacs/Olaj kapja majd param�terk�nt) 
	 * @author Hunor
	 */
	public void jump(Map map) {
		SkeletonUtility.printCall("Jump", this);
		/**
		 * TODO 
		 * mennyi id� egy ugr�s??
		 *  : 1 run h�v�s = 1 ugr�s.
		 */
		int time=1;
		this.speed.add(this.modSpeed);
		this.setModSpeed(new Vector(0,0));
		//Ha �letben van, akkor t�vols�got sz�molunk
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
	 * Visszadja a bitet ami jelzi, hogy a robot szeretne akad�lyt dobni
	 * @return 
	 * - 0, ha a robot nem szeretne dobni
	 * - 1, ha a robot ragacsot szeretne dobni
	 * - 2, ha a robot olajat szeretne dobni
	 * DEV:Minden m�s �rt�ket errork�nt elkapunk.
	 */
	public int getWantToDrop() {
		return wantToDrop;
	}
	
	/**
	 * Be�ll�tja a wantToDrop �rt�k�t. 
	 * @param to - Az �rt�k, amire szeretn�nk �ll�tani
	 */
	public void setWantToDrop(int to) {
		wantToDrop = to;
	}
	/**Lekezeli a PlayerRobot �tk�z�s�t valami m�ssal
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
