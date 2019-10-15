package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {
	private Dungeon dungeon;

	/**
	 * Create a player positioned in square (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public Player(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		this.entityLevel = EntityLevel.OBJECT;
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
		if (!(getY() < dungeon.getHeight() - 1)) {
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
		if (!(getX() < dungeon.getWidth() - 1)) {
			return;
		}
		int newX = getX() + 1;
		if (isPositionBlocked(newX, getY())) {
			return;
		}
		x().set(newX);
	}
}
