package Program.Core;


import Program.Helpers.FloatPoint;
import Program.Helpers.Vector;

/**
 * Olaj oszt�ly , ami akad�lyk�nt dobhat� a p�ly�ra.
 * @author Rover
 */
public class Olaj extends MapItem {
	
	/**
	 * timeLeft - Az olaj aktivit�si ideje, am�g a p�ly�n marad
	 */
	private int timeLeft;
	
	/**
	 * Szerializ�l�shoz sz�ks�ges
	 */
	private static final long serialVersionUID = -1668976720926783982L;
	
	/**
	 * Az olaj aktivit�si idej�t �ll�tja be a konstruktor.
	 * @param timing - Az id� ameddig az olaj akt�v
	 */
	public Olaj(int timing,FloatPoint position) {
		this.setTimeLeft(timing);
		this.position = position;
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Publikus konstruktor ami az olaj poz�ci�j�val k�pes be�ll�tani
	 * @param position
	 */
	public Olaj(FloatPoint position){
		this(3,position);
		state = CleaningState.canBeCleaned;
	}
	
	/**
	 * Az�rt felel�s, hogy a belel�pett roboton negat�v effektet fejtsen ki.
	 * @param playerRobot- Robot ami belel�pet        
	 */
	public void stepIn(PlayerRobot playerRobot) {
		Vector zero = new Vector(0,0);
		System.out.println(playerRobot.getModSpeed().getX() + " " + playerRobot.getModSpeed().getY());
		playerRobot.setModSpeed(zero);
		System.out.println(playerRobot.getModSpeed().getX() + " " + playerRobot.getModSpeed().getY());
	}
	
	/**
	 * Visszadja mennyi id� van m�g h�tra
	 * @return
	 */
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Be�ll�tja mennyi id� van m�g h�tra
	 * @param timeLeft
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	/**
	 * �reg�ti az olajat ha megvan h�v�dva.
	 */
	public void update(){
		timeLeft = timeLeft-1;
	}

	@Override
	public boolean isAlive() {
		return (timeLeft > 0);
	}

}
