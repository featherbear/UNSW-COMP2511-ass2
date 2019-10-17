package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;

public class InvincibilityPotion extends ItemEntity implements Usable {

	private int timer;

	public InvincibilityPotion(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.timer = 10;
	}

	@Override
	public void use(Entity entity) {
		if (this.timer <= 0) {
			return;
		}
		this.timer--;
	}

	@Override
	public int getUses() {
		return this.timer;
	}
}
