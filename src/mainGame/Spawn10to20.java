package mainGame;

import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * This class closely resembles Spawn1to10. Please refer to that class for
 * documentation
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Spawn10to20 {

	//Instance variables
	private Handler handler;
	private HUD hud;
	private Game game;
	private int scoreKeep = 0;
	private Random r = new Random();
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>();
	private int index;
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	public static int LEVEL_SET_2_RESET = 0;
	public int BossEyeTimer = 3600;
	private Victory victory;

	private double playerX;
	private double playerY;
	private double enemyX;
	private double enemyY;

	//Constructor
	public Spawn10to20(Handler handler, HUD hud, Spawn1to10 spawner, Game game) {
		restart();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.victory = victory;
		hud.restoreHealth();
		spawnTimer = 12;
		levelTimer = 100;
		levelsRemaining = 10;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;
	}

	//adds a level number to the array list of levels 
	public void addLevels() {
		for (int i = 1; i <= 10; i++) {
			levels.add(i);
		}
	}

	public void tick() {
	
		//Controls which enemies spawn on which levels
		switch (levelNumber) {
		
		/**
		 * Case system to spawn correct enemies based on current level
		 */
		
		//"Opening screen"
		case 0:
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Same levels...",
						ID.Levels1to10Text));
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2, "...but a little harder now",
						ID.Levels1to10Text));
				tempCounter++;
			}
			//Gets rid of all enemies if they are still present 
			if (levelTimer <= 0) {
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = levels.get(index); //Queues up the next level
			}

		break;

		//Case 1's comments apply to the remaining cases
		case 1:
			spawnTimer--; //Keep decrementing the spawning spawnTimer 60 times a second
			levelTimer--; // Keep decrementing the level spawnTimer 60 times a second
			if (tempCounter < 1) { //Sets time level timer ticks for 
				levelTimer = 1500;
				tempCounter++; //Ensures the method is only called once
			}
			if (spawnTimer == 0) { //Queue's another enemy to spawn 
				
				findX();
				findY();
				enemyX = r.nextInt(Game.HEIGHT); //Generates an enemy spawn x
				enemyY = r.nextInt(Game.WIDTH); //Generates an enemy spawn y
				
				//Regenerates enemy coordinates if they are in the same area as the player
				while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
					enemyY = r.nextInt(Game.WIDTH);
					enemyX = r.nextInt(Game.HEIGHT);
				}
				
				//Creates the enemy 
				handler.addObject(new LighterJelly(enemyY, enemyX, 9, 9, ID.EnemyBasic, handler));
				
				spawnTimer = 80;//Reset the spawn timer
			}
			if (levelTimer == 0) { //Checks if the level is over
				handler.clearEnemies(); //Clears all the enemies
				hud.setLevel(hud.getLevel() + 1); //Increments level number on HUD
				spawnTimer = 40;
				tempCounter = 0;
				if (levelsRemaining == 1) { //Checks if its time for the boss
					levelNumber = 101; //Arbitrary number for the boss level
				} else {
					levels.remove(index); //Remove the current level from being selected
					levelsRemaining--;
					index = r.nextInt(levelsRemaining); //Pick another level at random
					levelNumber = levels.get(index);
				}
			} 
			break;
		case 2:
			spawnTimer--;
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT);
			enemyY = r.nextInt(Game.WIDTH);
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH);
				enemyX = r.nextInt(Game.HEIGHT);
			}
			
			if (tempCounter < 1) {
				levelTimer = 1500;
				tempCounter++;
			}
			if (spawnTimer == 30) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 20, 2, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 20, -2, ID.EnemySweep, handler));
			} else if (spawnTimer == 10) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 20, 4, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 20, -4, ID.EnemySweep, handler));
				spawnTimer = 45;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
			break;
		case 3:
			spawnTimer--;
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT);
			enemyY = r.nextInt(Game.WIDTH);
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH);
				enemyX = r.nextInt(Game.HEIGHT);
			}
			
			if (tempCounter < 1) {
				levelTimer = 1500;
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new Leech(enemyY, enemyX, -5, ID.EnemySmart, handler));
				spawnTimer = 60;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} 
			break;
		case 4:
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT) - 75;
			enemyY = r.nextInt(Game.WIDTH) - 35;
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				
				//these two lines have constants subtracted from them
				//because thats how they were originally made when instantiated -EH
				enemyY = r.nextInt(Game.WIDTH) - 35;
				enemyX = r.nextInt(Game.HEIGHT) - 75;
			}
			
			if (tempCounter < 1) {
				handler.addObject(new ShooterEnemy(enemyY, enemyX, 100, 100,
						-30, ID.EnemyShooter, this.handler));
				levelTimer = 1300;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} 
			break;
		case 5:
			spawnTimer--;
			levelTimer--;
			
			//this case statement doesn't generate enemies like the rest,
			//so it doesn't need my special generation parameters. -EH
			
			if (tempCounter < 1) {
				levelTimer = 1400;
				tempCounter++;
			}
			if (spawnTimer <= 0) {
 				handler.addObject(new Turtle(-250, 250, 75, 75, 250, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 120;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} 
			break;
		case 6:
			spawnTimer--;
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT);
			enemyY = r.nextInt(Game.WIDTH);
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH);
				enemyX = r.nextInt(Game.HEIGHT);
			}
			
			if (tempCounter < 1) {
				levelTimer = 1500;
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new LighterJelly(enemyY, enemyX, 15, 15, ID.EnemyBasic, handler));
				spawnTimer = 50;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 40;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} break;
		case 7:
			spawnTimer--;
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT);
			enemyY = r.nextInt(Game.WIDTH);
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH);
				enemyX = r.nextInt(Game.HEIGHT);
			}
			
			if (tempCounter < 1) {
				levelTimer = 1500;
				tempCounter++;
			}
			if (spawnTimer == 35) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 25, 2, ID.EnemySweep, handler));
			} else if (spawnTimer == 25) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 25, -2, ID.EnemySweep, handler));
			} else if (spawnTimer == 15) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 30, 4, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new DarkerJelly2(enemyY, enemyX, 25, -4, ID.EnemySweep, handler));
				spawnTimer = 30;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} break;
		case 8:
			spawnTimer--;
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT);
			enemyY = r.nextInt(Game.WIDTH);
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH);
				enemyX = r.nextInt(Game.HEIGHT);
			}
			
			if (tempCounter < 1) {
				levelTimer = 1000;
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new Leech(enemyY, enemyX, -9, ID.EnemySmart, handler));
				spawnTimer = 50;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} break;
		case 9:
			levelTimer--;
			
			findX();
			findY();
			enemyX = r.nextInt(Game.HEIGHT) - 75;
			enemyY = r.nextInt(Game.WIDTH) - 35;
			while (Math.abs(enemyY - playerX) < 100 && Math.abs(enemyX - playerY) < 100) {
				enemyY = r.nextInt(Game.WIDTH) - 35;
				enemyX = r.nextInt(Game.HEIGHT) - 75;
			}
			
			if (tempCounter < 1) {
				handler.addObject(new ShooterEnemy(enemyY, enemyX, 200, 200,
						-40, ID.EnemyShooter, this.handler));
				levelTimer = 2500;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			} break;
		
		case 10:
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1400;
				tempCounter++;
			}
			if (spawnTimer <= 0) {
				handler.addObject(new Turtle(-300, 300, 60, 60, 300, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 60;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
			break;

		case 101:
			if (tempCounter < 1) {
				handler.addObject(new BossEye(Game.WIDTH / 2 - 300, Game.HEIGHT / 2 - 300, ID.BossEye, handler, 1));
				handler.addObject(new BossEye(Game.WIDTH / 2   -50, Game.HEIGHT / 2 - 300, ID.BossEye, handler, 2));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 200, Game.HEIGHT / 2 - 300, ID.BossEye, handler, 3));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 300, Game.HEIGHT / 2   -50, ID.BossEye, handler, 4));
				handler.addObject(new BossEye(Game.WIDTH / 2  -50 , Game.HEIGHT / 2   -50, ID.BossEye, handler, 5));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 200, Game.HEIGHT / 2   -50, ID.BossEye, handler, 6));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 300, Game.HEIGHT / 2 + 200, ID.BossEye, handler, 7));
				handler.addObject(new BossEye(Game.WIDTH / 2   -50, Game.HEIGHT / 2 + 200, ID.BossEye, handler, 8));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 200, Game.HEIGHT / 2 + 200, ID.BossEye, handler, 9));

				tempCounter++;
				
				} else if (tempCounter == 1) {
						for (int i = 0; i < handler.object.size(); i++) {
							GameObject tempObject = handler.object.get(i);
							if (tempObject.getId() == ID.BossEye) {
								if (tempObject.getHealth() <= 0) {
									handler.removeObject(tempObject);
									LEVEL_SET_2_RESET++;
									game.gameState = STATE.Victory;
									tempCounter++;
								}
							}
						}
				}
		}	
					}
				
	//Code for the skip level ability?
	public void skipLevel() {
		if (levelsRemaining == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsRemaining > 1) {
			levels.remove(index);
			levelsRemaining--;
			tempCounter = 0;
			index = r.nextInt(levelsRemaining);
			levelNumber = levels.get(index);
		}
	}

	//Resets the game
	public void restart() {
		levelNumber = 0;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 10;
		index = r.nextInt(levelsRemaining);

	}

	//Gets the player's x coordinate 
	public void findX() {
		playerX = Game.getPlayerX();
	}

	//Gets the player's y coordinate 
	public void findY() {
		playerY = Game.getPlayerY();
	}

}

