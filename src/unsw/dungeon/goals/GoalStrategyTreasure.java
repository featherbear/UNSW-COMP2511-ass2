package unsw.dungeon.goals;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.meta.Entity;

public class GoalStrategyTreasure implements GoalStrategy {
	/**
	 * All treasure must have been picked up
	 */
	@Override
	public boolean achieved(Goal g) {
		Dungeon dungeon = g.getDungeon();
		Player player = dungeon.getPlayer();

		ArrayList<Treasure> playerTreasure = Entity.filter(player.getInventory(), Treasure.class);
		ArrayList<Treasure> dungeonTreasure = Entity.filter(dungeon.getEntities(), Treasure.class);

		return playerTreasure.size() == dungeonTreasure.size();
	}

	@Override
	public String getInfoText() {
		return "Collect all treasure";
	}
}
