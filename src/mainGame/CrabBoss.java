package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * The first boss in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class CrabBoss extends GameObject {
	
	//Instance variables
	private Handler handler;
	private int timer = 80; //Timer for crab life?
	private int timer2 = 50; //Timer for projectile generation?
	Random r = new Random(); //random number generator 
	private Image img;
	private int spawn;
	public static int enemyDamage = 1; //sets the amount of damage the boss can do 

	//Constructor 
	public CrabBoss(ID id, Handler handler) {
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		velX = 0;
		velY = 2;
		img = null;
		
		try {
			img = ImageIO.read(new File("images/CrabBoss.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		velY = 4; //not sure why velY is set twice??

	
		this.health = 750;
	}

	public void tick() {
		//accounts for boss movement
		this.x += velX;
		this.y += velY;

		if (timer <= 0)
			velY = 0;
		else
			timer--;
		drawFirstBullet();
		if (timer <= 0)
			timer2--;
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			this.isMoving = true;
			//generates a random number of crab projectiles?
			spawn = r.nextInt(2);
			if (spawn == 0) {
				handler.addObject(
						new CrabProjectile((int) this.x + 48, (int) this.y + 72, ID.EnemyBossBullet, handler));
				this.health -= 1; //allows for the crab to die over time
			}
		}

		
		if (this.x <= -100 || this.x >= Game.WIDTH - 30)
			velX *= -1;
		
		collision();
	}
	
		//Code for when player shoots the boss
		public void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PlayerBullet) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit the boss
					this.health -= enemyDamage;
				}
			}
		}
	}

	//Gets the crab image for the class? Or the bullet image?
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

	//Draws the crab and its health bar
	public void render(Graphics g) {
		
		//Crab code
		g.setColor(Color.RED);
		g.drawLine(0, 300, Game.WIDTH, 300);
		g.drawImage(img, (int) this.x, (int) this.y, Game.WIDTH / 10, Game.WIDTH /10, null);



		//Health bar code
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, 750, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, 750, 50);

	}

	@Override
	//Hit box for the crap
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	//Allows for grey line to be drawn, as well as first bullet shot
	//Not sure what part of the code draws the gray line?
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(new CrabProjectile((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler));
	}

} 