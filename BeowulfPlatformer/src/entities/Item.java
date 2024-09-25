package entities;

import java.awt.geom.Rectangle2D;

public class Item {
	
	Rectangle2D.Float hitbox;
	float x, y;
	int width, height;
	
	public Item(Rectangle2D.Float hitbox, float x, float y, int width, int height) {
		this.hitbox = hitbox;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public Rectangle2D.Float getHitbox(){
		return hitbox;
	}
	
	
}
