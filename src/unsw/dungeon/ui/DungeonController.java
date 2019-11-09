package unsw.dungeon.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Player;
import unsw.dungeon.util.emitter.GenericEmitter;

/**
 * A JavaFX controller for the dungeon.
 * 
 */
public class DungeonController {

	@FXML
	private GridPane squares;

	private List<EntityImagePair> entities;

	private Player player;

	private Dungeon dungeon;

	public final GenericEmitter restartEvent;
	public final GenericEmitter playerDeadEvent;

	public DungeonController(Dungeon dungeon, List<EntityImagePair> entities) {
		this.dungeon = dungeon;
		this.player = dungeon.getPlayer();
		this.entities = new ArrayList<EntityImagePair>(entities);
		this.restartEvent = new GenericEmitter();
		this.playerDeadEvent = new GenericEmitter();

		// When the player dies, call the restart event
		this.player.alive().addListener((observable, oldValue, newValue) -> {
			if (newValue == false) {
				this.playerDeadEvent.emit();
			}
		});
	}

	@FXML
	public void initialize() {
		Image ground = new Image("/dirt_0_new.png");

		// Add the ground first so it is below all other entities
		for (int x = 0; x < dungeon.getWidth(); x++) {
			for (int y = 0; y < dungeon.getHeight(); y++) {
				squares.add(new ImageView(ground), x, y);
			}
		}

		// Sort entities by their EntityLevel order
		ObservableList<Node> children = squares.getChildren();
		entities.sort((e, f) -> f.entity.getEntityLevel().ordinal() - e.entity.getEntityLevel().ordinal());

		// Add items to the GridPane
		for (EntityImagePair entity : entities) {
			children.add(entity.imageView);
		}
	}

	@FXML
	public void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
		/*
		 * PLAYER MOVEMENT
		 */
		case UP:
			player.moveUp();
			break;
		case DOWN:
			player.moveDown();
			break;
		case LEFT:
			player.moveLeft();
			break;
		case RIGHT:
			player.moveRight();
			break;
		/*
		 * UTILITIES
		 */
		case R:
			this.restart();
			break;
		default:
			break;
		}
	}

	/**
	 * Call the restart event
	 */
	public void restart() {
		this.restartEvent.emit();
	}

	/**
	 * @return Dungeon instance
	 */
	public Dungeon getDungeon() {
		return this.dungeon;
	}

	public List<EntityImagePair> getEntityImagePairs() {
		return this.entities;
	}
}
