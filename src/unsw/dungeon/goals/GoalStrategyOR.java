package unsw.dungeon.goals;

public class GoalStrategyOR implements GoalStrategy {
	/**
	 * At least one subgoal must be achieved
	 */
	@Override
	public boolean achieved(Goal g) {
		GoalComposite G = (GoalComposite) g;
		for (Goal subGoal : G.getSubGoals()) {
			if (subGoal.achieved()) {
				return true;
			}
		}

		return false;
	}
}
