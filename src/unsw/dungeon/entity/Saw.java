package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.entity.saw.HorizontalBehaviour;
import unsw.dungeon.entity.saw.SawMovementBehaviour;
import unsw.dungeon.entity.saw.VerticalBehaviour;
import unsw.dungeon.events.LocationChanged;

public class Saw extends MovableEntity<Saw> implements Interactable {

	private SawMovementBehaviour state;
	private SawMovementBehaviour horizontal;
	private SawMovementBehaviour vertical;

	public Saw(Dungeon dungeon, int x, int y, String orientation) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.horizontal = new HorizontalBehaviour(this);
		this.vertical = new VerticalBehaviour(this);

		if (orientation.equals("H")) {
			this.state = horizontal;
		} else if (orientation.equals("V")) {
			this.state = vertical;
		}

	}

	@Override
	public boolean move(int xDirection, int yDirection) {
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
		this.state.move();
	}

}
