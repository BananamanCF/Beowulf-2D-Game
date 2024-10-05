package entities;

import static utilz.Constants.ItemConstants.*;
import static utilz.LoadSave.GetSpriteAtlas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Flood extends Item{
	
	Player p1;
	Player2 p2;//need the players here to manipulate damage, health, gravity ect;
	long durationHoldingItem;
	
	public Flood(float x, float y, int width, int height, Player p1, Player2 p2) { 
		super(x, y, width, height, 5000);//5000 is duration in milli seconds
		initHitbox(x, y, width, height);
		this.p1=p1;
		this.p2=p2;
		collected = false;
		
	}
	public void update() {
		if(!collected) {
			if(p2.getHitbox().intersects(hitbox) || p1.getHitbox().intersects(hitbox)) {//if player2's (Grendel) hitbox collides with the item start the item duration timer
				p2.setHoldingItem(FLOOD);
				p1.setGravity(.01F);//changes grav for both players
				p2.setGravity(.01F);
				p1.setJumpSpeed(-1.50F);
				p2.setJumpSpeed(-1.50F);
				p1.setSpeed(0.65F);
				p2.setSpeed(0.65F);
				started = System.currentTimeMillis();
				collected = true;
			}
			else {
				return;
			}
		}
		else if(collected && System.currentTimeMillis() - started >= itemDuration) {
			p2.setHoldingItem(NONE);
			p1.setGravity(.04F);//changes grav back
			p2.setGravity(.04F);
			p2.setJumpSpeed(-2.25F);
			p1.setJumpSpeed(-2.25F);
			p1.setSpeed(1.5F);
			p2.setSpeed(1.5F);
			
			hitbox=null;//just to say stop using this item
		}
	}
	
	public void drawItem(Graphics g) {//for when we have the picture of the item
		BufferedImage img = GetSpriteAtlas(LoadSave.FLOOD_ATLAS);
		Image image = img.getScaledInstance(width, height, img.getType());
        
		g.drawImage(image, 100, 300, null);
		//drawHitbox(g);
	}
	
}
