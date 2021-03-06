package unsw.dungeon.entity.saw;

import unsw.dungeon.entity.Saw;

public class HorizontalBehaviour implements SawMovementBehaviour {
	private Saw saw;
	private boolean directionSwitched = false;

	public HorizontalBehaviour(Saw saw) {
		this.saw = saw;
	}

	@Override
	public void move() {
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
