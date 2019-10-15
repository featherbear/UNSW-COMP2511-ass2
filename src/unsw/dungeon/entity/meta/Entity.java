package unsw.dungeon.entity.meta;

import javafx.beans.property.IntegerProperty;
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

	protected EntityLevel entityLevel;
	protected Dungeon dungeon;

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
}
