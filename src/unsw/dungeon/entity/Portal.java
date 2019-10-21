package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;

public class Portal extends Entity implements Interactable {

	public Portal(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
	}

	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}

		// Check activated

		return true;
	}

}
