package unsw.dungeon.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.events.LocationChanged;

public class Switch extends Entity {

	private BooleanProperty activated;

	public Switch(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.activated = new SimpleBooleanProperty(false);
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

	public void switchEnterEventHandler(Player player, LocationChanged event) {
		Entity e = this.getDungeon().getEntityAt(EntityLevel.OBJECT, this.getX(), this.getY());
		if (e != null) {
			if (e instanceof Boulder || e instanceof Player) {
				activate();
				System.out.println("Switched Activated");
			}
		} else {
			deactivate();
			System.out.println("Switched Deactivated");
		}
	}

}
