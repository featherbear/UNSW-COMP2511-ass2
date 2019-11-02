package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemUsed;
import unsw.dungeon.util.emitter.EventEmitter;

public class InvincibilityPotion extends ItemEntity implements Usable {

	private int timer;
	private EventEmitter<InvincibilityPotion, ItemUsed> itemUsed;

	public InvincibilityPotion(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.timer = 10;
		this.itemUsed = new EventEmitter<InvincibilityPotion, ItemUsed>(this);
	}

	@Override
	public boolean use(Interactable entity) {
		if (getUses() <= 0) {
			return false;
		}

//		if (entity instanceof Enemy) {
//			(Enemy) entity.kill()
//		}

		itemUsed().emit(new ItemUsed(this.timer, --this.timer));
		return true;
	}

	@Override
	public int getUses() {
		return this.timer;
	}

	@Override
	public boolean maxOne() {
		return false;
	}

	@Override
	public EventEmitter<InvincibilityPotion, ItemUsed> itemUsed() {
		return this.itemUsed;
	}
}
