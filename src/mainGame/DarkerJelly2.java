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
 
//I believe a stronger version of DarkerJelly for levels 10-20?
public class DarkerJelly2 extends GameObject {

	//Instance Variables
	private Handler handler;
	private Image img;

	//Constructor
	public DarkerJelly2(double x, double y, double velX, double velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		img = null;
		try {
			img = ImageIO.read(new File("images/DarkerJelly.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Controls jelly movement
	public void tick() {
		this.x += velX;
		this.y += velY;

		//Allows jelly to bounce off the screen
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		
		//removes the jelly if they go outside the screen?
		if (this.y >= Game.HEIGHT * 1.5 || this.y <= Game.HEIGHT * -1.5) {
			handler.removeObject(this); //To get jelly to bounce off top and bottom mimic the velX code
		}
		collision();

	}
	
	//Controls collision for the jelly
	public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {//TempObject is an enemy

				//Teleports the jelly far away when the player shoots it
				if (getBounds().intersects(tempObject.getBounds())) {
					setX(100000);
				}
			}
		}
	}

	//Draws the jelly
	public void render(Graphics g) {
	
		g.drawImage(img, (int) x, (int) y, 64, 64, null);

	}

	@Override
	//jelly hitbox
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
