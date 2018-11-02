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

	private Handler handler;
	Random r = new Random();
	private int max = 30;
	private int min = -30;

	private Image img;


	public CrabProjectile(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = (r.nextInt((max - min) + 1) + min);// OFFICIAL WAY TO GET A RANGE FOR randInt()
		velY = 45;

		
		img = null;
		try {
			img = ImageIO.read(new File("images/CrabProjectile.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		if (this.y >= Game.HEIGHT)
			handler.removeObject(this);



	}

	public void render(Graphics g) {

		g.drawImage(img, (int) this.x, (int) this.y, 32, 32, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

}

