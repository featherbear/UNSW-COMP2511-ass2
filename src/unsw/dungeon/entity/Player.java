package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.util.SAM;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends MovableEntity<Player> {

	/**
	 * Create a player positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Player(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
	}

	private boolean isPositionBlocked(int x, int y) {
		return this.getDungeon().hasEntitiesAt(EntityLevel.OBJECT, x, y);
	}

	private void move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();
			return;
		}
		if (yDirection == 1 && !(getY() < this.dungeon.getHeight() - 1)) {
			return;
		}

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;
		}

		if (!this.getDungeon().positionIsValid(newX, newY)) {
			return;
		}

		int curX = getX();
		int curY = getY();
		int newX = curX + xDirection;
		int newY = curY + yDirection;

		if (!this.checkMoveIntent(newX, newY)) {
			return;
		}

		if (isPositionBlocked(newX, newY)) {
			return;
		}

		if (oldX != newX) {
			x().set(newX);
		}
		if (oldY != newY) {
			y().set(newY);
		}

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

	@Override
	public boolean checkMoveIntent(int newX, int newY) {
		for (SAM<Player> function : this.moveIntentChecks) {
			if (!function.check(this, newX, newY)) {
				return false;
			}
		}
		return true;
	}

}
