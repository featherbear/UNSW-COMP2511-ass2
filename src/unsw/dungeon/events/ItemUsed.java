package unsw.dungeon.events;

import unsw.dungeon.util.emitter.EmitterData;

public class ItemUsed extends EmitterData {
	public final int oldValue;
	public final int newValue;

	public ItemUsed(int oldValue, int newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public String toString() {
		return String.format("<%d> to <%d>", this.oldValue, this.newValue);
	}
}
