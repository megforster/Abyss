package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Turtle extends GameObject {

	//Instance Variables
	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();
	private Image img;
	static Toolkit tool = Toolkit.getDefaultToolkit();

	//Constructor 
	public Turtle(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.timer = 60;
		this.side = side;
		this.size = size;
		
		//Code for when turtle is coming from the left
		if (this.side.equals("left")) {
			handler.object.add(new TurtleWarningBars(0, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			img = null;
			try {
				img = ImageIO.read(new File("images/TurtleL.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			setPos();
			setVel();
			
			//Code for when turtle is coming from the right 
		} else if (this.side.equals("right")) {
			handler.object.add(new TurtleWarningBars(Game.WIDTH - 25, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			img = null;
			try {
				img = ImageIO.read(new File("images/TurtleR.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			setPos();
			setVel();

			//Code for when the turtle is coming from the top
		} else if (this.side.equals("top")) {
			handler.object.add(new TurtleWarningBars(0, 0, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			img = null;
			try {
				img = ImageIO.read(new File("images/TurtleT.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			setPos();
			setVel();

			//Code for when turtule is coming from the bottom
		} else if (this.side.equals("bottom")) {
			handler.object.add(new TurtleWarningBars(0, Game.HEIGHT - 50, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			img = null;
			try {
				img = ImageIO.read(new File("images/TurtleB.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			setPos();
			setVel();

		}

	}

	//Controls level length and warning bar removal when turtle off screen 
	public void tick() {
		timer--;
		HUD.score++;
		if (timer <= 0) {
			this.x += velX;
			this.y += velY;

		}
		
		if (this.y >= Game.HEIGHT * 2 || this.y <= Game.HEIGHT *-2 || this.x >= Game.WIDTH * 2 || this.x <= Game.WIDTH * -2) {
			handler.removeObject(this);
		}

	}

	//Sets the turtle position based off of what side its coming from 
	public void setPos() {
		if (this.side.equals("left")) {
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
		} else if (this.side.equals("right")) {
			this.x = Game.WIDTH + 200;
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;

		} else if (this.side.equals("top")) {
			this.y = -(size);
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;

		} else if (this.side.equals("bottom")) {
			this.y = Game.HEIGHT + 200;
			;
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;

		}
	}

	//Sets turtle velocity based off of what side it is coming from
	public void setVel() {
		if (this.side.equals("left")) {
			this.velY = 0;
		} else if (this.side.equals("right")) {
			this.velX = -(this.velX);
			this.velY = 0;

		} else if (this.side.equals("top")) {
			this.velX = 0;

		} else if (this.side.equals("bottom")) {
			this.velX = 0;
			this.velY = -(this.velY);
		}
	}

	//Draws the turtle 
	public void render(Graphics g) {

		g.drawImage(img, (int) x, (int) y, this.size, this.size, null);

	}

	@Override
	
	//Turtle hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 200, 200);
	}

}
