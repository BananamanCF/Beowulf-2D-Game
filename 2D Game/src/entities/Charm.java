package entities;

import static utilz.Constants.ItemConstants.*;

public class Charm extends Item{
	
	Player p1;
	Player2 p2;
	long durationHoldingItem;
	
	public Charm(float x, float y, int width, int height, Player p1, Player2 p2) { 
		super(x, y, width, height, 5000);
		initHitbox(x, y, width, height);
		this.p1=p1;
		this.p2=p2;
		
		p1.setDamage(5);
		p2.setHoldingItem(CHARM);
	}
	
	
	
}
