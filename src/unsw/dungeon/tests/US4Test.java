package unsw.dungeon.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;

public class US4Test {

	private Boulder boulder;
	private Player player;
	private Dungeon dungeon;
	private Switch sw;
	private TestUtils Create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	@Test
	void boulderActivate() {
		boulder = Create.Boulder(1, 2);
		sw = Create.Switch(1, 3);
		Create.PostLoad();

		assertFalse(sw.getActivated());
		player.moveDown();
		assertTrue(sw.getActivated());
	}

	@Test
	void boulderDeactivate() {
		boulder = Create.Boulder(1, 2);
		sw = Create.Switch(1, 2);
		Create.PostLoad();

		assertTrue(sw.getActivated());
		player.moveDown();
		assertFalse(sw.getActivated());
	}

	@Test
	void boulderPushThrough() {
		boulder = Create.Boulder(1, 2);
		sw = Create.Switch(1, 3);
		Create.PostLoad();

		assertFalse(sw.getActivated());
		player.moveDown();
		assertTrue(sw.getActivated());
		player.moveDown();
		assertFalse(sw.getActivated());

	}

	@Test
	void otherEntityFail() {
		sw = Create.Switch(1, 2);
		assertFalse(sw.getActivated());
		player.moveDown();
		assertFalse(sw.getActivated());
	}
}
