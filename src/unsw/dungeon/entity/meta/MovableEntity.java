package unsw.dungeon.entity.meta;

import unsw.dungeon.Dungeon;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventEmitter;
import unsw.dungeon.util.emitter.IntentEmitter;

/**
 * Abstract class for entities that move
 *
 * @param <T> Entity type
 */
public abstract class MovableEntity<T> extends Entity {

	public final IntentEmitter<T, LocationChanged> moveIntent;
	public final EventEmitter<T, LocationChanged> moveEvent;

	protected boolean isPositionBlocked(int x, int y) {
		return this.getDungeon().hasEntitiesAt(EntityLevel.OBJECT, x, y);
	}

	@SuppressWarnings("unchecked")
	public MovableEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);

		// Intents are fired before the action is executed
		// Failed intents will cancel the execution of the action
		this.moveIntent = new IntentEmitter<T, LocationChanged>((T) this);

		// Events are fired after the action is executed
		this.moveEvent = new EventEmitter<T, LocationChanged>((T) this);
	}

	private boolean move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return false;
		}

		if (isPositionBlocked(newX, newY)) {
			return false;
		}

		this.setXY(newX, newY);
		return true;
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

	public boolean moveUp() {
		return move(0, -1);
	}

	public boolean moveDown() {
		return move(0, 1);
	}

	public boolean moveLeft() {
		return move(-1, 0);
	}

	public boolean moveRight() {
		return move(1, 0);
	}

}
