package unsw.dungeon.entity;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.GoalCondition;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.events.LocationChanged;

public class Goals extends Entity implements Interactable{

	private Dungeon dungeon;
	private GoalCondition goalcondition;
	private ArrayList<Class> subgoals;
	
	public Goals(Dungeon dungeon, GoalCondition goalcondition, ArrayList<Class> subgoals) {
		super(dungeon, EntityLevel.GOAL, 0, 0);
		this.goalcondition = goalcondition;
		this.subgoals = subgoals;
	}
	
	public void addsubGoal(Class e) {
		subgoals.add(e);
	}
	
	@Override
	public boolean interact(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}
	public Dungeon getDungeon() {
		return this.dungeon;
	}
	
	public boolean goalMoveEventHandler(Player player, LocationChanged event) {
		
		return false;
	}

}
