package de.unisaarland.cs.sopra.common;

import org.lwjgl.opengl.DisplayMode;

public class Setting {
	
	private static DisplayMode mode;
	private static boolean fullscreen;
	private static PlayerColors playerColor;
	private static String name = "ichbinkeinReh";
	
	public Setting(DisplayMode mode, boolean fullscreen, PlayerColors playerColor) {
		Setting.mode = mode;
		Setting.fullscreen = fullscreen;
		setPlayerColor(playerColor);
	}
	
	public static boolean isFullscreen() {
		return fullscreen;
	}
	public static void setFullscreen(boolean fullscreen) {
		Setting.fullscreen = fullscreen;
	}

	public static DisplayMode getDisplayMode() {
		return mode;
	}

	public static void setPlayerColor(PlayerColors playerColor) {
		Setting.playerColor = playerColor;
	}

	public static PlayerColors getPlayerColor() {
		return playerColor;
	}
	
	public static void setName(String n) {
		Setting.name = n;
	}
	
}
