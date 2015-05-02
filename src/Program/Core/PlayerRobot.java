package Program.Core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
public class PlayerRobot extends Robot implements Serializable, Drawable  {
	
	
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
	private BufferedImage playerImage;

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
		
		/*Robot képének inicializálása*/
		try {
			playerImage = ImageIO.read(new File("assets\\ingame\\PlayerRobot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * Sebességet módosítja (ami a következõ ugrást határozza meg)
	 * A sebességvektorhoz hozzáadja a paraméterként kapott vektort,
	 * majd a hosszát egységnyire változtatja (normalizálja)
	 * @param force
	 * @author Hunor
	 */
	public void modifySpeed(Vector force) {
		modSpeed.add(force);
		modSpeed.normalize();
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
		int time = 11; //Plusz 1 kell, mert az elsõ run is öregíti már. 
		int olajNumberInList = 1;
		int olajLeft = mapItemCarriedCounter.get(olajNumberInList);
		
		if(olajLeft > 0) {
			int olajNumberInDropping = 2;
			Olaj olaj = new Olaj(time,getPosition());
			PrototypeUtility.addClass(olaj, "olaj"+Game.olajId);
			Game.olajId++;
			map.addMapItem(olaj);
			if(PrototypeUtility.allowDebug)System.out.println("Olaj Deployed");
			mapItemCarriedCounter.set(olajNumberInList, olajLeft-1);
			wantToDrop = 0;
		} else {
			if(PrototypeUtility.allowDebug)System.out.println("No olaj left, sorryka");
		}
	}

	
	/**
	 * Ragacsot dob a pályára ha van, ha nem akkor nem csinál semmit. 
	 * @param map - Pálya amire ledobja
	 */
	public void dropRagacs(Map map) {
		// 3 -szor lehet belelépni
		int stepInCount = 3;
		int ragacsNumberInList = 0;
		int ragacsLeft = mapItemCarriedCounter.get(ragacsNumberInList);
		//Ha több ragacs van még a tárban, dobunk
		if(ragacsLeft >0 ) {
			int ragacsNumberInDropping = 1;
			Ragacs ragacs = new Ragacs(stepInCount,getPosition());
			PrototypeUtility.addClass(ragacs, "ragacs"+Game.ragacsId);
			Game.ragacsId++;
			map.addMapItem(ragacs);
			mapItemCarriedCounter.set(ragacsNumberInList, ragacsLeft-1);
			setWantToDrop(0);
		} else {
			//Ha már nincs, akkor jelezzük, hogy nem tudunk dobni. 
			if(PrototypeUtility.allowDebug)System.out.println("No ragacs left, sorryka");
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
		int time=1;
		this.speed.add(this.modSpeed);
		this.setModSpeed(new Vector(0,0));
		//Ha életben van, akkor távolságot számolunk
		if (alive){
			distance += speed.length();
			this.setPosition(new FloatPoint(position.getX()+speed.getX() , position.getY()+speed.getY()  ));
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
			if(this.speed.length()<robot.getSpeed().length()){
				this.die(map);
				//this.speed = new Vector(0,0);
			}
			else{
				this.setSpeed(Vector.average(this.speed,robot.getSpeed()));
			}
		} else {
			if(robot.isAlive()) {
			robot.die(map);
			if(PrototypeUtility.allowDebug)System.out.println("Cleaner robot destroyed");
			}
		}
		
	}

	/**
	 * Kirajzolja a robotot a képernyőre, és a sebességvektorát
	 * @author Hunor
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		/*Robot kirajzolása*/
		/*TODO NoteToSelf: Pozíciót szorozni egységgel később?*/
		int x = (int) position.getX();
		int y = (int) position.getY();
		x = x - (playerImage.getWidth() / 2); //Eltolás, hogy a középponton legyen a közepe
		y = y - (playerImage.getHeight() / 2);
		g2.drawImage(playerImage, x, y, null);
		
		
		/*Sebességvektor kirajzolása*/
        g2.setColor(new Color(0, 255, 255));
        AffineTransform tx = new AffineTransform();
        /*A vonalat eltolom a robot középpontjára és kirajzolom*/
        Line2D.Double line = new Line2D.Double(position.getX(),
        									   position.getY(),
        									   position.getX() + (speed.getX() * speed.length()),
        									   position.getY() + (speed.getY() * speed.length()));
        g2.draw(line);

        /*Ha van sebessége, kirajzolja a nyílfejet*/
        if (speed.length() > 0.0f){
	        /*Nyílfej létrehozása*/
	        Polygon arrowHead = new Polygon();  
	        arrowHead.addPoint( 0,5);
	        arrowHead.addPoint( -5, -5);
	        arrowHead.addPoint( 5,-5);
	
	        /*Nyílfej forgatása*/
	        tx.setToIdentity();
	        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
	        tx.translate(line.x2, line.y2);
	        tx.rotate((angle-Math.PI/2d));  
	
	        /*Nyílfej kirajzolása*/
	        Graphics2D gTemp = (Graphics2D) g2.create(); 
	        gTemp.setTransform(tx);   
	        gTemp.fill(arrowHead);
	        gTemp.dispose();
        }
	}


	
	
}
