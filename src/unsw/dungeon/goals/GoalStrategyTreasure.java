package unsw.dungeon.goals;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyTreasure implements GoalStrategy {
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		Player player = dungeon.getPlayer();

		ArrayList<Entity> playerTreasure = Entity.filter(player.getInventory(), Treasure.class);
		ArrayList<Entity> dungeonTreasure = Entity.filter(dungeon.getEntities(), Treasure.class);

		return playerTreasure.size() == dungeonTreasure.size();
	}
}