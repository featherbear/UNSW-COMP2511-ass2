package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;

public class Sword extends ItemEntity implements Usable {

	private int durability;

	public Sword(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.durability = 5;
	}

	@Override
	public boolean use(Interactable entity) {
		if (getUses() <= 0) {
			return false;

		}

		this.durability--;

//		if (entity instanceof Enemy) {
//			(Enemy) entity.kill()
//		}

	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	@Override
	public int getUses() {
		return this.durability;
	}

}
