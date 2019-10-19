package unsw.dungeon.entity;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.LocationChanged;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends MovableEntity<Player> implements Interactable {

	private ArrayList<ItemEntity> inventory;

	/**
	 * Create a player positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Player(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.inventory = new ArrayList<ItemEntity>();
	}

	private boolean isPositionBlocked(int x, int y) {
		return this.getDungeon().hasEntitiesAt(EntityLevel.OBJECT, x, y);
	}

	private void move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		if (!this.getDungeon().positionIsValid(newX, newY)) {
			return;
		}

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return;
		}

		if (isPositionBlocked(newX, newY)) {
			return;
		}

		if (oldX != newX) {
			x().set(newX);
		}
		if (oldY != newY) {
			y().set(newY);
		}

		this.moveEvent.emit(e);
	}

	public void moveUp() {
		move(0, -1);
	}

	public void moveDown() {
		move(0, 1);
	}

	public void moveLeft() {
		move(-1, 0);
	}

	public void moveRight() {
		move(1, 0);
	}

	public void pickUp(ItemEntity item) {
		// Check if the player can pickup the item
		if (item instanceof Sword && this.hasItem(Sword.class)) {
			return;
		}

		this.inventory.add(item);
		item.visibility().set(false);
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
}
