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

/**Map Editor, ami segítségével pályákat hozhatunk létre.
 * 
 * Az irányítás a következõ:
 * t- külsõ/belsõ ív
 * e - ellenõrzõ pontok
 * o - háttérfájl betöltése
 * s - mentés Tesztmap.txt-be
 * c - épp szerkesztett vonal törlése
 * 
 */

public class Editor extends JFrame{
	private static final long serialVersionUID = 1L;

	/*A file, ami a hátteret tárolja*/
	static File backgroundFile = null;
	static boolean imageLoaded = false; //Választott-e már a felhaszánló képet
	
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
			/*Inicializálás*/
			lineStrip1 = new ArrayList<Point>();
			lineStrip2 = new ArrayList<Point>();
			checkPoints = new ArrayList<Point>();
			/*Katitntások kezelése*/
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
		/*Ha változna a háttér, ez a függvény tölti be az új filet*/
		public void refreshBackground(){
			/*Betölti a háttért*/
			try {
				background = ImageIO.read(backgroundFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*Meghatározza a kép magasságát és szélességét*/
			height = background.getHeight(null);
			width = background.getWidth(null);
			repaint();
		}
		
		/*Kikényszeríti, hogy újra kelljen rajzolni az egész panelt, azzal, hogy letörli*/
		public void forceRepaint(Graphics2D g2){
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		/*Kirajzolja a paraméterben kapott linestrip-et*/
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
				/*És zárttá teszi a vonalat*/
				g2.drawLine((int)lineStrip.get(0).getX(), (int)lineStrip.get(0).getY(), (int)current.getX(), (int)current.getY());
			}
		}
		
		/*Kirajzolja a paraméterben kapott checkpointokat*/
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
		
		/*Képernyõ kirajzolása*/
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			forceRepaint(g2);
			//TODO tetszõleges képméret
			g2.drawImage(background, 0, 0, null);
			g2.finalize();
			
			/*Kirajzolja a kontrolpontokat és vonalakat*/
			g2.setColor(new Color(0, 0, 1.0f));	
			drawLineStrip(g2, lineStrip1);
			g2.setColor(new Color(1.0f, 0, 0));
			drawLineStrip(g2, lineStrip2);
			
			/*Kirajzolja az ellenõrzõpontokat*/
			g2.setColor(new Color(1.0f, 1.0f, 0.0f));
			drawCheckPoints(g2, checkPoints);
		}
		
		/*Letörli az épp kiválasztott lineStrip-et*/
		public void clear(){
			if (line == 0)
				lineStrip1.clear();
			else if (line == 1)
				lineStrip2.clear();
			else
				checkPoints.clear();
			repaint();
		}
		
		/*Átvált a másik lineStrip szerkesztésére*/
		public void toggle(){
			if (line == 0)
				line = 1;
			else
				line = 0;
		}
		
		/*Beállítja, hogy checkpointot fogunk rajzolni*/
		public void setCheckpoint(){
			line = 2;
		}
		
		/*Kiír file-ba egy pontot a megfelelõ formátumban*/
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
				
				/*Kiírja a vonalak számét*/
				b.write(new Integer(lineStrip1.size() + lineStrip2.size()).toString());
				b.newLine();
				
				/*Ha a checkpointok nem páros sázmú pontból állnak, remove-olja az utolsót*/
				if (checkPoints.size() % 2 == 1){
					checkPoints.remove(checkPoints.size() -1);
					repaint();
				}
				
				/*Kiírja checkpointok számát*/
				b.write(new Integer(checkPoints.size() / 2).toString());
				b.newLine();
				
				/*Kiírja a lineStrip-ek vonalainak koordinátáir soronként*/
				Point current = null, prev;
				if (lineStrip1.size() > 1){
					for (int i = 0; i < lineStrip1.size() - 1; i++){
						prev = lineStrip1.get(i);
						current = lineStrip1.get(i + 1);
						writePointToFile(b, prev);
						writePointToFile(b, current);
					}
					/*És zárttá teszi a vonalat*/
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
					/*És zárttá teszi a vonalat*/
					writePointToFile(b, lineStrip2.get(0));
					writePointToFile(b, lineStrip2.get(0));
				}
				
				/*Kiírja a checkpointok koordinátáit*/
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
	
	//TODO átírni, netrõl szedtem
	/*Egy fájl kiterjesztését határozza meg*/
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
	public static void main(String args[]){
		final Editor frame = new Editor();
		final Panel canvas = new Panel();
		
		/*Alapbeállítások (méret, cím, satöbbi)*/
		frame.setTitle("Phoebe Map Editor");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
		
		/*Billentyûkezelõ*/
		frame.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				/*o betû lenyomására kép betölthetõ*/
				if (e.getKeyChar() == 'o'){
					JFileChooser jfc = new JFileChooser();
					jfc.setFileFilter(new FileFilter(){

						@Override
						/*Milyen file-ok jelenjenek meg a válaszlehetõségek között*/
						public boolean accept(File file) {
							/*Könyvtárak megjelenhetnek*/
							if (file.isDirectory())
								return true;
							/*Meghatározza a file kiterjesztését*/
							String extension = getFileExtension(file);
							/*Ha bmp, akkor jó*/
							//TODO más filetípusokra is
							if (extension.equals("bmp"))
								return true;
							return false;
						}

						@Override
						public String getDescription() {
							return ".bmp";
						}});;
					/*Ha a user választott, elmenti a filet, az ablakot és a hátteret is hozzá igazítja*/
					if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						backgroundFile = jfc.getSelectedFile();
						canvas.refreshBackground();
					}
				}
				/*Letörli az épp szerkesztett lineStrip-et*/
				if (e.getKeyChar() == 'c')
					canvas.clear();
				
				/*Váltogatja, hogy melyik lineStrip-et szerkesztjük épp*/
				if (e.getKeyChar() == 't')
					canvas.toggle();
				
				/*Ha ellenõrzõ pontot akarunk rajzolni*/
				if (e.getKeyChar() == 'e')
					canvas.setCheckpoint();
				
				/*Pálya mentése*/
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
