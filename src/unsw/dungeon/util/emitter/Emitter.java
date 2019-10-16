package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public abstract class Emitter<SAMType, ReferenceType, DataType extends EmitterData> {
	protected ArrayList<SAMType> subscribers;
	protected ReferenceType reference;

	public Emitter(ReferenceType reference) {
		this.subscribers = new ArrayList<SAMType>();
		this.reference = reference;
	}

	abstract public boolean emit(DataType data);

	public void register(SAMType function) {
		if (this.subscribers.contains(function)) {
			return;
		}
		this.subscribers.add(function);
	}

	public void unregister(SAMType function) {
		this.subscribers.remove(function);
	}

}
