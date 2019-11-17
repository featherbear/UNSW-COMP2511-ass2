package unsw.dungeon.entity;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
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
	
	private EnemyMovementBehaviour state;

	public final IntentSAM<Player, LocationChanged> playerMoveIntentHandler;
	public final EventSAM<Player, LocationChanged> playerMoveEventHandler;

	public Enemy(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.OBJECT, x, y);
		this.isAlive = new SimpleBooleanProperty(true);
		
		roam = new RoamBehaviour(this);
		flee = new FleeBehaviour(this);
		state = roam;

		this.playerMoveEventHandler = (player, event) -> {
			if (player.hasItemUsable(InvincibilityPotion.class)) {
				setState(flee);
			} else {
				setState(roam);
			}
			state.move(player);
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

	public void roam(Player p) {
		state.move(p);
	}
	
	public void flee(Player p) {
		state.move(p);
	}
	
	public void setState(EnemyMovementBehaviour s) {
		this.state = s;
	}
	
	public EnemyMovementBehaviour getRoamState() {
		return roam;
	}
	
	public EnemyMovementBehaviour getfleeState() {
		return flee;
	}
	
	public EnemyMovementBehaviour getState() {
		return state;
	}

	@Override
	public boolean interact(Entity entity) {
		if (!isAlive.get()) return true;
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
