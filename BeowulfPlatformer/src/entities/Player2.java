package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;


public class Player2 extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed=35;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0F;
	private int[][] lvlData; 
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	private float airSpeed = 0F;
	private float gravity = 0.04F * Game.SCALE;
	private float jumpSpeed = -2.25F * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5F * Game.SCALE;
	private boolean inAir = false;	
	private Playing playing;
	private Player player;
	private long lastDealtDamage;
	private boolean lost;

	
	public Player2(float x, float y, int width, int height) {
		super(x, y, width, height, 100, 10, 500);
		loadAnimations();
		initHitbox(x, y, 30*Game.SCALE, 30*Game.SCALE);
		lost=false;
		
	}
	public void setP1(Player p) {
		player = p;
	}
	
	public void update() {
		updatePosition();
		updateAnimationTick();
		setAnimation();
		updateHealth();
	}
	public void updateHealth() {
		
		if(lost) {
			try {
				playing.death("Grendel");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(attacking && hitbox.intersects(player.getHitbox())) {
			if(System.currentTimeMillis() - lastDealtDamage > attackCoolDownMillis) {
				dealDamage(player.getDamage());
				lastDealtDamage = System.currentTimeMillis();
			}
		}
		
	}
	public void render(Graphics g) {
		g.drawImage(animations[playerAction-9][aniIndex], (int)(hitbox.x-xDrawOffset), (int) (hitbox.y-yDrawOffset), width, height, null);
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
			playerAction = gRUNNING;
		else
			playerAction = gIDLE;
		if(inAir) {
			if(airSpeed < 0)
				playerAction = gJUMP;
			else
				playerAction = gFALLING;
		}
		
		if(attacking)
			playerAction = gATTACK_1;
		
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
		
		
		
//		if(canMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData)) {
//			hitbox.x += xSpeed;
//			hitbox.y += ySpeed;
//			moving = true;
//		}
			
		
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

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.GRENDEL_ATLAS);

		animations = new BufferedImage[6][4];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 64, j * 50, 64, 48);

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
	public void setPlaying(Playing play) {
		this.playing = play;
	}
	public void setPlayerAction(int action) {
		this.playerAction = action;
	}
		
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
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
	public void setAniSpeed(int speed) {
		aniSpeed = speed;
	}
	
	public void dealDamage(int dam) {
		health-=damage;
		if(health<0)lost=true;
	}
	public void updateHealthBar(Graphics g) {
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
		g.drawString("Beowulf", 560, 40);


	}
	public int getHealth() {
		return health;
	}
}
