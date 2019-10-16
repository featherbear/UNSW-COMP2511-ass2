package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;

public class InvincibilityPotion extends Entity {

	public InvincibilityPotion(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
	}
}
