package unsw.dungeon.entity.saw;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.enemy.Enemy;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;

public class Saw extends MovableEntity<Saw> implements Interactable {

	private SawState state;
	private SawState horizontal;
	private SawState vertical;
	
	
	public Saw(Dungeon dungeon, int x, int y, String orientation) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		horizontal = new horizontalState(this);
		vertical = new verticalState(this);
		if (orientation.equals("H")) {
			state = horizontal;
		} else if (orientation.equals("V")) {
			state = vertical;
		} 
		
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			if (!((Player) entity).hasItemUsable(InvincibilityPotion.class)) {
				((Player) entity).kill();
				return true;
			}
		} else if (entity instanceof Enemy) {
			((Enemy) entity).kill();
			return true;
		}
		return false;
	}
	
	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}
		return player.interact(this);
	}
	
	public void playerMoveEventHandler(Player player, LocationChanged event) {
		state.move(event);
	}

}
