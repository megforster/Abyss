package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class ShooterEnemyBullet extends GameObject {

	//Instance variables
	private Handler handler;

	//Constructor
	public ShooterEnemyBullet(double x, double y, double velX, double velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	//Controls bullet movement 
	public void tick() {
		this.x += velX;
		this.y += velY;
		removeBullets();
	}

	//Gets rid of all bullets that go off screen
	public void removeBullets() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyShooterBullet) {
				if (tempObject.getX() >= Game.WIDTH || tempObject.getY() >= Game.HEIGHT) {
					handler.removeObject(tempObject);
				}
			}

		}

	}

	//Draws the bullets
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, 4, 4);

	}

	@Override
	//bullet hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
