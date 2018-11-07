package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class ShooterEnemy extends GameObject  {

	//Instance variable
	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;


	private Image img;
	public static int sizeDecrease = 3;

	//Constructor 
	public ShooterEnemy(double x, double y, int sizeX, int sizeY, int bulletSpeed, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 30;
		this.bulletSpeed = bulletSpeed;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
		
		try {
			img = ImageIO.read(new File("images/ShooterEnemy.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Lowers enemy health and controls frequency of shooting
	public void tick() {
		timer--;
		if (timer <= 0) {
			shoot();
			updateEnemy();
			timer = 10;
		}
		collision();
	}
		
	//Code for when player bullet collides with the enemy
	public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					this.sizeX -= sizeDecrease;
					this.sizeY -= sizeDecrease;
				}
			}
		}
	}

	//Code for the enemy shooting 
	public void shoot() {
		double diffX = this.x + (sizeX /2) - player.getX() - 16;
		double diffY = this.y + (sizeY / 2) - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
		bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

		handler.addObject(
				new ShooterEnemyBullet(x + this.sizeX / 2, y + this.sizeY /2, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler));
	}

	//Health for the enemy (enemy dies over time primarily)
	public void updateEnemy() {
		this.sizeX--;
		this.sizeY--;

		if (sizeX <= 0) {
			handler.removeObject(this);
		}
	}

	//Draws the enemy 
	public void render(Graphics g) {
		
		double centerX = x + this.sizeX / 2;
		double centerY = y +  this.sizeY / 2;
		
		      
		double radiansToPlayer = (float) Math.atan2(centerX - player.getX(), centerY - player.getY());
		
		     
        Graphics2D g2d = (Graphics2D)g; // Create a Java2D version of g.
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D)g;
        g2.rotate(-(radiansToPlayer), centerX, centerY);
        //draw the image here
        g2d.drawImage(img, (int) this.x, (int) this.y, sizeX, sizeY, null);
        g2.setTransform(reset);
        

	}
	

	@Override
	//Hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}




}