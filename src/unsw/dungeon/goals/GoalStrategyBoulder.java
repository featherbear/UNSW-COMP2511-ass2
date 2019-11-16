package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyBoulder implements GoalStrategy {
	/**
	 * All switches must have boulders on top of them
	 */
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		for (Switch switchEntity : Entity.filter(dungeon.getEntities(), Switch.class)) {
			if (!(switchEntity.getActivated())) {
				return false;
			}
		}

		return true;
	}
}
