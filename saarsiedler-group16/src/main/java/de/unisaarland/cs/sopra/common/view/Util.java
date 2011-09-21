package de.unisaarland.cs.sopra.common.view;

import static de.unisaarland.cs.sopra.common.PlayerColors.BLUE;
import static de.unisaarland.cs.sopra.common.PlayerColors.BROWN;
import static de.unisaarland.cs.sopra.common.PlayerColors.ORANGE;
import static de.unisaarland.cs.sopra.common.PlayerColors.PURPLE;
import static de.unisaarland.cs.sopra.common.PlayerColors.WHITE;
import static de.unisaarland.cs.sopra.common.PlayerColors.YELLOW;
import static de.unisaarland.cs.sopra.common.view.GameGUI.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import de.unisaarland.cs.sopra.common.PlayerColors;
import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.model.Player;

public class Util {

	private static Map<Player,PlayerColors> colorMap;
	
	public static void initiateUtil() {
		//set color of players
		List<PlayerColors> tmp = new LinkedList<PlayerColors>();
		tmp.addAll(Arrays.asList(new PlayerColors[] {YELLOW,ORANGE,WHITE,PURPLE,BLUE,BROWN}));
		tmp.remove(Setting.getPlayerColor());
		colorMap = new HashMap<Player,PlayerColors>();
		Iterator<Player> iterP = mr.getTableOrder().iterator();
		Iterator<PlayerColors> iterC = tmp.iterator();
		while (iterP.hasNext()) {
			Player act = iterP.next();
			if (act == mr.getMe()) {
				colorMap.put(act, Setting.getPlayerColor());
			}
			else {
				colorMap.put(act, iterC.next());
			}
		}
	}
	
	public static void setColor(PlayerColors playerColor) {
		switch(playerColor) {
		case BLUE:
			GL11.glColor4f(0.2f,0.2f,1.0f,1.0f); break;
		case YELLOW:
			GL11.glColor4f(1.0f,1.0f,0.0f,1.0f); break;
		case ORANGE:
			GL11.glColor4f(1.0f,0.5f,0.0f,1.0f); break;
		case BROWN:
			GL11.glColor4f(0.5f,0.25f,0.05f,1.0f); break;
		case WHITE:
			GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); break;
		case PURPLE:
			GL11.glColor4f(0.5f,0.25f,0.5f,1.0f); break;
		case BLACK:
			GL11.glColor4f(1.0f,1.0f,1.0f,1.0f); break;
		}
	}
	
	public static void setColor(Player player) {
		setColor(colorMap.get(player));
	}
	
}
