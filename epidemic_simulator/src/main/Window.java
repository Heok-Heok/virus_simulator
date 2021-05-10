package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

/**
 *
 */
private static final long serialVersionUID = -2076918404673422636L;
public Window( int width, int height, String title, Game game) {
	JFrame frame = new JFrame(title);

	frame.setPreferredSize(new Dimension(width,height));
	frame.setMaximumSize(new Dimension(width,height));
	frame.setMinimumSize(new Dimension(width,height));

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);//starts the program in the middle of the screen
	frame.add(game);
	frame.setVisible(true);
	game.start();

}

}