package unsw.dungeon.entity.meta;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventSAM;
import unsw.dungeon.util.emitter.GenericEmitter;

/**
 * Abstract class for Item entities
 */
public abstract class ItemEntity extends Entity {

	public EventSAM<Player, LocationChanged> LocationChangedHandler;
	public final GenericEmitter pickupEvent;

	public ItemEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);

		this.pickupEvent = new GenericEmitter();

		// Register item pickups
		this.LocationChangedHandler = (player, event) -> {
			if (this.getX() == event.newX && this.getY() == event.newY) {
				if (player.pickUp(this)) {
					this.pickupEvent.emit();

					// Unregister the pickup event after it has been picked up
					player.moveEvent.unregister(this.LocationChangedHandler);
				}
			}
		};
	}

	public abstract boolean maxOne();

}
