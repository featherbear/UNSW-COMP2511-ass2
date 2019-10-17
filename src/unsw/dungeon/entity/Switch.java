package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Switch extends Entity{

	public Switch(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
	}

}
