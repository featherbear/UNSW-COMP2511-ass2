package unsw.dungeon.util.emitter;

import java.util.ArrayList;

/**
 * Scoped Publish/Subscribe Emitter Implementation
 *
 * @param <SAMType>
 * @param <ReferenceType>
 * @param <DataType>
 */
public abstract class Emitter<SAMType, ReferenceType, DataType extends EmitterData> {
	protected ArrayList<SAMType> subscribers;
	protected ReferenceType reference;

	/**
	 * Create the Emitter.
	 * 
	 * @param reference - the object that can be accessed as the first argument by
	 *                  every subscriber function
	 */
	public Emitter(ReferenceType reference) {
		this.subscribers = new ArrayList<SAMType>();
		this.reference = reference;
	}

	abstract public boolean emit(DataType data);

	/**
	 * Add a new subscriber
	 * 
	 * @param <SAMType> SAM
	 */
	public void register(SAMType function) {
		if (this.subscribers.contains(function)) {
			return;
		}
		this.subscribers.add(function);
	}

	/**
	 * Remove a subscriber
	 * 
	 * @param <SAMType> SAM
	 */
	public void unregister(SAMType function) {
		this.subscribers.remove(function);
	}

}
