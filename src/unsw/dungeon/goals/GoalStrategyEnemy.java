package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyEnemy implements GoalStrategy {
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		return Entity.filter(dungeon.getEntities(), Enemy.class).size() == 0;
	}
}
