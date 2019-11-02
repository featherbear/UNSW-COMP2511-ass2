package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Sword;

class US9Test {

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

	@Test
	void durability() {
		Sword sword = Create.Sword(2, 1);

		assertEquals(sword.getUses(), 5);
		sword.use(Create.Enemy(0, 0));
		assertEquals(sword.getUses(), 4);
		sword.use(Create.Enemy(0, 0));
		assertEquals(sword.getUses(), 3);
		sword.use(Create.Enemy(0, 0));
		assertEquals(sword.getUses(), 2);
		sword.use(Create.Enemy(0, 0));
		assertEquals(sword.getUses(), 1);
		sword.use(Create.Enemy(0, 0));
		assertEquals(sword.getUses(), 0);
	}

	@Test
	void playerPickup() {
		Sword sword = Create.Sword(2, 1);
		assertFalse(player.getInventory().contains(sword));
		player.pickUp(sword);
		assertTrue(player.getInventory().contains(sword));
	}

	@Test
	void playerRemove() {
		Sword sword = Create.Sword(2, 1);
		sword.setDurability(1);
		player.pickUp(sword);
		assertTrue(player.getInventory().contains(sword));
		sword.use(Create.Enemy(0, 0));
		assertFalse(player.getInventory().contains(sword));
	}

	@Test
	void carrySingleSword() {
		Sword sword1 = Create.Sword(2, 1);

		Sword sword2 = Create.Sword(3, 1);

		player.moveRight();
		assertTrue(player.getInventory().contains(sword1));

		player.moveRight();
		assertFalse(player.getInventory().contains(sword2));

	}

	@Test
	void removedOnPickup() {
		Sword sword = Create.Sword(2, 1);
		assertTrue(sword.getVisibility());
		player.pickUp(sword);
		assertFalse(sword.getVisibility());
	}

}
