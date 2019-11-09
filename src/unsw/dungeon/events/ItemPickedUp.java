package unsw.dungeon.events;

import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.util.emitter.EmitterData;

/**
 * Item pick up emitter data
 *
 */
public class ItemPickedUp extends EmitterData {
	public final ItemEntity item;

	public ItemPickedUp(ItemEntity item) {
		this.item = item;
	}
}
