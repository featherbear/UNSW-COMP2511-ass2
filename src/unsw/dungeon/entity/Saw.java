package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.events.LocationChanged;

public class Saw extends MovableEntity<Saw> implements Interactable {

	private SawState state;
	private SawState horizontal;
	private SawState vertical;
	
	
	public Saw(Dungeon dungeon, int x, int y, String orientation) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		horizontal = new horizontalState(this);
		vertical = new verticalState(this);
		if (orientation.equals("H")) {
			state = horizontal;
		} else if (orientation.equals("V")) {
			state = vertical;
		} 
		
	}
	
	private boolean move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return false;
		}

		if (isPositionBlocked(newX, newY)) {
			Entity obstruction = getDungeon().getEntityAt(EntityLevel.OBJECT, newX, newY);
			if (obstruction instanceof Enemy) {
				((Enemy) obstruction).kill();
				this.setXY(newX, newY);
				return true;
			} else if (obstruction instanceof Player) {
				((Player) obstruction).interact(this);
				this.setXY(newX, newY);
				return true;
			} else if (obstruction instanceof Saw) {
				this.setXY(newX, newY);
				return true;
			}
			return false;
		}
		
		this.setXY(newX, newY);
		return true;
	}
	
	public boolean moveUp() {
		return move(0, -1);
	}

	public boolean moveDown() {
		return move(0, 1);
	}

	public boolean moveLeft() {
		return move(-1, 0);
	}

	public boolean moveRight() {
		return move(1, 0);
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			if (!((Player) entity).hasItemUsable(InvincibilityPotion.class)) {
				((Player) entity).kill();
				return true;
			}
		} else if (entity instanceof Enemy) {
			((Enemy) entity).kill();
			return true;
		}
		return false;
	}
	
	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}
		return player.interact(this);
	}
	
	public void playerMoveEventHandler(Player player, LocationChanged event) {
		state.move(event);
	}

}
