package unsw.dungeon.util.emitter;

import java.util.ArrayList;

/**
 * Returns true if all registered checks pass.
 * 
 * Similar to an Array.every() function in JavaScript or similar
 * 
 *
 * @param <BaseType>
 * @param <DataType>
 */
public class IntentEmitter<BaseType, DataType extends EmitterData>
		extends Emitter<IntentSAM<BaseType, DataType>, BaseType, DataType> {

	public IntentEmitter(BaseType reference) {
		super(reference);
	}

	@Override
	public boolean emit(DataType data) {
		// Create a copy of subscribers, to allow for subscribers to unregister
		// themselves
		ArrayList<IntentSAM<BaseType, DataType>> localSubcribers = new ArrayList<IntentSAM<BaseType, DataType>>();
		for (IntentSAM<BaseType, DataType> subscriber : this.subscribers) {
			localSubcribers.add(subscriber);
		}

		// Execute all subscribers, return false if any subscribers fail
		for (IntentSAM<BaseType, DataType> subscriber : localSubcribers) {
			if (!subscriber.execute(this.reference, data)) {
				return false;
			}
		}

		// Return with a pass
		return true;
	}
}
