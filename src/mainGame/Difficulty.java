package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import mainGame.Game.STATE;

public class Difficulty {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int buttonwidth = Game.WIDTH / 4;
	private int buttonheight = Game.HEIGHT / 5;

	public Difficulty(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Difficulty) {
			Font font = new Font("Amoebic", 1, Game.WIDTH / 20);

			g.setFont(font);
			g.setColor(Color.WHITE);

			Rectangle titleCenter = new Rectangle(((Game.WIDTH - 500) / 2), ((Game.HEIGHT - 200) / 16), 500, 200);
			drawCenteredString(g, "Pick a Difficulty", titleCenter, font);

			// EASY BUTTON
			Rectangle easyButton = new Rectangle(((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight);
			drawCenteredString(g, "EASY", easyButton, font);
			
			g.drawRect(((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) * 5 / 6), buttonwidth,
					buttonheight);
			Image sleepEmoji = null;
			try {
				sleepEmoji = ImageIO.read(new File("images/SleepEmoji.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(sleepEmoji, (Game.WIDTH * 1/6) - 100, (Game.HEIGHT / 2) - 105, 210,
					200, null);

			// MEDIUM Button
			Rectangle mediumButton = new Rectangle(((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight);
			drawCenteredString(g, "MEDIUM", mediumButton, font);
			g.drawRect(((Game.WIDTH - buttonwidth) / 2), ((Game.HEIGHT - buttonheight) * 5 / 6), buttonwidth, buttonheight);
			Image smilingEmoji = null;
			try {
				smilingEmoji = ImageIO.read(new File("images/smilingEmoji.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(smilingEmoji, ((Game.WIDTH / 2) - 105), ((Game.HEIGHT / 2) - 100), 210,
					200, null);

			// HARD BUTTON
			Rectangle hardButton = new Rectangle(((Game.WIDTH - buttonwidth) * 15/16), ((Game.HEIGHT - buttonheight) * 5 / 6),
					buttonwidth, buttonheight);
			drawCenteredString(g, "HARD", hardButton, font);
			g.drawRect(((Game.WIDTH - buttonwidth) * 15/16), ((Game.HEIGHT - buttonheight) * 5 / 6), buttonwidth,
					buttonheight);
			Image devilEmoji = null;
			try {
				devilEmoji = ImageIO.read(new File("images/DevilEmoji.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(devilEmoji, (Game.WIDTH * 5/6) - 100, (Game.HEIGHT / 2) - 105, 210,
					200, null);
		}
	}

	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {

		FontMetrics metrics = g.getFontMetrics(font);

		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

		g.setFont(font);
		g.drawString(text, x, y);
	}
}
