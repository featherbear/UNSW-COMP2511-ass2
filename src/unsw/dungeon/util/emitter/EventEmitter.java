package unsw.dungeon.util.emitter;

import java.util.ArrayList;

/**
 * Implementation of Emitter to pass data objects to subscribers for events
 *
 * @param <BaseType>
 * @param <DataType>
 */
public class EventEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<EventSAM<BaseType, DataType>, BaseType, DataType> {

	public EventEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {

		// Create a copy of subscribers, to allow for subscribers to unregister
		// themselves
		ArrayList<EventSAM<BaseType, DataType>> subs = new ArrayList<EventSAM<BaseType, DataType>>();
		for (EventSAM<BaseType, DataType> subscriber : this.subscribers) {
			subs.add(subscriber);
		}

		// Execute all subscribers
		for (EventSAM<BaseType, DataType> subscriber : subs) {
			subscriber.execute(this.reference, data);
		}

		// Always pass
		// < Could be a void, but then we won't be able to extend Emitter >
		return true;

	}

}
