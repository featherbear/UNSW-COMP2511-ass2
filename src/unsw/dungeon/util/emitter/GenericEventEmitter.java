package unsw.dungeon.util.emitter;

import java.util.ArrayList;

public class GenericEventEmitter {
	protected ArrayList<GenericSAM> subscribers;

	public GenericEventEmitter() {
		this.subscribers = new ArrayList<GenericSAM>();
	}

	public void emit() {
		ArrayList<GenericSAM> subs = new ArrayList<GenericSAM>();
		for (GenericSAM subscriber : this.subscribers) {
			subs.add(subscriber);
		}

		for (GenericSAM subscriber : subs) {
			subscriber.execute();
		}
	}

	public void register(GenericSAM function) {
		if (this.subscribers.contains(function)) {
			return;
		}
		this.subscribers.add(function);
	}

	public void unregister(GenericSAM function) {
		this.subscribers.remove(function);
	}

}
