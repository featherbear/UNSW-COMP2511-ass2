package unsw.dungeon;

import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
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
	@Override
	public void onLoad(Treasure treasure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Key key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Sword sword) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postLoad(Dungeon dungeon) {
		System.out.println("Dungeon load complete");
	}

}
