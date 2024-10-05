package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Item {
	
	Rectangle2D.Float hitbox;
	float x, y;
	int width, height;
	long itemDuration;
	long started;
	protected boolean collected;
	
	public Item(float x, float y, int width, int height, long itemDuration) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.itemDuration = itemDuration;
	}
	
	
	public void update() {
		
	}
	public void drawItem(Graphics g) {
		
	}
	
	public Rectangle2D.Float getHitbox(){
		return hitbox;
	}
	public void drawHitbox(Graphics g) {
		//For debugging hitbox
		//g.setColor(Color.PINK);
		//g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int) hitbox.height);
	}
	
	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
		
	}
	public boolean getCollected() {
		return collected;
	}
	
	
	
}
