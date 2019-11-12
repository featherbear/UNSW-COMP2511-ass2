package unsw.dungeon.entity.enemies;

import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;

public class FleeBehaviour implements EnemyBehaviour {

	@Override
	public void move(Enemy e) {
		Player p = e.getDungeon().getPlayer();

		int X = p.getX() - e.getX();
		int Y = p.getY() - e.getY();
		boolean moveSuccess = false;
		if (X > 0 && moveSuccess == false) {
			moveSuccess = e.moveLeft();
		}

		if (X < 0 && moveSuccess == false) {
			moveSuccess = e.moveRight();
		}

		if (Y > 0 && moveSuccess == false) {
			moveSuccess = e.moveUp();
		}

		if (Y < 0 && moveSuccess == false) {
			moveSuccess = e.moveDown();
		}
	}
}
