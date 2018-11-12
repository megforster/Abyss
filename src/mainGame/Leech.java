package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Leech extends GameObject {

	//Instance variables
	private Handler handler;
	private GameObject player;
	private int speed;
	private Image img;
	private static Random r = new Random();

	//Constructor 
	public Leech(double x, double y, int speed, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.speed = speed;

		//Adds a leech to the handler
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		

			//Reads the image for the leech
			try {
				img = Toolkit.getDefaultToolkit().getImage("images/Leech.gif");
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//returns the image for the leech
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
	

	//Controls leech movement 
	public void tick() {
		this.x += velX;
		this.y += velY;
		////////////////////////////// pythagorean theorem
		////////////////////////////// below//////////////////////////////////////////////////
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
		velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		if (this.y >= Game.HEIGHT * 2 || this.y <= Game.HEIGHT *-2 || this.x >= Game.WIDTH * 2 || this.x <= Game.WIDTH * -2) {
			handler.removeObject(this);
			System.out.print("Smart is removed");
		}
		collision();

	}
	
	//Controls leech collision
	public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					setX(100000); //teleports the leech far off screen when shot
					HUD.score = HUD.score + 50;
				}
			}
		}
	}

	//Draws the leech
	public void render(Graphics g) {
		double centerX = x + 48 / 2;
		double centerY = y +  100 / 2;
		
		      
		double radiansToPlayer = (float) Math.atan2(centerX - player.getX(), centerY - player.getY());
		
		     
        Graphics2D g2d = (Graphics2D)g; // Create a Java2D version of g.
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D)g;
        g2.rotate(-(radiansToPlayer), centerX, centerY);
        //draw the image here
        g2d.drawImage(img, (int) this.x, (int) this.y, 48, 100, null);
        g2.setTransform(reset);

	}
	public void move() {
		this.x = r.nextInt(1000) * 1;
		this.y = r.nextInt(500) * 1;
		
	}
	@Override
	//Leech hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 48, 100);
	}

}
