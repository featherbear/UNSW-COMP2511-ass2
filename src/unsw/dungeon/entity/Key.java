package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;

public class Key extends ItemEntity implements Usable {

	public Key(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
	}

	@Override
	public void use(Entity entity) {

	}

	@Override
	public int getUses() {
		return 0;
	}
}
