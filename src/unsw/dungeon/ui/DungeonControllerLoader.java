package unsw.dungeon.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.LoaderHook;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Door;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Exit;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Key;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Switch;
import unsw.dungeon.entity.Sword;
import unsw.dungeon.entity.Treasure;
import unsw.dungeon.entity.Wall;
import unsw.dungeon.entity.meta.Entity;


/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader implements LoaderHook {

	private List<EntityImagePair> entities;

	// Images
	private Image playerImage;
	private Image wallImage;
	private Image exitImage;
	private Image doorClosedImage;
	private Image doorOpenedImage;
	private Image treasureImage;
	private Image keyImage;
	private Image swordImage;
	private Image invincibilityPotionImage;
	private Image portalImage;
	private Image switchImage;
	private Image boulderImage;
	private Image enemyImage;
	
	public DungeonControllerLoader(String filename) throws FileNotFoundException {
		super(filename);
		this.entities = new ArrayList<>();
		this.playerImage = new Image("/human_new.png");
		this.enemyImage = new Image("/deep_elf_master_archer.png");
		this.wallImage = new Image("/brick_brown_0.png");
		this.exitImage = new Image("/exit.png");
		this.doorClosedImage = new Image("/closed_door.png");
		this.doorOpenedImage = new Image("/open_door.png");
		this.treasureImage = new Image("/gold_pile.png");
		this.keyImage = new Image("/key.png");
		this.swordImage = new Image("/greatsword_1_new.png");
		this.invincibilityPotionImage = new Image("/brilliant_blue_new.png");
		this.portalImage = new Image("/portal.png");
		this.switchImage = new Image("/pressure_plate.png");
		this.boulderImage = new Image("/boulder.png");

	}

	@Override
	public void onLoad(Player player) {
		ImageView view = new ImageView(playerImage);
		addEntity(player, view);
	}
	
	@Override
	public void onLoad(Enemy enemy) {
		ImageView view = new ImageView(enemyImage);
		addEntity(enemy, view);
	}

	@Override
	public void onLoad(Wall wall) {
		ImageView view = new ImageView(wallImage);
		addEntity(wall, view);
	}

	@Override
	public void onLoad(Exit exit) {
		ImageView view = new ImageView(exitImage);
		addEntity(exit, view);
	}
	
	@Override
	public void onLoad(Boulder boulder) {
		ImageView view = new ImageView(boulderImage);
		addEntity(boulder, view);
	}
	
	@Override
	public void onLoad(Switch sw) {
		ImageView view = new ImageView(switchImage);
		addEntity(sw, view);
	}

	@Override
	public void onLoad(Door door) {
		ImageView view = new ImageView(doorClosedImage);
		door.opened().addListener((observer, oldValue, newValue) -> {
			view.setImage(newValue ? doorOpenedImage : doorClosedImage);
		});
		addEntity(door, view);
	}

	@Override
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(treasureImage);
		addEntity(treasure, view);
	}

	@Override
	public void onLoad(Key key) {
		ImageView view = new ImageView(keyImage);
		addEntity(key, view);
	}

	@Override
	public void onLoad(Sword sword) {
		ImageView view = new ImageView(swordImage);
		addEntity(sword, view);
	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		ImageView view = new ImageView(invincibilityPotionImage);
		addEntity(potion, view);
	}

	@Override
	public void onLoad(Portal portal) {
		ImageView view = new ImageView(portalImage);

		ColorAdjust disabledEffect = new ColorAdjust(0, -0.5, -0.8, -0.3);

		portal.activated().addListener((observable, oldValue, newValue) -> {
			view.setEffect(newValue ? null : disabledEffect);
		});

		// Set the effect now
		view.setEffect(portal.getActivated() ? null : disabledEffect);

		addEntity(portal, view);
	}

	private void addEntity(Entity entity, ImageView view) {
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
		GridPane.setColumnIndex(node, entity.getX());
		GridPane.setRowIndex(node, entity.getY());

		entity.visibility().addListener((observable, oldValue, newValue) -> {
			node.setVisible(newValue.booleanValue());
		});

		entity.x().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GridPane.setColumnIndex(node, newValue.intValue());

			}
		});
		entity.y().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GridPane.setRowIndex(node, newValue.intValue());
			}
		});
	}

	/**
	 * Create a controller that can be attached to the DungeonView with all the
	 * loaded entities.
	 * 
	 * @return
	 */
	public DungeonController loadController() {
		DungeonController controller = new DungeonController(load(this), entities);
		this.entities = new ArrayList<>();
		return controller;
	}

}
