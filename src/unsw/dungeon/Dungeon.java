/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;

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

	private int width, height;
	private List<Entity> entities;
	private Player player;

	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.entities = new ArrayList<>();
		this.player = null;
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

//	public ArrayList<Entity> getEntitiesAt(int x, int y) {
//		ArrayList<Entity> result = new ArrayList<Entity>();
//		for (Entity entity : this.entities) {
//			if (entity.getX() == x && entity.getY() == y) {
//				result.add(entity);
//			}
//		}
//
//		return result;
//	}
//
//	public ArrayList<Entity> getEntitiesAt(EntityLevel entityLevel, int x, int y) {
//		ArrayList<Entity> result = new ArrayList<Entity>();
//		for (Entity entity : this.entities) {
//			if (entity.getEntityLevel() == entityLevel && entity.getX() == x && entity.getY() == y) {
//				result.add(entity);
//			}
//		}
//
//		return result;
//	}

	public boolean hasEntitiesAt(EntityLevel entityLevel, int x, int y) {
		for (Entity entity : this.entities) {
			if (entity.getEntityLevel() == entityLevel && entity.getX() == x && entity.getY() == y) {
				return true;
			}
		}

		return false;
	}

//	public boolean hasEntitiesAt(EntityLevel entityLevel, ArrayList<Entity> entities) {
//		for (Entity entity : entities) {
//			if (entity.getEntityLevel() == entityLevel) {
//				return true;
//			}
//		}
//
//		return false;
//	}

}
