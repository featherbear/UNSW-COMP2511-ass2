package unsw.dungeon.goals;

import java.util.ArrayList;

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
}
