package unsw.dungeon.entity.saw;

import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.enemy.Enemy;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
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
