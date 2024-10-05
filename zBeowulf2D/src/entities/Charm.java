package entities;

import static utilz.Constants.ItemConstants.*;
import static utilz.LoadSave.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Charm extends Item{
	
	Player p1;
	Player2 p2;//need the players here to manipulate damage, health, gravity ect;
	long durationHoldingItem;
	
	public Charm(float x, float y, int width, int height, Player p1, Player2 p2) { 
		super(x, y, width, height, 5000);//5000 is duration in milli seconds
		initHitbox(x, y, width, height);
		this.p1=p1;
		this.p2=p2;
		collected = false;
		
	}
	public void update() {
		if(!collected) {
			if(p2.getHitbox().intersects(hitbox)) {//if player2's (Grendel) hitbox collides with the item start the item duration timer
				p2.setHoldingItem(CHARM);
				p1.setDamage(3);//Grendels charm makes Beowulf do less damage
				started = System.currentTimeMillis();
				collected = true;
			}
			else {
				return;
			}
		}
		else if(collected && System.currentTimeMillis() - started >= itemDuration) {
			p1.setDamage(10);
			p2.setHoldingItem(NONE);
			hitbox=null;//just to say stop using this item
		}
	}
	
	public void drawItem(Graphics g) {//for when we have the picture of the item
		BufferedImage img = GetSpriteAtlas(LoadSave.CHARM_ATLAS);
		Image image = img.getScaledInstance(width, height, img.getType());
        
		g.drawImage(image, 300, 300, null);
		//drawHitbox(g);
	}
	
}
