package mainGame;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Class used for containing every instance of GameObject. These include all
 * enemies and players
 * 
 * @author Brandon Loehle 5/30/16
 */
public class Handler {

	ArrayList<GameObject> object = new ArrayList<GameObject>();
	ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	private int timer = 0;

	/**
	 * Updates each entity in the game by looping through each ArrayList and calling
	 * the tick() function on each object
	 */
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Trail
					|| tempObject.getId() == ID.EnemyBurstWarning) {// we don't want these to ever be frozen by the
																	// Screen Freeze ability

				// Every GameObject has a tick method, so this effectively updates every single
				// object
				tempObject.tick();

			} else {
				timer--;
				if (timer <= 0) {// if Screen Freeze power-up is unlocked, enemy ID's will pause for the length
									// of the timer, and not update
					tempObject.tick();
				}
			}
		}
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);

			// Every Pickup has a tick method, so this effectively updates every single
			// object
			tempObject.tick();
		}

	}

	/**
	 * Redraws each entity in the game by looping through each ArrayList and calling
	 * the tick() function on each object
	 */
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);

			tempObject.render(g);
		}
	}

	public void pause() {
		timer = 1000;
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	public void addPickup(Pickup object) {
		this.pickups.add(object);
	}

	public void removePickup(Pickup object) {
		this.pickups.remove(object);
	}

	/**
	 * Clears all entities that have an ID of some sort of enemy
	 */
	public void clearEnemies() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if (tempObject.getId() != ID.Player) {
				this.removeObject(tempObject);
				i--; // Removing shrinks the array by 1, causing the loop to skip an enemy
			}
		}
	}

	/**
	 * Clears all entities that have an ID of player
	 */
	public void clearPlayer() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if (tempObject.getId() == ID.Player) {
				this.removeObject(tempObject);
				i--; // Removing shrinks the array by 1, causing the loop to skip a player (should
						// there be more than one)
			}
		}
	}
}
