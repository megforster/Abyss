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

public class EnemyBoss extends GameObject {

	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private Image img;
	private int spawn;
	public static int enemyDamage = 2; 

	public EnemyBoss(ID id, Handler handler) {
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		velX = 0;


		velY = 2;
		img = null;
		try {
			img = ImageIO.read(new File("images/EnemyBoss.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		velY = 4;


		this.health = 750;//full health is 1000
	}

	public void tick() {
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
			spawn = r.nextInt(2);
			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y + 72, ID.EnemyBossBullet, handler));
				this.health -= 1;
			}
		}

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		if (this.x <= -100 || this.x >= Game.WIDTH - 30)
			velX *= -1;

		// handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.025,
		// this.handler));
		collision();
	}
	
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

	public void render(Graphics g) {
		g.setColor(Color.RED);


		g.drawLine(0, 300, Game.WIDTH, 300);
		g.drawImage(img, (int) this.x, (int) this.y, Game.WIDTH / 10, Game.WIDTH /10, null);



		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, 750, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 375, Game.HEIGHT - 150, 750, 50);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler));
	}

} 
//sneaky beaky