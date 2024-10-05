package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int health;
	protected int damage;
	protected int attackCoolDownMillis;
	
	public Entity(float x, float y, int width, int height, int health, int damage, int attackCool) {
		this.health = health;
		this.damage=damage;
		this.width = width;
		this.height = height;
		this.x=x;
		this.y=y;
		attackCoolDownMillis = attackCool;
		
	}
	protected void drawHitbox(Graphics g) {
		//For debugging hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
		
	}
//	protected void updateHitbox() {
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	public int getHealth() {
		return health;
	}
	
}
