package unsw.dungeon.entity;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemPickedUp;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventEmitter;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends MovableEntity<Player> implements Interactable {

	private ArrayList<ItemEntity> inventory;
	private BooleanProperty isAlive;

	public final EventEmitter<Player, ItemPickedUp> itemPickedUpEvent;

	/**
	 * Create a player positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Player(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.inventory = new ArrayList<ItemEntity>();
		this.isAlive = new SimpleBooleanProperty(true);
		this.itemPickedUpEvent = new EventEmitter<Player, ItemPickedUp>(this);
	}


	public boolean pickUp(ItemEntity item) {
		// Check if the player can pickup the item
		if (this.hasItem(item.getClass()) && item.maxOne()) {
			return false;
		}

		this.inventory.add(item);

		// Register Usable items
		if (item instanceof Usable) {
			((Usable) item).itemUsed().register((itemObj, evt) -> {
				if (evt.newValue == 0) {
					this.removeItem(item);
				}
			});
		}

		item.visibility().set(false);
		this.itemPickedUpEvent.emit(new ItemPickedUp(item));
		return true;
	}

	public boolean hasItemUsable(Class<?> itemClass) {
		for (ItemEntity invItem : this.inventory) {
			if (!(invItem instanceof Usable)) {
				continue;
			}

			if (itemClass.isInstance(invItem)) {
				if (((Usable) invItem).getUses() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasItem(Class<?> itemClass) {
		for (ItemEntity invItem : this.inventory) {
			if (itemClass.isInstance(invItem)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Interactable)) {
			return false;
		}

		return ((Interactable) entity).interact(this);

	}

	public ArrayList<ItemEntity> getInventory() {
		return this.inventory;
	}

	public void removeItem(ItemEntity item) {
		this.inventory.remove(item);
	}

	public void kill() {
		this.isAlive.set(false);
	}

	public BooleanProperty alive() {
		return this.isAlive;
	}

	public boolean isAlive() {
		return alive().get();
	}
}
