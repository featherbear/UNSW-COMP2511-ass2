package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.util.emitter.EmitterData;
import unsw.dungeon.util.emitter.EventEmitter;
import unsw.dungeon.util.emitter.EventSAM;

class DummyEvent extends EmitterData {

}

public class EventEmitterTest {
	EventEmitter<EventEmitterTest, DummyEvent> emitter;
	EventSAM<EventEmitterTest, DummyEvent> func;
	EventSAM<EventEmitterTest, DummyEvent> func2;

	DummyEvent event = new DummyEvent();

	int counter;

	@BeforeEach
	void init() {
		emitter = new EventEmitter<EventEmitterTest, DummyEvent>(this);

		func = (a, b) -> {
			counter++;
		};

		func2 = new EventSAM<EventEmitterTest, DummyEvent>() {
			@Override
			public void execute(EventEmitterTest obj, DummyEvent data) {
				counter++;
			}
		};

		counter = 0;

	}

	@Test
	void basicRun() {
		assertEquals(0, counter);
		emitter.register(func);
		emitter.emit(event);
		assertEquals(1, counter);
	}

	@Test
	void registerOnce() {
		emitter.register(func);
		emitter.register(func);
		emitter.emit(event);
		assertEquals(1, counter);
	}

	@Test
	void multipleSubscribers() {
		emitter.register(func);
		emitter.register(func2);
		emitter.emit(event);
		assertEquals(2, counter);
	}

	@Test
	void unregister() {
		multipleSubscribers();
		emitter.unregister(func);
		emitter.emit(event);
		assertEquals(3, counter);

		emitter.unregister(func2);
		emitter.emit(event);
		assertEquals(3, counter);

	}
}
