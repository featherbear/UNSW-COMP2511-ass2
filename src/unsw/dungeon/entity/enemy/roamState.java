package unsw.dungeon.entity.enemy;

import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Player;

public class roamState implements State {

	Enemy enemy;
	
	public roamState(Enemy enemy) {
		this.enemy = enemy;
	}
	
	@Override
	public void move(Player p) {
		if (p.hasItemUsable(InvincibilityPotion.class)) {
			enemy.setState(enemy.getfleeState());
			enemy.getState().move(p);
			return;
		}
		
		int X = p.getX() - enemy.getX();
		int Y = p.getY() - enemy.getY();
		boolean moveSuccess = false;
		if (X > 0 && moveSuccess == false) {
			moveSuccess = enemy.moveRight();
		}

		if (X < 0 && moveSuccess == false) {
			moveSuccess = enemy.moveLeft();
		}

		if (Y > 0 && moveSuccess == false) {
			moveSuccess = enemy.moveDown();
		}

		if (Y < 0 && moveSuccess == false) {
			moveSuccess = enemy.moveUp();
		}

	}
}
