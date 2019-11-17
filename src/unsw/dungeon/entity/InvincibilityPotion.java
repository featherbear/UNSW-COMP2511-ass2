package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.ItemUsed;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventEmitter;
import unsw.dungeon.util.emitter.EventSAM;

public class InvincibilityPotion extends ItemEntity implements Usable {

	private int timer;
	private EventEmitter<InvincibilityPotion, ItemUsed> itemUsed;
	public final EventSAM<Player, LocationChanged> playerMoveEventHandler;

	public InvincibilityPotion(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.ITEM, x, y);
		this.timer = 10;
		this.itemUsed = new EventEmitter<InvincibilityPotion, ItemUsed>(this);
		this.playerMoveEventHandler = (player, event) -> {
			itemUsed().emit(new ItemUsed(this.timer, --this.timer));
		};
	}

	@Override
	public boolean use(Interactable entity) {
		if (getUses() <= 0) {
			return false;
		}

		if (entity instanceof Enemy) {
			((Enemy) entity).kill();
		}

		return true;
	}

	@Override
	public int getUses() {
		return this.timer;
	}

	public void setUses(int remainingMoves) {
		this.timer = remainingMoves;
	}

	@Override
	public boolean maxOne() {
		return true;
	}

	@Override
	public EventEmitter<InvincibilityPotion, ItemUsed> itemUsed() {
		return this.itemUsed;
	}

}
