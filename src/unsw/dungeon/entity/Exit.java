package unsw.dungeon.entity;

import javafx.beans.property.BooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.events.LocationChanged;

public class Exit extends Entity {

	private BooleanProperty activated;

	public Exit(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
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

	public void playerMoveEventHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			deactivate();
			return;
		}

		activate();
	}

}
