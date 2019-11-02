package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Wall;

class US5Test {

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
	void boulderPush() {
		Boulder boulder = Create.Boulder(2, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);

		// TODO Switch event
	}

	@Test
	void boulderPushSwitch() {
		Boulder boulder = Create.Boulder(2, 1);
		Switch switchEntity = Create.Switch(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);

		assertEquals(switchEntity.getX(), 3);
		assertEquals(switchEntity.getY(), 1);
	}

	@Test
	void boulderPushPortal() {
		Boulder boulder = Create.Boulder(2, 1);
		Portal portal = Create.Portal(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);

		assertEquals(portal.getX(), 3);
		assertEquals(portal.getY(), 1);
	}

	@Test
	void boulderPushExit() {
		Boulder boulder = Create.Boulder(2, 1);
		Exit exit = Create.Exit(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);
	}

	@Test
	void boulderPushEnemy() {
		Boulder boulder = Create.Boulder(2, 1);
		Enemy enemy = Create.Enemy(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);

		// Enemy should die
		assertFalse(dungeon.getEntities().contains(enemy));
	}

	@Test
	void boulderPushItems() {
		// Test a sword
		Boulder boulder = Create.Boulder(2, 1);
		Sword sword = Create.Sword(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);

		assertTrue(dungeon.getEntities().contains(sword));
	}

	@Test
	void boulderPushWall() {
		Boulder boulder = Create.Boulder(2, 1);
		Wall wall = Create.Wall(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		player.moveRight();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);
	}

	@Test
	void boulderPushDoor() {
		Boulder boulder = Create.Boulder(2, 1);
		Door door = Create.Door(3, 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		// Test with a close door, should block
		player.moveRight();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);

		// Test with an open door, should allow
		door.open();

		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(boulder.getX(), 3);
		assertEquals(boulder.getY(), 1);
	}

	@Test
	void boulderPushBoulder() {
		Boulder boulder1 = Create.Boulder(2, 1);

		assertEquals(boulder1.getX(), 2);
		assertEquals(boulder1.getY(), 1);

		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(boulder1.getX(), 3);
		assertEquals(boulder1.getY(), 1);

		Boulder boulder2 = Create.Boulder(4, 1);
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(boulder2.getX(), 4);
		assertEquals(boulder2.getY(), 1);

	}

}
