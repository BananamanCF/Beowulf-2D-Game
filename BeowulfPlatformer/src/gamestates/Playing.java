package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements StateMethods{
	private Player player;
	private LevelManager levelManager;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {//makes a new player at location x, y
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)(64*game.SCALE), (int)(40*game.SCALE));
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();//this is to fix a bug where you click on another
								  //application while moving
	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
	
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.MENU;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;

		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
		
	}
	
	public Player getPlayer() {
		return player;
	}
}
