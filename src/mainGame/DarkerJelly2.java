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

public class DarkerJelly2 extends GameObject {

	private Handler handler;
	private Image img;

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

	public void tick() {
		this.x += velX;
		this.y += velY;

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 43) velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		
		
		if (this.y >= Game.HEIGHT * 1.5 || this.y <= Game.HEIGHT * -1.5) {
			handler.removeObject(this);
			System.out.print("Sweep is removed");
		}
		collision();

	}
	
	public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					setX(100000);
				}
			}
		}
	}

	public void render(Graphics g) {
	
		g.drawImage(img, (int) x, (int) y, 64, 64, null);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
