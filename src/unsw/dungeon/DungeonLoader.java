package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Goals;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.Wall;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.GoalCondition;


/**
 * Loads a dungeon from a .json file.
 *
 * By passing implementations of LoaderHook, entity creation can be hooked onto.
 * This is useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public class DungeonLoader {

	private JSONObject json;

	public DungeonLoader(String filename) throws FileNotFoundException {
		json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
	}

	/**
	 * Parses the JSON to create a dungeon.
	 * 
	 * @return
	 */
	public Dungeon load(LoaderHook... hooks) {
		int width = json.getInt("width");
		int height = json.getInt("height");

		Dungeon dungeon = new Dungeon(width, height);

		dungeon.setPlayer(new Player(dungeon, 0, 0));

		JSONArray jsonEntities = json.getJSONArray("entities");
		JSONObject jsonGoals = (JSONObject) json.get("goal-condition");

		LoaderComposite loaders = new LoaderComposite(hooks);
		loaders.addHook(new GameHooks());

		for (int i = 0; i < jsonEntities.length(); i++) {
			try {
				Entity entity = this.loadEntity(dungeon, jsonEntities.getJSONObject(i), loaders);
				if (entity != null) {
					dungeon.addEntity(entity);
				}
			} catch (Error e) {
				System.out.println(e);
			}
		}
		GoalCondition cond = null;
		JSONArray subgoals = null;
		String type;
		ArrayList<Class> objectives = new ArrayList<Class>();
		try {
			subgoals = jsonGoals.getJSONArray("subgoals");
			type = jsonGoals.getString("goal");
			switch (type) {
			case "AND":
				cond = GoalCondition.COMPOSITE;
			default: //OR
				cond = GoalCondition.OR;
			}
			
			for (int i = 0; i < subgoals.length(); i++) {
				try {
					objectives = loadGoals(subgoals.getJSONObject(i));
				} catch (Error e) {
					System.out.println(e);
				}
			}
			dungeon.addGoal(new Goals(dungeon, cond, objectives));
			
		} catch (JSONException e) {
			dungeon.addGoal(loadGoals(dungeon, jsonGoals, loaders));
		}

		loaders.postLoad(dungeon);
		return dungeon;
	}

	private Entity loadEntity(Dungeon dungeon, JSONObject json, LoaderHook loaders) {
		String type = json.getString("type");
		int x = json.getInt("x");
		int y = json.getInt("y");

		switch (type) {

		case "player":
			Player player = dungeon.getPlayer();
			player.x().set(x);
			player.y().set(y);
			loaders.onLoad(player);
			return player;
			
		case "enemy":
			Enemy enemy = new Enemy(dungeon, x, y);
			loaders.onLoad(enemy);
			return enemy;

		case "wall":
			Wall wall = new Wall(dungeon, x, y);
			loaders.onLoad(wall);
			return wall;

		case "exit":
			Exit exit = new Exit(dungeon, x, y);
			loaders.onLoad(exit);
			return exit;
			
		case "switch":
			Switch sw = new Switch(dungeon, x, y);
			loaders.onLoad(sw);
			return sw;
			
		case "boulder":
			Boulder boulder = new Boulder(dungeon, x, y);
			loaders.onLoad(boulder);
			return boulder;

		case "door":
			Door door = new Door(dungeon, x, y);
			door.setID(json.getInt("id"));
			loaders.onLoad(door);
			return door;

		case "treasure":
			Treasure treasure = new Treasure(dungeon, x, y);
			loaders.onLoad(treasure);
			return treasure;

		case "key":
			Key key = new Key(dungeon, x, y);
			key.setID(json.getInt("id"));
			loaders.onLoad(key);
			return key;

		case "sword":
			Sword sword = new Sword(dungeon, x, y);
			loaders.onLoad(sword);
			return sword;

		case "invincibility":
			InvincibilityPotion potion = new InvincibilityPotion(dungeon, x, y);
			loaders.onLoad(potion);
			return potion;

		case "portal":
			Portal portal = new Portal(dungeon, x, y);
			portal.setID(json.getInt("id"));
			if (json.optBoolean("activated", true)) {
				portal.activate();
			} else {
				portal.deactivate();
			}
			loaders.onLoad(portal);
			return portal;

		default:
			throw new Error("Could not load JSON for object type " + type);
		}

	}
	
	private Goals loadGoals(Dungeon dungeon, JSONObject json, LoaderHook loaders) {
		GoalCondition c = null;
		ArrayList<Class> subgoals = new ArrayList<Class>();
		String goals = json.getString("goal");
		
		switch(goals) {
		case "AND":
			c = GoalCondition.COMPOSITE;
			break;
		case "OR":
			c = GoalCondition.OR;
			break;
		
		case "enemies":
			subgoals.add(Enemy.class);
			break;
			
		case "treasure":
			subgoals.add(Treasure.class);
			break;
		
		case "exit":
			subgoals.add(Exit.class);
			break;
			
		default:
			break;
		}
		
		Goals g = new Goals(dungeon, c, subgoals);
		return g;
	}
	
	private ArrayList<Class> loadGoals(JSONObject json){
		ArrayList<Class> subgoals = new ArrayList<Class>();
		String goals = json.getString("goal");
		switch("goal") {
		
		case "enemies":
			subgoals.add(Enemy.class);
			break;
			
		case "treasure":
			subgoals.add(Treasure.class);
			break;
		
		case "exit":
			subgoals.add(Exit.class);
			break;
			
		default:
			break;
			
		}
		
		return subgoals;
	}
};

// TODO: change into array, a bit cluttered having to implement the same thing each time
class LoaderComposite implements LoaderHook {
	private ArrayList<LoaderHook> hooks;

	public LoaderComposite(LoaderHook... hooks) {
		this.hooks = new ArrayList<LoaderHook>();
		for (LoaderHook hook : hooks) {
			this.hooks.add(hook);
		}
	}

	public void addHook(LoaderHook hook) {
		if (this.hooks.contains(hook)) {
			return;
		}
		this.hooks.add(hook);
	}

	@Override
	public void onLoad(Player player) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(player);
		}
	}

	@Override
	public void onLoad(Wall wall) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(wall);
		}
	}

	@Override
	public void onLoad(Exit exit) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(exit);
		}
	}
	
	@Override
	public void onLoad(Boulder boulder) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(boulder);
		}
	}

	@Override
	public void onLoad(Switch sw) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(sw);
		}
	}

	@Override
	public void onLoad(Door door) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(door);
		}
	}

	@Override
	public void onLoad(Treasure treasure) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(treasure);
		}
	}

	@Override
	public void onLoad(Key key) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(key);
		}
	}

	@Override
	public void onLoad(Sword sword) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(sword);
		}
	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(potion);
		}
	}

	@Override
	public void onLoad(Enemy enemy) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(enemy);
		}
	}

	@Override
	public void onLoad(Portal portal) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(portal);
		}
	}

	@Override
	public void postLoad(Dungeon dungeon) {
		for (LoaderHook hook : this.hooks) {
			hook.postLoad(dungeon);
		}
	}

}