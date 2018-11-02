package mainGame;

import java.awt.Color;
import java.awt.Font;
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

import mainGame.Game.STATE;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {

	// Instance variables
	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	private int playerWidth, playerHeight;
	private Image img;
	public static int playerSpeed = 14;
	private EnvironmentalObstacle obstacle;

	// Constructor
	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 4;

		img = null;
		try {
			img = ImageIO.read(new File("images/PlayerSprite.png")); // reads image for obstacle visual
			img = img.getScaledInstance(50, 50, 0); // scales image down

		} catch (Exception e) {
			e.printStackTrace();
		}

		playerWidth = img.getWidth(null); // sets hit box width to image width
		playerHeight = img.getHeight(null); // sets hit box height to image height
	}

	@Override
	/*
	 * Updates player location based on velocity, checks if the player has collided
	 * with an enemy or obstacle, checks if the player is dead
	 */
	public void tick() {
		this.x += velX;
		this.y += velY;
		// makes sure player cannot go outside screen bounds
		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);

		collision();
		checkIfDead();
		// drawFirstBullet(); //uncomment this line to regain shooting with movement

	}

	// Checks if the player is dead
	public void checkIfDead() {
		if (hud.health <= 0) {// checks if player has a health level at or below 0

			if (hud.getExtraLives() == 0) { // checks if the player has an extra life
				game.gameState = STATE.GameOver; // changes game state to game over screen
			}

			else if (hud.getExtraLives() > 0) {// checks if the player has an extra life
				hud.setExtraLives(hud.getExtraLives() - 1); // Decreases player's amount of extra lives
				hud.setHealth(100); // sets player health to full health
			}
		}
	}

	// Checks if player collides with enemies or obstacle
	public void collision() {

		hud.updateScoreColor(Color.white); // changes Hud color on to visually display impact
		for (int i = 0; i < handler.object.size(); i++) { // cycles through handler objects
			GameObject tempObject = handler.object.get(i); // sets a temporary object to the object type handler is on

			// checks if tempObject is an enemy or envrionemtnal obstacle
			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart || tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye || tempObject.getId() == ID.EnvironmentalObstacle) {

				// Checks if a collision has occured with the temporary object
				if (getBounds().intersects(tempObject.getBounds())) {
					// code of collision with obstacle
					if (tempObject.getId() == ID.EnvironmentalObstacle) {
						obstacle = (EnvironmentalObstacle) tempObject;
						obstacle.move();
						hud.health -= 2;
						hud.updateScoreColor(Color.red);
						// code for collision with enemy
					} else {
						hud.health -= damage;
						hud.updateScoreColor(Color.red);
					}
				}

			}
			if (tempObject.getId() == ID.EnemyBoss) { // checks if tempObject is a boss
				/*
				 * Allows player time to get out of upper area where they will get hurt once the
				 * boss starts moving
				 */
				if (this.y <= 300 && tempObject.isMoving) {
					hud.health -= .5;
					hud.updateScoreColor(Color.red);
				}
			}

		}
	}

	// Draws the player sprite
	//Comment this further 
	public void render(Graphics g) {

		double centerX = x + playerWidth / 2;
		double centerY = y + playerHeight / 2;

		double angle = -Math.atan2(velX, velY);

		Graphics2D g2d = (Graphics2D) g; 
		AffineTransform reset = new AffineTransform();
		reset.rotate(0, 0, 0);
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate((angle), centerX, centerY);

		g2d.drawImage(img, (int) this.x, (int) this.y, playerWidth, playerHeight, null);
		g2.setTransform(reset);
	}

	@Override
	//creates player hit box
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

	//sets damage player receives 
	public void setDamage(int damage) {
		this.damage = damage;
	}

	//sets player width and height
	//comment this better
	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}

	//returns the player x location
	public double getX() {
		return this.x;
	}

	//returns the player y location
	public double getY() {
		return this.y;
	}

	//returns the player y velocity 
	public double getVelY() {
		return this.velY;
	}

	//returns the player x velocity
	public double getVelX() {
		return this.velX;
	}

}
