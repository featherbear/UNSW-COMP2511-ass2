package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

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
		return this.dungeon.hasEntitiesAt(EntityLevel.OBJECT, x, y);
	}

	public void moveUp() {
		if (!(getY() > 0)) {
			return;
		}
		int newY = getY() - 1;
		if (isPositionBlocked(getX(), newY)) {
			return;
		}
		y().set(newY);
	}

	public void moveDown() {
		if (!(getY() < this.dungeon.getHeight() - 1)) {
			return;
		}
		int newY = getY() + 1;
		if (isPositionBlocked(getX(), newY)) {
			return;
		}
		y().set(newY);
	}

	public void moveLeft() {
		if (!(getX() > 0)) {
			return;
		}
		int newX = getX() - 1;
		if (isPositionBlocked(newX, getY())) {
			return;
		}
		x().set(newX);
	}

	public void moveRight() {
		if (!(getX() < this.dungeon.getWidth() - 1)) {
			return;
		}
		int newX = getX() + 1;
		if (isPositionBlocked(newX, getY())) {
			return;
		}
		x().set(newX);
	}
}
