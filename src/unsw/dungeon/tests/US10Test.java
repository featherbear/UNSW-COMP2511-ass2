package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Player;

class US10Test {

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
		InvincibilityPotion invincibilityPotion = Create.InvincibilityPotion(2, 1);

		assertEquals(invincibilityPotion.getUses(), 10);
		invincibilityPotion.use(Create.Enemy(0, 0));
	}

	@Test
	void playerPickup() {
		InvincibilityPotion invincibilityPotion = Create.InvincibilityPotion(2, 1);
		assertFalse(player.getInventory().contains(invincibilityPotion));
		player.pickUp(invincibilityPotion);
		assertTrue(player.getInventory().contains(invincibilityPotion));
	}

	@Test
	void playerRemove() {
		InvincibilityPotion invincibilityPotion = Create.InvincibilityPotion(2, 1);
		invincibilityPotion.setUses(2);
		Enemy enemy = Create.Enemy(3, 1);

		assertFalse(player.getInventory().contains(invincibilityPotion));

		// Pick up the item
		player.moveRight();
		assertTrue(player.getInventory().contains(invincibilityPotion));

		// Kill the enemy
		player.moveRight();
		assertTrue(player.getInventory().contains(invincibilityPotion));

		// Extra step
		player.moveRight();
		assertFalse(player.getInventory().contains(invincibilityPotion));

	}

	@Test
	void carrySinglePotion() {
		InvincibilityPotion invincibilityPotion1 = Create.InvincibilityPotion(2, 1);

		InvincibilityPotion invincibilityPotion2 = Create.InvincibilityPotion(3, 1);

		player.moveRight();
		assertTrue(player.getInventory().contains(invincibilityPotion1));

		player.moveRight();
		assertFalse(player.getInventory().contains(invincibilityPotion2));
	}

	@Test
	void removedOnPickup() {
		InvincibilityPotion invincibilityPotion = Create.InvincibilityPotion(2, 1);
		assertTrue(invincibilityPotion.getVisibility());
		player.pickUp(invincibilityPotion);
		assertFalse(invincibilityPotion.getVisibility());
	}
}
