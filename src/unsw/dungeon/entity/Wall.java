package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Wall extends Entity {

	public Wall(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
	}

}
