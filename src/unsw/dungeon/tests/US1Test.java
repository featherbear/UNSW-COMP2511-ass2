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
	private TestUtils create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		create = new TestUtils(dungeon);
		dungeon.setPlayer(player);
	}

	/**
	 * 
	 */
	@Test
	void simpleExit() {
		exit = create.Exit(1, 2);
		assertEquals(false, exit.getActivated());
		player.moveDown();
		assertEquals(true, exit.getActivated());
	}
	
	@Test
	void failExit() {
		exit = create.Exit(5,5);
		player.moveRight();
		assertEquals(exit.getActivated(), false);
	}
}
