package unsw.dungeon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.goals.Goal;
import unsw.dungeon.goals.GoalCondition;

public class GoalLoader{

	private JSONObject json;
	public GoalLoader(JSONObject json) {
		this.json = json;
	}

	public Dungeon load(Dungeon dungeon) {
		JSONObject jsonGoals = (JSONObject) json.get("goal-condition");
		String type = (String) jsonGoals.getString("goal");	
		JSONArray subgoals;
		try {
			subgoals = (JSONArray) json.getJSONArray("subgoals");
		} catch (JSONException j) {
			subgoals = null;
		}
		if (subgoals != null) {
			dungeon.addGoal(new Goal(dungeon, checkType(type), fetchSubgoals(subgoals)));
		} else {
			ArrayList<Class>list = new ArrayList<Class>();
			list.add(fetchGoal(type));
			dungeon.addGoal(new Goal(dungeon, GoalCondition.OR, list));
		}
		System.out.println("Goals Loaded");
		return dungeon;
		
	}
	
	public ArrayList<Class> fetchSubgoals(JSONArray subgoals) {
		ArrayList<Class> list = new ArrayList<Class>();
		for (int i = 0; i < subgoals.length(); i++) {
			try {
				Class goal = (fetchGoal(subgoals.getJSONObject(i).getString("goal")));
				if (goal != null) {
					list.add(goal);
				}
			} catch (Error e) {
				System.out.println(e);
			}
		}
		return list;
	}
	
	public GoalCondition checkType(String type) {
		GoalCondition condition;
		switch (type) {
		case "AND":
			condition = GoalCondition.COMPOSITE;
			break;
				
		case "OR":
			condition = GoalCondition.OR;
			break;
			
		default:
			condition = null;
			break;
		}
		return condition;
	}
	
	public Class fetchGoal(String type) {
		Class goal;
		switch (type) {
		case "exit":
			goal = Exit.class;
			break;
				
		case "enemies":
			goal = Enemy.class;
			break;
			
		case "treasure":
			goal = Treasure.class;
			break;
		
		case "boulders":
			goal = Switch.class;
			break;
			
		default:
			goal = null;
			break;
		}
		return goal;
	}


}
