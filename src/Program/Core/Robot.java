package Program.Core;


import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;
/**
 * TODO: KOMMENTEZ�S
 * @author Rover
 *
 */
public abstract class Robot {
	
	/**
	 * name-legyen m�r kurva neve szeg�nynek
	 * Alive - Be�ll�that�, hogy �l-e m�g a robot
	 * Distance - Milyen messze jutott a p�ly�n(eredm�ny sz�mol�shoz)
	 * ModSpeed- Vektor amivel a user m�dos�thatja a robot sebess�g�t
	 * Position - A t�rk�pen val� poz�ci�j�t jel�li
	 * Speed ami a robot el�z� sebess�g�t t�rolja, ehhez ad�dik hozz� a modSpeed
	 * @author Barna,Rover
	 */
	protected String name;
	protected boolean alive;
	protected int distance;
	protected Vector speed;
	protected FloatPoint position;	
	
	
	/**
	 * Publikus Robot �skonstruktor
	 */
	public Robot() {
		speed = new Vector(0,0);
		position = new FloatPoint(0,0);
		alive = true;
		distance = 0;
	}
	
	public abstract void die(Map map);
	
	
	/**
	 * a thesame param�ter azt mutatja hogy a k�t robot ugyanolyan t�pus�-e, 
	 * mivel a f�n�k szerint tilos robot.class.equals(PlayerRobot.class)-t haszn�lni
	 * @author Barna
	 * @param robot
	 */
	public abstract void collide(Robot robot,Map map,boolean thesame);

	/*dropOlaj �s dropRagacs miatt kell a map param�ter, mert azok a f�ggv�nyek haszn�lj�k,
	 * ez�rt �rtam bele. (Hunor)*/
	public abstract void jump(Map map);

	
	/**
	 * Visszadja a t�vols�got
	 * @return
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Visszadja az aktu�lis sebess�get
	 * @return
	 */
	public Vector getSpeed() {
		return speed;
	}
	
	public void setSpeed(Vector sp){
		speed = sp;
	}
	
	/**
	 * Visszadja az aktu�lis poz�ci�t
	 * @return
	 */
	public FloatPoint getPosition() {
		return position;
	}
	
	/**
	 * Be�ll�tja az akut�lis poz�ci�t.
	 * @param position
	 */
	public void setPosition(FloatPoint position) {
		this.position= position;
	}
	
	/**
	 * Be�ll�tja az aktu�lis poz�ci�t k�t float pont alapj�n.
	 * @param x - X koordin�ta
	 * @param y - Y koordin�ta
	 */
	public void setPosition(float x, float y) {
		this.position = new FloatPoint(x,y);
	}
	
	/**
	 * Annak a lek�rdez�s�re, hogy �l-e m�g a robot.
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Be�ll�tja, hogy �l -e a robot. 
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
}
