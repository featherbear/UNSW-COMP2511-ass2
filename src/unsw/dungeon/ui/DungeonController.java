package unsw.dungeon.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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

	public DungeonController(Dungeon dungeon, List<EntityImagePair> entities) {
		this.dungeon = dungeon;
		this.player = dungeon.getPlayer();
		this.entities = new ArrayList<EntityImagePair>(entities);
		this.restartEvent = new GenericEmitter();
	}

	@FXML
	public void initialize() {
		squares.setBackground(new Background(new BackgroundImage(new Image("dirt_0_new.png"), BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

		// Sort entities by their EntityLevel order
		entities.sort((e, f) -> f.entity.getEntityLevel().ordinal() - e.entity.getEntityLevel().ordinal());
		ObservableList<Node> children = squares.getChildren();

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

	/**
	 * @return Entity image pairs
	 */
	public List<EntityImagePair> getEntityImagePairs() {
		return this.entities;
	}
}
