package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;

class PlayerTest {

	private Dungeon dungeon;
	private Player player;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
	}

	@Test
	void alive() {
		assertTrue(player.isAlive());
		player.kill();
		assertFalse(player.isAlive());
	}

	@Test
	void generalMovement() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.moveLeft();
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 1);

		player.moveUp();
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 0);

		player.moveRight();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 0);

		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

	}

	@Test
	void successfulMoveIntent() {
		player.moveIntent.register((reference, event) -> true);
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
	}

	@Test
	void unsuccessfulMoveIntent() {
		player.moveIntent.register((reference, event) -> false);
		player.moveRight();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
	}

	@Test
	void moveEvent() {
		player.moveEvent.register((reference, event) -> {
			assertEquals(reference, player);
			assertEquals(event.oldX, 1);
			assertEquals(event.oldY, 1);
			assertEquals(event.newX, 2);
			assertEquals(event.newY, 1);
		});

		player.moveRight();
	}

	@Test
	void blockedMovement() {
		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		// Create a player on the right of Player<1,1>
		dungeon.addEntity(new Player(dungeon, 3, 1));

		player.moveRight();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

	}

	@Test
	void outOfBoundsMovement() {
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		player.moveLeft();

		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 1);

		// Coordinates should not change
		player.moveLeft();
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 1);

	}
}
