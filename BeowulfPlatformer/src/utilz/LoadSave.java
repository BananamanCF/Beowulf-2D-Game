package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "player_spritesV2.png";//Grendel animations
	public static final String GRENDEL_ATLAS = "player_spritesGrendelV3.png";
	public static final String LEVEL_ATLAS = "levelTileset.png";
	public static final String LEVEL_ONE_DATA = "mapTest.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) { //returns an image at file location fileName
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);
		try{
			img = ImageIO.read(is);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return img;
	}
	public static int[][] GetLevelData(){
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		for(int i=0;i<img.getHeight();i++) {
			for(int j=0;j<img.getWidth();j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if(value >= 48)
						value=0;
				lvlData[i][j] = value;
			}
		}
		return lvlData;
	}
}
