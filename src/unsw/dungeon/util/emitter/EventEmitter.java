package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public class EventEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<EventSAM<BaseType, DataType>, BaseType, DataType> {

	public EventEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {

		ArrayList<EventSAM<BaseType, DataType>> subs = new ArrayList<EventSAM<BaseType, DataType>>();
		for (EventSAM<BaseType, DataType> subscriber : this.subscribers) {
			subs.add(subscriber);
		}

		for (EventSAM<BaseType, DataType> subscriber : subs) {
			subscriber.execute(this.reference, data);
		}
		return true;

	}

}
