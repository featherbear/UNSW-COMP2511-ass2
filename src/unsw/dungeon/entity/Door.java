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
	private int keyID;

	public Door(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.opened = new SimpleBooleanProperty(false);
		this.keyID = -1;
	}

	public boolean getOpen() {
		return this.opened.get();
	}

	public BooleanProperty opened() {
		return this.opened;
	}

	public void open() {
		this.opened.set(true);
	}

	public void close() {
		this.opened.set(false);
	}

	public void setKeyID(int keyID) {
		this.keyID = keyID;
	}

	public int getKeyID() {
		return this.keyID;
	}

	public boolean interact(Player player) {
		System.out.println("INTACT");
		for (ItemEntity item : player.getInventory()) {
			if (item instanceof Key) {
				if (((Key) item).use(this)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean interact(Key key) {
		System.out.println(key.getID() + " . " + getKeyID());
		if (key.getID() != getKeyID()) {
			return false;
		}
		this.open();
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

//		if (entity instanceof Switch) {
//			// If activated by a Switch, then toggle state
//			open.set((Switch) entity.getPressed());
//			return true;
//		}

		return false;

	}

	public boolean doorEnterIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}

		if (this.getOpen()) {
			return true;
		}

		return player.interact(this);
	}

}
