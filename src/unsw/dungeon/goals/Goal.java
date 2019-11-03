package unsw.dungeon.goals;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.ItemEntity;
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
	
	

	public Dungeon getDungeon() {
		return this.dungeon;
	}

	public void goalMoveEventHandler(Player p, LocationChanged event) {
		ArrayList<Entity> entities = p.getDungeon().getEntities();
		for (Class goal : subGoals) {
			ArrayList<Entity> goals = Entity.filter(entities, goal);
			if (goalCondition == goalCondition.COMPOSITE) {
				
			} else {
				if  (goal == Treasure.class){
					ArrayList<ItemEntity> items = ItemEntity.filterItems(p.getInventory(), Treasure.class);
					if (goals.size() == items.size()) {
						this.dungeon.finishEvent.emit();
					}
				} else if (goal == Enemy.class && goals.size() == 0){
					this.dungeon.finishEvent.emit();
				} else if (goal == Switch.class) {
					boolean allActivated = true;
					for (Entity e : goals) {
						if (e instanceof Switch) {
							Switch sw = (Switch) e;
							if (sw.getActivated() == false) {
								allActivated = false;
							}
						}
					}
					if (allActivated) {
						this.dungeon.finishEvent.emit();
					}
				} else if (goal == Exit.class) {
					
				}
			}
				
		}
		
		if (goalCondition == GoalCondition.COMPOSITE) {
			
		}
	}
	
	public void composite(ArrayList<Entity> entities, Player p) {
		
	}
	
	public void or(ArrayList<Entity> entities, Player p) {
		
	}
}
