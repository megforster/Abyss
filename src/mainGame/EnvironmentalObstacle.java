package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class EnvironmentalObstacle extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private Player player;
	private Image img;

	public EnvironmentalObstacle(double x, double y, int sizeX, int sizeY, ID id, Handler handler, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		Random rand = new Random();
		int pic = rand.nextInt(3) + 1;

		switch (pic) {
			case 1:
				try {
					img = ImageIO.read(new File("images/EnvironmentalObstacle.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					img = ImageIO.read(new File("images/EnvironmentalObstacle2.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					img = ImageIO.read(new File("images/EnvironmentalObstacle.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				try {
					img = ImageIO.read(new File("images/EnvironmentalObstacle.jpg"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		double centerX = x + (this.sizeX / 2);
		double centerY = y + (this.sizeY / 2);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform reset = new AffineTransform();
		reset.rotate(0, 0, 0);
		g2d.drawImage(img, (int) this.x, (int) this.y, sizeX, sizeY, null);
	}

	public void collision() {
		if(getBounds().intersects(player.getBounds())) {
			player.setDamage(5);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
