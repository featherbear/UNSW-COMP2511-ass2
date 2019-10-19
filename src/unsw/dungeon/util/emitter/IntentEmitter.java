package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public class IntentEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<Intent<BaseType, DataType>, BaseType, DataType> {

	public IntentEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {
		ArrayList<Intent<BaseType, DataType>> localSubcribers = new ArrayList<Intent<BaseType, DataType>>();
		for (Intent<BaseType, DataType> subscriber : this.subscribers) {
			localSubcribers.add(subscriber);
		}

		for (Intent<BaseType, DataType> subscriber : localSubcribers) {
			if (!subscriber.execute(this.reference, data)) {
				return false;
			}
		}
		return true;
	}

}
