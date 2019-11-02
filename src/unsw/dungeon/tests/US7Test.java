package unsw.dungeon.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Treasure;

class US7Test {

	private Dungeon dungeon;
	private Player player;
	private TestUtils Create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	// TODO: Goals

	@Test
	void treasureRemovedOnPickup() {
		Treasure treasure = Create.Treasure(2, 1);
		assertTrue(treasure.getVisibility());
		player.pickUp(treasure);
		assertFalse(treasure.getVisibility());

	}

}
