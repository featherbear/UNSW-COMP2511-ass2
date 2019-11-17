package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;

public class Saw extends MovableEntity<Saw> implements Interactable {

	public Saw(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
	}

	@Override
	public boolean interact(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		return true;
	}
	
	public void playerMoveEventHandler(Player player, LocationChanged event) {
		
	}

}
