package unsw.dungeon.ui;

import javafx.scene.image.ImageView;
import unsw.dungeon.entity.meta.Entity;

/**
 * Class to represent an Entity and ImageView object
 */
public class EntityImagePair {
	public Entity entity;
	public ImageView imageView;

	public EntityImagePair(Entity entity, ImageView imageView) {
		this.entity = entity;
		this.imageView = imageView;
	}
}
