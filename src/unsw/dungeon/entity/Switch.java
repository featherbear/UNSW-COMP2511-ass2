package unsw.dungeon.entity;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.events.SwitchToggled;
import unsw.dungeon.util.emitter.EventEmitter;

public class Switch extends Entity {

	private boolean activated;
	public final EventEmitter<Switch, SwitchToggled> switchEvent;
	private int id;

	public Switch(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.activated = false;
		this.switchEvent = new EventEmitter<Switch, SwitchToggled>(this);
		this.id = -1;
	}

	public boolean getActivated() {
		return this.activated;
	}

	public void activate() {
		this.setActivated(true);
	}

	public void deactivate() {
		this.setActivated(false);
	}

	private void setActivated(boolean activated) {
		boolean notify = this.activated != activated;

		this.activated = activated;

		if (notify) {
			this.switchEvent.emit(new SwitchToggled(this));
		}
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public void boulderMoveEventHandler(Boulder boulder, LocationChanged event) {
		checkBoulder();
	}

	public void checkBoulder() {
		Entity e = this.getDungeon().getEntityAt(EntityLevel.OBJECT, this.getX(), this.getY());
		this.setActivated(e instanceof Boulder);
	}

	public static ArrayList<Switch> filter(ArrayList<Entity> entities, int id) {
		ArrayList<Switch> result = new ArrayList<Switch>();
		for (Switch switchEntity : Entity.filter(entities, Switch.class)) {
			if (switchEntity.getID() == id) {
				result.add(switchEntity);

			}
		}

		return result;
	}
}
