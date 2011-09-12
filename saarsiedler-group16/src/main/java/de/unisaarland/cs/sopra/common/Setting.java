package de.unisaarland.cs.sopra.common;

import org.lwjgl.opengl.DisplayMode;

public class Setting {

	private DisplayMode mode;
	private boolean fullscreen;
	
	public Setting(DisplayMode mode, boolean fullscreen) {
		this.mode = mode;
		this.fullscreen = fullscreen;
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
	
}
