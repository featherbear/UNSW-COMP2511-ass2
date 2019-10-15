package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Exit extends Entity {

	public Exit(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
	}

}
