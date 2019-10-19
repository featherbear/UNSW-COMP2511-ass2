package unsw.dungeon;

import unsw.dungeon.entity.Door;
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

	}

	@Override
	public void onLoad(Door door) {

	}

	@Override
	public void onLoad(Treasure treasure) {
		Dungeon d = treasure.getDungeon();
		Player p = d.getPlayer();
		p.moveEvent.register(treasure::LocationChangedHandler);
	}

	@Override
	public void onLoad(Key key) {
		Dungeon d = key.getDungeon();
		Player p = d.getPlayer();
		p.moveEvent.register(key::LocationChangedHandler);
	}

	@Override
	public void onLoad(Sword sword) {
		Dungeon d = sword.getDungeon();
		Player p = d.getPlayer();
		p.moveEvent.register(sword::LocationChangedHandler);
	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		Dungeon d = potion.getDungeon();
		Player p = d.getPlayer();
		p.moveEvent.register(potion::LocationChangedHandler);
	}

	@Override
	public void postLoad(Dungeon dungeon) {
		System.out.println("Dungeon load complete");
	}

}
