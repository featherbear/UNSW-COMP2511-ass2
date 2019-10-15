package unsw.dungeon.entity.meta;

import unsw.dungeon.util.SAM;

public interface Movable<T> {
//	public void broadcastMoveIntent();

	public boolean checkMoveIntent(int newX, int newY);

	public void addMoveIntentCheck(SAM<T> function);

	public void removeMoveIntentCheck(SAM<T> function);

	// Moved event?
}
