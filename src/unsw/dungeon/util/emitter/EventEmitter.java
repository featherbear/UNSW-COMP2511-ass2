package unsw.dungeon.util.emitter;

public class EventEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<Event<BaseType, DataType>, DataType, BaseType> {

	public EventEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {
		for (Event<BaseType, DataType> subscriber : this.subscribers) {
			subscriber.execute(this.reference, data);
		}
		return true;

	}

}
