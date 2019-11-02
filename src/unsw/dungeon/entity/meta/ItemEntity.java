package unsw.dungeon.entity.meta;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventSAM;

/**
 * Abstract class for Item entities
 */
public abstract class ItemEntity extends Entity {

	public EventSAM<Player, LocationChanged> LocationChangedHandler;

	public ItemEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);

		// Register item pickups
		this.LocationChangedHandler = (player, event) -> {
			if (this.getX() == event.newX && this.getY() == event.newY) {
				if (player.pickUp(this)) {

					// Register Usable items
					if (this instanceof Usable) {
						((Usable) this).itemUsed().register((item, evt) -> {
							System.out.println("Item used! " + evt.newValue);
							if (evt.newValue == 0) {
								player.removeItem(this);
							}
						});
					}

					// Unregister the pickup event after it has been picked up
					player.moveEvent.unregister(this.LocationChangedHandler);
				}
			}
		};
	}

	public abstract boolean maxOne();

}
