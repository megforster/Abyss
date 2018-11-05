package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class DarkerJelly extends GameObject {

	//Instance variables 
	private Handler handler;
	private Image img;

	//Constructor 
	public DarkerJelly(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 2;
		velY = 9;
		img = null;
		try {
			img = ImageIO.read(new File("images/DarkerJelly.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Moves the Jelly and checks for collision
	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40) {
			velY *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 16) {
			velX *= -1;
		}
		collision();

	}
	
	//Code for when the enemy collides with the player bullet
	public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {//TempObject is an enemy
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					setX(100000); //So the enemy is removed it just set faaaar away??
				}
			}
		}
	}

	//Draws the jelly
	public void render(Graphics g) {

		g.drawImage(img, (int) x, (int) y, 64, 64, null);

	}

	@Override
	//Hit box for the jelly
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
