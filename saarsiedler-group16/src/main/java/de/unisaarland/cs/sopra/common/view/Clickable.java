package de.unisaarland.cs.sopra.common.view;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Player;

public abstract class Clickable {
	private static List<Clickable> model = new LinkedList<Clickable>();
	private static List<Clickable> ui = new LinkedList<Clickable>();

	private int x, y, z, width, height;
	private boolean active, visible;
	private String name;
	
	private Intersection intersection;
	private Field field;
	private Field field2;
	private Path path;
	private Path path2;
	private Player player;
	
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
	
	public Clickable(String name, int x, int y, int z, int width, int height, boolean active, boolean isGUIonly, boolean visible) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.active = active;
		this.visible = visible;
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setIntersection(Intersection intersection) {
		this.intersection = intersection;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Intersection getIntersection() {
		return intersection;
	}

	public Field getField() {
		return field;
	}

	public Path getPath() {
		return path;
	}

	public void setField2(Field field2) {
		this.field2 = field2;
	}

	public Field getField2() {
		return field2;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public Path getPath2() {
		return path2;
	}

	public void setPath2(Path path2) {
		this.path2 = path2;
	}
	
}
