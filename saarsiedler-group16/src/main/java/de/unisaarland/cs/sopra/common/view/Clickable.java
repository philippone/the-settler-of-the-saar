package de.unisaarland.cs.sopra.common.view;

import java.util.LinkedList;
import java.util.List;

public abstract class Clickable {
	private static List<Clickable> list = new LinkedList<Clickable>();

	private int x, y, z, width, height;
	private boolean active;
	private String name;
	
	public static void executeClicks() {
		for (Clickable act : list) {
			if (act.checkClick())
				act.execute();
		}
	}
	
	public Clickable(String name, int x, int y, int z, int width, int height, boolean active) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.active = active;
		this.setName(name);
		list.add(this);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public abstract void execute();
	
	private boolean checkClick() {
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	public static List<Clickable> getList() {
		return list;
	}
}