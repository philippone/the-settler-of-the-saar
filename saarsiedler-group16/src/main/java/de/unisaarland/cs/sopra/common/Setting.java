package de.unisaarland.cs.sopra.common;

public class Setting {

	private int windowHeight;
	private int windowWidth;
	private boolean fullscreen;
	
	public Setting(int windowWidth, int windowHeight, boolean fullscreen) {
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.fullscreen = fullscreen;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}
	public int getWindowWidth() {
		return windowWidth;
	}
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}
	public boolean isFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
}
