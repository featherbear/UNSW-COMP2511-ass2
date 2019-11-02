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

	@SuppressWarnings("unchecked")
	public MovableEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);

		// Intents are fired before the action is executed
		// Failed intents will cancel the execution of the action
		this.moveIntent = new IntentEmitter<T, LocationChanged>((T) this);

		// Events are fired after the action is executed
		this.moveEvent = new EventEmitter<T, LocationChanged>((T) this);
	}

}
