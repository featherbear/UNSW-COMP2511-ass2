package unsw.dungeon.entity;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;

public class Enemy extends MovableEntity implements Interactable{

	public Enemy(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
	}

	private boolean isPositionBlocked(int x, int y) {
		if (this.getDungeon().hasEntitiesAt(entityLevel.OBJECT, x, y)) {
			if (this.getDungeon().whatEntityAt(entityLevel.OBJECT, x, y) != null) {
				Entity e = this.getDungeon().whatEntityAt(entityLevel.OBJECT, x, y);
				if (e instanceof Boulder) {
					return false;
				}
			}
		}
		return this.getDungeon().hasEntitiesAt(EntityLevel.OBJECT, x, y);
	}

	private void move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return;
		}

		if (isPositionBlocked(newX, newY)) {
			return;
		}

		this.setXY(newX, newY);
	}

	public void setXY(int newX, int newY) {
		int oldX = getX();
		int oldY = getY();
		if (!this.getDungeon().positionIsValid(newX, newY)) {
			return;
		}

		if (oldX != newX) {
			x().set(newX);
		}
		if (oldY != newY) {
			y().set(newY);
		}

		this.moveEvent.emit(new LocationChanged(oldX, oldY, newX, newY));

	}

	public void moveUp() {
		move(0, -1);
	}

	public void moveDown() {
		move(0, 1);
	}

	public void moveLeft() {
		move(-1, 0);
	}

	public void moveRight() {
		move(1, 0);
	}
	
	public void kill() {
		this.hide();
		this.getDungeon().removeEntity(this);
	}
	
	
	
	public boolean enemyMoveEventHandler(Player player, LocationChanged event) {
		return player.interact(this);
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			Player p = (Player) entity;
			if (p.hasItemUsable(Sword.class)) {
				List<ItemEntity> inv = p.getInventory();
				for (ItemEntity item : inv) {
					if (item instanceof Sword) {
						((Sword) item).use(this);
					}
				}
			}
		}
		return true;
	}

}
