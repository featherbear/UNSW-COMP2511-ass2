package unsw.dungeon.util.emitter;

public class IntentEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<Intent<BaseType, DataType>, BaseType, DataType> {

	public IntentEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {
		for (Intent<BaseType, DataType> subscriber : this.subscribers) {
			if (!subscriber.execute(this.reference, data)) {
				return false;
			}
		}
		return true;
	}

}
