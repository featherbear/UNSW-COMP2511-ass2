package unsw.dungeon.entity.enemies;

import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;

public class RoamBehaviour implements EnemyBehaviour {

	@Override
	public void move(Enemy e) {
		Player p = e.getDungeon().getPlayer();

		int X = p.getX() - e.getX();
		int Y = p.getY() - e.getY();
		boolean moveSuccess = false;
		if (X > 0 && moveSuccess == false) {
			moveSuccess = e.moveRight();
		}

		if (X < 0 && moveSuccess == false) {
			moveSuccess = e.moveLeft();
		}

		if (Y > 0 && moveSuccess == false) {
			moveSuccess = e.moveDown();
		}

		if (Y < 0 && moveSuccess == false) {
			moveSuccess = e.moveUp();
		}

	}

}
