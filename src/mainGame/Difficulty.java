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
		if (game.gameState == STATE.Theme) {
			Font font = new Font("Amoebic", 1, Game.WIDTH / 20);

			g.setFont(font);
			g.setColor(Color.white);

			Rectangle titleCenter = new Rectangle(((Game.WIDTH - 500) / 2), ((Game.HEIGHT - 200) / 16), 500, 200);
			drawCenteredString(g, "Pick a Difficulty", titleCenter, font);
			g.drawRect(((Game.WIDTH - buttonwidth) / 16), ((Game.HEIGHT - buttonheight) / 2), buttonwidth,
					buttonheight);

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



