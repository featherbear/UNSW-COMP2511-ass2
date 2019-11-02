/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.util.emitter.GenericEmitter;
import unsw.dungeon.goals.Goal;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

	public final GenericEmitter finishEvent;

	private int width, height;
	private ArrayList<Entity> entities;
	private Player player;
	private Goal goal;

	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.entities = new ArrayList<>();
		this.player = null;
		this.finishEvent = new GenericEmitter();
		this.goal = null;
	}

	/**
	 * @return Dungeon width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return Dungeon height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Check if a position is valid within the dungeon
	 * 
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean positionIsValid(int x, int y) {
		return !(y < 0 || y >= this.height || x < 0 || x >= this.width);
	}

	/**
	 * @return The player object in the dungeon
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the player object in the dungeon
	 * 
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Add an entity into the dungeon
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	/**
	 * Remove an entity from the dungeon
	 * 
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	/**
	 * Get the entities in the dungeon
	 * 
	 * @return
	 */
	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	/**
	 * Get the first entity of a given level in the given coordinates
	 * 
	 * @param entityLevel
	 * @param x
	 * @param y
	 * @return Entity
	 */
	public Entity getEntityAt(EntityLevel entityLevel, int x, int y) {
	public void addGoal(Goals goal) {

	// TODO: Should this add to the children if it exists?
	// Or is it created in the Loader
	// In that case should it be setGoal() ...
	public void addGoal(Goal goal) {
		this.goal = goal;
	}
		for (Entity entity : Entity.filter(this.entities, x, y)) {
			if (entity.getEntityLevel() == entityLevel) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * Check if there is an entity of a given level in the given coordinates
	 * 
	 * @param entityLevel
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasEntitiesAt(EntityLevel entityLevel, int x, int y) {
		return this.getEntityAt(entityLevel, x, y) != null;
	}

}
