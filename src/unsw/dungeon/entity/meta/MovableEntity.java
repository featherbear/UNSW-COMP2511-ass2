package unsw.dungeon.entity.meta;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.util.SAM;

public abstract class MovableEntity<T> extends Entity implements Movable<T> {

	protected ArrayList<SAM<T>> moveIntentChecks;

	public MovableEntity(Dungeon dungeon, EntityLevel entityLevel, int x, int y) {
		super(dungeon, entityLevel, x, y);
		this.moveIntentChecks = new ArrayList<SAM<T>>();
	}

	@Override
	public void addMoveIntentCheck(SAM<T> function) {
		if (this.moveIntentChecks.contains(function)) {
			return;
		}
		this.moveIntentChecks.add(function);
	}

	@Override
	public void removeMoveIntentCheck(SAM<T> function) {
		this.moveIntentChecks.remove(function);
	}

}
