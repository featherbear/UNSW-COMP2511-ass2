package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.enemy.Enemy;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyEnemy implements GoalStrategy {
	/**
	 * All enemies must be defeated
	 */
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		return Entity.filter(dungeon.getEntities(), Enemy.class).size() == 0;
	}
}
