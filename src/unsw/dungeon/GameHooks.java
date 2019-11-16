package unsw.dungeon;

import java.util.ArrayList;

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
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.util.emitter.GenericSAM;

public class GameHooks implements LoaderHook {

	private Dungeon dungeon;
	private ArrayList<GenericSAM> postLoad;

	public GameHooks(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.postLoad = new ArrayList<GenericSAM>();
	}

	@Override
	public void onLoad(Player player) {
	}

	@Override
	public void onLoad(Enemy enemy) {
		Player p = this.dungeon.getPlayer();

		p.moveEvent.register(enemy.playerMoveEventHandler);
		p.moveIntent.register(enemy.playerMoveIntentHandler);
		enemy.alive().addListener((observer, oldValue, newValue) -> {
			if (newValue == false) {
				this.dungeon.removeEntity(enemy);
				p.moveEvent.unregister(enemy.playerMoveEventHandler);
				p.moveIntent.unregister(enemy.playerMoveIntentHandler);
			}
		});
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

		this.postLoad.add(() -> {
			for (Boulder boulderObject : Entity.filter(this.dungeon.getEntities(), Boulder.class)) {
				boulderObject.moveEvent.register(sw::boulderMoveEventHandler);
			}

			sw.checkBoulder();
		});

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

		// Handle goal
		p.moveEvent.register(dungeon::playerMoveEventGoalHandler);

		for (GenericSAM func : this.postLoad) {
			func.execute();
		}

		dungeon.finishEvent.register(() -> {
			System.out.println("Player has won!");
		});

		System.out.println("Dungeon load complete");
	}
}
