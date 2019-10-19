package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public class EventEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<Event<BaseType, DataType>, BaseType, DataType> {

	public EventEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {

		ArrayList<Event<BaseType, DataType>> subs = new ArrayList<Event<BaseType, DataType>>();
		for (Event<BaseType, DataType> subscriber : this.subscribers) {
			subs.add(subscriber);
		}

		for (Event<BaseType, DataType> subscriber : subs) {
			subscriber.execute(this.reference, data);
		}
		return true;

	}

}
