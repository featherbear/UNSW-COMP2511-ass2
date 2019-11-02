package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;

public class Boulder extends MovableEntity<Boulder> implements Interactable {

	public Boulder(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
	}

	/**
	 * Attempt to move the Boulder by the given offsets
	 * 
	 * @param xDirection
	 * @param yDirection
	 * @return result
	 */
	private boolean move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return false;
		}

		// If there is an entity blocking the path...
		if (isPositionBlocked(newX, newY)) {
			Entity obstruction = this.getDungeon().getEntityAt(EntityLevel.OBJECT, newX, newY);

			// Kill the enemy if they are in the way
			if (obstruction instanceof Enemy) {
				((Enemy) obstruction).kill();
			} else {
				// Otherwise, prevent movement
				return false;
			}
		}

		this.setXY(newX, newY);
		return true;
	}

	/**
	 * Set the X and Y positions of the Boulder
	 * 
	 * @param newX
	 * @param newY
	 */
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

	/**
	 * Move the boulder up
	 */
	public void moveUp() {
		move(0, -1);
	}

	/**
	 * Move the boulder down
	 */
	public void moveDown() {
		move(0, 1);
	}

	/**
	 * Move the boulder left
	 */
	public void moveLeft() {
		move(-1, 0);
	}

	/**
	 * Move the boulder right
	 */
	public void moveRight() {
		move(1, 0);
	}

	/**
	 * Interact with the Player
	 */
	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}

		return move(this.getX() - ((Player) entity).getX(), this.getY() - ((Player) entity).getY());
	}

	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}
		return player.interact(this);
	}

}
