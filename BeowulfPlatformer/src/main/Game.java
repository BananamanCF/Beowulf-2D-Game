package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;
import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable{
	
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
	
	public final static int TILE_DEFUALT_SIZE = 48;
	public final static float SCALE = 1.5F;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILE_DEFUALT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game() {
		initClasses();
		
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus(); 
		
		startGameLoop();
	}
	private void initClasses() {//makes a new player at location x, y
		menu = new Menu(this);
		playing = new Playing(this);
	}
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void update() {//updates position and animation
		
		switch(Gamestate.state) {
		
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			default:
				break;
			
		
		}
	}
	public void render(Graphics g) {//draws the position and animations
		switch(Gamestate.state) {
				
				case MENU:
					menu.draw(g);
					break;
				case PLAYING:
					playing.draw(g);
					break;
				default:
					break;
				
				}
	}

	@Override
	public void run() {//The run method that does everything while running the game
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long prevTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastChecked = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {//game loop 
			long curTime = System.nanoTime();
			
			deltaU += (curTime - prevTime)/timePerUpdate;
			deltaF += (curTime - prevTime)/timePerFrame;
			
			prevTime = curTime;
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			if(System.currentTimeMillis() - lastChecked >= 1000) {
				lastChecked = System.currentTimeMillis();
				System.out.println(frames+" FPS"+" | UPS: "+updates);
				frames=0;
				updates = 0;
			}
		}
	}
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}
	public Menu getMenu() {
		return menu;
	}
	public Playing getPlaying() {
		return playing;
	}
}
