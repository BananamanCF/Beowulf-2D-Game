package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LEFT=0;
		public static final int UP=1;
		public static final int RIGHT=2;
		public static final int DOWN=3;
	}
	public static class ItemConstants{
		public static final int NONE = 0;
		public static final int CHARM = 1;
		public static final int SWORD = 2;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;//Beowulf animations
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUND = 4;
		public static final int HIT = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_JUMP_1 = 7;
		public static final int ATTACK_JUMP_2 = 8;
		
		public static final int gIDLE = 10;//Grendel animations
		public static final int gRUNNING = 9;
		public static final int gJUMP = 11;
		public static final int gFALLING = 12;
		public static final int gGROUND = -1;
		public static final int gHIT = 13;
		public static final int gATTACK_1 = 14;
		public static final int gATTACK_JUMP_1 = 16;
		public static final int gATTACK_JUMP_2 = 17;

		public static int GetAnimationLen(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 3;
			case IDLE:
				return 2;
			case HIT:
				return 1;
			case JUMP:
				return 1;
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 2;
			case GROUND:
				return 2;
			case FALLING:
				return 1;
				
			case gIDLE:
				return 2;
			case gRUNNING:
				return 2;
			case gHIT:
				return 4;
			case gJUMP:
				return 1;
			case gATTACK_1:
			case gATTACK_JUMP_1:
			case gATTACK_JUMP_2:
				return 2;
			case gGROUND:
				return 1;
			case gFALLING:
				return 1;
				
			default:
				return 1;
			}
		}
	}
		
}
