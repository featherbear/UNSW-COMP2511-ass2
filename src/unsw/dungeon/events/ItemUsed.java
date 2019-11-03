package unsw.dungeon.events;

import unsw.dungeon.util.emitter.EmitterData;

/**
 * Item usage emitter data
 *
 */
public class ItemUsed extends EmitterData {
	public final int oldValue;
	public final int newValue;

	public ItemUsed(int oldValue, int newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
}
