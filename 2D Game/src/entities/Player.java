package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.ItemConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;


public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed=30;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.5F;
	private int[][] lvlData; 
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	private float airSpeed = 0F;
	private float gravity = 0.04F * Game.SCALE;
	private float jumpSpeed = -2.25F * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5F * Game.SCALE;
	private boolean inAir = false;
	private Playing playing;
	private Player2 player2;
	private long lastDealtDamage;
	private boolean lost;
	
	
	private int holdingItem = NONE;
	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height, 100, 10, 1000);
		loadAnimations();
		initHitbox(x, y, 30*Game.SCALE, 30*Game.SCALE);
		lost=false;
		
	}
	public void setP2(Player2 p2) {
		player2=p2;
	}

	
	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();
		updateItem(); 
		updateHealth();
	}
	public void updateItem() {
		switch(holdingItem){
			case NONE:
				break;
			case CHARM:
		}
	}
	public void updateHealth() {
		
		if(lost) {
			try {
				playing.death("Beowulf");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(attacking && hitbox.intersects(player2.getHitbox())) {
			if(System.currentTimeMillis() - lastDealtDamage > attackCoolDownMillis) {
				dealDamage(player2.getDamage());
				lastDealtDamage = System.currentTimeMillis();
			}
		}
	}
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int) (hitbox.y-yDrawOffset), width, height, null);
		drawHitbox(g);
		if(health<=0)lost=true;
		updateHealthBar(g);
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick>=aniSpeed) {
			aniTick=0;
			aniIndex++;
		}
		if(aniIndex >= GetAnimationLen(playerAction)) {
			aniIndex = 0;
			attacking = false;
		}
	}
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		if(inAir) {
			if(airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}
		
		if(attacking)
			playerAction = ATTACK_1;
		
		if(startAni != playerAction)
				resetAniTick();
	}
	private void resetAniTick() {
		aniTick=0;
		aniIndex=0;
	}

	private void updatePosition() {
		
		moving = false;
		if(jump)
			jump();
		
		if(!left && !right && !inAir)
			return;
		
		float xSpeed=0;
		
		if(left) 
			xSpeed -= playerSpeed;
		if(right) 
			xSpeed += playerSpeed;
		if(!inAir) 
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		
		if(inAir) {
			if(canMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}	
		}
		else 
			updateXPos(xSpeed);
		
		moving = true;
		
	}
	
	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed=0;		
	}

	private void updateXPos(float xSpeed) {
		if(canMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		}
		else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
		
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[9][3];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 38);

	}
	public void loadLevelData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}
	
	public void resetDirBooleans() { 
		left=false;
		right=false;
		up=false;
		down=false;
	}
	public void setDamage(int damage) {
		this.damage=damage;
	}

	public void setPlayerAction(int action) {
		playerAction = action;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	public void setPlaying(Playing play) {
		this.playing = play;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public int getDamage() {
		return damage;
	}
	public void dealDamage(int dam) {
		health-=damage;
		if(health<0)lost=true;
	}
	public void setHoldingItem(int item) {
		holdingItem = item;
	}

	public void updateHealthBar(Graphics g) {// i beg dont messs with this
		int h = (int)((Game.GAME_WIDTH-45)/3 * (health/100.0));

		g.setColor(Color.BLACK);
		g.fillRect(Game.GAME_WIDTH/3+80, 50, Game.GAME_WIDTH/3, (int)(20*Game.SCALE));

		if(health!=-10) {
			g.setColor(Color.WHITE);
			g.fillRect(Game.GAME_WIDTH/3+60+26, 57, h, (int)(10*Game.SCALE));
		}
		else {
			lost=true;
		}
		g.setFont(new Font( "SansSerif", Font.PLAIN, 20));
		g.drawString("Grendel", 560, 40);


	}
	public int getHealth() {
		return health;
	}
	
}
