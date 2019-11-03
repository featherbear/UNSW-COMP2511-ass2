package unsw.dungeon.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.entity.meta.Entity;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 */
public class DungeonControllerLoader extends DungeonLoader {

	private List<EntityImagePair> entities;
	private Images images;

	private DungeonControllerHooks hook;

	public DungeonControllerLoader(String filename) throws FileNotFoundException {
		super(filename);
		this.entities = new ArrayList<>();
		this.images = new Images();

		// Load hooks for the DungeonController
		this.hook = new DungeonControllerHooks(this, this.images);
	}

	/**
	 * Register JavaFX attributes for each entity
	 * 
	 * @param entity
	 * @param view
	 */
	void addEntity(Entity entity, ImageView view) {
		trackPosition(entity, view);
		entities.add(new EntityImagePair(entity, view));
	}

	/**
	 * Set a node in a GridPane to have its position track the position of an entity
	 * in the dungeon.
	 *
	 * By connecting the model with the view in this way, the model requires no
	 * knowledge of the view and changes to the position of entities in the model
	 * will automatically be reflected in the view.
	 * 
	 * @param entity
	 * @param node
	 */
	private void trackPosition(Entity entity, Node node) {
		// Set initial position
		GridPane.setColumnIndex(node, entity.getX());
		GridPane.setRowIndex(node, entity.getY());

		// Subscribe to position changes
		entity.x().addListener((observable, oldValue, newValue) -> GridPane.setColumnIndex(node, newValue.intValue()));
		entity.y().addListener((observable, oldValue, newValue) -> GridPane.setRowIndex(node, newValue.intValue()));

		// Subscribe to visibility changes
		entity.visibility().addListener((observable, oldValue, newValue) -> node.setVisible(newValue.booleanValue()));

	}

	/**
	 * Create a controller that can be attached to the DungeonView with all the
	 * loaded entities.
	 * 
	 * @return
	 */
	public DungeonController loadController() {
		DungeonController controller = new DungeonController(load(this.hook), entities);
		this.entities = new ArrayList<>();
		return controller;
	}
}
