package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Saw;
import unsw.dungeon.entity.Wall;

public class X1Test {
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
		Wall wall = Create.Wall(1, 1);
		saw = Create.saw(2, 1, "H");
		assertEquals(false, saw.moveLeft());
	}
}
