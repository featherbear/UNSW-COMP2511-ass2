package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public class IntentEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<IntentSAM<BaseType, DataType>, BaseType, DataType> {

	public IntentEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {
		ArrayList<IntentSAM<BaseType, DataType>> localSubcribers = new ArrayList<IntentSAM<BaseType, DataType>>();
		for (IntentSAM<BaseType, DataType> subscriber : this.subscribers) {
			localSubcribers.add(subscriber);
		}

		for (IntentSAM<BaseType, DataType> subscriber : localSubcribers) {
			if (!subscriber.execute(this.reference, data)) {
				return false;
			}
		}
		return true;
	}
}
