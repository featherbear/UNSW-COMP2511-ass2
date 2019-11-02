/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.util.emitter.GenericEventEmitter;

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

	public final GenericEventEmitter finishEvent;

	private int width, height;
	private ArrayList<Entity> entities;
	private Player player;

	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.entities = new ArrayList<>();
		this.player = null;

		this.finishEvent = new GenericEventEmitter();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean positionIsValid(int x, int y) {
		return !(y < 0 || y >= this.height || x < 0 || x >= this.width);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}
	
	public Entity whatEntityAt (EntityLevel entityLevel, int x, int y){
		for (Entity entity : Entity.filter(this.entities, x, y)) {
			if (entity.getEntityLevel() == entityLevel) {
				return entity;
			}
		}
		return null;
	}

	public boolean hasEntitiesAt(EntityLevel entityLevel, int x, int y) {
		for (Entity entity : Entity.filter(this.entities, x, y)) {
			if (entity.getEntityLevel() == entityLevel) {
				return true;
			}
		}

		return false;
	}

}
