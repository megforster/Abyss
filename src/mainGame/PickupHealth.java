package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * TO BE IMPLEMENTED - adds health to the player when they move over it
 * @author Brandon Loehle
 * 5/30/16
 *
 */

public class PickupHealth extends Pickup{
	
	//Instance Variables 
	private Handler handler;

	//Constructor 
	public PickupHealth(double x, double y, ID id, String path, Handler handler) {
		super(x, y, id, path);
		this.handler = handler;
	}

	//Implemented because it implements an abstract class with this method 
	public void tick() {

		
	}

	//Draws the health power up?
	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 16, 16, null);
		
	}

	//Hit box
	public Rectangle getBounds() {

		return null;
	}

}
