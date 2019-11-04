package unsw.dungeon;

import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.Wall;

public class GameHooks implements LoaderHook {

	private Dungeon dungeon;
	public GameHooks(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	@Override
	public void onLoad(Player player) {
	}

	@Override
	public void onLoad(Enemy enemy) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(enemy::playerMoveEventHandler);
		p.moveIntent.register(enemy::playerMoveIntentHandler);
	}

	@Override
	public void onLoad(Wall wall) {
	}

	@Override
	public void onLoad(Exit exit) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(exit::playerMoveEventHandler);
	}

	@Override
	public void onLoad(Boulder boulder) {
		Player p = this.dungeon.getPlayer();

		p.moveIntent.register(boulder::playerMoveIntentHandler);

	}

	@Override
	public void onLoad(Switch sw) {
		Dungeon d = sw.getDungeon();
		Player p = d.getPlayer();
		p.moveEvent.register(sw::playerMoveEventHandler);
	}

	}

	@Override
	public void onLoad(Portal portal) {
		Player p = this.dungeon.getPlayer();

		p.moveIntent.register(portal::playerMoveIntentHandler);
	}

	@Override
	public void onLoad(Door door) {
		Player p = this.dungeon.getPlayer();

		p.moveIntent.register(door::playerMoveIntentHandler);
	}

	@Override
	public void onLoad(Treasure treasure) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(treasure.LocationChangedHandler);
	}

	@Override
	public void onLoad(Key key) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(key.LocationChangedHandler);
	}

	@Override
	public void onLoad(Sword sword) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(sword.LocationChangedHandler);
	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		Player p = this.dungeon.getPlayer();
		p.moveEvent.register(potion.LocationChangedHandler);

		potion.pickupEvent.register(() -> {
			p.moveEvent.register(potion.playerMoveEventHandler);
		});
	}

	@Override
	public void postLoad(Dungeon dungeon) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(dungeon::playerMoveEventHandler);

		dungeon.finishEvent.register(() -> {
			System.out.println("Player has won!");
		});

		System.out.println("Dungeon load complete");
	}
}
