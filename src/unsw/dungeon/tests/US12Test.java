package unsw.dungeon.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.ui.DungeonController;
import unsw.dungeon.ui.EntityImagePair;

class US12Test {

	private Dungeon dungeon;
	private Player player;

	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		dungeon.addEntity((player = new Player(dungeon, 1, 1)));
		dungeon.setPlayer(player);
	}

	@Test
	void JavaFXRestartKey() {
		DungeonController controller = new DungeonController(dungeon, new ArrayList<EntityImagePair>());
		controller.restartEvent.register(() -> {
			assertTrue(true);
		});

		controller.handleKeyPress(new KeyEvent(null, null, null, KeyCode.R, false, false, false, false));

	}
}
