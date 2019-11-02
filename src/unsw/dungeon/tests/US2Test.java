package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Wall;
import unsw.dungeon.ui.DungeonController;
import unsw.dungeon.ui.EntityImagePair;

class US2Test {

	private Dungeon dungeon;
	private Player player;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
	}

	@Test
	void moveAir() {
		player.moveRight();
		player.moveDown();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
	}

	@Test
	void moveWall() {
		dungeon.addEntity(new Wall(dungeon, 2, 1));
		player.moveRight();
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
	}

	@Test
	void moveEnemy() {
		dungeon.addEntity(new Enemy(dungeon, 2, 1));
		player.moveRight();
		player.moveDown();
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 2);
	}

	@Test
	void keyPress() {
		DungeonController controller = new DungeonController(dungeon, new ArrayList<EntityImagePair>());

		// Player should move right
		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.RIGHT, false, false, false, false));
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		// Player should move down
		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.DOWN, false, false, false, false));
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);

		// Player should move up
		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.UP, false, false, false, false));
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);

		// Player should move left
		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.LEFT, false, false, false, false));
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

		// Nothing should happen for the SPACE button
		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.SPACE, false, false, false, false));
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);

	}
}
