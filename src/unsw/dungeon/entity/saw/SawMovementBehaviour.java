package unsw.dungeon.entity.saw;

import unsw.dungeon.events.LocationChanged;

public interface SawMovementBehaviour {
	public void move(LocationChanged event);
}
