package unsw.dungeon;

import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Wall;

public class GameHooks implements LoaderHook {

	@Override
	public void onLoad(Player player) {

	}

	@Override
	public void onLoad(Wall wall) {

	}

	@Override
	public void onLoad(Exit exit) {

	}

	@Override
	public void postLoad(Dungeon dungeon) {
		System.out.println("Dungeon load complete");
	}

}
