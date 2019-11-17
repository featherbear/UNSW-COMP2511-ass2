package unsw.dungeon.enemy;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.InvincibilityPotion;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.entity.meta.ItemEntity;
import unsw.dungeon.entity.meta.MovableEntity;
import unsw.dungeon.entity.meta.Usable;
import unsw.dungeon.events.LocationChanged;
import unsw.dungeon.util.emitter.EventSAM;
import unsw.dungeon.util.emitter.IntentSAM;

public class Enemy extends MovableEntity<Enemy> implements Interactable {

	private BooleanProperty isAlive;
	private State roam;
	private State flee;
	
	private State state;

	public final IntentSAM<Player, LocationChanged> playerMoveIntentHandler;
	public final EventSAM<Player, LocationChanged> playerMoveEventHandler;

	public Enemy(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.isAlive = new SimpleBooleanProperty(true);
		
		roam = new roamState(this);
		flee = new fleeState(this);
		state = roam;

		this.playerMoveEventHandler = (player, event) -> {
			state.move(player);
		};

		this.playerMoveIntentHandler = (player, event) -> {
			if (this.getX() != event.newX || this.getY() != event.newY) {
				return true;
			}

			return player.interact(this);
		};

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
			return false;
		}

		this.setXY(newX, newY);
		return true;
	}

	public void setXY(int newX, int newY) {
		int oldX = getX();
		int oldY = getY();
		if (!this.getDungeon().positionIsValid(newX, newY)) {
			return;
		}

		if (oldX != newX) {
			x().set(newX);
		}
		if (oldY != newY) {
			y().set(newY);
		}

		this.moveEvent.emit(new LocationChanged(oldX, oldY, newX, newY));

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

	public BooleanProperty alive() {
		return this.isAlive;
	}

	public boolean isAlive() {
		return alive().get();
	}

	public void kill() {
		this.isAlive.set(false);
		this.hide();
	}

	public void roam(Player p) {
		state.move(p);
	}
	
	public void flee(Player p) {
		state.move(p);
	}
	
	public void setState(State s) {
		this.state = s;
	}
	
	public State getRoamState() {
		return roam;
	}
	
	public State getfleeState() {
		return flee;
	}
	
	public State getState() {
		return state;
	}

	@Override
	public boolean interact(Entity entity) {
		if (entity instanceof Player) {
			Player p = (Player) entity;

			List<ItemEntity> inv = p.getInventory();
			for (ItemEntity item : inv) {
				if (item instanceof Usable) {
					boolean result = ((Usable) item).use(this);
					if (result) {
						return true;
					}
				}
			}

			p.kill();
		}

		return false;
	}

}
