package unsw.dungeon.goals;

import java.util.ArrayList;
import java.util.stream.Collectors;

import unsw.dungeon.Dungeon;

public class GoalComposite extends Goal {
	ArrayList<Goal> subGoals;

	public GoalComposite(Dungeon dungeon, GoalStrategy strategy) {
		super(dungeon, strategy);
		this.subGoals = new ArrayList<Goal>();
	}

	/**
	 * Add a new subgoal to the Goal composite
	 * 
	 * @param subgoal
	 */
	public void addSubGoal(Goal e) {
		this.subGoals.add(e);
	}

	/**
	 * @return Subgoals for the Goal composite
	 */
	public ArrayList<Goal> getSubGoals() {
		return this.subGoals;
	}

	public ArrayList<Goal> getRemainingGoals() {
		ArrayList<Goal> result = new ArrayList<Goal>();

		for (Goal g : this.subGoals) {
			if (!g.achieved()) {
				result.add(g);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return String.format("(%s: %s)", this.strategy.getInfoText(),
				this.subGoals.stream().map(Object::toString).collect(Collectors.joining(", ")));
	}
}
