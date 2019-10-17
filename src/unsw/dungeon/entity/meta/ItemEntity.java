package unsw.dungeon.entity.meta;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.events.LocationChanged;

public abstract class ItemEntity extends Entity {

	public ItemEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);
	}

	public void LocationChangedHandler(Player player, LocationChanged event) {
		if (this.getX() == event.newX && this.getY() == event.newY) {
			player.pickUp(this);
		}
	}

}
