package unsw.dungeon.util.emitter;

import java.util.ArrayList;

/**
 * Publish/Subscribe Emitter Implementation
 * 
 * Does not pass any references or scopes
 *
 */
public class GenericEmitter {
	protected ArrayList<GenericSAM> subscribers;

	/**
	 * Create the GenericEmitter
	 */
	public GenericEmitter() {
		this.subscribers = new ArrayList<GenericSAM>();
	}

	/**
	 * Execute each subscriber
	 */
	public void emit() {
		// Create a copy of subscribers, to allow for subscribers to unregister
		// themselves
		ArrayList<GenericSAM> subs = new ArrayList<GenericSAM>();
		for (GenericSAM subscriber : this.subscribers) {
			subs.add(subscriber);
		}

		// Execute all subscribers
		for (GenericSAM subscriber : subs) {
			subscriber.execute();
		}
	}

	/**
	 * Add a new subscriber
	 * 
	 * @param <GenericSAM> function
	 */
	public void register(GenericSAM function) {
		if (this.subscribers.contains(function)) {
			return;
		}
		this.subscribers.add(function);
	}

	/**
	 * Remove a subscriber
	 * 
	 * @param <GenericSAM> function
	 */
	public void unregister(GenericSAM function) {
		this.subscribers.remove(function);
	}

}
