package unsw.dungeon.entity;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.enemy.Enemy;
import unsw.dungeon.entity.meta.Entity;
import unsw.dungeon.entity.meta.EntityLevel;
import unsw.dungeon.entity.meta.Interactable;
import unsw.dungeon.events.LocationChanged;

public class Portal extends Entity implements Interactable {

	private BooleanProperty activated;
	private int id;

	public Portal(Dungeon dungeon, int x, int y) {
		super(dungeon, EntityLevel.FLOOR, x, y);
		this.activated = new SimpleBooleanProperty(true);
	}

	@Override
	public boolean interact(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}

		if (!this.getActivated()) {
			return false;
		}

		Player player = (Player) entity;

		ArrayList<Portal> portals = Entity.filter(this.getDungeon().getEntities(), Portal.class);
		ArrayList<Portal> matchingPortals = new ArrayList<Portal>();

		for (Portal portal : portals) {
			if (portal == this) {
				continue;
			}
			if (portal.id == this.id) {
				matchingPortals.add(portal);
			}
		}

		if (matchingPortals.size() == 0) {
			System.out.println(String.format("No other portals with ID %d exist!", this.id));
			return false;
		}

		boolean teleportClear = false;
		Portal destination = null;

		// Check for clear destinations
		while (!teleportClear) {
			if (matchingPortals.size() == 0) {
				// All portals are blocked
				return false;
			}

			destination = matchingPortals.get(new Random().nextInt(matchingPortals.size()));

			if ((teleportClear = checkTeleportDestination(destination.getX(), destination.getY()))) {
				matchingPortals.remove(destination);
			}
		}

		// Teleport the player to the destination portal
		player.setXY(destination.getX(), destination.getY());

		return true;
	}

	private boolean checkTeleportDestination(int newX, int newY) {
		Entity obstruction = this.getDungeon().getEntityAt(EntityLevel.OBJECT, newX, newY);
		if (obstruction != null) {
			if (obstruction instanceof Enemy) {
				((Enemy) obstruction).kill();
			} else {
				return false;
			}
		}

		return true;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public BooleanProperty activated() {
		return this.activated;
	}

	public boolean getActivated() {
		return this.activated.get();
	}

	public void activate() {
		this.setActivated(true);
	}

	public void deactivate() {
		this.setActivated(false);
	}

	public void setActivated(boolean activated) {
		this.activated.set(activated);
	}

	public boolean playerMoveIntentHandler(Player player, LocationChanged event) {
		if (this.getX() != event.newX || this.getY() != event.newY) {
			return true;
		}

		return !player.interact(this);
	}

}
