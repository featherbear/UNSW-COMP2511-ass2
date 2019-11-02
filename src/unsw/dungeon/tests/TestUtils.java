package unsw.dungeon.tests;

import unsw.dungeon.Dungeon;
import unsw.dungeon.GameHooks;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Wall;

public class TestUtils {
	private Dungeon dungeon;
	private GameHooks gameHooks;

	public TestUtils(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.gameHooks = new GameHooks();
	}

	Boulder Boulder(int x, int y) {
		Boulder boulder = new Boulder(this.dungeon, x, y);

		this.gameHooks.onLoad(boulder);
		dungeon.addEntity(boulder);

		return boulder;
	}

	Switch Switch(int x, int y) {
		Switch switchEntity = new Switch(this.dungeon, x, y);

		this.gameHooks.onLoad(switchEntity);
		dungeon.addEntity(switchEntity);

		return switchEntity;
	}

	Portal Portal(int x, int y) {
		Portal portal = new Portal(this.dungeon, x, y);

		this.gameHooks.onLoad(portal);
		dungeon.addEntity(portal);

		return portal;
	}

	Enemy Enemy(int x, int y) {
		Enemy enemy = new Enemy(this.dungeon, x, y);

		this.gameHooks.onLoad(enemy);
		dungeon.addEntity(enemy);

		return enemy;
	}

	Sword Sword(int x, int y) {
		Sword sword = new Sword(this.dungeon, x, y);

		this.gameHooks.onLoad(sword);
		dungeon.addEntity(sword);

		return sword;
	}

	Wall Wall(int x, int y) {
		Wall wall = new Wall(this.dungeon, x, y);

		this.gameHooks.onLoad(wall);
		dungeon.addEntity(wall);

		return wall;
	}

	Exit Exit(int x, int y) {
		Exit exit = new Exit(this.dungeon, x, y);

		this.gameHooks.onLoad(exit);
		dungeon.addEntity(exit);

		return exit;
	}

	Door Door(int x, int y) {
		Door door = new Door(this.dungeon, x, y);

		this.gameHooks.onLoad(door);
		dungeon.addEntity(door);

		return door;
	}

	Key Key(int x, int y) {
		Key key = new Key(this.dungeon, x, y);

		this.gameHooks.onLoad(key);
		dungeon.addEntity(key);

		return key;
	}

}
