package de.unisaarland.cs.sopra.common.view;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.HarborType;

public class Textures {

	public static Texture streetTexture;
	public static Texture catapultTexture;
	public static Texture robberTexture;
	public static Texture pathMarkTexture;
	public static Texture pathMarkTextureRed;
	public static Texture fieldMarkTexture;
	public static Texture intersectionMarkTexture;
	public static Texture intersectionMarkRedTexture;
	public static Texture villageTexture;
	public static Texture townTexture;
	public static Map<FieldType,Texture> fieldTextureMap;
	public static Map<HarborType, Texture> harborTextureMap;
	public static Map<Integer,Texture> numberTextureMap;
	public static Map<String,Texture> uiTextureMap;

	public static void initiateTextures() {
		try {
			fieldTextureMap = new HashMap<FieldType,Texture>();
			fieldTextureMap.put(FieldType.MOUNTAINS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Mountains.png")));
			fieldTextureMap.put(FieldType.DESERT, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Desert.png")));
			fieldTextureMap.put(FieldType.FIELDS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Fields.png")));
			fieldTextureMap.put(FieldType.FOREST, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Forest.png")));
			fieldTextureMap.put(FieldType.HILLS, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Hills.png")));
			fieldTextureMap.put(FieldType.PASTURE, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Pasture.png")));
			fieldTextureMap.put(FieldType.WATER, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Water.png")));
			
			numberTextureMap = new HashMap<Integer,Texture>();
			numberTextureMap.put(2, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/2.png")));
			numberTextureMap.put(3, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/3.png")));
			numberTextureMap.put(4, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/4.png")));
			numberTextureMap.put(5, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/5.png")));
			numberTextureMap.put(6, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/6.png")));
			numberTextureMap.put(8, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/8.png")));
			numberTextureMap.put(9, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/9.png")));
			numberTextureMap.put(10, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/10.png")));
			numberTextureMap.put(11, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/11.png")));
			numberTextureMap.put(12, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Numbers/12.png")));
		
			streetTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Street.png"));
			catapultTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Catapult.png"));
			robberTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Robber.png"));
			fieldMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Fields/Mark.png"));
			intersectionMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Mark.png"));
			intersectionMarkRedTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/MarkRed.png"));
			pathMarkTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/Mark.png"));
			pathMarkTextureRed = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Paths/MarkRed.png"));
			villageTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Village.png"));
			townTexture = TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Intersections/Town.png"));
			
			harborTextureMap = new HashMap<HarborType,Texture>();
			harborTextureMap.put(HarborType.GENERAL_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/General_Harbor.png")));
			harborTextureMap.put(HarborType.BRICK_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/Brick_Harbor.png")));
			harborTextureMap.put(HarborType.GRAIN_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/Grain_Harbor.png")));
			harborTextureMap.put(HarborType.LUMBER_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/Lumber_Harbor.png")));
			harborTextureMap.put(HarborType.ORE_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/Ore_Harbor.png")));
			harborTextureMap.put(HarborType.WOOL_HARBOR, TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Harbor/Wool_Harbor.png")));
			
			uiTextureMap = new HashMap<String,Texture>();
			uiTextureMap.put("Background", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/menue_background.png")));
			uiTextureMap.put("EndTurn", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_endTurn.png")));
			uiTextureMap.put("ClaimVictory", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_claimVictory.png")));
			uiTextureMap.put("ClaimLongestRoad", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_claimLongestRoad.png")));
			uiTextureMap.put("offerTrade", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_offerTrade.png")));
			uiTextureMap.put("BuildStreet", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildStreet.png")));
			uiTextureMap.put("BuildVillage", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildVillage.png")));
			uiTextureMap.put("BuildTown", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildTown.png")));
			uiTextureMap.put("BuildCatapult", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_BuildCatapult.png")));
			uiTextureMap.put("LumberScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Lumber_Score.png")));
			uiTextureMap.put("BrickScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Brick_Score.png")));
			uiTextureMap.put("GrainScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Grain_Score.png")));
			uiTextureMap.put("WoolScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Wool_Score.png")));
			uiTextureMap.put("OreScore", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Ore_Score.png")));
			uiTextureMap.put("Console", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/console.png")));
			uiTextureMap.put("PlayerColor", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Player_Color.png")));
			uiTextureMap.put("Cup", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Cup.png")));
			uiTextureMap.put("Trenner", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/PlayerTrenner.png")));
			uiTextureMap.put("PlayerBackgroundHighlight", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/PlayerBackgroundHighlight.png")));
			uiTextureMap.put("Quit", TextureLoader.getTexture("JPG", new FileInputStream("src/main/resources/Textures/Menue/Button_Quit.png")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
