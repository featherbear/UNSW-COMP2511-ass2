package unsw.dungeon.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;

public class US1Test {

	private Dungeon dungeon;
	private Player player;
	private Exit exit;
	private TestUtils Create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	@Test
	void manualExitActivate() {
		exit = Create.Exit(1, 2);
		assertFalse(exit.getActivated());
		exit.activate();
		assertTrue(exit.getActivated());

		exit.deactivate();
		assertFalse(exit.getActivated());
	}

	@Test
	void moveExitActivate() {
		exit = Create.Exit(1, 2);
		assertFalse(exit.getActivated());
		player.moveDown();
		assertTrue(exit.getActivated());
	}

	@Test
	void moveExitNoActivate() {
		exit = Create.Exit(5, 5);
		player.moveRight();
		assertFalse(exit.getActivated());
	}
}
