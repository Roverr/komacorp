package Program.Prototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.awt.Point;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**Map Editor, ami seg�ts�g�vel p�ly�kat hozhatunk l�tre.
 * 
 * Az ir�ny�t�s a k�vetkez�:
 * t- k�ls�/bels� �v
 * e - ellen�rz� pontok
 * o - h�tt�rf�jl bet�lt�se
 * s - ment�s Tesztmap.txt-be
 * c - �pp szerkesztett vonal t�rl�se
 * 
 */

public class Editor extends JFrame{
	private static final long serialVersionUID = 1L;

	/*A file, ami a h�tteret t�rolja*/
	static File backgroundFile = null;
	static boolean imageLoaded = false; //V�lasztott-e m�r a felhasz�nl� k�pet
	
	/*Panel, amibe rajzolunk*/
	static class Panel extends JPanel{
		private static final long serialVersionUID = 1L;
		BufferedImage background;
		int width, height;
		
		ArrayList<Point> lineStrip1;
		ArrayList<Point> lineStrip2;
		ArrayList<Point> checkPoints;
		int line = 0; //0 = line1-be rajzolunk, 1 = line2-be, 2 = CheckPoint
		
		public Panel(){
			/*Inicializ�l�s*/
			lineStrip1 = new ArrayList<Point>();
			lineStrip2 = new ArrayList<Point>();
			checkPoints = new ArrayList<Point>();
			/*Katitnt�sok kezel�se*/
			this.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX();
					int y = e.getY();
					if (line == 0){
						lineStrip1.add(new Point(x,y));
					}
					else if (line == 1)
						lineStrip2.add(new Point(x,y));
					else
						checkPoints.add(new Point(x,y));
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		/*Ha v�ltozna a h�tt�r, ez a f�ggv�ny t�lti be az �j filet*/
		public void refreshBackground(){
			/*Bet�lti a h�tt�rt*/
			try {
				background = ImageIO.read(backgroundFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*Meghat�rozza a k�p magass�g�t �s sz�less�g�t*/
			height = background.getHeight(null);
			width = background.getWidth(null);
			repaint();
		}
		
		/*Kik�nyszer�ti, hogy �jra kelljen rajzolni az eg�sz panelt, azzal, hogy let�rli*/
		public void forceRepaint(Graphics2D g2){
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		/*Kirajzolja a param�terben kapott linestrip-et*/
		public void drawLineStrip(Graphics2D g2, ArrayList<Point> lineStrip){
			/*Kirajzolja a kontrollpontokat*/
			for (Point p:lineStrip)
				g2.fillRect((int)p.getX() - 5, (int)p.getY() - 5, 10, 10);
			
			/*Kirajzolja a vonalakat*/
			Point current = null, prev;
			if (lineStrip.size() > 1){
				for (int i = 0; i < lineStrip.size() - 1; i++){
					prev = lineStrip.get(i);
					current = lineStrip.get(i + 1);
					g2.drawLine((int)prev.getX(), (int)prev.getY(), (int)current.getX(), (int)current.getY());
				}
				/*�s z�rtt� teszi a vonalat*/
				g2.drawLine((int)lineStrip.get(0).getX(), (int)lineStrip.get(0).getY(), (int)current.getX(), (int)current.getY());
			}
		}
		
		/*Kirajzolja a param�terben kapott checkpointokat*/
		public void drawCheckPoints(Graphics2D g2, ArrayList<Point> cp){
			/*Kirajzolja a kontrollpontokat*/
			for (Point p:cp)
				g2.fillRect((int)p.getX() - 5, (int)p.getY() - 5, 10, 10);
			
			/*Kirajzolja a vonalakat*/
			Point current = null, prev;
			if (cp.size() > 1){
				for (int i = 0; i < cp.size() - 1; i+=2){
					prev = cp.get(i);
					current = cp.get(i + 1);
					g2.drawLine((int)prev.getX(), (int)prev.getY(), (int)current.getX(), (int)current.getY());
				}
			}
		}
		
		/*K�perny� kirajzol�sa*/
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			forceRepaint(g2);
			//TODO tetsz�leges k�pm�ret
			g2.drawImage(background, 0, 0, null);
			g2.finalize();
			
			/*Kirajzolja a kontrolpontokat �s vonalakat*/
			g2.setColor(new Color(0, 0, 1.0f));	
			drawLineStrip(g2, lineStrip1);
			g2.setColor(new Color(1.0f, 0, 0));
			drawLineStrip(g2, lineStrip2);
			
			/*Kirajzolja az ellen�rz�pontokat*/
			g2.setColor(new Color(1.0f, 1.0f, 0.0f));
			drawCheckPoints(g2, checkPoints);
		}
		
		/*Let�rli az �pp kiv�lasztott lineStrip-et*/
		public void clear(){
			if (line == 0)
				lineStrip1.clear();
			else if (line == 1)
				lineStrip2.clear();
			else
				checkPoints.clear();
			repaint();
		}
		
		/*�tv�lt a m�sik lineStrip szerkeszt�s�re*/
		public void toggle(){
			if (line == 0)
				line = 1;
			else
				line = 0;
		}
		
		/*Be�ll�tja, hogy checkpointot fogunk rajzolni*/
		public void setCheckpoint(){
			line = 2;
		}
		
		/*Ki�r file-ba egy pontot a megfelel� form�tumban*/
		public void writePointToFile(BufferedWriter b, Point p) throws IOException{
			b.write(new Integer((int)p.getX()).toString());
			b.newLine();
			b.write(new Integer((int)p.getY()).toString());
			b.newLine();
		}
		public void saveMap() {
			try {
				Writer f = new FileWriter(new File("Tesztmap.txt"));
				BufferedWriter b = new BufferedWriter(f);
				
				/*Ki�rja a vonalak sz�m�t*/
				b.write(new Integer(lineStrip1.size() + lineStrip2.size()).toString());
				b.newLine();
				
				/*Ha a checkpointok nem p�ros s�zm� pontb�l �llnak, remove-olja az utols�t*/
				if (checkPoints.size() % 2 == 1){
					checkPoints.remove(checkPoints.size() -1);
					repaint();
				}
				
				/*Ki�rja checkpointok sz�m�t*/
				b.write(new Integer(checkPoints.size() / 2).toString());
				b.newLine();
				
				/*Ki�rja a lineStrip-ek vonalainak koordin�t�ir soronk�nt*/
				Point current = null, prev;
				if (lineStrip1.size() > 1){
					for (int i = 0; i < lineStrip1.size() - 1; i++){
						prev = lineStrip1.get(i);
						current = lineStrip1.get(i + 1);
						writePointToFile(b, prev);
						writePointToFile(b, current);
					}
					/*�s z�rtt� teszi a vonalat*/
					writePointToFile(b, lineStrip1.get(0));
					writePointToFile(b, lineStrip1.get(0));
				}
				if (lineStrip2.size() > 1){
					for (int i = 0; i < lineStrip2.size() - 1; i++){
						prev = lineStrip2.get(i);
						current = lineStrip2.get(i + 1);
						writePointToFile(b, prev);
						writePointToFile(b, current);
					}
					/*�s z�rtt� teszi a vonalat*/
					writePointToFile(b, lineStrip2.get(0));
					writePointToFile(b, lineStrip2.get(0));
				}
				
				/*Ki�rja a checkpointok koordin�t�it*/
				if (checkPoints.size() > 1){
					for (int i = 0; i < checkPoints.size() - 1; i+=2){
						prev = checkPoints.get(i);
						current = checkPoints.get(i + 1);
						writePointToFile(b, prev);
						writePointToFile(b, current);
					}
				}
				
				b.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	//TODO �t�rni, netr�l szedtem
	/*Egy f�jl kiterjeszt�s�t hat�rozza meg*/
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
	public static void main(String args[]){
		final Editor frame = new Editor();
		final Panel canvas = new Panel();
		
		/*Alapbe�ll�t�sok (m�ret, c�m, sat�bbi)*/
		frame.setTitle("Phoebe Map Editor");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
		
		/*Billenty�kezel�*/
		frame.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				/*o bet� lenyom�s�ra k�p bet�lthet�*/
				if (e.getKeyChar() == 'o'){
					JFileChooser jfc = new JFileChooser();
					jfc.setFileFilter(new FileFilter(){

						@Override
						/*Milyen file-ok jelenjenek meg a v�laszlehet�s�gek k�z�tt*/
						public boolean accept(File file) {
							/*K�nyvt�rak megjelenhetnek*/
							if (file.isDirectory())
								return true;
							/*Meghat�rozza a file kiterjeszt�s�t*/
							String extension = getFileExtension(file);
							/*Ha bmp, akkor j�*/
							//TODO m�s filet�pusokra is
							if (extension.equals("bmp"))
								return true;
							return false;
						}

						@Override
						public String getDescription() {
							return ".bmp";
						}});;
					/*Ha a user v�lasztott, elmenti a filet, az ablakot �s a h�tteret is hozz� igaz�tja*/
					if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						backgroundFile = jfc.getSelectedFile();
						canvas.refreshBackground();
					}
				}
				/*Let�rli az �pp szerkesztett lineStrip-et*/
				if (e.getKeyChar() == 'c')
					canvas.clear();
				
				/*V�ltogatja, hogy melyik lineStrip-et szerkesztj�k �pp*/
				if (e.getKeyChar() == 't')
					canvas.toggle();
				
				/*Ha ellen�rz� pontot akarunk rajzolni*/
				if (e.getKeyChar() == 'e')
					canvas.setCheckpoint();
				
				/*P�lya ment�se*/
				if (e.getKeyChar() == 's')
					canvas.saveMap();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
