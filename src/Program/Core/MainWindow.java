package Program.Core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Program.Helpers.FloatPoint;
import Program.Prototype.MyFileNotFoundException;

interface Drawable{
	public abstract void draw(Graphics g);
}

/**
 * Osztály, JPanelből származik, alkalmas arra, 
 * hogy kirajzolja a játék képernyőjét
 * @author Hunor
 *
 */
class Canvas extends JPanel implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int screenWidth = 1000;
	private int screenHeight = 700;
	BufferedImage background;
	Game game;
	
	public Canvas(int screenWidth, int screenHeight, String backgroundFileName, Game game){
		/*Inicializálás*/
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.game = game;
		/*Háttérkép betöltése*/
		try {
			background = ImageIO.read(new File(backgroundFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Játék képernyő újrarajzolásáért felel*/
    @Override
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        /*Háttér kirajzolása*/
        g2.drawImage(background, 0, 0, null);
              
        /*Mapon szereplő objektumokat kirajzolja*/
	    for (PlayerRobot pRobot : game.getMap().getRobots())
	       		pRobot.draw(g);
        for (CleanerRobot cRobot : game.getMap().getCleanerRobots())
	       	cRobot.draw(g);
        for (MapItem mItem : game.getMap().getMapItems())
	       	mItem.draw(g);  
        
     // create an AffineTransform 
     // and a triangle centered on (0,0) and pointing downward
     // somewhere outside Swing's paint loop
    }
    
    @Override
	public void keyPressed(KeyEvent e) {
    	//Értelmezi a gombnyomást és a megfelelő robotot mozgatja
		game.userControl(e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private Map map;
	private JPanel panel;
	Canvas canvas;
	String reds;
	String whites;
	String greens;
	String laps="20";
	JTextField red;
	JTextField white;
	JTextField green;
	JTextField lap;
	private PlayerRobot robotRed;
	private PlayerRobot robotWhite;
	private PlayerRobot robotGreen;
	private ActionListener bl;
	private final int screenWidth = 1000;
	private final int screenHeight = 700;

	public MainWindow() {
		
		this.setSize(screenWidth, screenHeight);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("assets\\cursors\\hajo.png");		
		Cursor chajo = toolkit.createCustomCursor(image , new Point(0,0), "hajo");
		this.setCursor (chajo);
		
		
		robotRed=new PlayerRobot();
		robotWhite=new PlayerRobot();
		robotGreen=new PlayerRobot();
		
		panel = new JPanel();
		canvas = null;
		bl = new ButtonListener();
		showMenu();

		this.setVisible(true);
	}


	/**
	 * Újrafesti a pályát (run hívogatja)
	 * TODO Remélhetőleg ez csak a new game hívása után hívódik meg, igaz?
	 * @author Hunor
	 */
	public void showGame(Map GameMap) {
		canvas.repaint();
	}

	public void showMenu() {
		/*Ha játékból léptünk ide vissza, letakarítja a képernyőt*/
		if (canvas != null)
			remove(canvas);
		this.add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.setVisible(false);
		panel.removeAll();
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		
		Image robot = toolkit.getImage("assets\\cursors\\robot.png");		
		Cursor crobot = toolkit.createCustomCursor(robot , new Point(16,16), "robot");
		Image katona = toolkit.getImage("assets\\cursors\\katona.png");		
		Cursor ckatona = toolkit.createCustomCursor(katona , new Point(16,16), "katona");
		Image vader = toolkit.getImage("assets\\cursors\\vader.png");		
		Cursor cvader = toolkit.createCustomCursor(vader , new Point(16,16), "vader");
		
		JButton newGame = new JButton(new ImageIcon("assets\\menus\\newgame.jpg"));
		newGame.setActionCommand("newgame");
		newGame.addActionListener(bl);
		newGame.setBounds(250, 50, 463,67);
		newGame.setCursor(crobot);
		panel.add(newGame);

		

		
		JButton options = new JButton(new ImageIcon("assets\\menus\\options.jpg"));
		options.setActionCommand("options");
		options.addActionListener(bl);
		options.setBounds(250, 300, 463,67);
		options.setCursor(ckatona);
		panel.add(options);

		
		JButton exit = new JButton(new ImageIcon("assets\\menus\\exit.jpg"));
		exit.setActionCommand("exit");
		exit.addActionListener(bl);
		exit.setBounds(250, 550, 463,67);
		exit.setCursor(cvader);
		panel.add(exit);
		
		//ez csak a teszteléshez kell, hogy lássam hogy néz ki a results
		
		JButton result=new JButton();
		result.setActionCommand("results");
		result.addActionListener(bl);
		result.setBounds(0,0,50,50);
		panel.add(result);
		//eddig
		
		this.setTitle("Koma Corp");
		panel.setVisible(true);

	}

	private void showOptionsMenu() {
		panel.setVisible(false);
		panel.removeAll();
		
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		
		Image redimage = toolkit.getImage("assets\\cursors\\red.png");		
		Cursor cred = toolkit.createCustomCursor(redimage , new Point(16,16), "red");
		Image whiteimage = toolkit.getImage("assets\\cursors\\white.png");		
		Cursor cwhite = toolkit.createCustomCursor(whiteimage , new Point(16,16), "white");
		Image greenimage = toolkit.getImage("assets\\cursors\\green.png");		
		Cursor cgreen = toolkit.createCustomCursor(greenimage , new Point(16,16), "green");
		Image orangeimage = toolkit.getImage("assets\\cursors\\orange.png");		
		Cursor corange = toolkit.createCustomCursor(orangeimage , new Point(16,16), "orange");
		Image vader = toolkit.getImage("assets\\cursors\\vader.png");		
		Cursor cvader = toolkit.createCustomCursor(vader , new Point(16,16), "vader");
		
		JButton back=new JButton(new ImageIcon("assets\\menus\\back.jpg"));
	    back.setActionCommand("back");
	    back.addActionListener(bl);
	    back.setBounds(50, 550, 467, 68);
	    back.setCursor(cvader);
	    panel.add(back);
	    
	    
	    
	    red=new JTextField(reds);
	    red.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    red.setBackground(Color.RED);
	    red.setSelectedTextColor(Color.BLUE);
	    red.setForeground(Color.BLACK);
	    red.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    red.setBounds(400, 250, 400, 50);
	    red.setCursor(cred);
	    panel.add(red);
	    
	    white=new JTextField(whites);
	    white.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    white.setBackground(Color.WHITE);
	    white.setSelectedTextColor(Color.BLUE);
	    white.setForeground(Color.BLACK);
	    white.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    white.setBounds(400, 310, 400, 50);
	    white.setCursor(cwhite);
	    panel.add(white);
	    
	    
	    green=new JTextField(greens);
	    green.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    green.setBackground(Color.GREEN);
	    green.setSelectedTextColor(Color.BLUE);
	    green.setForeground(Color.BLACK);
	    green.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    green.setBounds(400, 370, 400, 50);
	    green.setCursor(cgreen);
	    panel.add(green);
	    
	    lap=new JTextField(laps);
	    lap.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    lap.setBackground(Color.ORANGE);
	    lap.setSelectedTextColor(Color.BLUE);
	    lap.setForeground(Color.BLACK);
	    lap.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    lap.setBounds(450, 450, 300, 50);
	    lap.setCursor(corange);
	    panel.add(lap);
	    
	    
	    JLabel mapicon=new JLabel(new ImageIcon("assets\\menus\\maps.jpg"));
	    mapicon.setBounds(50, 50, 180, 70); 
	    panel.add(mapicon);
	    JLabel playericon=new JLabel(new ImageIcon("assets\\menus\\players.jpg"));
	    playericon.setBounds(50, 250, 340, 80); 
	    panel.add(playericon);
	    JLabel lapicon=new JLabel(new ImageIcon("assets\\menus\\laps.jpg"));
	    lapicon.setBounds(50, 450, 340, 80); 
	    panel.add(lapicon);
	    
	    this.setTitle("Options");
		panel.setVisible(true);
	}

	private void showResult() {
		panel.setVisible(false);
		panel.removeAll();
		
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Image vader = toolkit.getImage("assets\\cursors\\vader.png");		
		Cursor cvader = toolkit.createCustomCursor(vader , new Point(16,16), "vader");
		
		JLabel playericon=new JLabel(new ImageIcon("assets\\menus\\players.jpg"));
	    playericon.setBounds(160, 100, 340, 80); 
	    panel.add(playericon);
	    JLabel pointicon=new JLabel(new ImageIcon("assets\\menus\\points.jpg"));
	    pointicon.setBounds(500, 100, 340, 80); 
	    panel.add(pointicon);
	    
	    JLabel redName =new JLabel(robotRed.getPilot());
	    redName.setOpaque(true);
	    redName.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    redName.setBounds(165, 200, 330, 50);
	    redName.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    redName.setBackground(Color.RED);
	    redName.setForeground(Color.BLACK);
	    panel.add(redName);
	    JLabel whiteName =new JLabel(robotWhite.getPilot());
	    whiteName.setOpaque(true);
	    whiteName.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    whiteName.setBounds(165, 260, 330, 50);
	    whiteName.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    whiteName.setBackground(Color.WHITE);
	    whiteName.setForeground(Color.BLACK);
	    panel.add(whiteName);
	    JLabel greenName =new JLabel(robotGreen.getPilot());
	    greenName.setOpaque(true);
	    greenName.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    greenName.setBounds(165, 320, 330, 50);
	    greenName.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    greenName.setBackground(Color.GREEN);
	    greenName.setForeground(Color.BLACK);
	    panel.add(greenName);
	    
	    JLabel redPoint =new JLabel(Integer.toString(robotRed.getDistance()));
	    redPoint.setOpaque(true);
	    redPoint.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    redPoint.setBounds(505, 200, 330, 50);
	    redPoint.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    redPoint.setBackground(Color.RED);
	    redPoint.setForeground(Color.BLACK);
	    panel.add(redPoint);
	    JLabel whitePoint =new JLabel(Integer.toString(robotWhite.getDistance()));
	    whitePoint.setOpaque(true);
	    whitePoint.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    whitePoint.setBounds(505, 260, 330, 50);
	    whitePoint.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    whitePoint.setBackground(Color.WHITE);
	    whitePoint.setForeground(Color.BLACK);
	    panel.add(whitePoint);
	    JLabel greenPoint =new JLabel(Integer.toString(robotGreen.getDistance()));
	    greenPoint.setOpaque(true);
	    greenPoint.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    greenPoint.setBounds(505, 320, 330, 50);
	    greenPoint.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    greenPoint.setBackground(Color.GREEN);
	    greenPoint.setForeground(Color.BLACK);
	    panel.add(greenPoint);
	    
	    
	    JButton next=new JButton(new ImageIcon("assets\\menus\\next.jpg"));
	    next.setActionCommand("next");
	    next.addActionListener(bl);
	    next.setBounds(410, 550, 180, 70);
	    next.setCursor(cvader);
	    panel.add(next);
	    
	    this.setTitle("Results");
		panel.setVisible(true);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			/*Új játéknál lecseréli a panel-t a játék vásznára
			 * és elindít egy új játékot*/
			if (ae.getActionCommand().equals("newgame")) {
				/*Játék indítása*/
				Game game = null;
				try {
					//TODO játékidő és játékosok száma ne konstans legyen, hanem menüben beállított
					game = new Game(1000, "Tesztmap", 3, MainWindow.this);
					map = game.getMap();
				} catch (MyFileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*Vászon létrehozása*/
				canvas = new Canvas(screenWidth, screenHeight, "assets\\ingame\\background.jpeg", game);
				

				/*Vászon csere*/
				remove(panel);
				add(canvas);
				/*Vászon eseménykezelője*/
				canvas.addKeyListener(canvas);
				canvas.setFocusable(true);
				canvas.requestFocusInWindow(); //Fókusz elkérése
				//Játék indítása
				game.startGame();
				repaint();	
				revalidate();
			} else if (ae.getActionCommand().equals("options")) {
				showOptionsMenu();			
			} else if (ae.getActionCommand().equals("exit")) {
				System.exit(0);
			} else if (ae.getActionCommand().equals("next")) {
				showMenu();				
			} else if (ae.getActionCommand().equals("back")) {
				reds=red.getText();
				whites=white.getText();
				greens=green.getText();
				robotRed.setPilot(reds);
				robotWhite.setPilot(whites);
				robotGreen.setPilot(greens);
				showMenu();
				
			} else if(ae.getActionCommand().equals("results")){
				showResult();
			}

		}

	}
}
