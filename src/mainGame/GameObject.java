package mainGame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Every object in the game extends this abstract class
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public abstract class GameObject {

	//Instance Variables
	protected double x;
	protected double y;
	protected ID id;
	protected double velX, velY;
	protected boolean isMoving;
	protected int health;

	//Constructor 
	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;

	}

	//Abstract classes that are needed in subclasses
	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	/*non abstract can be accessed via OBJECT.________, does not need to be
	implemented, but can be overidden*/

	//Returns the object's x coordinate
	public double getX() {
		return x;
	}

	//Sets the object's x coordinate 
	public void setX(int x) {
		this.x = x;
	}

	//Returns the object's y coordinate 
	public double getY() {
		return y;
	}

	//Sets the object's y coordinate 
	public void setY(int y) {
		this.y = y;
	}

	//Returns the objects ID
	public ID getId() {
		return id;
	}

	//Sets the object's ID
	public void setId(ID id) {
		this.id = id;
	}

	//Returns the object's change in x position
	public double getVelX() {
		return velX;
	}

	//Sets the object's change in x position
	public void setVelX(int velX) {
		this.velX = velX;
	}

	//Returns the object's change in y position
	public double getVelY() {
		return velY;
	}

	//Sets the object's change in y position
	public void setVelY(int velY) {
		this.velY = velY;
	}

	//Return the object's health
	public int getHealth() {
		return this.health;
	}

}
