package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * This is the text you see before each set of 10 levels
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class LevelText extends GameObject {

	private String text;
	private int timer;
	private Color[] color = { Color.WHITE, Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.YELLOW };
	private Random r = new Random();
	private int index;

	public LevelText(double x, double y, String text, ID id) {
		super(x, y, id);
		this.text = text;
		AffineTransform at = new AffineTransform();
		timer = 15;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		timer--;

		Font font = new Font("Amoebic", 1, 125);
		g.setFont(font);
		g.setColor(color[index]);// set the new random color
		g.drawString(this.text, Game.WIDTH / 2 - getTextWidth(font, this.text) / 2, (int) this.y);

		if (timer == 0) {
			index = r.nextInt(9);// get a new random color
			timer = 15;
		}

	}

	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
