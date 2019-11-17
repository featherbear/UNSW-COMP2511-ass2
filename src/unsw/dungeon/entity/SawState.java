package unsw.dungeon.entity;

import unsw.dungeon.events.LocationChanged;

public interface SawState {
	public void move(LocationChanged event);
}
