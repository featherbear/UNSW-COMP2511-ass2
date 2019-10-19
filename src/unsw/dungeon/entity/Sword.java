package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemUsed;
import unsw.dungeon.util.emitter.EventEmitter;

public class Sword extends ItemEntity implements Usable {

	private int durability;
	private EventEmitter<Sword, ItemUsed> itemUsed;

	public Sword(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.durability = 5;
		this.itemUsed = new EventEmitter<Sword, ItemUsed>(this);

	}

	@Override
	public boolean use(Interactable entity) {
		if (getUses() <= 0) {
			return false;

		}

//		if (entity instanceof Enemy) {
//			(Enemy) entity.kill()
//		}

		itemUsed().emit(new ItemUsed(this.durability, --this.durability));
		return true;

	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	@Override
	public int getUses() {
		return this.durability;
	}

	@Override
	public boolean maxOne() {
		return true;
	}

	@Override
	public EventEmitter<Sword, ItemUsed> itemUsed() {
		return this.itemUsed;
	}

}
