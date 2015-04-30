package Program.Core;


/**
 * K�l�n sz�lon fut, m�sodpercenk�nt fogja h�vni a Game oszt�ly run f�ggv�ny�t.
 * @author Hunor
 *
 */
public class Timer extends Thread{
	Game game;
	public boolean stop = false;
	
	public Timer(Game game){
		this.game = game;
	}
	
	@Override
	public void run(){
		while (!stop){
			try {
				game.canRun();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
