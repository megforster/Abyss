package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class EnvironmentalObstacle extends GameObject {

	//Instance Variables
	private int sizeX; // length of hitbox
	private int sizeY; // height of hitbox
	private Image img;
	private static Random r = new Random(); // random number generator used in move()
	private double x, y; // x and y coordinate on screen

	//Constructor
	public EnvironmentalObstacle(double x, double y, ID id, Handler handler, Player player) {
		super(x, y, id);
		this.x = x; //sets x coordinate to random number generated in Spawn10to20 or Spawn1-10
		this.y = y; //sets x coordinate to random number generated in Spawn10to20 or Spawn1-10

		try {
			img = ImageIO.read(new File("images/EnvironmentalObstacle.png")); // reads image for obstacle visual
			img = img.getScaledInstance(200, 200, 0); // scales image down
		} catch (Exception e) {
			e.printStackTrace();
		}
		sizeX = img.getWidth(null); // set hit box width to image width
		sizeY = img.getHeight(null); // sets hit box height to image height
		getBounds(); // generates the hit box
	}

	@Override
	//Needed so class can extend GameObject
	public void tick() {
	}

	@Override
	//Draws the obstacle 
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
		AffineTransform reset = new AffineTransform();
		reset.rotate(0, 0, 0);
		Graphics2D g2 = (Graphics2D) g;
		g2d.drawImage(img, (int) this.x, (int) this.y, null);
		g2.setTransform(reset);
	}

	//Creates the hit box 
	public Rectangle getBounds() {
		return new Rectangle((int) this.x + 30, (int) this.y + 30, this.sizeX - 60, this.sizeY - 60);
		//the additions to the dimensions account for the image being scaled
	}

	//Generates the new random location when player runs into obstacle
	public void move() {
		this.x = r.nextInt(1000) * 1;
		this.y = r.nextInt(500) * 1;
	}

}
