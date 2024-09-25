package inputs;

import java.awt.event.KeyEvent;
import static utilz.Constants.Directions.*;

import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs implements KeyListener{//this class is for any actions based on keyboard inputs
	
	private GamePanel gamePanel;
	
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {//move wasd
		switch(Gamestate.state){
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {//moves wasd
		switch(Gamestate.state){
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
		}
		
	}


}
