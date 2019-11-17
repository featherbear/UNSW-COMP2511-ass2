package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Saw;
import unsw.dungeon.entity.Wall;
import unsw.dungeon.events.LocationChanged;

public class US19Test {
	private Player player;
	private Dungeon dungeon;
	private TestUtils Create;
	private Saw saw;
	
	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
		Create = new TestUtils(dungeon);
	}

	@Test
	void playerCollideSaw() {
		saw = Create.saw(1, 2, "V");
		player.moveDown();
		assertEquals(player.alive().get(), false);
	}
	
	@Test
	void playerInvincibleCollideSaw() {
		// Now test if player is invincible
		saw = Create.saw(1, 2, "V");
		player.getInventory().add(Create.InvincibilityPotion(2,1));
		player.moveDown();
		assertEquals(player.alive().get(), true);
	}
	
	@Test
	void sawCollideWall() {
		Wall wall = Create.Wall(2, 1);
		saw = Create.saw(2, 2, "V");
		Create.PostLoad();
		assertFalse(saw.moveUp());
		assertTrue(saw.moveDown());
	}
	
	@Test
	void sawCollideBoulder() {
		Boulder boulder = Create.Boulder(2, 1);
		saw = Create.saw(2, 2, "V");
		assertFalse(saw.moveUp());
		assertTrue(saw.moveDown());
	}
	
	@Test
	void sawCollideEnemy() {
		Enemy enemy = Create.Enemy(2, 1);
		saw = Create.saw(2, 2, "V");
		assertTrue(enemy.isAlive());
		assertTrue(saw.moveUp());
		assertFalse(enemy.isAlive());
		assertEquals(2, saw.getX());
		assertEquals(1, saw.getY());
	}
	
	@Test
	void sawCollideSaw() {
		saw = Create.saw(2, 2, "V");
		Saw saw2 = Create.saw(2, 1, "H");
		assertTrue(saw.moveUp());
		assertEquals(2, saw.getX());
		assertEquals(1, saw.getY());
		assertEquals(2, saw2.getX());
		assertEquals(1, saw2.getY());
	}
	
	@Test
	void sawBounceOffWall() {
		saw = Create.saw(5,5, "H");
		Create.Wall(1, 5);
		Create.Wall(6, 5);
		LocationChanged event = null;
		saw.playerMoveEventHandler(player, event);
		assertEquals(4, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(3, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(2, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(3, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(4, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(5, saw.getX());
		assertEquals(5, saw.getY());
		
		saw.playerMoveEventHandler(player, event);
		assertEquals(4, saw.getX());
		assertEquals(5, saw.getY());
	}
	
}
