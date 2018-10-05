package mainGame;

import java.awt.Canvas;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Toolkit;

/**
 * Loads the window of the game, and sets the proper dimensions
 * @author Brandon Loehle
 * 5/30/16
 */

public class Window extends Canvas{

	private static final long serialVersionUID = 1L;
	
	public Window(int width, int height, String title, Game game){
		
		JFrame frame = new JFrame(title);

		Toolkit tool = Toolkit.getDefaultToolkit();
		
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		int x = ((int) (tool.getScreenSize().getWidth()));
		int y = ((int) ((tool.getScreenSize().getHeight()) - taskBarHeight));


		frame.setSize(x,y);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		
	}


}
