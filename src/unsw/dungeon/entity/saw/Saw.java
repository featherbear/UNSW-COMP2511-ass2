package unsw.dungeon.entity.saw;

import unsw.dungeon.Dungeon;
import unsw.dungeon.enemy.State;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventSAM;
import unsw.dungeon.util.emitter.IntentSAM;

public class Saw extends MovableEntity<Saw> implements Interactable {

	private State state;
	
	
	public Saw(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).kill();
			return true;
		}
		return false;
	}
	
	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		return true;
	}
	
	public void playerMoveEventHandler(Player player, LocationChanged event) {
		
	}

}
