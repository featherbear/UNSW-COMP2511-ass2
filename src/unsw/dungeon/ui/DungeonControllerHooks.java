package unsw.dungeon.ui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
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

public class DungeonControllerHooks implements LoaderHook {

	private DungeonControllerLoader loader;
	private Images images;

	public DungeonControllerHooks(DungeonControllerLoader loader, Images images) {
		this.loader = loader;
		this.images = images;
	}

	@Override
	public void onLoad(Player player) {
		ImageView view = new ImageView(images.playerImage);
		loader.addEntity(player, view);
	}

	@Override
	public void onLoad(Enemy enemy) {
		ImageView view = new ImageView(images.enemyImage);
		loader.addEntity(enemy, view);
	}

	@Override
	public void onLoad(Wall wall) {
		ImageView view = new ImageView(images.wallImage);
		loader.addEntity(wall, view);
	}

	@Override
	public void onLoad(Exit exit) {
		ImageView view = new ImageView(images.exitImage);
		loader.addEntity(exit, view);
	}

	@Override
	public void onLoad(Boulder boulder) {
		ImageView view = new ImageView(images.boulderImage);
		loader.addEntity(boulder, view);
	}

	@Override
	public void onLoad(Switch sw) {
		ImageView view = new ImageView(images.switchImage);
		loader.addEntity(sw, view);
	}

	@Override
	public void onLoad(Door door) {
		ImageView view = new ImageView(images.doorClosedImage);

		// Register door opening graphical updates
		door.opened().addListener((observer, oldValue, newValue) -> {
			view.setImage(newValue ? images.doorOpenedImage : images.doorClosedImage);
		});
		loader.addEntity(door, view);
	}

	@Override
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(images.treasureImage);
		loader.addEntity(treasure, view);
	}

	@Override
	public void onLoad(Key key) {
		ImageView view = new ImageView(images.keyImage);
		loader.addEntity(key, view);
	}

	@Override
	public void onLoad(Sword sword) {
		ImageView view = new ImageView(images.swordImage);
		loader.addEntity(sword, view);
	}

	@Override
	public void onLoad(InvincibilityPotion potion) {
		ImageView view = new ImageView(images.invincibilityPotionImage);
		loader.addEntity(potion, view);
	}

	@Override
	public void onLoad(Portal portal) {
		ImageView view = new ImageView(images.portalImage);

		// Register portal activation graphical effect
		ColorAdjust disabledEffect = new ColorAdjust(0, -0.5, -0.8, -0.3);
		portal.activated().addListener((observable, oldValue, newValue) -> {
			view.setEffect(newValue ? null : disabledEffect);
		});

		// Set the effect now
		view.setEffect(portal.getActivated() ? null : disabledEffect);

		loader.addEntity(portal, view);
	}

}
