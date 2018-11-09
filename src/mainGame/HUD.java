package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

//Constructor
public class HUD {

	public double health = 100;
	private double healthMax = 100;

	private double greenValue = 255;

	public static int score = 0;
	private int level = 0;

	private boolean regen = false;
	private int timer = 60;
	private int healthBarWidth = 400;
	private int healthBarModifier = 2;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;

	private Color scoreColor = Color.white;

	private int extraLives = 0;

	public void tick() {
		health = Game.clamp(health, 0, health);

		greenValue = Game.clamp(greenValue, 0, 255);

		greenValue = health * healthBarModifier;

		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0 && health < 100) {
				health += 1;
				timer = 60;
			}
		}
	}

	//Draws the health bar, score, level, extra lives, and any titles needed for power-ups
	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 30);

		g.setColor(Color.GRAY);
		g.fillRect(15, 15, healthBarWidth, 64);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) health * 4, 64);
		g.setColor(scoreColor);
		g.drawRect(15, 15, healthBarWidth, 64);

		g.setFont(font);

		g.drawString("Score: " + score, 15, 115);
		g.drawString("Level: " + level, 15, 150);
		g.drawString("Extra Lives: " + extraLives, 15, 185);

		if (ability.equals("freezeTime")) {
			g.drawString("Time Freezes: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("clearScreen")) {
			g.drawString("Screen Clears: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("levelSkip")) {
			g.drawString("Level Skips: " + abilityUses, Game.WIDTH - 300, 64);
		}
	}

	//Sets ability
	public void setAbility(String ability) {
		this.ability = ability;
	}

	//Gets the number of uses left?
	public int getAbilityUses() {
		return this.abilityUses;
	}

	//Sets the number of uses
	public void setAbilityUses(int abilityUses) {
		this.abilityUses = abilityUses;
	}

	//Changes the score bar color
	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	//Sets the score
	public void setScore(int score) {
		this.score = score;
	}

	//Returns the score
	public int getScore() {
		return score;
	}

	//Returns the level number
	public int getLevel() {
		return level;
	}

	//Sets the level number
	public void setLevel(int level) {
		this.level = level;
	}

	//Sets the health bar number
	public void setHealth(int health) {
		this.health = health;
	}

	//Toggles player health regeneration on?
	public void setRegen() {
		regen = true;
	}

	//Toggles player health regneration off?
	public void resetRegen() {
		regen = false;
	}

	//Sets the number of extra lives
	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	//Returns the number of extra lives
	public int getExtraLives() {
		return this.extraLives;
	}

	//Increases player health and the health bar (for a power up)?
	public void healthIncrease() {
		doubleHealth = true;
		healthMax = 200;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = 800;
	}

	//Sets player health and health bar back to original amount
	public void resetHealth() {
		doubleHealth = false;
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2;
		healthBarWidth = 400;
	}
	
	//Returns player to the maximum health
	public void restoreHealth() {
		this.health = healthMax;
	}
}
