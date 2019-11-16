package unsw.dungeon.ui;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
		ImageView view = applyColourShift(new ImageView(images.switchImage), sw.getID());
		loader.addEntity(sw, view);
	}

	@Override
	public void onLoad(Door door) {

		ImageView view = applyColourShift(new ImageView(images.doorClosedImage), door.getID());

		// Register door opening graphical updates
		door.opened().addListener((observer, oldValue, newValue) -> {
			Image newImage = newValue ? images.doorOpenedImage : images.doorClosedImage;
			view.setImage(newImage);

			ImageView clip = ((ImageView) view.getClip());
			if (clip != null) {
				clip.setImage(newImage);
			}

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
		ImageView view = applyColourShift(new ImageView(images.keyImage), key.getID());
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
		ImageView view = applyColourShift(new ImageView(images.portalImage), portal.getID());

		Effect activatedEffect = view.getEffect();

		// Register portal activation graphical effect
		ColorAdjust disabledEffect = new ColorAdjust(0, -0.5, -0.8, -0.3);
		disabledEffect.setInput(activatedEffect);

		portal.activated().addListener((observable, oldValue, newValue) -> {
			view.setEffect(newValue ? activatedEffect : disabledEffect);
		});

		// Set the effect now
		view.setEffect(portal.getActivated() ? activatedEffect : disabledEffect);

		loader.addEntity(portal, view);
	}

	private ImageView applyColourShift(ImageView imageView, int i) {
		// Don't apply for i = 1 ... because... because.
		if (i == 1) {
			return imageView;
		}

		ImageView view = new ImageView(imageView.getImage());

		view.setClip(imageView);

		// Effect code from https://stackoverflow.com/a/18124868
		ColorAdjust monochromeBase = new ColorAdjust();
		monochromeBase.setSaturation(-.8);
		Blend keyColour = new Blend(BlendMode.MULTIPLY, monochromeBase,
				new ColorInput(0, 0, view.getImage().getWidth(), view.getImage().getHeight(), ColorFromID(i)));

		view.setEffect(keyColour);

		return view;
	}

	private Color ColorFromID(int id) {
		// https://graphicdesign.stackexchange.com/a/3815
		String[] hexCodes = { "#023FA5", "#7D87B9", "#BEC1D4", "#D6BCC0", "#BB7784", "#FFFFFF", "#4A6FE3", "#8595E1",
				"#B5BBE3", "#E6AFB9", "#E07B91", "#D33F6A", "#11C638", "#8DD593", "#C6DEC7", "#EAD3C6", "#F0B98D",
				"#EF9708", "#0FCFC0", "#9CDED6", "#D5EAE7", "#F3E1EB", "#F6C4E1", "#F79CD4" };

		return Color.web(hexCodes[(id - 1 + hexCodes.length) % hexCodes.length]);
	}
}
