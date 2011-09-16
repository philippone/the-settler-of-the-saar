package de.unisaarland.cs.sopra.common.view;

import java.util.HashMap;
import java.util.Map;

public class Console {
	
	private int x, y, z, width, height;
	private Map<String,Object> text;
	private int index;

	public Console(int x, int y, int z, int width, int height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.index = 0;
		this.text = new HashMap<String,Object>();
	}
	
	public void addText (String text, Object o) {
		//TODO: implement it!
	}
	
	public void scrollDown() {
		index++;
		//TODO: implement it!
	}
	
	public void scrollUp() {
		//TODO: implement it!
	}
	
	public Object select (int oglx, int ogly) {
		return null;
		//TODO: implement it!
	}
	
	public void render() {
		//TODO: implement it!
	}
	
}
