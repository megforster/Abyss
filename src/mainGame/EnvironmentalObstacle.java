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
	private Rectangle rec;

	public EnvironmentalObstacle(double x, double y, ID id, Handler handler, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		this.velX = 0;
		this.velY = 0;

		
				try {
					img = ImageIO.read(new File("images/Monster-1.png"));
					img = img.getScaledInstance(200, 200, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sizeX = img.getWidth(null);
				sizeY = img.getHeight(null);
		getBounds();	
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		double centerX = x + img.getWidth(null) / 2;
		double centerY = y +  img.getHeight(null) / 2;
		
		double angle = -Math.atan2(velX, velY);
		      
		
		
		     
        Graphics2D g2d = (Graphics2D)g; // Create a Java2D version of g.
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D)g;
        g2.rotate((angle), centerX, centerY);
        g2d.drawImage(img, (int) this.x, (int) this.y, null);
        g2.setTransform(reset);
	}

	public void collision() {
		if(rec.intersects(player.getBounds())) {
			System.out.println("BAM");
		}
	}

	
	public Rectangle getBounds() {
		System.out.println("Bop");
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	
	}

}
