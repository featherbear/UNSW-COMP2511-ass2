package unsw.dungeon.entity;

import unsw.dungeon.events.LocationChanged;

public class horizontalState implements SawState{
	private Saw saw;
	private boolean directionSwitched = false;
	
	public horizontalState(Saw saw) {
		this.saw = saw;
	}
	
	@Override
	public void move(LocationChanged event) {
		boolean moveSuccess = false;	
		if (!directionSwitched && saw.moveLeft()) {
			moveSuccess = true;
		} else if (!moveSuccess && saw.moveRight()) {
			moveSuccess = true;
			directionSwitched = true;
		} else if (!moveSuccess && saw.moveLeft()) {
			directionSwitched = false;
			moveSuccess = true;
		}
	}
}
