package unsw.dungeon.goals;

public class GoalStrategyAND implements GoalStrategy {

	/**
	 * All subgoals have to be achieved to return true
	 */
	@Override
	public boolean achieved(Goal g) {
		boolean result = true;

		GoalComposite G = (GoalComposite) g;
		for (Goal subGoal : G.getSubGoals()) {
			if (!(subGoal.check())) {
				result = false;
			}
		}

		return result;
	}

	@Override
	public String getInfoText() {
		return "AND";
	}
}
