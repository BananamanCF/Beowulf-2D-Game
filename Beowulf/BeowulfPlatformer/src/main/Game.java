package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable{
	
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;
	private LevelManager levelManager;
	
	public final static int TILE_DEFUALT_SIZE = 32;
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
		player = new Player(200, 200, (int)(64*SCALE), (int)(40*SCALE));
		levelManager = new LevelManager(this);
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
	}
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void update() {//updates position and animation
		player.update();
		levelManager.update();
	}
	public void render(Graphics g) {//draws the position and animations
		levelManager.draw(g);
		player.render(g);
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
		player.resetDirBooleans();//this is to fix a bug where you click on another
								  //application while moving
	}
	public Player getPlayer() {
		return player;
	}
}
