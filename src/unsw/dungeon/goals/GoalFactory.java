package unsw.dungeon.goals;

import org.json.JSONObject;

import unsw.dungeon.Dungeon;

public class GoalFactory {

	private static final GoalStrategy GoalStrategyAND = new GoalStrategyAND();
	private static final GoalStrategy GoalStrategyOR = new GoalStrategyOR();
	private static final GoalStrategy GoalStrategyExit = new GoalStrategyExit();
	private static final GoalStrategy GoalStrategyEnemy = new GoalStrategyEnemy();
	private static final GoalStrategy GoalStrategyBoulder = new GoalStrategyBoulder();
	private static final GoalStrategy GoalStrategyTreasure = new GoalStrategyTreasure();

	private static GoalStrategy stringToStrategy(String string) {
		switch (string) {
		case "exit":
			return GoalStrategyExit;
		case "enemies":
			return GoalStrategyEnemy;
		case "boulders":
			return GoalStrategyBoulder;
		case "treasure":
			return GoalStrategyTreasure;
		case "AND":
			return GoalStrategyAND;
		case "OR":
			return GoalStrategyOR;
		default:
			throw new Error("Unhandled goal type!");
		}

	}

	public static Goal create(Dungeon dungeon, JSONObject JSON) {
		String strategyType = JSON.getString("goal");
		GoalStrategy strategy = stringToStrategy(strategyType);

		switch (strategyType) {
		case "exit":
		case "enemies":
		case "boulders":
		case "treasure":
			return new Goal(dungeon, strategy);
		}

		GoalComposite node = new GoalComposite(dungeon, strategy);

		for (Object subGoalJSON : JSON.getJSONArray("subgoals")) {
			node.addSubGoal(create(dungeon, (JSONObject) subGoalJSON));
		}

		return node;
	}

}