package unsw.dungeon.goals;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;

public class GoalComposite extends Goal {
	ArrayList<Goal> subGoals;

	public GoalComposite(Dungeon dungeon, GoalStrategy strategy) {
		super(dungeon, strategy);
		this.subGoals = new ArrayList<Goal>();
	}

	public void addSubGoal(Goal e) {
		this.subGoals.add(e);
	}

	public ArrayList<Goal> getSubGoals() {
		return this.subGoals;
	}
}
