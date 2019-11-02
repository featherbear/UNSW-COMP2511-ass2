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
import unsw.dungeon.util.emitter.GenericSAM;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

	@FXML
	private GridPane squares;

	private List<EntityImagePair> entities;

	private Player player;

	private Dungeon dungeon;

	private GenericSAM onRestart;

	public DungeonController(Dungeon dungeon, List<EntityImagePair> entities) {
		this.dungeon = dungeon;
		this.player = dungeon.getPlayer();
		this.player.alive().addListener((observable, oldValue, newValue) -> {
			if (newValue == false) {
				this.restart();
			}
		});
		this.entities = new ArrayList<EntityImagePair>(entities);
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

		ObservableList<Node> children = squares.getChildren();
		entities.sort((e, f) -> f.getEntity().getEntityLevel().ordinal() - e.getEntity().getEntityLevel().ordinal());

		for (EntityImagePair entity : entities) {
			children.add(entity.getImageView());
		}
	}

	@FXML
	public void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
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
		case R:
			this.restart();
			break;
		default:
			break;
		}
	}

	public void restart() {
		if (this.onRestart != null) {
			this.onRestart.execute();
		}
	}

	public void onRestart(GenericSAM event) {
		this.onRestart = event;
	}

}
