package unsw.dungeon.entity.meta;

import unsw.dungeon.events.ItemUsed;
import unsw.dungeon.util.emitter.EventEmitter;

public interface Usable {
	public boolean use(Interactable entity);

	public int getUses();

	public EventEmitter<? extends Usable, ItemUsed> itemUsed();

}
