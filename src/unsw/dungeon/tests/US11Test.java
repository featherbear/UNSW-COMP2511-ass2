package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;

class US11Test {

	private Dungeon dungeon;
	private Player player;
	private TestUtils Create;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 2, 3)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	@Test
	void teleportPlayer() {
		Portal portal1 = Create.Portal(3, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(1);

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);

		player.moveRight();
		// Successful teleport
		assertEquals(player.getX(), 7);
		assertEquals(player.getY(), 7);
	}

	@Test
	void singlePortal() {
		Portal portal = Create.Portal(3, 3);

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);

		player.moveRight();
		// No matching teleport destination
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);
	}

	@Test
	void noMatchingPortal() {
		Portal portal1 = Create.Portal(3, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(2);


		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);

		player.moveRight();
		// No teleport
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);
	}

	@Test
	void boulderPushedOnPortal() {
		Portal portal1 = Create.Portal(4, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(1);

		Boulder boulder = Create.Boulder(3, 3);

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);

		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);

		assertEquals(boulder.getX(), 4);
		assertEquals(boulder.getY(), 3);
	}

	@Test
	void boulderPushedOnPortalAndPast() {
		Portal portal1 = Create.Portal(4, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(1);

		Boulder boulder = Create.Boulder(3, 3);

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);

		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);

		assertEquals(boulder.getX(), 4);
		assertEquals(boulder.getY(), 3);

		player.moveRight();
		assertEquals(player.getX(), 7);
		assertEquals(player.getY(), 7);

	}

	@Test
	void boulderOnDestinationPortal() {
		Portal portal1 = Create.Portal(3, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(1);

		Boulder boulder = Create.Boulder(7, 7);

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);
		player.moveRight();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);
	}

	@Test
	void TPKill() {
		Portal portal1 = Create.Portal(3, 3);
		portal1.setID(1);

		Portal portal2 = Create.Portal(7, 7);
		portal2.setID(1);

		Enemy enemy = Create.Enemy(7, 7);

		player.moveRight();
		assertFalse(dungeon.getEntities().contains(enemy));
	}
}
