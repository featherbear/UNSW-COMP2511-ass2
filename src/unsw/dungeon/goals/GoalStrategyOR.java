package unsw.dungeon.goals;

public class GoalStrategyOR implements GoalStrategy {
	/**
	 * At least one subgoal must be achieved
	 */
	@Override
	public boolean achieved(Goal g) {
		boolean result = false;

		GoalComposite G = (GoalComposite) g;
		for (Goal subGoal : G.getSubGoals()) {
			if (subGoal.check()) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public String getInfoText() {
		return "OR";
	}
}
