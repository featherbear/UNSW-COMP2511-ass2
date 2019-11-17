package unsw.dungeon.enemy;

import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Player;

public class fleeState implements State{
	Enemy enemy;
	
	public fleeState(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public void move(Player p) {
		if (!p.hasItemUsable(InvincibilityPotion.class)) {
			enemy.setState(enemy.getState());
			enemy.getState().move(p);
			return;
		}
		
		int X = p.getX() - enemy.getX();
		int Y = p.getY() - enemy.getY();
		boolean moveSuccess = false;
		if (X > 0 && moveSuccess == false) {
			moveSuccess = enemy.moveLeft();
		}

		if (X < 0 && moveSuccess == false) {
			moveSuccess = enemy.moveRight();
		}

		if (Y > 0 && moveSuccess == false) {
			moveSuccess = enemy.moveUp();
		}

		if (Y < 0 && moveSuccess == false) {
			moveSuccess = enemy.moveDown();
		}
	}
}
