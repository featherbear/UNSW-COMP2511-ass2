package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.Wall;
import unsw.dungeon.entity.meta.Entity;

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

		case "wall":
			Wall wall = new Wall(dungeon, x, y);
			loaders.onLoad(wall);
			return wall;

		case "exit":
			Exit exit = new Exit(dungeon, x, y);
			loaders.onLoad(exit);
			return exit;

		case "treasure":
			Treasure treasure = new Treasure(dungeon, x, y);
			loaders.onLoad(treasure);
			return treasure;

		case "key":
			Key key = new Key(dungeon, x, y);
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

		default:
			throw new Error("Could not load JSON for object type " + type);
		}

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
	public void postLoad(Dungeon dungeon) {
		for (LoaderHook hook : this.hooks) {
			hook.postLoad(dungeon);
		}
	}
}