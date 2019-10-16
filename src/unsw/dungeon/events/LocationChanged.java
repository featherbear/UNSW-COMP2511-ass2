package unsw.dungeon.events;

import unsw.dungeon.util.emitter.EmitterData;

public class LocationChanged extends EmitterData {
	public final int oldX;
	public final int oldY;
	public final int newX;
	public final int newY;

	public LocationChanged(int oldX, int oldY, int newX, int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
}
