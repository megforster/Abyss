package mainGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import mainGame.Game.STATE;

/**
 * Handles all mouse input
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class MouseListener extends MouseAdapter {

	// Instance variables
	private Game game;
	private Handler handler;
	private HUD hud;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private String upgradeText;
	private Victory victory;
	private int buttonwidth = Game.WIDTH / 4;
	private int buttonheight = Game.HEIGHT / 5;
	private Difficulty difficulty;
	private Theme theme;
	private Pause pause;

	// Constructor
	public MouseListener(Game game, Handler handler, HUD hud, Spawn1to10 spawner, Spawn10to20 spawner2,
			UpgradeScreen upgradeScreen, Player player, Upgrades upgrades, Victory victory, Theme theme, Difficulty difficulty, Pause pause) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
		this.victory = victory;
		this.theme = theme;
		this.difficulty = difficulty;
		this.pause = pause;
	}

	// Code for reaction to a mouse press
	public void mousePressed(MouseEvent e) {
		// gets x and y coordinates of the mouse press
		int mx = e.getX();
		int my = e.getY();

		// checks if the game has ended and restarts the game to an active game screen
		if (game.gameState == STATE.GameOver) {
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawner.restart();
			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to10.LEVEL_SET = 1;
			game.gameState = STATE.Game;
			handler.addObject(player);
		}

		// Checks if the game has been won and restarts it to the main menu
		else if (game.gameState == STATE.Victory) {
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawner.restart();
			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to10.LEVEL_SET = 1;
			game.gameState = STATE.Menu;
		}

		// checks if the game is currently active
		else if (game.gameState == STATE.Game) {

		}

		// checks if the upgrades are being accessed
		else if (game.gameState == STATE.Upgrade) {

			// checks if the mouse press was over an upgrade option and if so performs that
			// option

			if (mouseOver(mx, my, 100, 300, 1721, 174)) {
				upgradeText = upgradeScreen.getPath(1);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(1);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(2);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(2);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(3);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(3);

				game.gameState = STATE.Game;
			}

		}

		// Checks if the game is on the menu screen
		else if (game.gameState == STATE.Menu) {

			// checks if "click to start" has been selected and starts the game
			if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) / 2), buttonwidth,
					buttonheight)) {
				handler.object.clear();
				game.gameState = STATE.Game;
				handler.addObject(player);
			}

			// Checks if the Help Button has been pressed and opens it
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight)) {
				JOptionPane.showMessageDialog(game, game.message, "Help Menu", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(MouseListener.class.getResource("/images/PlayerSprite.png")));
			}

			// Checks if the Credits button has been pressed and opens it
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) * 15 / 16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight)) {
				JOptionPane.showMessageDialog(game,
						"Made by the Brogrammers 2.0 for CSC 225. \n In Loving Memory of Christopher Cherry.");
			}

			// Checks if the quit button has been pressed and exits the game
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight)) {
				System.exit(1);
			} else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) / 2),
					buttonwidth, buttonheight)) {
				game.gameState = STATE.Theme;
			}
		}
			//Code for theme menu
		else if (game.gameState == STATE.Theme) {
			//code for ocean background
			if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) / 2), buttonwidth,
					buttonheight)) {
				theme.setBackgroundImage(0);
				theme.setBackgroundMusic(0);
				game.gameState = STATE.Menu; //returns the user to the main game menu after selection
				
				
				//code for classic background
			} else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) / 2),
					buttonwidth, buttonheight)) {
				theme.setBackgroundImage(1);
				theme.setBackgroundMusic(1);
				game.gameState = STATE.Menu;
				
				//code for space background
			} else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth)*15/16), ((Game.HEIGHT - buttonheight) / 2),
					buttonwidth, buttonheight)) {
				theme.setBackgroundImage(2);
				theme.setBackgroundMusic(2);
				game.gameState = STATE.Menu;
			}
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth)*15/16), ((Game.HEIGHT - buttonheight)*5/6), buttonwidth, buttonheight)) {
				System.exit(1);
			}
		}
		
		else if(game.gameState == STATE.Difficulty) {
			if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) / 2), buttonwidth,
					buttonheight)) { 
				
				
			}
		}

		// Code for when the game is paused
		else if (game.gameState == STATE.Pause) {

			// Checks if the play button has been pressed and unpauses the game (currently
			// broken)
			if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) / 2), buttonwidth,
					buttonheight)) {
				//game.gameState = STATE.Game;
				pause.tick();
				
			}

			// Checks if a different help button has been pressed and opens a help menu
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight)) {
				JOptionPane.showMessageDialog(game, game.message);
			}

			// Checks if the exit button has been pressed and ??
			else if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) * 15 / 16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight)) {
				game.gameState = STATE.Menu;
				handler.clearPlayer();
				handler.clearEnemies();
				// Spawn1to10.restart();
				// hud.setLevehl(0);
				// hud.resetHealth();
				// hud.setScore(0);
				// hud.setExtraLives(0); // Exit button still fails to fully reset the game

			}

		}

		// Checks if the back button on the help menu has been pressed and returns to
		// the main menu
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, ((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) * 5 / 6), buttonwidth,
					buttonheight)) {
				game.gameState = STATE.Menu;
				return;
			}
		}
	}

	// code for when a mouse press is released
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Helper method to detect is the mouse is over a "button" drawn via Graphics
	 * 
	 * @param mx
	 *            mouse x position
	 * @param my
	 *            mouse y position
	 * @param x
	 *            button x position
	 * @param y
	 *            button y position
	 * @param width
	 *            button width
	 * @param height
	 *            button height
	 * @return boolean, true if the mouse is contained within the button
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
}
