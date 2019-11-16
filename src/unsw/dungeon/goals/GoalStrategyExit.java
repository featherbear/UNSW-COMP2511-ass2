package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyExit implements GoalStrategy {
	/**
	 * The player must be on top of an exit
	 */
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();

		for (Entity exitObject : Entity.filter(dungeon.getEntities(), Exit.class)) {
			Exit exitEntity = (Exit) exitObject;
			if (exitEntity.getActivated()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getInfoText() {
		return "Reach an exit";
	}
}
