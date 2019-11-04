package unsw.dungeon.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.util.emitter.GenericEmitter;
import unsw.dungeon.util.emitter.GenericSAM;

public class EmitterTest {
	GenericEmitter emitter;
	GenericSAM func;
	GenericSAM func2;

	int counter;

	@BeforeEach
	void init() {
		emitter = new GenericEmitter();

		func = () -> {
			counter++;
		};

		func2 = new GenericSAM() {
			@Override
			public void execute() {
				counter++;
			}
		};

		counter = 0;

	}

	@Test
	void basicRun() {
		assertEquals(0, counter);
		emitter.register(func);
		emitter.emit();
		assertEquals(1, counter);
	}

	@Test
	void registerOnce() {
		emitter.register(func);
		emitter.register(func);
		emitter.emit();
		assertEquals(1, counter);
	}

	@Test
	void multipleSubscribers() {
		emitter.register(func);
		emitter.register(func2);
		emitter.emit();
		assertEquals(2, counter);
	}

	@Test
	void unregister() {
		multipleSubscribers();
		emitter.unregister(func);
		emitter.emit();
		assertEquals(3, counter);

		emitter.unregister(func2);
		emitter.emit();
		assertEquals(3, counter);

	}
}
