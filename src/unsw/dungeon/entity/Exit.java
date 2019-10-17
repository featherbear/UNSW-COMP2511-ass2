package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;

public class Exit extends Entity implements Interactable {

	public Exit(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
	}

	@Override
	public void interact(Entity entity) {

	}

}
