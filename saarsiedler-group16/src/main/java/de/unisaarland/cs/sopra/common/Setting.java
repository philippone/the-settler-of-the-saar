package de.unisaarland.cs.sopra.common;

import org.lwjgl.opengl.DisplayMode;

public class Setting {
	
	private DisplayMode mode;
	private boolean fullscreen;
	private PlayerColors playerColor;
	
	public Setting(DisplayMode mode, boolean fullscreen, PlayerColors playerColor) {
		this.mode = mode;
		this.fullscreen = fullscreen;
		this.setPlayerColor(playerColor);
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public DisplayMode getDisplayMode() {
		return mode;
	}

	public void setPlayerColor(PlayerColors playerColor) {
		this.playerColor = playerColor;
	}

	public PlayerColors getPlayerColor() {
		return playerColor;
	}
	
}
