package unsw.dungeon.goals;

public class GoalStrategyAND implements GoalStrategy {

	@Override
	public boolean achieved(Goal g) {
		GoalComposite G = (GoalComposite) g;
		for (Goal subGoal : G.getSubGoals()) {
			if (!(subGoal.achieved())) {
				return false;
			}
		}

		return true;
	}

}
