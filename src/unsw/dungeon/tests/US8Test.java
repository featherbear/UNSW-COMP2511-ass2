package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;

class US8Test {

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
	void testEnemyMovement() {
		Enemy enemy = Create.Enemy(5, 5);
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		enemy.moveRight();

		assertEquals(enemy.getX(), 6);
		assertEquals(enemy.getY(), 5);
	}

	@Test
	void testEnemyCollideBoulder() {
		Enemy enemy = Create.Enemy(5, 5);
		Create.Boulder(6, 5);
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		enemy.moveRight();
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		enemy.moveDown();
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 6);
	}

	@Test

	void testEnemyCollideDoor() {
		Enemy enemy = Create.Enemy(5, 5);
		Door door = Create.Door(6, 5);

		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		enemy.moveRight();
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		door.open();

		enemy.moveRight();
		assertEquals(enemy.getX(), 6);
		assertEquals(enemy.getY(), 5);
	}

	@Test
	void testEnemyApproach() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		Enemy enemy = Create.Enemy(5, 5);
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		player.moveRight();

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 4);
		assertEquals(enemy.getY(), 5);
	}

	@Test
	void testEnemyFlee() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		Enemy enemy = Create.Enemy(5, 5);
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 5);

		// Enemy will flee when the player has an invincibility potion
		player.pickUp(Create.InvincibilityPotion(0, 1));
		player.moveRight();

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		assertEquals(enemy.getX(), 6);
		assertEquals(enemy.getY(), 5);
	}

	@Test
	void testEnemyDeathSword() {
		Enemy enemy = Create.Enemy(2, 1);
		player.pickUp(Create.Sword(0, 0));

		player.moveRight();

		assertFalse(dungeon.getEntities().contains(enemy));
	}

	@Test
	void testEnemyDeathPotion() {
		Enemy enemy = Create.Enemy(2, 1);
		player.pickUp(Create.InvincibilityPotion(0, 0));

		player.moveRight();
		assertFalse(dungeon.getEntities().contains(enemy));
	}

	@Test
	void testPlayerDeathPlayerMove() {
		Enemy enemy = Create.Enemy(2, 1);
		assertTrue(player.isAlive());
		player.moveRight();
		assertFalse(player.isAlive());
	}

//	@Test
//	void testPlayerDeathEnemyMove() {
//		Enemy enemy = Create.Enemy(2, 2);
//		assertTrue(player.isAlive());
//		player.moveDown();
//		assertFalse(player.isAlive());
//	}

}
