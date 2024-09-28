package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entities.Charm;
import entities.Flood;
import entities.Item;
import entities.Player;
import entities.Player2;
import levels.LevelManager;
import main.Game;
import utilz.Constants;

public class Playing extends State implements StateMethods{
	private Player player;
	private Player2 player2;
	private LevelManager levelManager;
	private List<Item> items;//list of items on the map
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {//makes a new player at location x, y
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)(64*game.SCALE), (int)(40*game.SCALE));
		player2 = new Player2(200, 200, (int)(64*game.SCALE), (int)(40*game.SCALE));
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		player2.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		player.setP2(player2);
		player2.setP1(player);
		player.setPlaying(this);
		player2.setPlaying(this);
		
		items = new ArrayList<Item>();
		items.add(new Charm(300, 300, 50, 50, player, player2));//testing the charm class we can add a random feature later
		items.add(new Flood(100, 300, 50, 50, player, player2));
		//Havnt made a picture so it will just be a pink hitbox
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();//this is to fix a bug where you click on another
		player2.resetDirBooleans();//application while moving
	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		player2.update();
		List<Item> toBeRemoved = new ArrayList<Item>();
		for(Item i : items) {
			if(i.getHitbox()==null)//if they hitbox is null then the item timer is done
				toBeRemoved.add(i);
			else
				i.update();
		}
		items.removeAll(toBeRemoved);
		
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		player2.render(g);
		
		player.updateHealthBar(g);
		player2.updateHealthBar(g);
		for(Item a : items) {
			if(!a.getCollected()) {// if they item has been collected you shouldnt draw it
				a.drawItem(g);
				a.drawHitbox(g);
			}
				

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
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
	
		case KeyEvent.VK_S:
			player.setAttacking(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_W:
			player.setJump(true);
			break;
		case KeyEvent.VK_DOWN:
			player2.setAttacking(true);
			break;
		case KeyEvent.VK_LEFT:
			player2.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			player2.setRight(true);
			break;
		case KeyEvent.VK_UP:
			player2.setJump(true);
			break;
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.MENU;
			break;
		default:
			break;
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
		case KeyEvent.VK_W:
			player.setJump(false);
			break;
		case KeyEvent.VK_LEFT:
			player2.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			player2.setRight(false);
			break;
		case KeyEvent.VK_UP:
			player2.setJump(false);
			break;
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.PLAYING;
			break;
		default:
			break;
		}
		
	}
	public void death(String name) throws InterruptedException {//dont worry about this for now
		if(name.equals("Grendel") || true) {
//			player2.setAniSpeed(40);
//			player2.setPlayerAction(Constants.PlayerConstants.gHIT);
		}
		else {
			
		}
		
		TimeUnit.SECONDS.sleep(1);
		Gamestate.state = Gamestate.MENU;
		Menu.death = name;
	}
	
	
	public Player getPlayer() {
		return player;
	}
	public Player2 getPlayer2() {
		return player2;
	}
}
