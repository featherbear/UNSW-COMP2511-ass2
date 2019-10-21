package unsw.dungeon.entity.meta;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.Dungeon;

/**
 * An entity in the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

	// IntegerProperty is used so that changes to the entities position can be
	// externally observed.
	private IntegerProperty x, y;
	private BooleanProperty visible;

	protected EntityLevel entityLevel;
	private Dungeon dungeon;

	/**
	 * Create an entity positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Entity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		this.dungeon = dungeon;
		this.entityLevel = entityLevel;
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.visible = new SimpleBooleanProperty(true);
	}

	public IntegerProperty x() {
		return x;
	}

	public IntegerProperty y() {
		return y;
	}

	public int getY() {
		return y().get();
	}

	public int getX() {
		return x().get();
	}

	public EntityLevel getEntityLevel() {
		return this.entityLevel;
	}

	public Dungeon getDungeon() {
		return this.dungeon;
	}

	public void hide() {
		visibility().set(false);
	}

	public void show() {
		visibility().set(true);

	}

	public BooleanProperty visibility() {
		return visible;
	}

	public boolean getVisibility() {
		return visibility().get();
	}

	@Override
	public String toString() {
		return String.format("%s<%d,%d>", this.getClass().getSimpleName(), this.getX(), this.getY());
	}

	public static ArrayList<Entity> filter(List<Entity> entities, Class<?> EntityType) {

		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (EntityType.isInstance(entity)) {
				results.add(entity);
			}
		}

		return results;
	}

	public static ArrayList<Entity> filter(List<Entity> entities, int x, int y) {

		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getX() == x && entity.getY() == y) {
				results.add(entity);
			}
		}

		return results;
	}

	public static ArrayList<Entity> filter(List<Entity> entities, EntityLevel entityLevel) {
		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getEntityLevel() == entityLevel) {
				results.add(entity);
			}
		}
		return results;
	}

}
