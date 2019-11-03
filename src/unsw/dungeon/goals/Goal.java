package unsw.dungeon.goals;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.events.LocationChanged;

public class Goal {

	private Dungeon dungeon;
	private GoalCondition goalCondition;
	private ArrayList<Class> subGoals;

	public Goal(Dungeon dungeon, GoalCondition goalcondition, ArrayList<Class> subgoals) {
		this.goalCondition = goalcondition;
		this.subGoals = subgoals;
	}

	public void addsubGoal(Class e) {
		this.subGoals.add(e);
	}
	
	

//	@Override
//	public Dungeon getDungeon() {
//		return this.dungeon;
//	}

	public void goalMoveEventHandler(Player p, LocationChanged event) {
		
	}
}
