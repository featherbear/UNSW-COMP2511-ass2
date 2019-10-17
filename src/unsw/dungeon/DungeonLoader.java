package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Wall;

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

		JSONArray jsonEntities = json.getJSONArray("entities");

		LoaderComposite loaders = new LoaderComposite(hooks);
		loaders.addHook(new GameHooks());

		for (int i = 0; i < jsonEntities.length(); i++) {
			try {
				Entity entity = this.loadEntity(dungeon, jsonEntities.getJSONObject(i), loaders);
				dungeon.addEntity(entity);
			} catch (Error e) {
				System.out.println(e);
			}
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
			Player player = new Player(dungeon, x, y);
			dungeon.setPlayer(player);
			loaders.onLoad(player);
			return player;

		case "wall":
			Wall wall = new Wall(dungeon, x, y);
			loaders.onLoad(wall);
			return wall;

		case "exit":
			Exit exit = new Exit(dungeon, x, y);
			loaders.onLoad(exit);
			return exit;
			
		case "boulder":
			Boulder boulder = new Boulder(dungeon, x, y);
			loaders.onLoad(boulder);
			return boulder;
			
		case "switch":
			Switch s = new Switch(dungeon, x, y);
			loaders.onLoad(s);
			return s;

		default:
			throw new Error("Could not load JSON for object type " + type);
		}

	}
};

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
	public void onLoad(Switch s) {
		for (LoaderHook hook : this.hooks) {
			hook.onLoad(s);
		}
	}

	@Override
	public void postLoad(Dungeon dungeon) {
		for (LoaderHook hook : this.hooks) {
			hook.postLoad(dungeon);
		}
	}
}