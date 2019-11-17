package unsw.dungeon.entity.saw;

import unsw.dungeon.events.LocationChanged;

public class verticalState implements SawState{
	private Saw saw;
	private boolean directionSwitched = false;
	
	public verticalState(Saw saw) {
		this.saw = saw;
	}
	
	@Override
	public void move(LocationChanged event) {
		boolean moveSuccess = false;	
		if (!directionSwitched && saw.moveUp()) {
			moveSuccess = true;
		} else if (!moveSuccess && saw.moveDown()) {
			moveSuccess = true;
			directionSwitched = true;
		} else if (!moveSuccess && saw.moveUp()) {
			directionSwitched = false;
			moveSuccess = true;
		}
	}
}
