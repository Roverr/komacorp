package Program.Skeleton;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Program.Core.*;
import Program.Helpers.Vector;
import Program.Prototype.MyFileNotFoundException;

/**
 * A szkeleton modell megvalósításáért felelõs osztály. A belsõ business modell
 * a korábbiakban megbeszélt. A SkeletonUtility osztály létrehozza a
 * teszteléshez szükséges környezetet. Továbbá parancsokkal biztosítja az
 * Use-casek meghívhatóságát, nyomon követi azok lefutását. További infó a
 * dokumentációban.
 * 
 * @author Székely Károly
 *
 */
public class SkeletonUtility {

	/**
	 * @attribute ident - A szöveg behúzását jelzi. Annyi tabulátor kerül a sor
	 *            elejére, amekkora az ident.
	 * @attribute allowSkeleton - A skeleton csak akkor írhat ki bármit ha ez a
	 *            változó true.
	 * @attribute classTable - tartalmazza az osztályok példányainak neveit a
	 *            példányokhoz rendelve.
	 */
	private static int ident;
	private static boolean allowSkeleton;
	private static HashMap<Object, String> classTable = new HashMap<Object, String>();

	/**
	 * Az alábbi változók az elnevezésekhez szükségesek. Amikor létrejön egy új
	 * objektum akkor ezt a számot a neve után írva megkülömböztethetjük a
	 * többitõl.
	 */
	public static int robotCounter = 0;
	public static int mapItemCounter = 0;

	/**
	 * Változók a tesztelésre. Minden Önálló objektumból van egy példány, amin a
	 * változtatásokat lehet végezni.
	 */

	private static Game dummyGame;
	private static Map dummyMap;
	private static PlayerRobot dummyRobot;
	private static Olaj dummyOlaj;
	private static Ragacs dummyRagacs;

	private static BufferedReader brKeyboard;

	/**
	 * Klasszikus konstruktor, megalkotja a teszteléshez szükséges dummy
	 * osztályokat. Beállítja a kellõ statikus változókat.
	 * 
	 */
	public SkeletonUtility() {
		/*Létrehozza és kiszerializálja a tesztpályát*/
		/*TODO delete véglegesben*/
		
		
		// Create Dummy classes
		try {
			dummyGame = new Game(270, "halal_kanyon.txt", 3);
		} catch (MyFileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dummyMap = new Map();
		try {
			dummyMap.loadMap("halal_kanyon.txt", 3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		dummyRobot = new PlayerRobot();
		dummyOlaj = new Olaj(10, new Point(0,0));
		dummyRagacs = new Ragacs(3, new Point(0,0));

		// Kiírások engedélyezése:
		allowSkeleton = true;

		// Input reader inicializálás:
		brKeyboard = new BufferedReader(new InputStreamReader(System.in));

	}

	/**
	 * Privát kiíró függvény, gondoskodik arról hogy csak akkor kerüljön valami
	 * kiírásra, ha a Skeleton módot engedélyeztük.
	 * 
	 * @param s
	 */
	static boolean notificationsent = true;

	private static void printSkeleton(String s) {
		if (allowSkeleton) {
			System.out.println(s);
		} else {
			if (!notificationsent) {
				System.out
						.println("allowSkeleton is not activated, you cant use skeleton methods");
				notificationsent = true;
			}
		}
	}

	/**
	 * A printSkeleton párja, üres stringet dob vissza ha nincs allowSkeleton
	 * 
	 * @author Barna
	 * @throws IOException
	 */
	private static String readSkeleton() throws IOException {
		if (allowSkeleton) {
			String line = brKeyboard.readLine();
			return line;
		} else {
			if (!notificationsent) {
				System.out
						.println("allowSkeleton is not activated, you cant use skeleton methods");
				notificationsent = true;
			}
			return "";
		}

	}

	/**
	 * yesorno question megvalósítása, rekurzívan addig kérdezget míg jó választ
	 * adsz neki
	 * 
	 * @author Barna
	 * @throws IOException
	 */
	public static boolean yesOrNoQuestion(String question) throws IOException {
		boolean isYes = false;
		boolean invalidAnswer = true;
		// Addig feltesszük a kérdést, amíg értelmes választ nem kapunk.
		while (invalidAnswer) {
			// Kiírjuk a kérdést
			printSkeleton(question + " yes/no y/n igen/nem");
			String answer = readSkeleton();
			// Az igen vagy nem esetek mnegállapítása:
			if ("Y".equals(answer) || "YES".equals(answer)
					|| "IGEN".equals(answer) || "y".equals(answer)
					|| "yes".equals(answer) || "igen".equals(answer)) {
				isYes = true;
				invalidAnswer = false;
			} else if ("N".equals(answer) || "NO".equals(answer)
					|| "NEM".equals(answer) || "n".equals(answer)
					|| "no".equals(answer) || "nem".equals(answer)) {
				isYes = false;
				invalidAnswer = false;
			} else {
				printSkeleton("Invalid answer");
				invalidAnswer = true;
			}
		}
		return isYes;
	}

	/**
	 * A szkeleton Modellben meghíváskor kiírja hogy melyik osztályban, milyen
	 * metódust hívódott meg. A kiírás tördelt jellegét az ident változó intézi.
	 * A kiírás csak akkor történik meg hogyha az allowSkeleton változó értéke
	 * True.
	 * 
	 * @param methodname
	 *            A meghívott metódus neve.
	 * @param caller
	 *            A hívó osztály. (Legtöbb esetben: this)
	 */
	public static void printCall(String methodname, Object caller) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident++; /*
				 * Az ident növelése azért szükséges, hogy a metódus belsõ
				 * hívásai már beljebb legyenek húzva.
				 */
		sb.append("called ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}

	/**
	 * A szkeleton Modellben visszatéréskor kiírja hogy melyik osztály, milyen
	 * metódusa tér vissza. A kiírás tördelt jellegét az ident változó intézi. A
	 * kiírás csak akkor történik meg hogyha az allowSkeleton változó értéke
	 * True.
	 * 
	 * @param methodname
	 *            A visszatérõ metódus neve.
	 * @param caller
	 *            A hívó osztály. (Legtöbb esetben: this)
	 */
	public static void printReturn(String methodname, Object caller) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ident; i++) {
			sb.append("  ");
		}
		ident--; /*
				 * Az ident csökkentése szükséges, hiszen a belsõ hívásoknak
				 * vége van.
				 */
		sb.append("return ");
		sb.append(methodname + " ");
		sb.append(matchNameToObject(caller));
		printSkeleton(sb.toString());
	}

	/**
	 * Belsõ függvény, egy osztály példányhoz nevet párosít, ha az szerepel a
	 * classTable-ben. Ha nincs az nyilvántartásban az osztály, üres sztringgel
	 * tér vissza.
	 * 
	 * @param caller
	 *            - a szóban forgó osztály.
	 * @return visszatér az osztály nevével, vagy egy üres "" stringgel.
	 */
	private static String matchNameToObject(Object caller) {
		String s = classTable.get(caller);
		if (s != null) {
			return "- " + s;
		}
		return "";
	}

	public static void addClass(Object obj, String name) {
		classTable.put(obj, name);

	}

	/**
	 * Ez az imputhandler ami a parancsokat várja Megadott parancsra és " " el
	 * elválasztott paramétereket átadja a megfelelõ függvénynek, ami egy
	 * use-caset reprezentál a függvényt zöld komment jelzi, még nincsenek
	 * megírva nem akartam hibát beletenni, értelemszerûen azokat kell
	 * implementálni
	 *
	 * @author Barna
	 * @throws IOException
	 */
	public void inputHandler() throws IOException {
		boolean quit = false;
		while (!quit) {
			printSkeleton("Please enter a valid command:");
			String line = readSkeleton();
			String[] parts = line.split(" ");
			String command = parts[0].toLowerCase();

			boolean wrongParameters = false;

			// Egy pálya betöltésének az use-case.
			// paraméter: pályanév.
			if (command.equals("loadmap")) {
				if (parts.length >= 2) {
					String name = parts[1];
					chooseMap(name);
				} else {
					wrongParameters = true;
				}
				// Játékoszám beállításának use-case.
				// Paraméter játékosszám.
			} else if (command.equals("setplayercount")) {
				if (parts.length >= 2) {
					try {
						int number = Integer.parseInt(parts[1]);
						chooseNumberOfPlayers(number);
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}
				} else {
					wrongParameters = true;
				}
				// A játék megnyerésének az use-case.
			} else if (command.equals("wingame")) {
				if (parts.length >= 2) {
					try {
						int playernumber = Integer.parseInt(parts[1]);
						winGame(playernumber);
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}
				} else {
					wrongParameters = true;
				}
				// Kilépés a játékból Use-case.
			} else if (command.equals("exit")) {
				exitGame();

			} else if (command.equals("losegame")) {
				if (parts.length >= 2) {
					try {
						int playernumber = Integer.parseInt(parts[1]);

						loseGame(playernumber);
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}

				} else {
					wrongParameters = true;
				}
			} else if (command.equals("setspeedmod")) {
				float x, y;
				if (parts.length >= 3) {
					try {
						x = Float.parseFloat(parts[1]);
						y = Float.parseFloat(parts[2]);
						Vector newvector = new Vector(x, y);
						setSpeedModification(newvector);
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}
				} else {
					wrongParameters = true;
				}
			} else if (command.equals("setpos")) {
				int x, y;
				if (parts.length >= 3) {
					try {
						x = Integer.parseInt(parts[1]);
						y = Integer.parseInt(parts[2]);
						Point newpoint = new Point(x, y);
						setPosition(newpoint);
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}
				} else {
					wrongParameters = true;
				}
			} else if (command.equals("setdrop")) {
				if (parts.length >= 2) {
					String item = parts[1];
					/**
					 * Out of item? Yes-re nem történik semmi. No-ra kérdezünk.
					 * Drop Ragacs? Yes-re eldobjuk a ragacsot No-ra kérdezünk.
					 * Drop Olaj? Yes-re Olajat dobunk No-ra közöljük, hogy
					 * nincs más dobnivaló.
					 */
					String question = "Out of item?";
					boolean outOfItem;
					try {
						outOfItem = yesOrNoQuestion(question);

						if (!outOfItem) {
							System.out.println(item);
							setDropItem(item);
						} else {
							printSkeleton("No throwing then!");
						}
					} catch (Exception e) {
						printSkeleton(e.getMessage());
					}
				} else {
					wrongParameters = true;
				}
			} else if (command.equals("validatestate")) {
				if (parts.length >= 2) {
					try {
						int playernumber = Integer.parseInt(parts[1]);
						boolean beforeAllow = allowSkeleton;
						allowSkeleton = false;
						if (dummyMap.getRobots().size() > playernumber) {
							PlayerRobot rob = dummyMap.getRobots().get(playernumber);
							allowSkeleton = beforeAllow;
							dummyMap.validateState(rob);
						} else {
							wrongParameters = true;
						}
						allowSkeleton = beforeAllow;
					} catch (Exception e) {
						System.out.println(e.getMessage()
								+ "\nIt's not valid, use help!");
						wrongParameters = true;
					}
				} else {
					wrongParameters = true;
				}

			} else if (command.equals("modspeed")) {
				float x, y;
				if (parts.length >= 3) {
					try {
						x = Float.parseFloat(parts[1]);
						y = Float.parseFloat(parts[2]);
						Vector modify = new Vector(x, y);
						String question = "Out of item?";
						boolean outOfItem;

						outOfItem = !yesOrNoQuestion(question);

						if (outOfItem) {
							question = "Drop Ragacs?";
							boolean goo;
							goo = yesOrNoQuestion(question);

							if (goo) {
								setDropItem("ragacs");
							} else {

								boolean oil;
								question = "Drop Olaj?";
								oil = yesOrNoQuestion(question);

								if (oil) {
									setDropItem("olaj");
								} else {
									printSkeleton("Can't drop other traps!");
								}
							}
						}
						setSpeedModification(modify);
					} catch (Exception e) {
						printSkeleton(e.getMessage());
					}

				} else {
					wrongParameters = true;
				}
			} else if (command.equals("help")) {
				printSkeleton("UseCase & Elágazások    Parancs:    paraméter\n"
						+ "Choose Map    LoadMap    string név\n"
						+ "Choose Number of Players    SetPlayerCount    int 1..3\n"
						+ "Win Game    WinGame    int whichplayer\n"
						+ "Exit Game       Exit\n"
						+ "Lose Game       LoseGame    int whichplayer\n"
						+ "	Only One Left?           y/n\n"
						+ "Set Speed Modification (In Air)    SetSpeedMod    vector float,float\n"
						+ "Set Position (In Air)    SetPos       int int\n"
						+ "Set Drop Item (In Air)    SetDrop       string olaj/ragacs\n"
						+ "	Out of Item?           y/n\n"
						+ "Validate State (On Fall)        ValidateState    int whichplayer\n"
						+ "	Out Of Map?           y/n\n"
						+ "	StepIn Olaj?           y/n\n"
						+ "	StepIn Ragacs?           y/n\n"
						+ "Modify Speed (On Launch)        ModSpeed    vector float,float\n"
						+ "	Drop Olaj?           y/n\n"
						+ "	Drop Ragacs?           y/n\n"
						+ "Quit (Kilép a Skeletonból)\n" + "\n" + "\n");
			} else if (command.equals("quit")) {
				quit = true;
			} else {
				printSkeleton("Wrong command.");
				printSkeleton("Type \"Help\" to see the commands.");
			}
			if (wrongParameters) {
				printSkeleton("Something Went wrong with the parameters :S .");
			}
		}

		printSkeleton("Skeleton is now Quitting!");

		brKeyboard.close();
	}

	/**
	 * Ez választ pályát, és be is tölti azt, a korábban elfogadott
	 * játékosszámmal
	 *
	 * @author Bence
	 */
	private static void chooseMap(String name) {

		try {
			dummyMap.loadMap(name, previousnumberofplayers);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Robot sebességének megváltozatásához a parancs, float inputokkal.
	 * 
	 * @param x
	 *            - A vektor x komponense
	 * @param y
	 *            - A vektor y komponense
	 */
	public void setSpeedModification(float x, float y) {
		Vector modifier = new Vector(x, y);
		dummyRobot.modifySpeed(modifier);
	}

	/**
	 * Robot sebességének megváltozatásához a parancs, Vector inputtal.
	 * 
	 * @param modifier
	 *            - A vektor amivel változtatni akarunk
	 */
	public void setSpeedModification(Vector modifier) {
		dummyRobot.modifySpeed(modifier);
	}

	/**
	 * Robot pozíciójának beállítása int inputokkal.
	 * 
	 * @param x
	 *            - x koordináta
	 * @param y
	 *            - y koordináta
	 */
	public void setPosition(int x, int y) {
		dummyRobot.setPosition(new Point(x, y));
	}

	/**
	 * Robot pozíciójának beállítása int inputokkal.
	 * 
	 * @param to
	 *            - A pont ahova a robotot akarjuk tenni.
	 */
	public void setPosition(Point to) {
		dummyRobot.setPosition(to);
	}

	/**
	 * Ha a robot ragacsot vagy olajat dob el akkor hívódik meg
	 * 
	 * @param what
	 *            - A string, ami kiválasztja, hogy mit dobjon a robot. String,
	 *            mert az jobban olvasható kódot szül.
	 */
	public void setDropItem(String what) {
		if (what.equalsIgnoreCase("ragacs")) {
			dummyRobot.dropRagacs(dummyMap);
		} else if (what.equalsIgnoreCase("olaj")) {
			dummyRobot.dropOlaj(dummyMap);
		} else {
			// Hibakezelés
			SkeletonUtility
					.printSkeleton("I can't throw it! It's not Ragacs or Olaj!\n"
							+ "Probebly dev Error!\n" + what);
			// throw new OutOfShitError(); ???
		}
	}

	/**
	 * Ez választ játékosszámot, és betölti a pályát újra, csak a játékosok
	 * számát változtatva.
	 *
	 * @author Bence
	 */
	private void chooseNumberOfPlayers(int number) {

		try {
			dummyMap.loadMap(previousname, number);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @attribute previousname - Elmenti az aktuális pálya nevét,
	 *            alapértelmezett a Halálos Kanyon
	 * @attribute previousnumberofplayers - Elmenti a aktuális játékosok számát,
	 *            2 az alapértelmezett.
	 */
	private static String previousname = "Halálos Kanyon";
	private static int previousnumberofplayers = 2;

	/**
	 * A megadott játékos megnyeri a játékot
	 * 
	 * @param player
	 * @author Barna
	 */
	private void winGame(int player) {
		dummyGame.endGame();
		printSkeleton("Játékos " + player + " megnyerte a játékot!");
	}

	/**
	 * A megadott játékos elveszti a játékot
	 * 
	 * @param player
	 * @author Barna
	 * @throws IOException
	 */
	private void loseGame(int player) throws IOException {
		printSkeleton(player + " elvesztette a játékot!");
		if (yesOrNoQuestion("Ez volt az utolsó játékos?")) {
			dummyGame.endGame();
		}
	}

	/**
	 * 
	 * @author Barna
	 * @throws IOException
	 */
	private void exitGame() throws IOException {
		if (yesOrNoQuestion("Biztos hogy meg akarod szakítani a meccset? Ha másoknak még van esélye nyerni, elveszed tõlük a lehetõséget."))
			dummyGame.endGame();
		else
			printSkeleton("Helyes, egy igazi BME-s a végsõkig küzd!");
	}

}
