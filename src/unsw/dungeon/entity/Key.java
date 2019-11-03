package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemUsed;
import unsw.dungeon.util.emitter.EventEmitter;

public class Key extends ItemEntity implements Usable {

	private int id;
	private int uses;
	private EventEmitter<Key, ItemUsed> itemUsed;

	public Key(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.uses = 1;
		this.id = -1;
		this.itemUsed = new EventEmitter<Key, ItemUsed>(this);

	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	@Override
	public boolean use(Interactable entity) {
		if (getUses() <= 0) {
			return false;
		}

		if (!entity.interact(this)) {
			return false;
		}

		itemUsed().emit(new ItemUsed(this.uses, --this.uses));
		return true;
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	@Override
	public boolean maxOne() {
		return true;
	}

	@Override
	public EventEmitter<Key, ItemUsed> itemUsed() {
		return this.itemUsed;
	}
}
