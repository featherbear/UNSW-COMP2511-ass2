package unsw.dungeon.events;

import unsw.dungeon.entity.Switch;
import unsw.dungeon.util.emitter.EmitterData;

public class SwitchToggled extends EmitterData {
	public final Switch switchObj;

	public SwitchToggled(Switch switchObj) {
		this.switchObj = switchObj;
	}
}
