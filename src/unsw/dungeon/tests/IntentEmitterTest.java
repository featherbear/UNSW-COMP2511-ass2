package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.util.emitter.EmitterData;
import unsw.dungeon.util.emitter.IntentEmitter;
import unsw.dungeon.util.emitter.IntentSAM;

class DummyIntent extends EmitterData {

}

public class IntentEmitterTest {
	IntentEmitter<IntentEmitterTest, DummyIntent> emitter;
	IntentSAM<IntentEmitterTest, DummyIntent> retFalse;
	IntentSAM<IntentEmitterTest, DummyIntent> retTrue;

	DummyIntent event = new DummyIntent();

	int counter;

	@BeforeEach
	void init() {
		emitter = new IntentEmitter<IntentEmitterTest, DummyIntent>(this);

		retFalse = (a, b) -> {
			counter++;
			return false;
		};

		retTrue = (a, b) -> {
			counter++;
			return true;
		};

		counter = 0;

	}

	@Test
	void basicRunFalse() {
		assertEquals(0, counter);
		emitter.register(retFalse);
		assertFalse(emitter.emit(event));
		assertEquals(1, counter);
	}

	@Test
	void basicRunTrue() {
		assertEquals(0, counter);
		emitter.register(retTrue);
		assertTrue(emitter.emit(event));
		assertEquals(1, counter);
	}

	@Test
	void registerOnceFalse() {
		emitter.register(retFalse);
		emitter.register(retFalse);
		assertFalse(emitter.emit(event));
		assertEquals(1, counter);
	}

	@Test
	void registerOnceTrue() {
		emitter.register(retTrue);
		emitter.register(retTrue);
		assertTrue(emitter.emit(event));
		assertEquals(1, counter);
	}

	@Test
	void multipleSubscribersFalseTrue() {
		emitter.register(retFalse);
		emitter.register(retTrue);
		assertFalse(emitter.emit(event));
		assertEquals(1, counter);
	}

	@Test
	void multipleSubscribersTrueFalse() {
		emitter.register(retTrue);
		emitter.register(retFalse);
		assertFalse(emitter.emit(event));
		assertEquals(2, counter);
	}

	@Test
	void unregister() {
		multipleSubscribersTrueFalse();

		emitter.unregister(retFalse);
		assertTrue(emitter.emit(event));
		assertEquals(3, counter);

		emitter.unregister(retTrue);
		assertTrue(emitter.emit(event));
		assertEquals(3, counter);

	}
}
