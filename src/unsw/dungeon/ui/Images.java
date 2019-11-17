package unsw.dungeon.ui;

import javafx.scene.image.Image;

/**
 * Class to hold the images
 */
public class Images {
	public final Image playerImage;
	public final Image wallImage;
	public final Image exitImage;
	public final Image doorClosedImage;
	public final Image doorOpenedImage;
	public final Image treasureImage;
	public final Image keyImage;
	public final Image swordImage;
	public final Image invincibilityPotionImage;
	public final Image portalImage;
	public final Image switchImage;
	public final Image boulderImage;
	public final Image enemyImage;
	public final Image sawImage;

	public Images() {
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
		this.sawImage = new Image("/saw_blade.png");
	}
}
