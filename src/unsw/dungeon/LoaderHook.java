package unsw.dungeon;

import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.Wall;

public interface LoaderHook {
	public void onLoad(Player player);

	public void onLoad(Wall wall);

	public void onLoad(Exit exit);

	public void onLoad(Door door);

	public void onLoad(Treasure treasure);

	public void onLoad(Key key);

	public void onLoad(Sword sword);

	public void onLoad(InvincibilityPotion potion);

	public void onLoad(Portal portal);

	default public void postLoad(Dungeon dungeon) {
	}

}
