package unsw.dungeon.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;

public class Portal extends Entity implements Interactable {

	private BooleanProperty activated;
	private int id;

	public Portal(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.activated = new SimpleBooleanProperty(true);
	}

	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}

		// Check activated

		return true;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public BooleanProperty activated() {
		return this.activated;
	}

	public boolean getActivated() {
		return this.activated.get();
	}

	public void activate() {
		this.activated.set(true);

	}

	public void deactivate() {
		this.activated.set(false);

	}

}
