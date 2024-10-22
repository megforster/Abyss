package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A warning that is displayed before EnemyBurst comes across the screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class TurtleWarningBars extends GameObject {

	//Instance variables 
	private Handler handler;
	private int width;
	private int height;
	private int timer;
	private Color color;
	private int hasFlashed;

	//Constructor 
	public TurtleWarningBars(double x, double y, int width, int height, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.width = width;
		this.height = height;
		timer = 10;
		this.color = Color.orange;
		this.hasFlashed = 0;

	}

	//COntrols the flashing of the warning bars 
	public void tick() {
		flash();
		checkFlash();
	}

	//Code that makes the warning bars flash
	public void flash() {
		timer--;
		if (timer == 5) {
			this.color = Color.black;
		} else if (timer == 0) {
			this.color = Color.orange;
			this.hasFlashed++;
			timer = 10;
		}

	}
	
	//Code for controlling length of flash
	public void checkFlash() {
		if (this.hasFlashed == 5) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.EnemyBurstWarning) {
					handler.removeObject(tempObject);
					i--;
				}
			}
		}
	}
	
	//Draws the warning bar
	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillRect((int) x, (int) y, this.width, this.height);

	}

	@Override
	//Warning bar hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
