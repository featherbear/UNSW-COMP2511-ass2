package unsw.dungeon.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.events.LocationChanged;

public class Door extends Entity implements Interactable {

	private BooleanProperty opened;
	private boolean openedByKey;

	private int id;

	public Door(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.opened = new SimpleBooleanProperty(false);
		this.openedByKey = false;
		this.id = -1;

		// Change entityLevel to FLOOR when opened, or OBJECT when closed
		this.opened.addListener((observer, oldValue, newValue) -> {
			this.entityLevel = newValue ? EntityLevel.FLOOR : EntityLevel.OBJECT;
		});
	}

	/**
	 * @return Door's open status
	 */
	public boolean getOpen() {
		return this.opened.get();
	}

	/**
	 * @return BooleanProperty for the door's open status
	 */
	public BooleanProperty opened() {
		return this.opened;
	}

	/**
	 * Open/unlock the door
	 */
	public void open() {
		this.setOpened(true);
	}

	/**
	 * Close/lock the door
	 */
	public void close() {
		this.setOpened(false);
	}

	public void setOpened(boolean opened) {
		if (!opened && this.openedByKey) {
			// Don't close if already opened by key
			return;
		}
		this.opened.set(opened);
	}

	/**
	 * Set the ID for the door
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return ID of the door
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Interact with the player
	 * 
	 * @param player
	 * @return result
	 */
	public boolean interact(Player player) {
		for (ItemEntity item : player.getInventory()) {
			if (item instanceof Key) {
				if (((Key) item).use(this)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Unlock the door if the right key is given
	 * 
	 * @param key
	 * @return result
	 */
	public boolean interact(Key key) {
		if (key.getID() != getID()) {
			return false;
		}

		this.open();
		this.openedByKey = true;
		return true;
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			return interact((Player) entity);
		}

		if (entity instanceof Key) {
			return interact((Key) entity);
		}

		return false;

	}

	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}

		// Return true if the door is already opened
		if (this.getOpen()) {
			return true;
		}

		return player.interact(this);
	}

}
