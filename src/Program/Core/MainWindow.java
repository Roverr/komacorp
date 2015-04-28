package Program.Core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private Map map;
	private JPanel panel;
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

	public MainWindow() {
		
		this.setSize(/*1300*/1000,/*700*/700);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		robotRed=new PlayerRobot();
		robotWhite=new PlayerRobot();
		robotGreen=new PlayerRobot();
		
		panel = new JPanel();
		bl = new ButtonListener();
		this.add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.setVisible(true);
		drawMainMenu();

		this.setVisible(true);
	}

	public void showMenu() {

	}

	public void showGame() {

	}

	public void showResult() {

	}

	private void drawMainMenu() {
		panel.setVisible(false);
		panel.removeAll();
		JButton newGame = new JButton(new ImageIcon("newgame.jpg"));
		newGame.setActionCommand("newgame");
		newGame.addActionListener(bl);
		newGame.setBounds(250, 50, 463,67);
		panel.add(newGame);

		JButton options = new JButton(new ImageIcon("options.jpg"));
		options.setActionCommand("options");
		options.addActionListener(bl);
		options.setBounds(250, 300, 463,67);
		panel.add(options);

		JButton exit = new JButton(new ImageIcon("exit.jpg"));
		exit.setActionCommand("exit");
		exit.addActionListener(bl);
		exit.setBounds(250, 550, 463,67);
		panel.add(exit);
		
		//ez csak a tesztel�shez kell, hogy l�ssam hogy n�z ki a results
		
		JButton result=new JButton();
		result.setActionCommand("results");
		result.addActionListener(bl);
		result.setBounds(0,0,50,50);
		panel.add(result);
		//eddig
		
		this.setTitle("Koma Corp");
		panel.setVisible(true);

	}

	private void drawOptionsMenu() {
		panel.setVisible(false);
		panel.removeAll();
		JButton back=new JButton(new ImageIcon("back.jpg"));
	    back.setActionCommand("back");
	    back.addActionListener(bl);
	    back.setBounds(50, 550, 467, 68);
	   
	    panel.add(back);
	    
	    
	    
	    red=new JTextField(reds);
	    red.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    red.setBackground(Color.RED);
	    red.setSelectedTextColor(Color.BLUE);
	    red.setForeground(Color.BLACK);
	    red.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    red.setBounds(400, 250, 400, 50);
	    panel.add(red);
	    
	    white=new JTextField(whites);
	    white.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    white.setBackground(Color.WHITE);
	    white.setSelectedTextColor(Color.BLUE);
	    white.setForeground(Color.BLACK);
	    white.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    white.setBounds(400, 310, 400, 50);
	    panel.add(white);
	    
	    
	    green=new JTextField(greens);
	    green.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    green.setBackground(Color.GREEN);
	    green.setSelectedTextColor(Color.BLUE);
	    green.setForeground(Color.BLACK);
	    green.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    green.setBounds(400, 370, 400, 50);
	    panel.add(green);
	    
	    lap=new JTextField(laps);
	    lap.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
	    lap.setBackground(Color.ORANGE);
	    lap.setSelectedTextColor(Color.BLUE);
	    lap.setForeground(Color.BLACK);
	    lap.setHorizontalAlignment((int) CENTER_ALIGNMENT);
	    lap.setBounds(400, 450, 400, 50);
	    panel.add(lap);
	    
	    
	    JLabel mapicon=new JLabel(new ImageIcon("maps.jpg"));
	    mapicon.setBounds(50, 50, 180, 70); 
	    panel.add(mapicon);
	    JLabel playericon=new JLabel(new ImageIcon("players.jpg"));
	    playericon.setBounds(50, 250, 340, 80); 
	    panel.add(playericon);
	    JLabel lapicon=new JLabel(new ImageIcon("laps.jpg"));
	    lapicon.setBounds(50, 450, 340, 80); 
	    panel.add(lapicon);
	    
	    this.setTitle("Options");
		panel.setVisible(true);
	}

	private void drawResult() {
		panel.setVisible(false);
		panel.removeAll();
		
		JLabel playericon=new JLabel(new ImageIcon("players.jpg"));
	    playericon.setBounds(160, 100, 340, 80); 
	    panel.add(playericon);
	    JLabel pointicon=new JLabel(new ImageIcon("points.jpg"));
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
	    
	    
	    JButton next=new JButton(new ImageIcon("next.jpg"));
	    next.setActionCommand("next");
	    next.addActionListener(bl);
	    next.setBounds(410, 550, 180, 70);
	    panel.add(next);
	    
	    this.setTitle("Results");
		panel.setVisible(true);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			if (ae.getActionCommand().equals("newgame")) {
				panel.setVisible(false);
			} else if (ae.getActionCommand().equals("options")) {
				drawOptionsMenu();			
			} else if (ae.getActionCommand().equals("exit")) {
				System.exit(0);
			} else if (ae.getActionCommand().equals("next")) {
				drawMainMenu();				
			} else if (ae.getActionCommand().equals("back")) {
				reds=red.getText();
				whites=white.getText();
				greens=green.getText();
				robotRed.setPilot(reds);
				robotWhite.setPilot(whites);
				robotGreen.setPilot(greens);
				drawMainMenu();
				
			} else if(ae.getActionCommand().equals("results")){
				drawResult();
			}

		}

	}
}
