package unsw.dungeon.ui;

import javafx.scene.image.ImageView;
import unsw.dungeon.entity.meta.Entity;

public class EntityImagePair {
	private Entity entity;
	private ImageView imageView;

	public EntityImagePair(Entity entity, ImageView imageView) {
		this.entity = entity;
		this.imageView = imageView;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public ImageView getImageView() {
		return this.imageView;
	}
}
