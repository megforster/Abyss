package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;

/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references to ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Game extends Canvas implements Runnable {

	//Instance Variables
	private static final long serialVersionUID = 1L; //Don't know what this does 

	static Toolkit tool = Toolkit.getDefaultToolkit(); //Don't know what this is 
	static Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize(); //Returns the size of the screen
	static Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();//returns the size of the game window
	static int taskBarHeight = scrnSize.height - winSize.height;
	public static final int WIDTH = (int) (scrnSize.getWidth()) + 5;
	public static final int HEIGHT = (int) (scrnSize.getHeight()) - taskBarHeight + 5;
	public String message = "Controls:" //message used for all help menus
			+ " use WASD or the Arrow Keys to move and space to shoot. \nCollect power-ups like speed and health boosts to assist you on your journey. \nSurvive and Fight as long as you can!";
	
	private Thread thread;
	private boolean running = false;
	private boolean pauseState = false; //Added boolean to toggle elements within the tick method

	private Handler handler;
	private HUD hud;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private Menu menu;
	private GameOver gameOver;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private static Player player;
	private Image Background;
	private PlayerBullet bullet;
	private Theme theme;
	private Victory victory;
	private Pause pause; // added type Pause variable 

	public STATE gameState = STATE.Menu;
	public static int TEMP_COUNTER;
	public static Window test;
	
	private SoundEffects background = new SoundEffects();

	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, Help, Game, GameOver, Upgrade, Victory, Pause, Theme
	};

	//Constructor, creates the game
	public Game() {
		handler = new Handler();
		hud = new HUD();
		theme = new Theme(this, this.handler, this.hud);
		spawner = new Spawn1to10(this.handler, this.hud, this, player);
		spawner2 = new Spawn10to20(this.handler, this.hud, this.spawner, this);
		menu = new Menu(this, this.handler, this.hud, this.spawner, this.theme);
		upgradeScreen = new UpgradeScreen(this, this.handler, this.hud);
		player = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, this.hud, this);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawner,
				this.spawner2);
		gameOver = new GameOver(this, this.handler, this.hud);
		victory = new Victory(this, this.handler, this.hud);
		pause = new Pause(this, this.handler, this.hud); // updated core game mechanics to include a new Pause state
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawner, this.spawner2, this.upgradeScreen,
				this.player, this.upgrades, this.victory, this.theme);
		this.addKeyListener(new KeyInput(this.handler, this, this.hud, this.player, this.spawner, this.upgrades));
		this.addMouseListener(mouseListener);
		new Window((int) WIDTH, (int) HEIGHT, "Abyss", this);
		
		Background = null;
		try {
			//CHANGED BACKGROUND IN PLAYING SCREEN!!
			//SPACE IMAGE BACKGROUND (abyssspacebackground.png) 
			//ADDITIONAL BACKGROUND(abysswaterbackground.jpg)
			Background = ImageIO.read(new File(theme.getBackground()));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
			//Plays the background music
			//Will have to adjust this when player can pick a theme
			background.playCont(theme.getMusic());
		
	}

	/**
	 * The thread is simply a programs path of execution. This method ensures that
	 * this thread starts properly.
	 */
	
	//Triggers the start of the game?
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	//Triggers the game being over or the player exiting?
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Best Java game loop out there (used by Notch!)
	 */
	@Override
	//Code to keep the game running, like a fancy timer?
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();// 60 times a second, objects are being updated
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				System.out.println(gameState);
				System.out.println(Spawn1to10.LEVEL_SET);
				frames = 0;
			}
		}
		stop();

	}

	/**
	 * Constantly ticking (60 times per second, used for updating smoothly). Used
	 * for updating the instance variables (DATA) of each entity (location, health,
	 * appearance, etc).
	 */
	private void tick() {
		if (pauseState == false) { // stops ticking handler if game is paused 
		handler.tick();
		}
		if (gameState == STATE.Game) {// game is running
			hud.tick();
			if (Spawn1to10.LEVEL_SET == 1) {// user is on levels 1 thru 10, update them
				spawner.tick();
			} else if (Spawn1to10.LEVEL_SET == 2) {// user is on levels 10 thru 20, update them
				spawner2.tick();
			}
		} else if (gameState == STATE.Menu || gameState == STATE.Help) {// user is on menu, update the menu items
			menu.tick();
		} else if (gameState == STATE.Upgrade) {// user is on upgrade screen, update the upgrade screen
			upgradeScreen.tick();
		} else if (gameState == STATE.GameOver) {// game is over, update the game over screen
			gameOver.tick();
		} else if (gameState == STATE.Victory) {//game has been won, eye boss has been defeated
			victory.tick();
		} else if (gameState == STATE.Pause) { //Think this code needs to be changed to fix pause button
			pause.tick();
			pauseState = true;
		}else if (gameState== STATE.Theme) {
			theme.tick();
		}

	}

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring the
	 * Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {

		/*
		 * BufferStrategies are used to prevent screen tearing. In other words, this
		 * allows for all objects to be redrawn at the same time, and not individually
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		///////// Draw things bellow this/////////////

		g.setColor(Color.black);
		g.drawImage(Background, 0, 0, this.getWidth(), this.getHeight(),null);
		
		
		handler.render(g); // ALWAYS RENDER HANDLER, NO MATTER IF MENU OR GAME SCREEN

		if (gameState == STATE.Game) {// user is playing game, draw game objects
			hud.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.Help) {// user is in help or the menu, draw the menu
																		// and help objects
			menu.render(g);
		} else if (gameState == STATE.Upgrade) {// user is on the upgrade screen, draw the upgrade screen
			upgradeScreen.render(g);
		} else if (gameState == STATE.GameOver) {// game is over, draw the game over screen
			gameOver.render(g);
		} else if (gameState == STATE.Victory) {//game is over (won), draw victory screen
			victory.render(g);
		} else if (gameState == STATE.Pause) { // game is paused, pause menu is drawn 
			pause.render(g);	
		} else if (gameState==STATE.Theme) {
			theme.render(g);
		}

		///////// Draw things above this//////////////
		g.dispose();
		bs.show();
	}

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't leave
	 * screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	//Triggers creation of a new game
	public static void main(String[] args) {

		new Game();
	}
	
	//returns the players x coordinate
	public static double getPlayerX() {
		return player.getX();
	}
	
	//returns the players y coordinate 
	public static double getPlayerY() {
		return player.getY();
	}


}
