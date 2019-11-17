package unsw.dungeon.entity.saw;

import unsw.dungeon.events.LocationChanged;

public interface SawState {
	public void move(LocationChanged event);
}
