package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyBoulder implements GoalStrategy {
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		for (Entity switchObject : Entity.filter(dungeon.getEntities(), Switch.class)) {
			Switch switchEntity = (Switch) switchObject;
			if (!(switchEntity.getActivated())) {
				return false;
			}
		}

		return true;
	}
}
