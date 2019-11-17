package unsw.dungeon.entity;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.enemy.EnemyMovementBehaviour;
import unsw.dungeon.entity.enemy.FleeBehaviour;
import unsw.dungeon.entity.enemy.RoamBehaviour;
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
	private EnemyMovementBehaviour roam;
	private EnemyMovementBehaviour flee;

	private EnemyMovementBehaviour strategy;

	public final IntentSAM<Player, LocationChanged> playerMoveIntentHandler;
	public final EventSAM<Player, LocationChanged> playerMoveEventHandler;

	public Enemy(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.isAlive = new SimpleBooleanProperty(true);

		this.roam = new RoamBehaviour(this);
		this.flee = new FleeBehaviour(this);
		this.strategy = roam;

		this.playerMoveEventHandler = (player, event) -> {
			this.strategy.move(player);
		};

		this.playerMoveIntentHandler = (player, event) -> {
			if (this.getX() != event.newX || this.getY() != event.newY) {
				return true;
			}

			return player.interact(this);
		};

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

	@Override
	protected boolean move(int xDirection, int yDirection) {
		int oldX = getX();
		int oldY = getY();

		int newX = oldX + xDirection;
		int newY = oldY + yDirection;

		LocationChanged e = new LocationChanged(oldX, oldY, newX, newY);

		if (!this.moveIntent.emit(e)) {
			return false;
		}

		if (isPositionBlocked(newX, newY)) {
			Player p = this.getDungeon().getPlayer();

			if (p.getX() == newX && p.getY() == newY) {
				this.interact(p);
			} else {
				return false;
			}
		}

		this.setXY(newX, newY);
		return true;
	}

	public void setState(EnemyMovementBehaviour behaviour) {
		this.strategy = behaviour;
	}

	public void setRoam() {
		this.strategy = this.roam;
	}

	public void setFlee() {
		this.strategy = this.flee;
	}

	public EnemyMovementBehaviour getBehaviour() {
		return strategy;
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
