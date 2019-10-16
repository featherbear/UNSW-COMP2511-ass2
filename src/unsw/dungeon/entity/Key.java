package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;

public class Key extends Entity {

	public Key(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
	}
}
