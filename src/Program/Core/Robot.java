package Program.Core;

import java.awt.Point;

import Program.Helpers.Vector;
/**
 * TODO: KOMMENTEZÉS
 * @author Rover
 *
 */
public abstract class Robot {
	
	/**
	 * name-legyen már kurva neve szegénynek
	 * Alive - Beállítható, hogy él-e még a robot
	 * Distance - Milyen messze jutott a pályán(eredmény számoláshoz)
	 * ModSpeed- Vektor amivel a user módosíthatja a robot sebességét
	 * Position - A térképen való pozícióját jelöli
	 * Speed ami a robot elõzõ sebességét tárolja, ehhez adódik hozzá a modSpeed
	 * @author Barna,Rover
	 */
	protected String name;
	protected boolean alive;
	protected int distance;
	protected Vector speed;
	protected Point position;
	
	/**
	 * Publikus Robot õskonstruktor
	 */
	public Robot() {
		speed = new Vector(0,0);
		position = new Point(0,0);
		alive = true;
		distance = 0;
	}
	
	public abstract void die(Map map);
	
	
	/**
	 * a thesame paraméter azt mutatja hogy a két robot ugyanolyan típusú-e, 
	 * mivel a fõnök szerint tilos robot.class.equals(PlayerRobot.class)-t használni
	 * @author Barna
	 * @param robot
	 */
	public abstract void collide(Robot robot,Map map,boolean thesame);

	/*dropOlaj és dropRagacs miatt kell a map paraméter, mert azok a függvények használják,
	 * ezért írtam bele. (Hunor)*/
	public abstract void jump(Map map);

	
	/**
	 * Visszadja a távolságot
	 * @return
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Visszadja az aktuális sebességet
	 * @return
	 */
	public Vector getSpeed() {
		return speed;
	}
	
	public void setSpeed(Vector sp){
		speed = sp;
	}
	
	/**
	 * Visszadja az aktuális pozíciót
	 * @return
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Beállítja az akutális pozíciót.
	 * @param position
	 */
	public void setPosition(Point position) {
		this.position= position;
	}
	
	/**
	 * Beállítja az aktuális pozíciót két int pont alapján.
	 * @param x - X koordináta
	 * @param y - Y koordináta
	 */
	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	/**
	 * Annak a lekérdezésére, hogy él-e még a robot.
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Beállítja, hogy él -e a robot. 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	 *Ugráskor meghívva a megtett távolságot növeli az ugráskor megtett távolsággal.
	 *@param time- meddig van a levegõben a robot
	 * érdemes az értékét nem túl kicsire megválasztani a kvantálás maitt(>10)
	 * @author Bence
	 */	
	protected void countDistance(int time){
		distance+=Math.round(speed.length()*time);
	}
}
