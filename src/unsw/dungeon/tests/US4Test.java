package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Switch;

public class US4Test {

	private Boulder boulder;
	private Player player;
	private Dungeon dungeon;
	private Switch sw;
	private TestUtils create;
	
	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		create = new TestUtils(dungeon);
		dungeon.setPlayer(player);
	}
	
	@Test
	void simpleSwitch() {
		boulder = create.Boulder(1, 2);
		sw = create.Switch(1, 3); 
		assertEquals(false, sw.getActivated());
		player.moveDown();
		assertEquals(3, boulder.getY());
		assertEquals(true, sw.getActivated());
		player.moveDown();
		assertEquals(false, sw.getActivated());
	}
}
