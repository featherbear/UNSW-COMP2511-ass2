package unsw.dungeon;

import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Wall;

public interface LoaderHook {
	public void onLoad(Player player);

	public void onLoad(Wall wall);

	public void onLoad(Exit exit);

	default public void postLoad(Dungeon dungeon) {
	}

	public void onLoad(Boulder boudler);
	
	public void onLoad(Switch sw);
}
