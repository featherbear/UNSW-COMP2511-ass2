package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;

public class US1Test {

	private Dungeon dungeon;
	private Player player;
	private Exit exit;
	private Boulder boulder;
	private Enemy enemy;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
	}

	@Test
	void simpleExit() {
		dungeon.addEntity((exit = new Exit(dungeon, 1, 2)));
		assertEquals(player.interact(exit),true);
		assertEquals(exit.interact(player),true);
	}
	
	@Test
	void failExit() {
		dungeon.addEntity((exit = new Exit(dungeon, 1, 2)));
		dungeon.addEntity(boulder = new Boulder(dungeon, 2, 2));
		dungeon.addEntity(enemy = new Enemy(dungeon, 3, 3));
		assertEquals(exit.interact(boulder),false);
		assertEquals(exit.interact(enemy), false);
		assertEquals(boulder.interact(exit),false);
		assertEquals(enemy.interact(exit),false);
	}
}
