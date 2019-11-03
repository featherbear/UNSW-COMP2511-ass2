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
 */
public abstract class Entity {

	// Beans are used so that changes to the entity can be externally observed.
	private IntegerProperty x, y;
	private BooleanProperty visible;

	protected EntityLevel entityLevel;
	private Dungeon dungeon;

	/**
	 * Create an entity positioned in tile (x,y)
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

	/**
	 * @return IntegerProperty for the x position
	 */
	public IntegerProperty x() {
		return x;
	}

	/**
	 * @return IntegerProperty for the y position
	 */
	public IntegerProperty y() {
		return y;
	}

	/**
	 * @return Entity's x position
	 */
	public int getX() {
		return x().get();
	}

	/**
	 * @return Entity's y position
	 */
	public int getY() {
		return y().get();
	}

	/**
	 * @return Entity's EntityLevel
	 */
	public EntityLevel getEntityLevel() {
		return this.entityLevel;
	}

	/**
	 * @return Dungeon the Entity is in
	 */
	public Dungeon getDungeon() {
		return this.dungeon;
	}

	/**
	 * Hide the entity from the visible map
	 */
	public void hide() {
		visibility().set(false);
	}

	/**
	 * Show the entity on the visible map
	 */
	public void show() {
		visibility().set(true);

	}

	/**
	 * @return BooleanProperty for the Entity's visibility
	 */
	public BooleanProperty visibility() {
		return visible;
	}

	/**
	 * @return Entity's visibility
	 */
	public boolean getVisibility() {
		return visibility().get();
	}

	@Override
	public String toString() {
		return String.format("%s<%d,%d>", this.getClass().getSimpleName(), this.getX(), this.getY());
	}

	/**
	 * Filter entities by class
	 * 
	 * @param entities
	 * @param EntityType
	 * @return List of entities that match the given class
	 */
	public static ArrayList<Entity> filter(List<? extends Entity> entities, Class<? extends Entity> EntityType) {

		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (EntityType.isInstance(entity)) {
				results.add(entity);
			}
		}

		return results;
	}

	/**
	 * Filter entities by position
	 * 
	 * @param entities
	 * @param x
	 * @param y
	 * @return List of entities that match the given position
	 */
	public static ArrayList<Entity> filter(List<? extends Entity> entities, int x, int y) {

		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getX() == x && entity.getY() == y) {
				results.add(entity);
			}
		}

		return results;
	}

	/**
	 * Filter entities by EntityLevel
	 * 
	 * @param entities
	 * @param entityLevel
	 * @return List of entities that match the given EntityLevel
	 */
	public static ArrayList<Entity> filter(List<? extends Entity> entities, EntityLevel entityLevel) {
		ArrayList<Entity> results = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getEntityLevel() == entityLevel) {
				results.add(entity);
			}
		}
		return results;
	}

}
