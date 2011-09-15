package de.unisaarland.cs.sopra.common.view;

import java.util.LinkedList;
import java.util.List;

public abstract class Clickable {
	private static List<Clickable> model = new LinkedList<Clickable>();
	private static List<Clickable> ui = new LinkedList<Clickable>();

	private int x, y, z, width, height;
	private boolean active;
	private String name;
	
	public static List<Clickable> executeModelClicks(float xogl, float yogl) {
		List<Clickable> liste = new LinkedList<Clickable>();
		for (Clickable act : model) {
			if (act.checkClick(xogl, yogl))
				liste.add(act);
		}
		return liste;
	}
	
	public static List<Clickable> executeUIClicks(float xogl, float yogl) {
		List<Clickable> liste = new LinkedList<Clickable>();
		for (Clickable act : ui) {
			if (act.checkClick(xogl, yogl))
				liste.add(act);
		}
		return liste;
	}
	
	public Clickable(String name, int x, int y, int z, int width, int height, boolean active, boolean isGUIonly) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.active = active;
		this.setName(name);
		if (isGUIonly)
			ui.add(this);
		else
			model.add(this);
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
	
	private boolean checkClick(float xogl, float yogl) {
		if (active) {
			return (xogl > x && xogl < x+width && yogl > y && yogl < y+height);
		}
		return false;
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

	public static List<Clickable> getRenderList() {
		List<Clickable> tmp = new LinkedList<Clickable>();
		tmp.addAll(model);
		tmp.addAll(ui);
		return tmp;
	}
	
}
