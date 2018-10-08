package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;


import javax.imageio.ImageIO;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {
	

	Toolkit tool = Toolkit.getDefaultToolkit();
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	private int taskBarHeight = scrnSize.height - winSize.height;
	private int x = ((int) (tool.getScreenSize().getWidth()));
	private int y = ((int) ((tool.getScreenSize().getHeight()) - taskBarHeight));
	private Game game;
	private Handler handler;
	private HUD hud;
	private BufferedImage img;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to10 spawner;
	private JPanel helpPanel;
	private int buttonwidth = Game.WIDTH/4;
	private int buttonheight = Game.HEIGHT/5;
	private Image abyss;
	
	public Menu(Game game, Handler handler, HUD hud, Spawn1to10 spawner) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		r = new Random();
		addColors();

		img = null;
		try {
			//CHANGE BACKGROUND IMAGE (background.png --> abyssspacebackground.png) 
			//ADDITIONAL BACKGROUND(abysswaterbackground.jpg)
			//CHANGED BACKGROUND IN THE MAIN MENU SCREEN
			img = ImageIO.read(new File("images/abyssapcebackground.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		abyss = null;
		try {
			abyss = ImageIO.read(new File("images/abyss.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		



		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}
	
	
	
	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}
	
	
	//Draws a string at the center of the sceen
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {

	    FontMetrics metrics = g.getFontMetrics(font);
	    
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

	    g.setFont(font);

	    g.drawString(text, x, y);
	}
	


	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			Font font = new Font("Amoebic", 1, Game.WIDTH/20);
			Font font2 = new Font("Amoebic", 1, Game.WIDTH/30);

			g.drawImage(abyss, ((Game.WIDTH - 720)/2), ((Game.HEIGHT - 200)/16), 720, 200, null);



			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect( ((Game.WIDTH - buttonwidth)/2), ((Game.HEIGHT - buttonheight)/2), buttonwidth, buttonheight);
			Rectangle wavebutton = new Rectangle (((Game.WIDTH - buttonwidth)/2), ((Game.HEIGHT - buttonheight)/2), buttonwidth, buttonheight);
			g.setColor(Color.white);
			drawCenteredString(g, "Click to Start", wavebutton, font2);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(((Game.WIDTH - buttonwidth)/16), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			Rectangle Quitbutton = new Rectangle (((Game.WIDTH - buttonwidth)/16), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			g.setColor(Color.white);
			drawCenteredString(g, "Quit", Quitbutton, font2 );
			
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(((Game.WIDTH - buttonwidth)/2), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			Rectangle Helpbutton = new Rectangle (((Game.WIDTH - buttonwidth)/2), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			g.setColor(Color.white);
			drawCenteredString(g, "Help", Helpbutton, font2 );
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(((Game.WIDTH - buttonwidth)*15/16), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			Rectangle Creditbutton = new Rectangle (((Game.WIDTH - buttonwidth)*15/16), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight);
			g.setColor(Color.white);
			drawCenteredString(g, "Credits", Creditbutton, font2 );




	}


}}
