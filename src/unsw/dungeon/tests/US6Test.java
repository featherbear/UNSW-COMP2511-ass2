package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;

class US6Test {

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
	void manualUnlockDoor() {
		Door door = Create.Door(2, 1);
		assertFalse(door.getOpen());
		door.open();
		assertTrue(door.getOpen());
	}

	@Test
	void playerMoveDoorNoKey() {
		Door door = Create.Door(2, 1);

		player.moveRight();
		assertFalse(door.getOpen());
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
	}

	@Test
	void playerMoveDoorWrongKey() {
		Door door = Create.Door(3, 1);
		door.setID(1);

		Key key = Create.Key(2, 1);
		key.setID(2);

		assertTrue(key.getVisibility());

		// Pick up
		player.moveRight();

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertTrue(player.hasItem(Key.class));
		assertTrue(player.hasItemUsable(Key.class));

		assertFalse(key.getVisibility());

		player.moveRight();
		assertFalse(door.getOpen());
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
	}

	@Test
	void playerMoveDoorRightKey() {
		Door door = Create.Door(3, 1);
		door.setID(1);

		Key key = Create.Key(2, 1);
		key.setID(1);

		// Pick up
		player.moveRight();

		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		assertEquals(key.getUses(), 1);
		assertTrue(player.hasItem(Key.class));
		assertTrue(player.hasItemUsable(Key.class));

		// Open door
		player.moveRight();
		assertTrue(door.getOpen());
		assertEquals(key.getUses(), 0);

		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);

	}

	@Test
	void keyUsedRemoveFromInventory() {
		Door door = Create.Door(2, 2);
		door.setID(1);

		Key key = Create.Key(3, 3);
		key.setID(1);

		assertFalse(player.getInventory().contains(key));
		player.pickUp(key);
		assertTrue(player.getInventory().contains(key));

		player.interact(door);
		assertTrue(door.getOpen());
		assertFalse(player.getInventory().contains(key));

	}

	@Test
	void keyCarryMaxOne() {
		Key key1 = Create.Key(2, 1);
		key1.setID(1);

		Key key2 = Create.Key(3, 1);
		key2.setID(2);

		player.moveRight();
		assertTrue(player.getInventory().contains(key1));

		player.moveRight();
		assertFalse(player.getInventory().contains(key2));

	}

	@Test
	void keyRemovedOnPickup() {
		Key key = Create.Key(2, 1);
		assertTrue(key.getVisibility());
		player.pickUp(key);
		assertFalse(key.getVisibility());
	}

}
