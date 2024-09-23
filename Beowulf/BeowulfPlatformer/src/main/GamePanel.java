package main;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;
 
public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private Game game;
	
	public GamePanel(Game game) {
		this.game = game;
		setPanelSize();
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);//Size of the game window
		setPreferredSize(size);
		System.out.println("size : "+GAME_WIDTH+" gmae Height : "+GAME_HEIGHT);
	}



	public void updateGame() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
		
	}
	
	public Game getGame() {
		return game;
	}
	
	
	
	
}
