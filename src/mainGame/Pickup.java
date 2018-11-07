package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

/**
 * TO BE IMPLEMENTED - class for pickups that affect the player, such as health
 * boost, speed boost, etc...
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public abstract class Pickup {

	//Instance Variables
	protected double x;
	protected double y;
	protected ID id;
	protected String path;
	protected Image img;

	//Constructor 
	public Pickup(double x, double y, ID id, String path) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.path = path;
		this.img = setgetImg(this.path);

	}

	//Sets and gets the image?
	public Image setgetImg(String path) {
		Image img = null;
		try {
			URL imageURL = Game.class.getResource(path);
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	//Methods used in classes that implement this
	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	//Returns x coordinate of object its called on
	public double getX() {
		return x;
	}

	//Sets x coordinate of object its called on
	public void setX(double x) {
		this.x = x;
	}

	//Returns y coordinate of object its called on
	public double getY() {
		return y;
	}

	//Sets y coordiate of object its called on
	public void setY(double y) {
		this.y = y;
	}

	//Returns the ID of the object its called on
	public ID getId() {
		return id;
	}

	//Sets the ID of the object its called on
	public void setId(ID id) {
		this.id = id;
	}

}
