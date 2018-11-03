package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * The bullets that the first boss shoots
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class CrabProjectile extends GameObject {

	//Instance variables
	private Handler handler;
	Random r = new Random(); //random number generator
	private Image img;

	//
	public CrabProjectile(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = (r.nextInt(30)*-30); //variable for the angled way projectiles shoot from crab?
		velY = 45;

		
		img = null;
		try {
			img = ImageIO.read(new File("images/CrabProjectile.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Brings in the projectile image?
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}
	
	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y >= Game.HEIGHT)
			handler.removeObject(this); //Removes projectiles once they reach a certain point?
	}

	//Controls the visual aspect of the projectiles 
	public void render(Graphics g) {
		g.drawImage(img, (int) this.x, (int) this.y, 32, 32, null);
	}

	@Override
	//Projectile hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

}

