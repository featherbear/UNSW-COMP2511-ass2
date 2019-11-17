package unsw.dungeon.entity.saw;

import unsw.dungeon.entity.Saw;
import unsw.dungeon.events.LocationChanged;

public class VerticalBehaviour implements SawMovementBehaviour{
	private Saw saw;
	private boolean directionSwitched = false;
	
	public VerticalBehaviour(Saw saw) {
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
